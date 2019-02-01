/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient;


import albergueperronclient.logic.StayManagerFactory;
import albergueperronclient.logic.StaysManager;
import albergueperronclient.logic.UserManagerFactory;
import albergueperronclient.logic.UsersManager;
import albergueperronclient.ui.controller.UIGuestFXMLController;
import albergueperronclient.ui.controller.UIStayFXMLController;
import java.io.IOException;
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
    public void start(Stage primaryStage) {
        /*try{
            //Get the logic manager object for the initial stage
            //UsersManager userManager = UserManagerFactory.createUserManager();
            StaysManager stayManager=StayManagerFactory.createStayManager();
            
            //Load the fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/albergueperronclient/ui/fxml/UIStay.fxml"));
            Parent root = loader.load();
            //Get controller from the loader
            UIStayFXMLController stayController = loader.getController();
            //Set a reference in the controller for the UILogin view for the logic manager object           
            
            stayController.setStaysManager(stayManager);
            //Set a reference for Stage in the UILogin view controller
            stayController.setStage(primaryStage);
            //Initialize the primary stage of the application
            stayController.initStage(root);
        }catch(IOException e){
            LOGGER.severe(e.getMessage());
        }*/
        
        try{
            //Get the logic manager object for the initial stage
            //UsersManager userManager = UserManagerFactory.createUserManager();
            UsersManager userManager=UserManagerFactory.createUserManager();
            
            //Load the fxml file
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/albergueperronclient/ui/fxml/UIGuest.fxml"));
            Parent root = loader.load();
            //Get controller from the loader
            UIGuestFXMLController userController = loader.getController();
            //Set a reference in the controller for the UILogin view for the logic manager object           
            
            userController.setUsersManager(userManager);
            //Set a reference for Stage in the UILogin view controller
            userController.setStage(primaryStage);
            //Initialize the primary stage of the application
            userController.initStage(root);
        }catch(Exception e){
            LOGGER.severe(e.getMessage());
        }
    }
}

