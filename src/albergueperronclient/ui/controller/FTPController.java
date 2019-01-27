/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.ui.controller;

import albergueperronclient.exceptions.FTPException;
import albergueperronclient.logic.IFTPImplementation;
import albergueperronclient.modelObjects.MyFile;
import static albergueperronclient.ui.controller.GenericController.LOGGER;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.T;
import javafx.scene.input.MouseEvent;

import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;


/**

 * @author Nerea Jimenez
 */
public class FTPController extends GenericController{
   
    
    @FXML
    private Button btnSearch;
    @FXML
    private Text txtFile;
    @FXML
    private Button btnUpload;
    @FXML
    private Button btnDownload;
    @FXML
    private Button btnDeleteF;
    @FXML
    private Button btnDeleteD;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnCrear;
    @FXML
    private TreeView treeFile;
    @FXML
    private TreeTableColumn columnFile;
    
    private TreeItem<MyFile> rootItem;
    private Node rootIcon1 =  new ImageView(new Image(getClass().getResourceAsStream
            ("/albergueperronclient/root.png")));
    private Node rootIcon2 =  new ImageView(new Image(getClass().getResourceAsStream
            ("/albergueperronclient/file.png"))); 
    
   
    /**
     * Method for initializing Login Stage. 
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
               
        treeFile.getSelectionModel().selectedItemProperty().addListener(this::itemSelected);
        
      
        try {
            ftpManager.connect();
        } catch (FTPException ex) {
            LOGGER.severe(ex.getMessage());
            showErrorAlert("Error de conexión");
        }
        
        MyFile rootF = new MyFile();
        rootF.setPath("/");
        rootF.setFile(false);
        rootF.setName("root");
        rootItem = new TreeItem<MyFile>(rootF,rootIcon1);
        rootItem.setExpanded(true);

        // set the tree root
        treeFile.setRoot(rootItem);

        // start building the file tree
        ftpManager.buildFileTree(rootItem);
        ftpManager.setTreeFile(treeFile);
        
        stage.setScene(scene);
        //Show primary window
        
        //ftpManager.disconnect(ftp);
        stage.show();
        

    }
    /**
     * Window event method handler. It implements behavior for WINDOW_SHOWING type 
     * event.
     * @param event  The window event 
     */
    private void handleWindowShowing(WindowEvent event){
        
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
    
    public void search(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Text Files", "*.txt"),
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
            new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
            new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            txtFile.setText(selectedFile.getPath());
            
                
        }
       
    }
    
    public void upload(ActionEvent event){
        if(!txtFile.getText().equals("")){
            TreeItem<MyFile> itemSelected = (TreeItem<MyFile>) treeFile.getSelectionModel().getSelectedItem();
            MyFile file=ftpManager.uploadFile(txtFile.getText());
            TreeItem<MyFile> fileToUpload = new TreeItem<MyFile>(file,rootIcon2);
            itemSelected.getChildren().add(fileToUpload);
            txtFile.setText("");
            treeFile.refresh();
        }else{
           Alert alert = new Alert(Alert.AlertType.INFORMATION,"Elija un archivo local para subir");
           alert.show();
           
        }
       
        
    }
      
      public void delete(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"¿Quiere borrar el fichero?",
                    ButtonType.CANCEL,ButtonType.OK);
       
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            TreeItem<MyFile> treeItem= (TreeItem<MyFile>)treeFile.getSelectionModel().getSelectedItem();
            TreeItem<MyFile> parent=treeItem.getParent();
            ftpManager.deleteFile(treeItem.getValue().getName());
            parent.getChildren().remove(treeItem);
            treeFile.refresh();
        }
      }
            
    public void download(ActionEvent event){
        TreeItem<MyFile> treeItem= (TreeItem<MyFile>) treeFile.getSelectionModel().getSelectedItem();
        LOGGER.info(treeItem.getValue().getName());
        ftpManager.downloadFile(treeItem.getValue().getName());
        
        
    }
 
    public void createDir(ActionEvent event){
        TreeItem<MyFile> itemSelected = (TreeItem<MyFile>) treeFile.getSelectionModel().getSelectedItem();
        
        MyFile file=ftpManager.createDirectory();
        TreeItem<MyFile> directoryToCreate = new TreeItem<MyFile>(file,rootIcon1);
        itemSelected.getChildren().add(directoryToCreate);
        treeFile.refresh();
        ftpManager.createDirectory();
        
        
    }
    
    public void deleteDir(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"¿Quiere borrar el directorio?",
                    ButtonType.CANCEL,ButtonType.OK);
       
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            TreeItem<MyFile> treeItem= (TreeItem<MyFile>)treeFile.getSelectionModel().getSelectedItem();
            TreeItem<MyFile> parent=treeItem.getParent();
            ftpManager.deleteDirectory(treeItem.getValue().getPath());
            parent.getChildren().remove(treeItem);
            treeFile.refresh();
            
        }
         
        
    }


     private void itemSelected(ObservableValue observable,
             Object oldValue,
             Object newValue) {
         
            if(newValue==null){
                btnCrear.setDisable(true);
                btnDeleteD.setDisable(true);
                btnUpload.setDisable(true);
                btnDownload.setDisable(true);
                btnDeleteF.setDisable(true);  
            }else{
                TreeItem<MyFile> selectedItem = (TreeItem<MyFile>) newValue;

                String path = selectedItem.getValue().getPath();
                ftpManager.changeDirectory(path);
                if(selectedItem.getValue().isFile()){
                    btnCrear.setDisable(true);
                    btnDeleteD.setDisable(true);
                    btnUpload.setDisable(true);
                    btnDownload.setDisable(false);
                    btnDeleteF.setDisable(false);
                   
                }else if(!selectedItem.getValue().isFile()){
                    btnDownload.setDisable(true);
                    btnDeleteF.setDisable(true);
                    btnCrear.setDisable(false);
                    btnDeleteD.setDisable(false);
                    btnUpload.setDisable(false);
                }
      
            }
     }

}
    
