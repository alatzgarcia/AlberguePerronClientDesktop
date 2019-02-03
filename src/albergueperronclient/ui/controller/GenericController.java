/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.ui.controller;

import albergueperronclient.logic.IFTP;
import albergueperronclient.logic.ILogin;
import albergueperronclient.logic.IRecovery;
import albergueperronclient.logic.PetsManager;
import albergueperronclient.logic.RoomManager;
import albergueperronclient.logic.StaysManager;
import albergueperronclient.logic.UsersManager;
import albergueperronclient.modelObjects.UserBean;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * Generic controler for the application
 *
 * @author Alatz
 */
public class GenericController {

    protected static final Logger LOGGER
            = Logger.getLogger("albergueperronclient.ui.controller");
    protected final int userPasswordMinLength = 8;
    protected final int userPasswordMaxLength = 30;
    protected final int fullNameMinLength = 5;
    protected final int fullNameMaxLength = 50;

    protected Stage stage;

    protected Stage previousStage;

    //The interface that links the UI with the Logic
    protected UsersManager usersManager;
    protected ILogin loginManager;
    protected IFTP ftpManager;
    protected UserBean user;

    protected IRecovery recoveryManager;
    protected StaysManager staysManager;
    protected RoomManager roomsManager;
    
    protected PetsManager petsManager;

    /**
     * Shows an alert with an error message
     *
     * @param errorMsg
     */
    protected void showErrorAlert(String errorMsg) {
        //Shows error dialog.
        Alert alert = new Alert(Alert.AlertType.ERROR,
                errorMsg,
                ButtonType.OK);

        alert.showAndWait();

    }

    public void setRecoveryManager(IRecovery recoveryManager) {
        this.recoveryManager = recoveryManager;
    }

    public IRecovery getRecoveryManager() {
        return recoveryManager;
    }

    public void setFtpManager(IFTP ftpManager) {
        this.ftpManager = ftpManager;
    }

    public void setLoginManager(ILogin loginManager) {
        this.loginManager = loginManager;
    }
    
    public void setPetsManager(PetsManager petsManager){
        this.petsManager=petsManager;
    }

    /**
     * Sets the stage
     *
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Sets the user
     *
     * @param user
     */
    public void setUser(UserBean user) {
        this.user = user;

    }

    /**
     * Sets the stage of the previous window to give the option to show it again
     * later
     *
     * @param stage
     */
    public void setPreviousStage(Stage stage) {
        this.previousStage = stage;
    }

    /**
     * Sets the logic Manager
     *
     * @param logicManager
     */
    public void setUsersManager(UsersManager usersManager) {
        this.usersManager = usersManager;
    }

    public void setRoomsManager(RoomManager roomsManager) {
        this.roomsManager = roomsManager;
    }

    public void setStaysManager(StaysManager staysManager) {
        this.staysManager = staysManager;
    }
}
