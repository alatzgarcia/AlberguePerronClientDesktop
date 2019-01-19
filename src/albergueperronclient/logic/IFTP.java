/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.logic;

import java.io.IOException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/**
 *
 * @author 2dam
 */
public interface IFTP {

    public FTPFile[] getFiles(FTPClient ftp) throws IOException ;
   
    public boolean uploadFile(String text,FTPClient ftp);
    
    public void deleteFile(FTPClient ftp,String name);
    
    public void downloadFile(FTPClient ftp,String file);

    public void createDirectory(FTPClient ftp);
    
    public FTPClient connect();

    public void disconnect(FTPClient ftp);

    public void deleteDirectory(FTPClient ftp,String name);
}
