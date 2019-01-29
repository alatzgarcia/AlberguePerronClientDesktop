/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.ui.controller;

import albergueperronclient.exceptions.BusinessLogicException;
import albergueperronclient.logic.IFTP;
import albergueperronclient.logic.IFTPFactory;
import albergueperronclient.logic.ILogin;
import albergueperronclient.logic.ILoginFactory;
import albergueperronclient.logic.IRecovery;
import albergueperronclient.logic.IRecoveryFactory;
import albergueperronclient.logic.UserManagerFactory;
import albergueperronclient.logic.UsersManager;
import albergueperronclient.modelObjects.Privilege;
import java.util.Optional;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.WindowEvent;
import albergueperronclient.modelObjects.UserBean;
//import albergueperronclient.exceptions.IncorrectLoginException;
//import albergueperronclient.exceptions.IncorrectPasswordException;
//import albergueperronclient.exceptions.ServerNotAvailableException;
import static albergueperronclient.ui.controller.GenericController.LOGGER;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.Stage;

/**
 * Controller class for UILoggued.fxml
 *
 * @author Nerea Jimenez
 */
public class UILogguedFXMLController extends GenericController {

    /**
     * Button to open the view with all the guests
     */
    @FXML
    private Button btnGuest;

    /**
     * Button to open the view with all the pets
     */
    @FXML
    private Button btnPet;
    
     /**
     * Button to open the view with all the incidences
     */
    @FXML
    private Button btnIncidences;
    
     /**
     * Button to open the view with all the stays
     */
    @FXML
    private Button btnStay;
     /**
     * Button to open the view with all the rooms
     */
    @FXML
    private Button btnRoom;
     /**
     * Button to open the FTP client
     */
    @FXML
    private Button btnFTP;

    /**
     * InitStage method for the UILogged view
     *
     * @param root parent object to initialize the new scene
     */
    public void initStage(Parent root) {
        stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Logged");
        stage.setResizable(false);

        stage.setOnShowing(this::handleWindowShowing);

        btnGuest.setOnAction(this::openGuestsView);
        btnPet.setOnAction(this::openPetsView);
        //btnIncidences.setOnAction(this::openIncidentsView);
        btnStay.setOnAction(this::openStaysView);
        // btnRoom.setOnAction(this::openRoomsView);
        btnFTP.setOnAction(this::openFTPView);

        stage.show();
    }

     
    /**
     * Initializes window state. It implements behavior for WINDOW_SHOWING type 
     * event.
     * @param event  The window event 
     */
    public void handleWindowShowing(WindowEvent event) {
        btnGuest.setDisable(false);
        btnGuest.setMnemonicParsing(true);
        btnGuest.setText("_Huéspedes");
        btnPet.setDisable(false);
        btnPet.setMnemonicParsing(true);
        btnPet.setText("_Mascotas");
        btnIncidences.setDisable(false);
        btnIncidences.setMnemonicParsing(true);
        btnIncidences.setText("_Incidencias");
        btnStay.setDisable(false);
        btnStay.setMnemonicParsing(true);
        btnStay.setText("_Estancias");
        btnRoom.setDisable(false);
        btnRoom.setMnemonicParsing(true);
        btnRoom.setText("_Habitaciones");
        btnFTP.setDisable(false);
        btnFTP.setMnemonicParsing(true);
        btnFTP.setText("_Cliente FTP");

    }

    public void openGuestsView(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/albergueperronclient/ui/fxml/UIGuest.fxml"));

        try {
            Parent root = loader.load();

            //UsersManager usersManager = UserManagerFactory.createUserManager();
            //Get controller from the loader
            //UIGuestFXMLController loggedController = loader.getController();

            //Initialize the primary stage of the application
            //loggedController.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(UILogguedFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void openPetsView(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/albergueperronclient/ui/fxml/UIPet.fxml"));

        try {
            Parent root = loader.load();

        } catch (IOException ex) {
            Logger.getLogger(UILogguedFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void openStaysView(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/albergueperronclient/ui/fxml/UIStay.fxml"));

        try {
            Parent root = loader.load();

            //UsersManager usersManager = UserManagerFactory.createUserManager();
            //Get controller from the loader
            UIStayController stayController = loader.getController();

            //Initialize the primary stage of the application
            //stayController.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(UILogguedFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void openFTPView(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/albergueperronclient/ui/fxml/UIBlackList.fxml"));

        IFTP ftpManager = IFTPFactory.getIFTPImplementation();

        try {
            Parent root = loader.load();

            //UsersManager usersManager = UserManagerFactory.createUserManager();
            //Get controller from the loader
            FTPController ftpController = loader.getController();
            ftpController.setFtpManager(ftpManager);

            //Initialize the primary stage of the application
            ftpController.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(UILogguedFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method to exit the application
     *
     * @param event event that has caused the call to the function
     */
    public void exit(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cerrar aplicación");
            alert.setContentText("¿Desea salir de la aplicación?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                LOGGER.info("Exiting the application.");
                loginManager.close();
                Platform.exit();
            } else {
                LOGGER.info("Exit cancelled.");
            }
        } catch (Exception ex) {
            LOGGER.severe(ex.getMessage());
            showErrorAlert("Error al intentar cerrar la aplicación.");
        }
    }

}
