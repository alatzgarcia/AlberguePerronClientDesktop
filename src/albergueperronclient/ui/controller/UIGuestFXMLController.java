/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.ui.controller;

import albergueperronclient.exceptions.BusinessLogicException;
import albergueperronclient.logic.BlackListManagerFactory;
import albergueperronclient.logic.ILogin;
import albergueperronclient.logic.ILoginFactory;
import albergueperronclient.logic.IncidentManager;
import albergueperronclient.logic.IncidentManagerFactory;
import albergueperronclient.logic.PetManagerFactory;
import albergueperronclient.logic.RoomManager;
import albergueperronclient.logic.RoomManagerFactory;
import albergueperronclient.logic.StayManagerFactory;
import albergueperronclient.logic.UserManagerFactory;
import albergueperronclient.logic.UsersManager;
import albergueperronclient.modelObjects.IncidentBean;
import albergueperronclient.modelObjects.Privilege;
import albergueperronclient.modelObjects.UserBean;
import static albergueperronclient.ui.controller.GenericController.LOGGER;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.lang.String;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.DatatypeConverter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author Diego
 */
public class UIGuestFXMLController extends GenericController {

    //FXML auto-generated
    @FXML
    private TableView<UserBean> tableGuest;
    @FXML
    private TableColumn columnName;
    @FXML
    private TableColumn columnDni;
    @FXML
    private Button btnNewGuest;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtFirstSurname;
    @FXML
    private TextField txtSecondSurname;
    @FXML
    private TextField txtDni;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSaveChanges;
    @FXML
    private Button btnDeleteGuest;
    @FXML
    private Button btnModifyGuest;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtLogin;
    @FXML
    private Button btnInsertGuest;
    @FXML
    private Button btnReturn;
    @FXML
    private Button btnReport;
    @FXML
    private MenuItem menuGuest;
    @FXML
    private MenuItem menuPet;
    @FXML
    private MenuItem menuIncidences;
    @FXML
    private MenuItem menuStay;
    @FXML
    private MenuItem menuRoom;
    @FXML
    private MenuItem menuBlackList;
    @FXML
    private MenuItem menuLogOut;
    @FXML
    private MenuItem menuExit;
    //attributes
    private ObservableList<UserBean> usersData;
    private UserBean user;
    private int visible = 1;
    private int invisible = 2;
    private int enable = 3;
    private int disable = 4;
    private int clean = 5;

    /**
     * Initializes the controller class.
     */
    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Huespedes");
        stage.setResizable(false);

        stage.setOnShowing(this::handleWindowShowing);

        //The listeners for the fields
        txtDni.textProperty().addListener(this::onTextChanged);
        txtEmail.textProperty().addListener(this::onTextChanged);
        txtFirstSurname.textProperty().addListener(this::onTextChanged);
        txtSecondSurname.textProperty().addListener(this::onTextChanged);
        txtLogin.textProperty().addListener(this::onTextChanged);
        txtName.textProperty().addListener(this::onTextChanged);

        //Sets the button methods when they are clicked
        btnReturn.setOnAction(this::returnToMenu);
        btnCancel.setOnAction(this::cancel);
        btnNewGuest.setOnAction(this::newGuest);
        btnInsertGuest.setOnAction(this::saveNewGuest);
        btnSaveChanges.setOnAction(this::saveUpdateGuest);
        btnModifyGuest.setOnAction(this::updateGuest);
        btnDeleteGuest.setOnAction(this::deleteUser);
        //REPORT
        btnReport.setOnAction(this::generateReport);

        menuGuest.setOnAction(this::goToGuestsView);
        menuPet.setOnAction(this::goToPetsView);
        menuStay.setOnAction(this::goToStaysView);
        menuBlackList.setOnAction(this::goToBlackListView);
        menuRoom.setOnAction(this::goToRoomView);
        menuIncidences.setOnAction(this::goToIncidentView);
        menuLogOut.setOnAction(this::logOut);
        menuExit.setOnAction(this::exit);

