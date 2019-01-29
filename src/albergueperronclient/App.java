package albergueperronclient;


import java.util.logging.Logger;
import albergueperronclient.controller.UIPetFXMLController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import albergueperronclient.logic.PetManagerFactory;
import albergueperronclient.logic.PetsManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ikerm
 */
public class App extends Application {
    /**
     * Entry point for the Java application.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
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
            PetsManager petsManager = PetManagerFactory.createPetManager();
            //Load the fxml file
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/albergueperronclient/fxml/Mascotas.fxml"));
            Parent root = loader.load();
            //Get controller from the loader
            UIPetFXMLController petController = loader.getController();
            /*Set a reference in the controller 
                for the UILogin view for the logic manager object           
            */
            petController.setPetsManager(petsManager);
            //Set a reference for Stage in the UILogin view controller
            petController.setStage(primaryStage);
            //Initialize the primary stage of the application
            petController.initStage(root);
        }catch(Exception e){
            Logger.getLogger(e.getMessage());
        }  
    }
}
