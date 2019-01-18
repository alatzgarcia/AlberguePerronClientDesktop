/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient;


import albergueperronclient.logic.UserManagerFactory;
import albergueperronclient.logic.UsersManager;
import albergueperronclient.ui.controller.UIGuestFXMLController;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Application class for the SignUpSignIn application
 * @author Alatz
 */
public class App extends Application {
    private static final Logger LOGGER = Logger.getLogger("albergueperron.App");
    
    /**
     * Main method of the client application
     * @param args 
     */
    public static void main(String[] args){
        launch(args);
    }

    /**
     * Method start for the JavaFX Application
     * @param primaryStage
     * @throws Exception 
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            //Get the logic manager object for the initial stage
            UsersManager userManager = UserManagerFactory.createUserManager();
            
            //Load the fxml file
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/albergueperronclient/ui/fxml/UIGuest.fxml"));
            Parent root = loader.load();
            //Get controller from the loader
            UIGuestFXMLController guestController = loader.getController();
            /*Set a reference in the controller for the UILogin view for the logic manager object           
            */
            guestController.setUsersManager(userManager);
            //Set a reference for Stage in the UILogin view controller
            guestController.setStage(primaryStage);
            //Initialize the primary stage of the application
            guestController.initStage(root);
        }catch(Exception e){
            LOGGER.severe(e.getMessage());
        }  
    }
}

