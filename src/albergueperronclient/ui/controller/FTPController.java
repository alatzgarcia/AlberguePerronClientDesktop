/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.ui.controller;

import albergueperronclient.exceptions.FTPException;
import albergueperronclient.logic.IFTP;
import albergueperronclient.logic.IFTPFactory;
import albergueperronclient.logic.ILogin;
import albergueperronclient.logic.ILoginFactory;
import albergueperronclient.modelObjects.MyFile;
import static albergueperronclient.ui.controller.GenericController.LOGGER;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Controller class for the FTP Client It contains event handlers and
 * initialization code for the view defined in BlackList.fmxl file.
 *
 * @author Nerea Jimenez
 */
public class FTPController extends GenericController {

    /**
     * The button to search for a local file
     */
    @FXML
    private Button btnSearch;
    /**
     * Path of the local file choosen
     */
    @FXML
    private Text txtFile;
    /**
     * Button to upload a file
     */
    @FXML
    private Button btnUpload;
    /**
     * Button to download a file
     */
    @FXML
    private Button btnDownload;
    /**
     * Button to delete a file
     */
    @FXML
    private Button btnDeleteF;
    /**
     * Button to delete a directory
     */
    @FXML
    private Button btnDeleteD;
    /**
     * Button to go back to the main view of the Admin
     */
    @FXML
    private Button btnBack;
    /**
     * Button to create a directory
     */
    @FXML
    private Button btnCrear;
    /**
     * Treeview for the files
     */
    @FXML
    private TreeView treeFile;
    @FXML
    private MenuItem menuGuests;
    @FXML
    private MenuItem menuPets;
    @FXML
    private MenuItem menuIncidences;
    @FXML
    private MenuItem menuStay;
    @FXML
    private MenuItem menuBlackList;
    @FXML
    private MenuItem menuLogOut;
    @FXML
    private MenuItem menuExit;

    private TreeItem<MyFile> rootItem;

    /**
     * Method for initializing FTP Stage.
     *
     * @param root The Parent object representing root node of view graph.
     */
    public void initStage(Parent root) throws IOException {

        //Create a scene associated to the node graph root.
        Scene scene = new Scene(root);
        //Associate scene to primaryStage(Window)
        Stage stage = new Stage();
        stage.setScene(scene);
        //Set window properties
        stage.setTitle("FTP");
        stage.setResizable(false);
        //Set window's events handlers
        stage.setOnShowing(this::handleWindowShowing);

        btnSearch.setOnAction(this::search);
        btnUpload.setOnAction(this::upload);
        btnDeleteF.setOnAction(this::delete);
        btnDownload.setOnAction(this::download);
        btnCrear.setOnAction(this::createDir);
        btnDeleteD.setOnAction(this::deleteDir);
        btnBack.setOnAction(this::goBack);
        menuGuests.setOnAction(this::openGuestsView);
        menuPets.setOnAction(this::openPetsView);
        menuStay.setOnAction(this::openStaysView);
        //menuBlackList.setOnAction(this::openBlackListView);
        menuLogOut.setOnAction(this::logOut);
        menuExit.setOnAction(this::exit);
        treeFile.getSelectionModel().selectedItemProperty().addListener(this::itemSelected);

        //Connect to the ftp server
        try {
            ftpManager.connect();
        } catch (FTPException ex) {
            LOGGER.severe(ex.getMessage());
            showErrorAlert("Error de conexión");
        }

        //Create the root file
        MyFile rootF = new MyFile();
        rootF.setPath("/");
        rootF.setFile(false);
        rootF.setName("root");
        rootItem = new TreeItem<MyFile>(rootF, new ImageView(new Image(getClass().getResourceAsStream("/albergueperronclient/root.png"))));
        rootItem.setExpanded(true);

        // set the tree root
        treeFile.setRoot(rootItem);

        // start building the file tree
        ftpManager.buildTree(rootItem);
        ftpManager.setTreeFile(treeFile);

        stage.setScene(scene);
        //Show primary window

        stage.show();

    }

    /**
     * Window event method handler. It implements behavior for WINDOW_SHOWING
     * type event.
     *
     * @param event The window event
     */
    private void handleWindowShowing(WindowEvent event) {

        btnUpload.setDisable(true);
        btnUpload.setMnemonicParsing(true);
        btnUpload.setText("_Subir");
        btnDeleteF.setDisable(true);
        btnDeleteF.setMnemonicParsing(true);
        btnDeleteF.setText("_Borrar fichero");
        btnDownload.setDisable(true);
        btnDownload.setMnemonicParsing(true);
        btnDownload.setText("_Descargar");
        btnCrear.setDisable(true);
        btnCrear.setMnemonicParsing(true);
        btnCrear.setText("_Crear directorio");
        btnDeleteD.setDisable(true);
        btnDeleteD.setMnemonicParsing(true);
        btnDeleteD.setText("_Borrar directorio");
        btnBack.setDisable(false);
        btnBack.setMnemonicParsing(true);
        btnBack.setText("_Volver");

    }

