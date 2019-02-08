/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient;

import albergueperronclient.logic.IFTP;
import albergueperronclient.logic.IFTPFactory;
import albergueperronclient.logic.ILogin;
import albergueperronclient.logic.ILoginFactory;
import albergueperronclient.ui.controller.FTPController;

import albergueperronclient.ui.controller.UILoginFXMLController;
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
 * Application class for the AlberguePerron application
 * @author Alatz
 */
public class App extends Application {
    private static final Logger LOGGER = Logger.getLogger("albergueperronclient.App");
    
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
            loginController.setStage(primaryStage);
            //Initialize the primary stage of the application
            loginController.initStage(root);
            
        }catch(Exception e){
            LOGGER.severe(e.getMessage());
        }  
    }
}

