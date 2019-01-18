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
 *
 * @author Alatz
 */
public class GenericController {
    //Meter aqui maxLength y MinLength para los diferentes campos
    protected static final Logger LOGGER = 
            Logger.getLogger("albergueperronclient.ui.controller");
    protected final int userPasswordMinLength = 8;
    protected final int userPasswordMaxLength = 30;
    protected final int fullNameMinLength = 5;
    protected final int fullNameMaxLength = 50;
    
    protected Stage stage;
    protected UserBean user;
    protected Stage previousStage;
    
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
    
    /**
     * Sets the stage of the previous window to give the option
     * to show it again later
     * @param stage 
     */
    public void setPreviousStage(Stage stage){
        this.previousStage = stage;
    }
}
