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
    private TreeView treeFile;
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
       ftp=ftpManager.connect();
       
       TreeItem<String> rootItem = new TreeItem<>("/home/user/");
        rootItem.setExpanded(true);

        // set the tree root
        treeFile.setRoot(rootItem);

        // start building the file tree
        buildFileTree(treeFile.getRoot());
        //scene.add(treeFile);
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
        
        ftpManager.deleteFile(ftp,treeFile.getTreeItem(treeFile.getSelectionModel().getSelectedIndex()).getValue().toString());
    }
      
    public void download(ActionEvent event){
        LOGGER.info("Archivo"+treeFile.getTreeItem(treeFile.getSelectionModel().getSelectedIndex()).getValue());
        ftpManager.downloadFile(ftp,treeFile.getTreeItem(treeFile.getSelectionModel().getSelectedIndex()).getValue().toString());
    }
 
    public void createDir(ActionEvent event){
        
        ftpManager.createDirectory(ftp);
    }
    
    public void deleteDir(ActionEvent event){
        
        ftpManager.deleteDirectory(ftp,treeFile.getTreeItem(treeFile.getSelectionModel().getSelectedIndex()).getValue().toString());
    }


    private void buildFileTree(TreeItem treeNode) throws IOException {
        TreeItem<String> rootItem = new TreeItem<>();
     // display the files
        FTPFile[] files = ftp.listFiles();

        for (FTPFile file : files) {

            if(!file.getName().startsWith(".")) {

                System.out.println("File: " + file.getName());

                // add file to file tree
                treeNode.getChildren().add(new TreeItem<>(file.getName()));

            } // if

        } // for

        // get the directories
        FTPFile[] directories = ftp.listDirectories();

        for (FTPFile dir : directories) {

            if(!dir.getName().startsWith(".")) {

                // change working directory to detected directory
                ftp.changeWorkingDirectory(dir.getName());

                // save working dir
                String pwd = ftp.printWorkingDirectory();

                // create treeItem to represent new Directory
                TreeItem newDir = new TreeItem<>(dir.getName());
                newDir.setExpanded(false);

                // add directory to file tree
                treeNode.getChildren().add(newDir);

          
                System.out.println("Discovering Files in: " + pwd);

                // recursively call method to add files and directories to new directory
                buildFileTree(newDir);

                // go back to parent directory, once finished in this directory
                ftp.changeToParentDirectory();   
            } 
        }
    }

}
    
