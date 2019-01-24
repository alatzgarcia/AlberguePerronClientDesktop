/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.logic;

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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;

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
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
             
            
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        
    }
     
     @Override
     public MyFile uploadFile(String file){
        //boolean uploaded=false;
        MyFile filetoUP = null;
       
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(file));
            //elegir nombre del archivo
            filetoUP = new MyFile();
        
            filetoUP.setPath(ftp.printWorkingDirectory()+"/");
            filetoUP.setName("subido.txt");
            filetoUP.setFile(true);
            ftp.storeFile(ftp.printWorkingDirectory()+"/"+"subido.txt", in);
             in.close();
             
            
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
                
                LOGGER.info("Fichero eliminado");
                deleted=true;
            }else{
                LOGGER.info("No se ha podido eliminar el fichero……..");         
            }
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }
        return deleted;
    }
     
    public void downloadFile(String file){
        //elegir el directorio en el que queramos descargar el archivo
         DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Open Resource File");
        Window ownerWindow = null;
        
        File selectedFile = dirChooser.showDialog(ownerWindow);
       
        BufferedOutputStream out;
        try {
            out = new BufferedOutputStream(
                    //new FileOutputStream("C:\\Users\\2dam\\Documents\\textoD.txt"));
                    new FileOutputStream(selectedFile+"/descargado.txt"));
            LOGGER.info(selectedFile+"descargado.txt");
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
            Logger.getLogger(IFTPImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dir;
    }
    
    @Override
    public void deleteDirectory(String name) {
        try {
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
        //añadir imagen para diferenciar directorio y archivo
        
        //TreeItem<MyFile> rootItem = new TreeItem<>();
        treeNode.getChildren().clear();
     // display the files
        FTPFile[] files = ftp.listFiles();
          
        for (FTPFile file : files) {
            MyFile fileFTP = new MyFile();
            fileFTP.setName(file.getName());
            
            LOGGER.info(fileFTP.getPath());
            if(file.isFile()) {
                fileFTP.setPath(ftp.printWorkingDirectory()+'/');
                fileFTP.setFile(true);
                // add file to file tree
                TreeItem<MyFile> treeItem=new TreeItem<MyFile>(fileFTP);
                
                
                treeNode.getChildren().add(treeItem);
             

            } else if(file.isDirectory()) {
                fileFTP.setPath(ftp.printWorkingDirectory()+'/'+file.getName());
                LOGGER.info(fileFTP.getPath());
                fileFTP.setFile(false);
                ftp.changeWorkingDirectory(file.getName());

                String pwd = ftp.printWorkingDirectory();

                // create treeItem to represent new Directory
                TreeItem newDir = new TreeItem<MyFile>(fileFTP);
                newDir.setExpanded(false);

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
            Logger.getLogger(IFTPImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
         
}
