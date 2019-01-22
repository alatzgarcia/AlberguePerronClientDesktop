/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.logic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/**
 *
 * @author Nerea
 */
public class IFTPImplementation implements IFTP{
    private static final Logger LOGGER=Logger.getLogger("albergueperronclient");
    private static FTPClient ftp;

    private TreeView treeFile;
    
    @Override
    public void setTreeFile(TreeView treeFile) {
        this.treeFile = treeFile;
    }
    
    @Override
    public void connect() {
        String server = "localhost";
        int port = 147;
        String user="user";
        String pass="password";
        ftp = new FTPClient();
        boolean uploaded=false;
        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        try{
            ftp.connect(server,port);
            ftp.enterLocalPassiveMode();
            boolean login = ftp.login(user, pass);
            
            if(login){
            //ftp.changeWorkingDirectory("/prueba");
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
             
            
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        
    }
     
     @Override
     public boolean uploadFile(String file){
        boolean uploaded=false;
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(file));
             uploaded=ftp.storeFile("texto.txt", in);
             in.close();
             
            
        } catch (FileNotFoundException ex) {
            LOGGER.severe(ex.getMessage());
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }
       
        return uploaded;
     }
     
    public void renameFile() throws IOException{
        if (ftp.rename("texto.txt","textoRenombrado.txt")){
            LOGGER.info("Fichero renombrado");
            

        }else{
             LOGGER.info("No se ha podido renombrar el fichero……..");
        }         
    }
     
    public void deleteFile(String name){
  
        try {
            if (ftp.deleteFile(name)){
                LOGGER.info("Fichero eliminado");
            }else{
                LOGGER.info("No se ha podido eliminar el fichero……..");         
            }
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }

    }
     
    public void downloadFile(String file){

        BufferedOutputStream out;
        try {
            out = new BufferedOutputStream(
                    new FileOutputStream("/home/nerea/Documentos/textoD.txt"));
            LOGGER.info(file);
            if (ftp.retrieveFile(file, out)){
                LOGGER.info("Recuperado correctamente…..");
            }else{
                LOGGER.info("No se ha podido descargar……..");
            }
            out.close();  
        } catch (FileNotFoundException ex) {
            LOGGER.severe(ex.getMessage());
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }
                   

     }

  

    @Override
    public void createDirectory() {
        try {
            //String path=ftp.printWorkingDirectory()
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("New directory");
            dialog.setHeaderText("New directoryLook, a Text Input Dialog");
            dialog.setContentText("Please enter the name of the directory:");

            // Traditional way to get the response value.
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                ftp.makeDirectory(result.get());
            }
            
        } catch (IOException ex) {
            Logger.getLogger(IFTPImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void deleteDirectory(String name) {
        try {
            //String path=ftp.printWorkingDirectory()
            ftp.removeDirectory(name);
        } catch (IOException ex) {
            Logger.getLogger(IFTPImplementation.class.getName()).log(Level.SEVERE, null, ex);
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
        
        TreeItem<FTPFile> rootItem = new TreeItem<>();
     // display the files
        FTPFile[] files;
            try {
                files = ftp.listFiles();
           
        for (FTPFile file : files) {

            if(!file.getName().startsWith(".")) {

                System.out.println("File: " + file.getName());

                // add file to file tree
                TreeItem<FTPFile> treeItem=new TreeItem<FTPFile>(file);
                
                treeNode.getChildren().add(treeItem);
             

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
                TreeItem newDir = new TreeItem<FTPFile>(dir);
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
            } catch (IOException ex) {
                Logger.getLogger(IFTPImplementation.class.getName()).log(Level.SEVERE, null, ex);
            }

        
    }
    
    public void changeDirectory(String path){
        try {
            ftp.changeWorkingDirectory('/'+path);
        } catch (IOException ex) {
            Logger.getLogger(IFTPImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
         
}