    /**
     * Action event handler for search button.
     *
     * @param event The ActionEvent object for the event.
     */
    public void search(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            //set the path of the local file
            txtFile.setText(selectedFile.getPath());
        }

    }

    /**
     * Action event handler for the upload button.
     *
     * @param event The ActionEvent object for the event.
     */
    public void upload(ActionEvent event) {
        try {
            //if there is local file selected
            if (!txtFile.getText().equals("")) {
                TreeItem<MyFile> itemSelected
                        = (TreeItem<MyFile>) treeFile.getSelectionModel().getSelectedItem();
                MyFile file = ftpManager.uploadFile(txtFile.getText());

                //create a treeitem with the new file
                TreeItem<MyFile> fileToUpload = new TreeItem<MyFile>(
                        file, new ImageView(new Image(getClass().getResourceAsStream("/albergueperronclient/file.png"))));
                //add it to the selected tree item
                itemSelected.getChildren().add(fileToUpload);
                txtFile.setText("");
                treeFile.refresh();
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "El archivo se ha subido correctamente");
                alert.show();
            } else {
                showErrorAlert("Elija un archivo local para subir");

            }
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
            showErrorAlert("Error en la subida");

        }

    }

    /**
     * Action event handler for the delete button.
     *
     * @param event The ActionEvent object for the event.
     */
    public void delete(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Quiere borrar el fichero?",
                    ButtonType.CANCEL, ButtonType.OK);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                TreeItem<MyFile> treeItem
                        = (TreeItem<MyFile>) treeFile.getSelectionModel().getSelectedItem();
                //get the parent tree item of the item selected
                TreeItem<MyFile> parent = treeItem.getParent();
                //delete the file
                ftpManager.deleteFile(treeItem.getValue().getName());
                //remove the tree item from the parent item
                parent.getChildren().remove(treeItem);
                treeFile.refresh();
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION,
                        "El archivo se ha borrado correctamente");
                alert2.show();

            }
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
            showErrorAlert("Error en borrado");

        }
    }

    /**
     * Action event handler for the download button.
     *
     * @param event The ActionEvent object for the event.
     */
    public void download(ActionEvent event) {
        try {
            TreeItem<MyFile> treeItem
                    = (TreeItem<MyFile>) treeFile.getSelectionModel().getSelectedItem();
            ftpManager.downloadFile(treeItem.getValue().getName());
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION,
                    "El archivo se ha descargado correctamente");
            alert2.show();

        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
            showErrorAlert("Error en la bajada");

        }

    }

    /**
     * Action event handler for the create directory button.
     *
     * @param event The ActionEvent object for the event.
     */
    public void createDir(ActionEvent event) {
        try {
            TreeItem<MyFile> itemSelected
                    = (TreeItem<MyFile>) treeFile.getSelectionModel().getSelectedItem();

            MyFile file = ftpManager.createDirectory();
            //Create a tree item with the new directory
            TreeItem<MyFile> directoryToCreate = new TreeItem<MyFile>(file, new ImageView(new Image(getClass().
                    getResourceAsStream("/albergueperronclient/root.png"))));
            //add the new tree item to the item selected
            itemSelected.getChildren().add(directoryToCreate);
            treeFile.refresh();
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION,
                    "El directorio se ha creado correctamente");
            alert2.show();

        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
            showErrorAlert("Error en la creación del directorio");

        }

    }

    /**
     * Action event handler for the delete directory button.
     *
     * @param event The ActionEvent object for the event.
     */
    public void deleteDir(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Quiere borrar el directorio?",
                    ButtonType.CANCEL, ButtonType.OK);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                TreeItem<MyFile> treeItem
                        = (TreeItem<MyFile>) treeFile.getSelectionModel().getSelectedItem();
                TreeItem<MyFile> parent = treeItem.getParent();
                //delete the directory
                ftpManager.deleteDirectory(treeItem.getValue().getPath());
                //remove the tree item
                parent.getChildren().remove(treeItem);
                treeFile.refresh();
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION,
                        "El directorio se ha borrado correctamente");
                alert2.show();

            }
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
            showErrorAlert("Error en borrado del directorio");

        }

    }

    /**
     * Handler for the item selected event
     *
     * @param observable the observable value
     * @param oldValue the old value
     * @param newValue the new value
     */
    private void itemSelected(ObservableValue observable,
            Object oldValue,
            Object newValue) {

        if (newValue == null) {
            //if an item is deselected the buttons are disabled
            btnCrear.setDisable(true);
            btnDeleteD.setDisable(true);
            btnUpload.setDisable(true);
            btnDownload.setDisable(true);
            btnDeleteF.setDisable(true);
        } else {
            //get the selected item
            TreeItem<MyFile> selectedItem = (TreeItem<MyFile>) newValue;
            //get the path
            String path = selectedItem.getValue().getPath();
            ftpManager.changeDirectory(path);
            if (selectedItem.getValue().isFile()) {
                btnCrear.setDisable(true);
                btnDeleteD.setDisable(true);
                btnUpload.setDisable(true);
                btnDownload.setDisable(false);
                btnDeleteF.setDisable(false);

            } else if (!selectedItem.getValue().isFile()) {
                btnDownload.setDisable(true);
                btnDeleteF.setDisable(true);
                btnCrear.setDisable(false);
                btnDeleteD.setDisable(false);
                btnUpload.setDisable(false);
            }

        }
    }

    /**
     * Action event handler for the go back button.
     *
     * @param event The ActionEvent object for the event.
     */
    public void goBack(ActionEvent event) {

        ftpManager.disconnect();
        stage.hide();

    }

    public void openGuestsView(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/albergueperronclient/ui/fxml/UIGuest.fxml"));

        try {
            Parent root = loader.load();

            //UsersManager usersManager = UserManagerFactory.createUserManager();
            //Get controller from the loader
            //UIGuestFXMLController loggedController = loader.getController();
            //Initialize the primary stage of the application
            //loggedController.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(UILogguedFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void openPetsView(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/albergueperronclient/ui/fxml/UIPet.fxml"));

        try {
            Parent root = loader.load();

        } catch (IOException ex) {
            Logger.getLogger(UILogguedFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void openStaysView(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/albergueperronclient/ui/fxml/UIStay.fxml"));

        try {
            Parent root = loader.load();

            //UsersManager usersManager = UserManagerFactory.createUserManager();
            //Get controller from the loader
            UIStayFXMLController stayController = loader.getController();

            //Initialize the primary stage of the application
            //stayController.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(UILogguedFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void openFTPView(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/albergueperronclient/ui/fxml/UIBlackList.fxml"));

        IFTP ftpManager = IFTPFactory.getIFTPImplementation();

        try {
            Parent root = loader.load();

            //UsersManager usersManager = UserManagerFactory.createUserManager();
            //Get controller from the loader
            FTPController ftpController = loader.getController();
            ftpController.setFtpManager(ftpManager);

            //Initialize the primary stage of the application
            ftpController.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(UILogguedFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void logOut(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cerrar Sesión");
            alert.setContentText("¿Desea cerrar sesion?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                stage.close();
                try {
                    //Get the logic manager object for the initial stage
                    ILogin loginManager = ILoginFactory.getLoginManager();

                    //Load the fxml file
                    FXMLLoader loader = new FXMLLoader(getClass()
                            .getResource("/albergueperronclient/ui/fxml/UILogin.fxml"));
                    Parent root = loader.load();
                    //Get controller from the loader
                    UILoginFXMLController loginController = loader.getController();
                    /*Set a reference in the controller for the UILogin view for the logic manager object           
                     */
                    loginController.setLoginManager(loginManager);
                    //Set a reference for Stage in the UILogin view controller
                    loginController.setStage(stage);
                    //Initialize the primary stage of the application
                    loginController.initStage(root);

                } catch (Exception e) {
                    LOGGER.severe(e.getMessage());
                }
            } else {
                LOGGER.info("Logout cancelled.");
            }
        } catch (Exception ex) {
            LOGGER.severe(ex.getMessage());
        }
    }

    /**
     * Method to exit the application
     *
     * @param event event that has caused the call to the function
     */
    public void exit(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cerrar aplicación");
            alert.setContentText("¿Desea salir de la aplicación?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                LOGGER.info("Exiting the application.");
                stage.close();
                Platform.exit();
            } else {
                LOGGER.info("Exit cancelled.");
            }
        } catch (Exception ex) {
            LOGGER.severe(ex.getMessage());
            showErrorAlert("Error al intentar cerrar la aplicación.");
        }
    }

}
