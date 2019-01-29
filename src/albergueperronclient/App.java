/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient;

import albergueperronclient.logic.IncidentManager;
import albergueperronclient.logic.IncidentManagerFactory;
import albergueperronclient.logic.RoomManager;
import albergueperronclient.logic.RoomManagerFactory;
import albergueperronclient.logic.UsersManager;
import albergueperronclient.logic.UsersManagerFactory;
import albergueperronclient.ui.controller.IncidentFXMLController;
import albergueperronclient.ui.controller.RoomFXMLController;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author 2dam
 */
public class App extends Application {
    private static final Logger LOGGER = Logger.getLogger("albergueperronclient.App");
    
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            //Get the logic manager object for the initial stage
            IncidentManager incidentManager = IncidentManagerFactory.getIncidentManager();
            RoomManager roomManager = RoomManagerFactory.getRoomManager();
            UsersManager userManager = UsersManagerFactory.getUsersManager();
            
            //Load the fxml file
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/albergueperronclient/ui/fxml/Incident.fxml"));
            Parent root = loader.load();
            //Get controller from the loader
            IncidentFXMLController incidentController = loader.getController();
            
            incidentController.setLogicManager(incidentManager, roomManager,
                    userManager);
            //Set a reference for Stage in the UILogin view controller
            incidentController.setStage(primaryStage);
            //Initialize the primary stage of the application
            incidentController.initStage(root);
            
            //Load the fxml file
            /*FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/albergueperronclient/ui/fxml/Room.fxml"));
            Parent root = loader.load();
            //Get controller from the loader
            RoomFXMLController roomController = loader.getController();
            
            roomController.setLogicManager(roomManager);
            //Set a reference for Stage in the UILogin view controller
            roomController.setStage(primaryStage);
            //Initialize the primary stage of the application
            roomController.initStage(root);*/
        }catch(Exception e){
            LOGGER.info(e.getMessage());
        }  
    }
}