        //Sets the columns the attributes to use
        columnDni.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("fullname"));

        try {
            //Create an observable lis for the users
            usersData = FXCollections.observableArrayList(usersManager.findUsersByPrivilege(Privilege.USER));
        } catch (BusinessLogicException ex) {
            LOGGER.severe(ex.getMessage());
        }

        //Set the observable data
        tableGuest.setItems(usersData);

        //Sets the selection listener
        tableGuest.getSelectionModel().selectedItemProperty().addListener(this::handleUserTableFocus);

        stage.show();

    }

    /**
     * Sets the buttons and fields that will be visible, disable or editable at
     * the start of the window
     *
     * @param event WindowEvent: The listeadminner of the window
     */
    public void handleWindowShowing(WindowEvent event) {
        btnCancel.setDisable(true);
        btnDeleteGuest.setDisable(true);
        btnNewGuest.setDisable(false);
        btnReturn.setDisable(false);
        btnInsertGuest.setVisible(false);
        btnSaveChanges.setVisible(false);
        btnModifyGuest.setDisable(true);

        fieldChange(invisible);
        menuGuest.setDisable(true);
    }

    /**
     * Controls the buttons and the fields when the focus of the table changes
     *
     * @param observable ObservableValue:
     * @param oldValue Object: The old value of the object modified
     * @param newValue Object: The new value of the object modified
     */
    public void handleUserTableFocus(ObservableValue observable, Object oldValue, Object newValue) {
        fieldChange(visible);
        fieldChange(disable);
        btnSaveChanges.setDisable(true);
        btnSaveChanges.setVisible(false);
        btnInsertGuest.setDisable(true);
        btnInsertGuest.setVisible(false);
        if (newValue != null) {
            UserBean user = (UserBean) newValue;
            txtDni.setText(user.getId());
            txtEmail.setText(user.getEmail());
            txtFirstSurname.setText(user.getSurname1());
            txtLogin.setText(user.getLogin());
            txtName.setText(user.getName());
            txtSecondSurname.setText(user.getSurname2());

            //Enables the correspondent buttons
            btnDeleteGuest.setDisable(false);
            btnNewGuest.setDisable(false);
            btnModifyGuest.setDisable(false);
        } else {
            btnDeleteGuest.setDisable(true);
            btnModifyGuest.setDisable(true);
        }
    }

    /**
     * Returns to the last window
     *
     * @param event ActionEvent The listenr of the button
     */
    public void returnWindow(ActionEvent event) {
        LOGGER.info("Entra en el return");
        stage.close();
    }

    /**
     * Enables the buttons, comboboxes and DatePicker for the update.
     *
     * @param event ActionEvent: The listener of the button
     */
    public void updateGuest(ActionEvent event) {
        //Enable the buttons needed to modify a guest
        btnCancel.setDisable(false);
        btnSaveChanges.setVisible(true);
        btnSaveChanges.setDisable(true);
        btnSaveChanges.toFront();
        tableGuest.setDisable(true);

        btnDeleteGuest.setDisable(true);
        btnNewGuest.setDisable(true);
        btnModifyGuest.setDisable(true);

        //Enables all the fields
        fieldChange(visible);
        fieldChange(enable);
        txtDni.setDisable(true);
    }

    /**
     * Enables the buttons, comboboxes and DatePicker for the new stay.
     *
     * @param event ActionEvent: The listener of the button
     */
    public void newGuest(ActionEvent event) {
        // Enable the buttons needed to create a new guest
        btnCancel.setDisable(false);
        btnInsertGuest.setVisible(true);
        btnInsertGuest.setDisable(true);
        btnModifyGuest.setDisable(true);
        btnDeleteGuest.setDisable(true);
        btnNewGuest.setDisable(true);
        tableGuest.setDisable(true);
        // Enables all the fields
        fieldChange(enable);
        fieldChange(visible);
        fieldChange(clean);

    }

    /**
     * Method that controls the fields will be inserted
     *
     * @param change int: The valor of wanted modification of the fields
     */
    public void fieldChange(int change) {
        switch (change) {
            case 1:
                //Sets visbles all the fields of the window
                txtDni.setVisible(true);
                txtEmail.setVisible(true);
                txtFirstSurname.setVisible(true);
                txtLogin.setVisible(true);
                txtName.setVisible(true);
                txtSecondSurname.setVisible(true);
                break;
            case 2:
                //Sets invisible all the fields of the window
                txtDni.setVisible(false);
                txtEmail.setVisible(false);
                txtFirstSurname.setVisible(false);
                txtLogin.setVisible(false);
                txtName.setVisible(false);
                txtSecondSurname.setVisible(false);
                break;
            case 3:
                //Enables the fields
                txtDni.setDisable(false);
                txtEmail.setDisable(false);
                txtFirstSurname.setDisable(false);
                txtLogin.setDisable(false);
                txtName.setDisable(false);
                txtSecondSurname.setDisable(false);
                txtDni.setEditable(true);
                txtEmail.setEditable(true);
                txtFirstSurname.setEditable(true);
                txtLogin.setEditable(true);
                txtName.setEditable(true);
                txtSecondSurname.setEditable(true);
                break;
            case 4:
                //Disables the fields
                txtDni.setDisable(true);
                txtEmail.setDisable(true);
                txtFirstSurname.setDisable(true);
                txtLogin.setDisable(true);
                txtName.setDisable(true);
                txtSecondSurname.setDisable(true);
                break;
            case 5:
                //Deletes all the existing data
                txtDni.setText("");
                txtEmail.setText("");
                txtFirstSurname.setText("");
                txtLogin.setText("");
                txtName.setText("");
                txtSecondSurname.setText("");
                break;
        }
    }

    /**
     * Creates a Guest with the inserted data
     *
     * @param event ActionEvent: The listener of the button
     */
    private void saveNewGuest(ActionEvent event) {
        try {
            usersManager.createUser(getUserFromFieldsCreate());
            tableGuest.getItems().add(user);
            tableGuest.refresh();
            Alert alert = new Alert(Alert.AlertType.INFORMATION.INFORMATION);
            alert.setTitle("Crear usuario");
            alert.setContentText("El usuario fue creado");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                tableGuest.getSelectionModel().clearSelection();
                tableGuest.setDisable(true);
                finishOperation();
                alert.close();
            }
        } catch (BusinessLogicException ble) {
            LOGGER.info("The create failed " + ble.getMessage());
        } catch (Exception e) {
            LOGGER.info("The create failed " + e.getMessage());
        }
    }

    /**
     * Updates a Guest with the inserted data
     *
     * @param event ActionEvent: The listener of the button
     */
    private void saveUpdateGuest(ActionEvent event) {
        LOGGER.info("SE HACE EL BOTON DE UPDATE");
        LOGGER.info("Entrando en save update guest");
        //Updates the user
        try {
            usersManager.updateUser(getUserFromFieldsUpdate());
            tableGuest.getItems().remove(tableGuest.getSelectionModel().getSelectedItem());
            tableGuest.getItems().add(user);
            tableGuest.refresh();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION.INFORMATION);
            alert.setTitle("Modificado");
            alert.setContentText("Se modificó el usuario ");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                tableGuest.getSelectionModel().clearSelection();
                tableGuest.setDisable(true);
                finishOperation();
                alert.close();
            }
        } catch (BusinessLogicException ble) {
            LOGGER.info("The update failed " + ble.getMessage());
        }
    }

    /**
     * The method that cancels the creating or updating data insert
     *
     * @param event ActionEvent: The listener of the button
     */
    public void cancel(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION.CONFIRMATION);
            alert.setTitle("Cancelar");
            alert.setContentText("¿Desea cancelar la operación?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                finishOperation();
            } else if (result.get() == ButtonType.CANCEL) {
                alert.close();
            }
        } catch (Exception ex) {
            LOGGER.severe(ex.getMessage());
        }
    }

    /**
     * This method return all the buttons to the initial status
     */
    public void finishOperation() {
        fieldChange(invisible);
        fieldChange(clean);
        btnCancel.setDisable(true);
        btnModifyGuest.setDisable(true);
        btnSaveChanges.setVisible(false);
        btnInsertGuest.setVisible(false);
        btnSaveChanges.setDisable(true);
        btnInsertGuest.setDisable(true);
        btnNewGuest.setDisable(false);
        tableGuest.getSelectionModel().clearSelection();
        tableGuest.setDisable(false);
    }

    /**
     * Gets all the from the fields and it put them into a UserBean Object for
     * the update operation
     *
     * @return user
     */
    private UserBean getUserFromFieldsUpdate() {
        user = new UserBean();

        //Sets the attributes with the fields
        user.setEmail(txtEmail.getText().toString());
        user.setLogin(txtLogin.getText().toString());
        user.setName(txtName.getText().toString());
        user.setSurname1(txtFirstSurname.getText().toString());
        user.setSurname2(txtSecondSurname.getText().toString());
        user.setId(txtDni.getText().toString());

        //Gets the unmodificable attributes
        user.setPassword(tableGuest.getSelectionModel().getSelectedItem().getPassword());
        user.setLastPasswordChange(tableGuest.getSelectionModel().getSelectedItem().getLastPasswordChange());

        user.setPrivilege(Privilege.USER);

        return user;
    }

    /**
     * Gets all the from the fields and it put them into a UserBean Object for
     * the create operation
     *
     * @return user
     */
    private UserBean getUserFromFieldsCreate() {
        user = new UserBean();

        //Sets the attributes with the fields
        user.setEmail(txtEmail.getText().toString());
        user.setLogin(txtLogin.getText().toString());
        user.setName(txtName.getText().toString());
        user.setSurname1(txtFirstSurname.getText().toString());
        user.setSurname2(txtSecondSurname.getText().toString());
        user.setId(txtDni.getText().toString());
        user.setPrivilege(Privilege.USER);

        //DatatypeConverter.parseHexBinary(password);
        Date lastPasswordChange = new Date();
        user.setLastPasswordChange(lastPasswordChange);

        return user;
    }

    /**
     * Deletes the selected entire guest
     *
     * @param event ActionEvent: The listener of the button
     */
    private void deleteUser(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION.CONFIRMATION);
            alert.setTitle("Borrar");
            alert.setContentText("¿Desea borrar el usuario?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                usersManager.deleteUser(tableGuest.getSelectionModel().getSelectedItem().getId());
                tableGuest.getItems().remove(tableGuest.getSelectionModel().getSelectedItem());
                tableGuest.refresh();
            } else if (result.get() == ButtonType.CANCEL) {
                alert.close();
            }
        } catch (BusinessLogicException ble) {
            LOGGER.info("Delete failed " + ble.getMessage());
        } catch (Exception e) {
            LOGGER.info("Error" + e.getMessage());
        }
    }

    /**
     * A listener that checks the fields everytime one of them is modified
     *
     * @param observable ObservableValue The listener of the fields
     * @param oldValue String The old value of the field
     * @param newValue String The new value of the field
     */
    public void onTextChanged(ObservableValue observable,
            String oldValue,
            String newValue) {
        if (txtDni.getText().trim().length() != 9
                || (!txtEmail.getText().matches("^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$"))
                || txtSecondSurname.getText().trim().length() < fullNameMinLength || txtSecondSurname.getText().trim().length() > fullNameMaxLength
                || txtFirstSurname.getText().trim().length() < fullNameMinLength || txtFirstSurname.getText().trim().length() > fullNameMaxLength
                || txtLogin.getText().trim().length() < fullNameMinLength || txtLogin.getText().trim().length() > fullNameMaxLength
                || txtName.getText().trim().length() < fullNameMinLength || txtName.getText().trim().length() > fullNameMaxLength) {

            if (btnSaveChanges.isVisible()) {
                btnSaveChanges.setDisable(true);
            }
            if (btnInsertGuest.isVisible()) {
                btnInsertGuest.setDisable(true);
            }
        } else if (txtDni.getText().trim().length() == 9
                && txtEmail.getText().matches("^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$")
                && txtSecondSurname.getText().trim().length() >= fullNameMinLength && txtSecondSurname.getText().trim().length() < fullNameMaxLength
                && txtFirstSurname.getText().trim().length() >= fullNameMinLength && txtFirstSurname.getText().trim().length() < fullNameMaxLength
                && txtLogin.getText().trim().length() >= fullNameMinLength && txtLogin.getText().trim().length() < fullNameMaxLength
                && txtName.getText().trim().length() >= fullNameMinLength && txtName.getText().trim().length() < fullNameMaxLength) {

            if (btnSaveChanges.isVisible()) {
                btnSaveChanges.setDisable(false);
            }
            if (btnInsertGuest.isVisible()) {
                btnInsertGuest.setDisable(false);
            }
        }
    }

    /**
     * Returns to the menu view
     *
     * @param event
     */
    public void returnToMenu(ActionEvent event) {

        try {
            stage.close();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            showErrorAlert("Error al redirigir al menú.");
        }

    }

    /*public void returnToPrevious(ActionEvent event){
        stage.close();
        previousStage.show();
    }*/
    /**
     * Logs out, sending the user to the login view
     *
     * @param event
     */
    public void logOut(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION.CONFIRMATION);
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
     * Exit from the application
     *
     * @param event
     */
    public void exit(ActionEvent event) {
        Platform.exit();
    }

    /**
     * Opens the guest view
     *
     * @param event
     */
    public void goToGuestsView(ActionEvent event) {
        //calls the logicManager register functio
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/albergueperronclient/ui/fxml/UIGuest.fxml"));
            Parent root = loader.load();
            //Get controller from the loader
            UIGuestFXMLController guestController = loader.getController();

            guestController.setUsersManager(UserManagerFactory.createUserManager());
            //Send the current stage for coming back later
            guestController.setPreviousStage(stage);
            //Initialize the primary stage of the application
            guestController.initStage(root);
            stage.close();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            showErrorAlert("Error al redirigir a la vista de huéspedes.");
        }
    }

    /**
     * Opens the pet view
     *
     * @param event
     */
    public void goToPetsView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/albergueperronclient/ui/fxml/UIPet.fxml"));
            Parent root = loader.load();
            //Get controller from the loader
            UIPetFXMLController petController = loader.getController();

            petController.setPetsManager(PetManagerFactory.createPetManager());
            //Send the current stage for coming back later
            petController.setPreviousStage(stage);
            //Initialize the primary stage of the application
            petController.initStage(root);
            stage.close();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            showErrorAlert("Error al redirigir a la vista de mascotas.");
        }
    }

    /**
     * Opens the stay view
     *
     * @param event
     */
    public void goToStaysView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/albergueperronclient/ui/fxml/UIStay.fxml"));
            Parent root = loader.load();
            //Get controller from the loader
            UIStayFXMLController stayController = loader.getController();

            stayController.setStaysManager(StayManagerFactory.createStayManager());
            stayController.setPreviousStage(stage);
            //Initialize the primary stage of the application
            stayController.initStage(root);
            stage.close();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            showErrorAlert("Error al redirigir a la vista de estancias.");
        }
    }

    /**
     * Opens the blacklist view
     *
     * @param event
     */
    public void goToBlackListView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/albergueperronclient/ui/fxml/UIBlackList.fxml"));
            Parent root = loader.load();
            //Get controller from the loader
            BlackListFXMLController blackListController = loader.getController();

            blackListController.setLogicManager(
                    BlackListManagerFactory.getBlackListManager(), UserManagerFactory.createUserManager());
            //Send the current stage for coming back later
            blackListController.setPreviousStage(stage);
            //Initialize the primary stage of the application
            blackListController.initStage(root);
            stage.close();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            showErrorAlert("Error al redirigir a la vista de la lista negra.");
        }
    }

    /**
     * Opens the room view
     *
     * @param event
     */
    public void goToRoomView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/albergueperronclient/ui/fxml/Room.fxml"));
            Parent root = loader.load();
            //Get controller from the loader
            RoomFXMLController roomController = loader.getController();

            roomController.setLogicManager(RoomManagerFactory.getRoomManager());
            //Send the current stage for coming back later
            //roomController.setPreviousStage(stage);
            //Initialize the primary stage of the application
            roomController.initStage(root);
            //--TOFIX --> Decidir si esconder el stage o cerrarlo
            stage.close();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            showErrorAlert("Error al redirigir a la vista de habitaciones.");
        }
    }

    /**
     * Opens the incident view
     *
     * @param event
     */
    public void goToIncidentView(ActionEvent event) {
        try {
            //Get the logic manager object for the initial stage
            IncidentManager incidentManager = IncidentManagerFactory.getIncidentManager();
            UsersManager userManager = UserManagerFactory.createUserManager();
            RoomManager roomManager = RoomManagerFactory.getRoomManager();

            //Load the fxml file
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/albergueperronclient/ui/fxml/Incident.fxml"));
            Parent root = loader.load();
            //Get controller from the loader
            IncidentFXMLController incidentController = loader.getController();
            incidentController.setLogicManager(incidentManager, roomManager,
                    userManager);
            //Send the current stage for coming back later
            //incidentController.setPreviousStage(stage);
            //Initialize the primary stage of the application
            incidentController.initStage(root);
            stage.close();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            showErrorAlert("Error al redirigir a la vista de incidencias.");
        }
    }

    public void generateReport(ActionEvent event) {
        try {
            JasperReport report
                    = JasperCompileManager.compileReport(getClass()
                            .getResourceAsStream("/albergueperronclient/ui/report/UserReport.jrxml"));
            //Data for the report: a collection of UserBean passed as a JRDataSource 
            //implementation 
            JRBeanCollectionDataSource dataItems
                    = new JRBeanCollectionDataSource((Collection<UserBean>) this.tableGuest.getItems());
            //Map of parameter to be passed to the report
            Map<String, Object> parameters = new HashMap<>();
            //Fill report with data
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            //Create and show the report window. The second parameter false value makes 
            //report window not to close app.
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
            // jasperViewer.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        } catch (JRException ex) {
            //If there is an error show message and
            //log it.
            showErrorAlert("Error al imprimir:\n"
                    + ex.getMessage());
            LOGGER.log(Level.SEVERE,
                    "UI GestionUsuariosController: Error printing report: {0}",
                    ex.getMessage());
        }
    }

}
