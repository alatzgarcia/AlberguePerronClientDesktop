/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.logic;

import albergueperronclient.exceptions.FTPException;
import albergueperronclient.modelObjects.MyFile;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/**
 *
 * @author Nerea JImenez
 */
public class IFTPImplementation implements IFTP{
    private static final Logger LOGGER=Logger.getLogger("albergueperronclient");
    private static FTPClient ftp;

    private TreeView treeFile;
    
   
    public void setTreeFile(TreeView treeFile) {
        this.treeFile = treeFile;
    }
    
    @Override
    public void connect() throws FTPException{
        
        String server = ResourceBundle.getBundle("albergueperronclient.config.parameters")
                    .getString("FTPServer");
        int port = Integer.parseInt(ResourceBundle.getBundle("albergueperronclient.config.parameters")
                    .getString("FTPPort"));
        String user = ResourceBundle.getBundle("albergueperronclient.config.parameters")
                    .getString("FTPUser");
        String pass= ResourceBundle.getBundle("albergueperronclient.config.parameters")
                    .getString("FTPPassword");
        
        ftp = new FTPClient();
        
        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        try{
            ftp.connect(server,port);
            ftp.enterLocalPassiveMode();
            boolean login = ftp.login(user, pass);
            
            if(login){
                ftp.setFileType(FTP.BINARY_FILE_TYPE);
            }else{
                throw new FTPException();
            }
        }catch(Exception e){
            LOGGER.severe(e.getMessage());
        }
        
    }
     
     @Override
     public MyFile uploadFile(String file){
        
        MyFile filetoUP = null;
       
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(file));
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("Subir archivo");
            dialog.setContentText("Nombre del archivo");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                filetoUP = new MyFile();
                filetoUP.setPath(ftp.printWorkingDirectory()+"/");
                filetoUP.setName(result.get());
                filetoUP.setFile(true);
                ftp.storeFile(ftp.printWorkingDirectory()+"/"+result.get(), in);
                in.close();
            }   
            
             
            
        } catch (FileNotFoundException ex) {
            LOGGER.severe(ex.getMessage());
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }
       
        return filetoUP;
     }
     
    
     
    @Override
    public boolean deleteFile(String path){
         boolean deleted=false;
        try {
            if (ftp.deleteFile(path)){
                
                deleted=true;
            }else{
                LOGGER.info("No se ha podido eliminar el fichero.");         
            }
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }
        return deleted;
    }
     
    @Override
    public void downloadFile(String file){
        
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Open Resource File");
        Window ownerWindow = null;
        
        File selectedFile = dirChooser.showDialog(ownerWindow);
       
        BufferedOutputStream out = null;
        try {
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("Subir archivo");
            dialog.setContentText("Nombre del archivo");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                out = new BufferedOutputStream(
                        new FileOutputStream(selectedFile+"/"+result.get()));
                
                if (ftp.retrieveFile(file, out)){
                    LOGGER.info("Recuperado correctamente…..");
                }else{
                   Alert alert = new Alert(Alert.AlertType.ERROR,"Error en la descarga");
                }
                out.close();
            }
        } catch (FileNotFoundException ex) {
            LOGGER.severe(ex.getMessage());
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }
   
     }
    
 @Override
    public MyFile createDirectory() {
        MyFile dir = new MyFile();
        try {
           
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("New directory");
            dialog.setHeaderText("New directory");
            dialog.setContentText("Please enter the name of the directory:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                dir.setPath(ftp.printWorkingDirectory()+'/'+result.get());
                dir.setName(result.get());
                dir.setFile(false);
                ftp.makeDirectory(result.get());
                
            }
            
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }
        return dir;
    }
    
    @Override
    public void deleteDirectory(String name) {
        try {
            ftp.removeDirectory(name);
        } catch (IOException ex) {
           LOGGER.severe(ex.getMessage());
        }
    }
    
    @Override
    public void disconnect() {
        try {
            ftp.logout();
            ftp.disconnect();
        } catch (IOException ex) {
           LOGGER.severe(ex.getMessage());
        }
    }
        
    
        
    @Override
    public void buildFileTree(TreeItem treeNode) throws IOException {
        
     
        FTPFile[] files = ftp.listFiles();
          
        for (FTPFile file : files) {
            MyFile fileFTP = new MyFile();
            fileFTP.setName(file.getName());

            if(file.isFile()) {
                fileFTP.setPath(ftp.printWorkingDirectory()+'/');
                fileFTP.setFile(true);
          
                TreeItem<MyFile> treeItem=new TreeItem<MyFile>(fileFTP,new ImageView(new Image(getClass().getResourceAsStream
            ("/albergueperronclient/file.png"))));
                treeNode.getChildren().add(treeItem);
             

            } else if(file.isDirectory()) {
                fileFTP.setPath(ftp.printWorkingDirectory()+'/'+file.getName());
                fileFTP.setFile(false);
                ftp.changeWorkingDirectory(file.getName());

                // create treeItem to represent new Directory
                TreeItem newDir = new TreeItem<MyFile>(fileFTP,new ImageView(new Image(getClass().getResourceAsStream
            ("/albergueperronclient/root.png"))));
                // add directory to file tree
                treeNode.getChildren().add(newDir);
                // recursively call method to add files and directories to new directory
                buildFileTree(newDir);

                // go back to parent directory, once finished in this directory
                ftp.changeToParentDirectory();

            }

        }
        

        
    }
    
    @Override
    public void changeDirectory(String path){
        try {
            ftp.changeWorkingDirectory(path);
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }
    }
    
         
}
