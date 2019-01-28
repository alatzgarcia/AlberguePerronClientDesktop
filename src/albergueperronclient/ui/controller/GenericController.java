/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.ui.controller;

import albergueperronclient.logic.RoomManager;
import albergueperronclient.logic.StaysManager;
import albergueperronclient.logic.UsersManager;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * Generic controler for the application
 * @author Alatz
 */
public class GenericController {
    
    protected static final Logger LOGGER = Logger.getLogger("/signupsigninuidesktop.ui.controller");
    protected final int userPasswordMinLength = 8;
    protected final int userPasswordMaxLength = 30;
    protected final int fullNameMinLength = 5;
    protected final int fullNameMaxLength = 50;
    
    protected Stage stage;
    
    //The interface that links the UI with the Logic
    protected UsersManager usersManager;
    protected StaysManager staysManager;
    protected RoomManager roomsManager;

    /**
     * Shows an alert with an error message
     * @param errorMsg 
     */
    protected void showErrorAlert(String errorMsg){
        //Shows error dialog.
        Alert alert=new Alert(Alert.AlertType.ERROR,
                              errorMsg,
                              ButtonType.OK);
       
        alert.showAndWait();
        
    }
     
    /**
     * Sets the logic Manager
     * @param logicManager 
     */
    public void setUsersManager(UsersManager usersManager){
        this.usersManager = usersManager;
    }
    
    public void setStaysManager(StaysManager staysManager){
        this.staysManager=staysManager;
    }
    
    /**
     * Sets the stage
     * @param stage 
     */
    public void setStage(Stage stage){
        this.stage = stage;
    }
    /**
     * Sets the user
     * @param user 
     */
    /*public void setUser(User user){
        this.user=user;
        
    }*/

    
}