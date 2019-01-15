/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.logic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/**
 *
 * @author Nerea
 */
public class IFTPImplementation implements IFTP{
    
     
     public void subirArchivo(){
        String server = "localhost";
        int port = 147;
        String user="user";
        String pass="password";
        FTPClient ftp = new FTPClient();
        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        try{
            ftp.connect(server,port);
            ftp.enterLocalPassiveMode();
            boolean login = ftp.login(user, pass);
            
            if(login){
            //ftp.changeWorkingDirectory("/prueba");
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
                   
            
            //stream de entrada con el fichero a subir
            BufferedInputStream in;
            in = new BufferedInputStream(
                    new FileInputStream("C:\\Users\\2dam\\Documents\\texto.txt"));
            ftp.storeFile("texto.txt", in);
            
            in.close();
            ftp.logout();
            ftp.disconnect();
           
            }
           
        }catch(IOException ioe){
            ioe.printStackTrace();
        } 
     }
     public void renombrarArchivo(){
        String server = "localhost";
        int port = 147;
        String user="user";
        String pass="password";
        FTPClient ftp = new FTPClient();
        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        try{
            ftp.connect(server,port);
            ftp.enterLocalPassiveMode();
            boolean login = ftp.login(user, pass);
            
            if(login){
                if (ftp.rename("texto.txt","textoRenombrado.txt")){
                    System.out.println("Fichero renombrado…..");
                }else{
                    System.out.println("No se ha podido renombrar el fichero……..");
                }         


                ftp.logout();
                ftp.disconnect();
           
            }
           
        }catch(IOException ioe){
            ioe.printStackTrace();
        } 
     }
     
     public void borrarArchivo(){
        String server = "localhost";
        int port = 147;
        String user="user";
        String pass="password";
        FTPClient ftp = new FTPClient();
        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        try{
            ftp.connect(server,port);
            ftp.enterLocalPassiveMode();
            boolean login = ftp.login(user, pass);
            
            if(login){
                if (ftp.deleteFile("textoRenombrado.txt")){
                    System.out.println("Fichero eliminado");
                }else{
                    System.out.println("No se ha podido eliminar el fichero……..");
                }         


                ftp.logout();
                ftp.disconnect();
           
            }
           
        }catch(IOException ioe){
            ioe.printStackTrace();
        } 
     }
     
     public void descargarArchivo(){
        String server = "localhost";
        int port = 147;
        String user="user";
        String pass="password";
        FTPClient ftp = new FTPClient();
        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        try{
            ftp.connect(server,port);
            ftp.enterLocalPassiveMode();
            boolean login = ftp.login(user, pass);
            
            if(login){
                BufferedOutputStream out= new BufferedOutputStream(
                        new FileOutputStream("C:\\Users\\2dam\\Documents\\texto.txt"));
                if (ftp.retrieveFile("textoRenombrado.txt", out)){
                    System.out.println("Recuperado correctamente…..");
                }else{
                    System.out.println("No se ha podido descargar……..");
                }
                out.close();        


                ftp.logout();
                ftp.disconnect();
           
            }
           
        }catch(IOException ioe){
            ioe.printStackTrace();
        } 
     }

    public FTPFile[] getFiles() {
        String server = "localhost";
        int port = 147;
        String user="user";
        String pass="password";
        FTPClient ftp = new FTPClient();
        FTPFile[] files = null;
        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        try{
            ftp.connect(server,port);
            ftp.enterLocalPassiveMode();
            boolean login = ftp.login(user, pass);
            
            if(login){
                files = ftp.listFiles();
                for (int i = 0; i < files.length; i++) {
                    System.out.println(files[i].getName());
                }
        
                

                ftp.logout();
                ftp.disconnect();
           
            }
           
        }catch(IOException ioe){
            ioe.printStackTrace();
        } 
     return files;
    }
         
}
