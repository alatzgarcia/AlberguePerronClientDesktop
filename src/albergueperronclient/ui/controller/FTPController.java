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
import static javafx.scene.input.KeyCode.T;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.WindowEvent;
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
    private TreeTableView treeFile;
    @FXML
    private TreeTableColumn columnFile;
   
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
        IFTPImplementation ftp = new IFTPImplementation();
        FTPFile[] files=ftp.getFiles();
        
         //Creating the root element
        final TreeItem<String> rootI = new TreeItem<>("Root node");
        rootI.setExpanded(true); 
        
        
        for (int i = 0; i < files.length; i++) {
            TreeItem<String> childNode = new TreeItem<>(files[i].getName());
            //rootI.getChildren().set(i+1, childNode);  
        }
             
        treeFile.setRoot(rootI);
              
        treeFile.setShowRoot(true);             
        //sceneRoot.getChildren().add(treeTableView);
        
              
        
        btnSearch.setOnAction(this::search);
        btnUpload.setOnAction(this::upload);
        //Show primary window
        stage.show();
        

    }
    /**
     * Window event method handler. It implements behavior for WINDOW_SHOWING type 
     * event.
     * @param event  The window event 
     */
    private void handleWindowShowing(WindowEvent event){
        
            

       
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
        //ClienteFTP ftp = new ClienteFTP();
        //if(ftp.uploadFile(txtFile.getText())){
        //    txtFile.setText("");
        //}
    }
 
    

}
