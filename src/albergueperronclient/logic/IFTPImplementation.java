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
import java.util.logging.Logger;
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
 * Class for the implementation of the FTP Client
 *
 * @author Nerea Jimenez
 */
public class IFTPImplementation implements IFTP {

    private static final Logger LOGGER = Logger.getLogger("albergueperronclient");

    //FTP Client
    private static FTPClient ftp;
    //The treeview to visualize the server files
    private TreeView treeFile;

    public void setTreeFile(TreeView treeFile) {
        this.treeFile = treeFile;
    }

    /**
     * Method to conect to the FTP Server
     *
     * @throws FTPException Exception login in the FTP server
     */
    @Override
    public void connect() throws FTPException {

        String server = ResourceBundle.getBundle("albergueperronclient.config.parameters")
                .getString("FTPServer");
        int port = Integer.parseInt(ResourceBundle.getBundle("albergueperronclient.config.parameters")
                .getString("FTPPort"));
        String user = ResourceBundle.getBundle("albergueperronclient.config.parameters")
                .getString("FTPUser");
        String pass = ResourceBundle.getBundle("albergueperronclient.config.parameters")
                .getString("FTPPassword");

        ftp = new FTPClient();

        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        try {
            ftp.connect(server, port);
            ftp.enterLocalPassiveMode();
            //login
            boolean login = ftp.login(user, pass);

            if (login) {
                ftp.setFileType(FTP.BINARY_FILE_TYPE);
            } else {
                throw new FTPException();
            }
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }

    }

    /**
     * Method to upload a file to the server
     *
     * @param path The path of th file to upload
     * @return The file
     */
    @Override
    public MyFile uploadFile(String path) {

        MyFile filetoUP = null;

        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(path));
            //the user can choose a name for the file
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("Subir archivo");
            dialog.setContentText("Nombre del archivo");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                filetoUP = new MyFile();
                filetoUP.setPath(ftp.printWorkingDirectory() + "/");
                filetoUP.setName(result.get());
                filetoUP.setFile(true);
                ftp.storeFile(ftp.printWorkingDirectory() + "/" + result.get(), in);
                in.close();
            }

        } catch (FileNotFoundException ex) {
            LOGGER.severe(ex.getMessage());
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }

        return filetoUP;
    }

    /**
     * Method to delete a file from the server
     *
     * @param name The name of the file
     * @return if the file has been deleted or not
     */
    @Override
    public boolean deleteFile(String name) {
        boolean deleted = false;
        try {
            if (ftp.deleteFile(name)) {

                deleted = true;
                Alert alert = new Alert(
                        Alert.AlertType.INFORMATION, "Borrado correctamente");
            } else {
                LOGGER.info("No se ha podido eliminar el fichero.");
                Alert alert = new Alert(
                        Alert.AlertType.INFORMATION, "No se ha podido borrar");
            }
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }
        return deleted;
    }

    /**
     * Method to download a file from the server
     *
     * @param name The name of the file
     */
    @Override
    public void downloadFile(String name) {

        //choose a directory to download the file to locally
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Open Resource File");
        Window ownerWindow = null;

        File selectedDir = dirChooser.showDialog(ownerWindow);

        BufferedOutputStream out = null;
        try {
            //choose the name of the file
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("Descargar archivo");
            dialog.setContentText("Nombre del archivo");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                out = new BufferedOutputStream(
                        new FileOutputStream(selectedDir + "/" + result.get()));

                if (ftp.retrieveFile(name, out)) {
                    LOGGER.info("Descargado correctamente…..");
                    Alert alert = new Alert(
                            Alert.AlertType.INFORMATION, "Descargado correctamente");
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error en la descarga");
                }
                out.close();
            }
        } catch (FileNotFoundException ex) {
            LOGGER.severe(ex.getMessage());
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }

    }

    /**
     * Method to create a directory in the server
     *
     * @return The file
     */
    @Override
    public MyFile createDirectory() {
        MyFile dir = new MyFile();
        try {

            //choose the name of the directory
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("Nueva carpeta");
            dialog.setHeaderText("Nueva carpeta");
            dialog.setContentText("Introduzca el nombre del directorio:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                dir.setPath(ftp.printWorkingDirectory() + '/' + result.get());
                dir.setName(result.get());
                dir.setFile(false);
                ftp.makeDirectory(result.get());

            }

        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }
        return dir;
    }

    /**
     * Method to delete a directory in the server
     *
     * @param path The path of the directory
     */
    @Override
    public void deleteDirectory(String path) {
        try {
            ftp.removeDirectory(path);
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }
    }

    /**
     * Method to disconnect the FTP client
     */
    @Override
    public void disconnect() {
        try {
            ftp.logout();
            ftp.disconnect();
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }
    }

    /**
     * Method to disconnect the FTP client
     *
     * @param treeNode The parent node
     * @throws java.io.IOException The exception
     */
    @Override
    public void buildTree(TreeItem treeNode) throws IOException {

        //list the files in the server
        FTPFile[] files = ftp.listFiles();

        for (FTPFile file : files) {
            MyFile fileFTP = new MyFile();
            fileFTP.setName(file.getName());

            //if the it is a file
            if (file.isFile()) {
                //set the path of the file
                fileFTP.setPath(ftp.printWorkingDirectory() + '/');
                fileFTP.setFile(true);
                //create a tree item with the file
                TreeItem<MyFile> treeItem = new TreeItem<MyFile>(fileFTP, new ImageView(new Image(getClass().getResourceAsStream("/albergueperronclient/file.png"))));
                //add the treeitem to the parent node
                treeNode.getChildren().add(treeItem);

                //if the it is a directory
            } else if (file.isDirectory()) {
                //set the path of the directory
                fileFTP.setPath(ftp.printWorkingDirectory() + '/' + file.getName());
                fileFTP.setFile(false);
                //change the working directory in the ftp server
                ftp.changeWorkingDirectory(file.getName());

                //create a tree item with the directory
                TreeItem newDir = new TreeItem<MyFile>(fileFTP, new ImageView(new Image(getClass().getResourceAsStream("/albergueperronclient/root.png"))));
                //add the treeitem to the parent node
                treeNode.getChildren().add(newDir);
                //recursively call method to add files and directories to new directory
                buildTree(newDir);

                // go back to parent directory, once finished in this directory
                ftp.changeToParentDirectory();

            }

        }

    }

    /**
     * Method to change the directory
     *
     * @param path the path
     */
    @Override
    public void changeDirectory(String path) {
        try {
            ftp.changeWorkingDirectory(path);
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }
    }

}
