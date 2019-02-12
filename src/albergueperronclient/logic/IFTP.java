/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.logic;

import albergueperronclient.exceptions.FTPException;
import albergueperronclient.modelObjects.MyFile;
import java.io.IOException;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;


/**
 * Business logic interface encapsulating business methods for the FTP client
 * @author Nerea Jimenez
 */
public interface IFTP {

   
    public MyFile uploadFile(String text)throws IOException;
    
    public boolean deleteFile(String name)throws IOException;
    
    public void downloadFile(String file)throws IOException;

    public MyFile createDirectory()throws IOException;
    
    public void connect() throws FTPException;

    public void disconnect();

    public void deleteDirectory(String name)throws IOException;
    
    public void buildTree(TreeItem treeNode) throws IOException;

    public void changeDirectory(String path);

    public void setTreeFile(TreeView treeFile)throws IOException;
}
