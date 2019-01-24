/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.logic;

import albergueperronclient.modelObjects.MyFile;
import java.io.IOException;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/**
 *
 * @author 2dam
 */
public interface IFTP {

   
    public MyFile uploadFile(String text);
    
    public boolean deleteFile(String name);
    
    public void downloadFile(String file);

    public MyFile createDirectory();
    
    public void connect();

    public void disconnect();

    public void deleteDirectory(String name);
    
    public void buildFileTree(TreeItem treeNode) throws IOException;
    
    public void setTreeFile(TreeView treeFile);
    
    public void changeDirectory(String path);
}
