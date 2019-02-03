/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.ui.controller;

import albergueperronclient.exceptions.BusinessLogicException;
import albergueperronclient.exceptions.ReadException;
import albergueperronclient.logic.BlackListManager;
import albergueperronclient.logic.BlackListManagerFactory;
import albergueperronclient.logic.IFTP;
import albergueperronclient.logic.IFTPFactory;
import albergueperronclient.logic.ILogin;
import albergueperronclient.logic.ILoginFactory;
import albergueperronclient.logic.IncidentManager;
import albergueperronclient.logic.IncidentManagerFactory;
import albergueperronclient.logic.PetManagerFactory;
import albergueperronclient.logic.PetsManager;
import albergueperronclient.logic.RoomManager;
import albergueperronclient.logic.RoomManagerFactory;
import albergueperronclient.logic.StayManagerFactory;
import albergueperronclient.logic.StaysManager;
import albergueperronclient.logic.UserManagerFactory;
import albergueperronclient.logic.UsersManager;
import java.util.Optional;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.WindowEvent;
//import albergueperronclient.exceptions.IncorrectLoginException;
//import albergueperronclient.exceptions.IncorrectPasswordException;
//import albergueperronclient.exceptions.ServerNotAvailableException;
import static albergueperronclient.ui.controller.GenericController.LOGGER;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.MenuItem;
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
     * Button to open the blackList
     */
    @FXML
    private Button btnBlackList;
    
    @FXML
    private MenuItem menuGuest;
    @FXML
    private MenuItem menuPet;
    @FXML
    private MenuItem menuIncidences;
    @FXML
    private MenuItem menuStay;
    @FXML
    private MenuItem menuBlackList;
    @FXML
    private MenuItem menuLogOut;
    @FXML
    private MenuItem menuExit;
    @FXML
    private MenuItem menuFTP;

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
        btnIncidences.setOnAction(this::openIncidentsView);
        btnStay.setOnAction(this::openStaysView);
        btnRoom.setOnAction(this::openRoomsView);
        btnFTP.setOnAction(this::openFTPView);
        btnBlackList.setOnAction(this::openBlackListView);
        menuGuest.setOnAction(this::openGuestsView);
        menuPet.setOnAction(this::openPetsView);
        menuStay.setOnAction(this::openStaysView);
        menuBlackList.setOnAction(this::openBlackListView);
        menuLogOut.setOnAction(this::logOut);
        menuExit.setOnAction(this::exit);

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

    public void openPetsView(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/albergueperronclient/ui/fxml/UIPet.fxml"));

        try {
            Parent root = loader.load();

            PetsManager petManager = PetManagerFactory.createPetManager();
            //Get controller from the loader
            UIPetFXMLController petController = loader.getController();

            petController.setPetsManager(petsManager);
            //Initialize the primary stage of the application
            petController.initStage(root);
            stage.hide();
        } catch (IOException ex) {
            Logger.getLogger(UILogguedFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BusinessLogicException ex) {
            Logger.getLogger(UILogguedFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void openGuestsView(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/albergueperronclient/ui/fxml/UIGuest.fxml"));

        try {
            Parent root = loader.load();

            UsersManager usersManager = UserManagerFactory.createUserManager();
            //Get controller from the loader
            UIGuestFXMLController guestController = loader.getController();
            guestController.setUsersManager(usersManager);
            //Initialize the primary stage of the application
            guestController.initStage(root);
            stage.hide();
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }
    }

    public void openRoomsView(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/albergueperronclient/ui/fxml/UIRoom.fxml"));

        try {
            
            Parent root = loader.load();

            RoomManager roomManager = RoomManagerFactory.getRoomManager();
            //Get controller from the loader
            RoomFXMLController roomController = loader.getController();
            roomController.setLogicManager(roomManager);
            //Initialize the primary stage of the application
            roomController.initStage(root);
            stage.hide();

        } catch (IOException ex) {
            Logger.getLogger(UILogguedFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void openStaysView(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/albergueperronclient/ui/fxml/UIStay.fxml"));

        try {
            Parent root = loader.load();

            StaysManager staysManager = StayManagerFactory.createStayManager();
            //Get controller from the loader
            UIStayFXMLController stayController = loader.getController();
            stayController.setStaysManager(staysManager);

            //Initialize the primary stage of the application
            stayController.initStage(root);
            stage.hide();
        } catch (IOException ex) {
            Logger.getLogger(UILogguedFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void openFTPView(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/albergueperronclient/ui/fxml/UIFTP.fxml"));

        IFTP ftpManager = IFTPFactory.getIFTPImplementation();

        try {
            Parent root = loader.load();

            //Get controller from the loader
            FTPController ftpController = loader.getController();
            ftpController.setFtpManager(ftpManager);

            //Initialize the primary stage of the application
            ftpController.initStage(root);
            
            stage.hide();
        } catch (IOException ex) {
            Logger.getLogger(UILogguedFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void openIncidentsView(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/albergueperronclient/ui/fxml/UIncident.fxml"));
       try {
            Parent root = loader.load();

            IncidentManager incidentManager = IncidentManagerFactory.getIncidentManager();
            RoomManager roomManager = RoomManagerFactory.getRoomManager();
            UsersManager userManager = UserManagerFactory.createUserManager();
            //Get controller from the loader
            IncidentFXMLController incidentController = loader.getController();
            incidentController.setLogicManager(incidentManager, roomManager, userManager);
            
            //Initialize the primary stage of the application
            incidentController.initStage(root);
            
            stage.hide();
        } catch (IOException ex) {
            Logger.getLogger(UILogguedFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ReadException ex) {
            Logger.getLogger(UILogguedFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void openBlackListView(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/albergueperronclient/ui/fxml/UIBlackList.fxml"));
       try {
            Parent root = loader.load();

            BlackListManager blackListManager = BlackListManagerFactory.getBlackListManager();
            UsersManager usersManager = UserManagerFactory.createUserManager();
            //Get controller from the loader
            BlackListFXMLController blackListController = loader.getController();
            blackListController.setLogicManager(blackListManager,usersManager);
            
            //Initialize the primary stage of the application
            blackListController.initStage(root);
            
            stage.hide();
        } catch (IOException ex) {
            Logger.getLogger(UILogguedFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void logOut(ActionEvent event){
        try{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cerrar Sesión");
            alert.setContentText("¿Desea cerrar sesion?");        
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get()== ButtonType.OK){
               stage.close();
                try {
                    //Get the logic manager object for the initial stage
                    ILogin loginManager = ILoginFactory.getLoginManager();

                    //Load the fxml file
                    FXMLLoader loader = new FXMLLoader(getClass()
                            .getResource("/albergueperronclient/ui/fxml/UILogin.fxml"));
                    Parent root = loader.load();
                    //Get controller from the loader
                    UILoginFXMLController loginController = loader.getController();
                    /*Set a reference in the controller for the UILogin view for the logic manager object           
                     */
                    loginController.setLoginManager(loginManager);
                    //Set a reference for Stage in the UILogin view controller
                    loginController.setStage(stage);
                    //Initialize the primary stage of the application
                    loginController.initStage(root);

                } catch (Exception e) {
                    LOGGER.severe(e.getMessage());
                } 
            }else{
                LOGGER.info("Logout cancelled.");
            } 
        }catch(Exception ex){
            LOGGER.severe(ex.getMessage());
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
                stage.close();
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
