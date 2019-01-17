/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.ui.controller;

import albergueperronclient.logic.IFTPImplementation;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import static javafx.scene.input.KeyCode.T;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.WindowEvent;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;


/**
 * Controller UI class for Login view in users' management application. It contains 
 * event handlers and initialization code for the view defined in Login.fxml file.
 * @author javi
 */
public class FTPController extends GenericController{
   /**
    * User's login name UI text field.
    */
    
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
    private TreeTableView treeFile;
    @FXML
    private TreeTableColumn columnFile;
    
    static FTPClient ftp;
   
    /**
     * Method for initializing Login Stage. 
     * @param root The Parent object representing root node of view graph.
     */
    public void initStage(Parent root) throws IOException {
        LOGGER.info("Initializing Login stage.");
        //Create a scene associated to the node graph root.
        Scene scene = new Scene(root);
        //Associate scene to primaryStage(Window)
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
        
        //IFTPImplementation ftp = new IFTPImplementation();
        TreeItem<String> rootI = new TreeItem<String>("Root node");
      treeFile.setRoot(rootI);
        FTPFile[] files=ftpManager.getFiles(ftp);
        for (FTPFile file : files) {

            if(!file.getName().startsWith(".")) {

                System.out.println("File: " + file.getName());

                // add file to file tree
                rootI.getChildren().add(new TreeItem<>(file.getName()));

            } // if

        } // for
        
                   
        //Creating the root element
      //TreeItem<String> rootI = new TreeItem<String>("Root node");
      //treeFile.setRoot(rootI);
      
        //Creating tree items
        //for (int i = 0; i < files.length; i++) {
         //TreeItem<String> treeItem = new TreeItem<String>(files[i].getName()); 
        //columnFile.setCellValueFactory(new TreeItemPropertyValueFactory(treeItem.getValue()));
      //  rootI.getChildren().setAll(treeItem);
       
        //}
      
     
         //Adding tree items to the root
      // rootI.getChildren().setAll(childNode1, childNode2, childNode3);        
      
       
      
       //treeFile.getColumns().add(columnFile);
       
       treeFile.setShowRoot(true); 
        
       columnFile.setVisible(true);
       treeFile.setVisible(true);
       treeFile.toFront();
        //scene.add(treeFile);
        stage.setScene(scene);
        //Show primary window
        ftp=ftpManager.connect();
        //ftpManager.disconnect(ftp);
        stage.show();
        

    }
    /**
     * Window event method handler. It implements behavior for WINDOW_SHOWING type 
     * event.
     * @param event  The window event 
     */
    private void handleWindowShowing(WindowEvent event){
        
           btnUpload.setVisible(true);
           btnDeleteF.setDisable(false);
           btnDownload.setDisable(false);

       
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
        
        if(ftpManager.uploadFile(txtFile.getText(),ftp)){
           txtFile.setText("");
        }
    }
      
      public void delete(ActionEvent event){
        
        ftpManager.deleteFile(ftp);
    }
      
    public void download(ActionEvent event){
        
        ftpManager.downloadFile(ftp);
    }
 
    public void createDir(ActionEvent event){
        
        ftpManager.createDirectory(ftp);
    }
    
    public void deleteDir(ActionEvent event){
        
        ftpManager.deleteDirectory(ftp);
    }

}
