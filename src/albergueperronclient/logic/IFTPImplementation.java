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
import java.util.logging.Level;
import java.util.logging.Logger;

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
    @Override
    public FTPClient connect() {
        String server = "localhost";
        int port = 147;
        String user="user";
        String pass="password";
        FTPClient ftp = new FTPClient();
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
        return ftp;
    }
     
     @Override
     public boolean uploadFile(String file,FTPClient ftp){
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
     
    public void renameFile(FTPClient ftp) throws IOException{
        if (ftp.rename("texto.txt","textoRenombrado.txt")){
            LOGGER.info("Fichero renombrado");
        }else{
             LOGGER.info("No se ha podido renombrar el fichero……..");
        }         
    }
     
    public void deleteFile(FTPClient ftp){
  
        try {
            if (ftp.deleteFile("texto.txt")){
                LOGGER.info("Fichero eliminado");
            }else{
                LOGGER.info("No se ha podido eliminar el fichero……..");         
            }
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }

    }
     
    public void downloadFile(FTPClient ftp){

        BufferedOutputStream out;
        try {
            out = new BufferedOutputStream(
                    new FileOutputStream("C:\\Users\\2dam\\Documents\\texto.txt"));
            if (ftp.retrieveFile("texto.txt", out)){
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
    public FTPFile[] getFiles(FTPClient ftp) throws IOException {
        FTPFile[] files = ftp.listFiles();
           for (int i = 0; i < files.length; i++) {
                System.out.println(files[i].getName());
           }
   
     return files;
    }

    @Override
    public void createDirectory(FTPClient ftp) {
        try {
            //String path=ftp.printWorkingDirectory()
            ftp.makeDirectory("/directorioCreado");
        } catch (IOException ex) {
            Logger.getLogger(IFTPImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void deleteDirectory(FTPClient ftp) {
        try {
            //String path=ftp.printWorkingDirectory()
            ftp.removeDirectory("/directorioCreado");
        } catch (IOException ex) {
            Logger.getLogger(IFTPImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void disconnect(FTPClient ftp) {
        try {
            ftp.logout();
            ftp.disconnect();
        } catch (IOException ex) {
           LOGGER.severe(ex.getMessage());
        }
        
    }


    
         
}
