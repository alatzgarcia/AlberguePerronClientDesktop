/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.controller;

import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import albergueperronclient.logic.PetsManager;

/**
 *
 * @author ikerm
 */
public class GenericController {
        /**
     * Logger object used to log messages for application.
     */
    protected static final Logger LOGGER=Logger.getLogger("ui.controller");
    /**
     * Maximum text fields length.
     */
    protected final int MAX_LENGTH=255;
    /**
     * The business logic object containing all business methods.
     */
    protected PetsManager petsManager;
    /**
     * Sets the business logic object to be used by this UI controller. 
     * @param petsManager An object implementing {@link PetSsManager} interface.
     */
    protected Stage previousStage;
    
    public void setPetsManager(PetsManager petsManager){
        this.petsManager=petsManager;
    }
    /**
     * The Stage object associated to the Scene controlled by this controller.
     * This is an utility method reference that provides quick access inside the 
     * controller to the Stage object in order to make its initialization. Note 
     * that this makes Application, Controller and Stage being tightly coupled.
     */
    protected Stage stage;
    /**
     * Gets the Stage object related to this controller.
     * @return The Stage object initialized by this controller.
     */
    public Stage getStage(){
        return stage;
    }
    /**
     * Sets the Stage object related to this controller. 
     * @param stage The Stage object to be initialized.
     */
    public void setStage(Stage stage){
        this.stage=stage;
    }
    /**
     * Shows an error message in an alert dialog.
     * @param errorMsg The error message to be shown.
     */
    protected void showErrorAlert(String errorMsg){
        //Shows error dialog.
        Alert alert=new Alert(Alert.AlertType.ERROR,
                              errorMsg,
                              ButtonType.OK);
        alert.getDialogPane().getStylesheets().add(
              getClass().getResource("/ui/view/login.fxml").toExternalForm());
        alert.showAndWait();
        
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
