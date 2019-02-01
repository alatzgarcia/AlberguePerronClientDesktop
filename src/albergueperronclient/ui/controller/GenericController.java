/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.ui.controller;

import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import albergueperronclient.modelObjects.UserBean;

/**
 * GenericController class for AlberguePerronClient application
 * @author Alatz
 */
public class GenericController {
    /**
     * Logger for the class
     */
    protected static final Logger LOGGER = 
            Logger.getLogger("albergueperronclient.ui.controller");
    /**
     * Min. length for the user's password and login
     */
    protected final int userPasswordMinLength = 8;
    /**
     * Max. length for the user's password and login
     */
    protected final int userPasswordMaxLength = 30;
    /**
     * Min. length for the user's fullname
     */
    protected final int fullNameMinLength = 5;
    /**
     * Max. length for the user's fullname
     */
    protected final int fullNameMaxLength = 50;
    /**
     * Current stage
     */
    protected Stage stage;
    /**
     * The user of the application
     */
    protected UserBean user;
    
    /**
     * Method to show error alerts in the application
     * @param errorMsg 
     */
    protected void showErrorAlert(String errorMsg){
        //Shows error dialog.
        Alert alert=new Alert(Alert.AlertType.ERROR,
                              errorMsg,
                              ButtonType.OK);
        // --TOFIX
        //alert.getDialogPane().getStylesheets().add(
              //getClass().getResource("/ui/fxml/customCascadeStyleSheet.css").toExternalForm());
        alert.showAndWait();
        
    }
    
    /**
     * Method to set the stage
     * @param stage 
     */
    public void setStage(Stage stage){
        this.stage = stage;
    }
    
    /**
     * Method to set the user
     * @param dbUser 
     */
    public void setUser(UserBean dbUser){
        this.user = dbUser;
    }
}
