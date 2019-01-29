/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.ui.controller;

import albergueperronclient.exceptions.IncorrectEmailException;
import java.util.Optional;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.WindowEvent;
import albergueperronclient.modelObjects.UserBean;
import utils.PasswordGenerator;
import static albergueperronclient.ui.controller.GenericController.LOGGER;
import javafx.stage.Stage;
/**
 * Controller class for UIRecovery.fxml
 * @author Nerea Jimenez
 */
public class UIPassRecoveryController extends GenericController {
    /**
     * TextField for the email of the user
     */
    @FXML
    private TextField txtEmail;
   
    @FXML
    private Button btnSend;
    
    @FXML
    private Label lblError;
    
    /**
     * InitStage method for the UILogin view
     * @param root parent object to initialize the new scene
     */
    public void initStage(Parent root){
        Scene scene = new Scene(root);
         stage = new Stage();
        
         stage.setScene(scene);
        stage.setTitle("Password Recovery");
        stage.setResizable(false);
        
        stage.setOnShowing(this::handleWindowShowing);
        
        txtEmail.textProperty().addListener(this::onTextChanged);
        txtEmail.focusedProperty().addListener(this::onFocusChanged);
                
        btnSend.setOnAction(this::recoverEmail);
        
        
        stage.show();
    }
    
    /**
     * OnShowing handler for the UILogin view
     * @param event event of window showing/opening that calls to the method
     */
    public void handleWindowShowing(WindowEvent event){
        btnSend.setDisable(true);
        btnSend.setMnemonicParsing(true);
        btnSend.setText("_Enviar");
        
    }
    
    /**
     * Method for the login of a user
     * @param event event that has caused the call to the function
     */
    public void checkEmail(ActionEvent event){
        
        try{
            //Sends a user to the logic controller with the entered parameters
            String email = txtEmail.getText();
            
            UserBean user = recoveryManager.getUserByEmail(email);
            if(user!=null){
                //generates a random password
                PasswordGenerator randomPass = new PasswordGenerator.
                        PasswordGeneratorBuilder().useDigits(true).useUpper(true).
                        useLower(true).build();
                String password = randomPass.generate(8);
                user.setPassword(password);
                //Calls the logic method to update the password
                recoveryManager.recoverEmail(user);
            }else{
                throw new IncorrectEmailException();
            }
            
            txtEmail.setText("");
            
            stage.hide();
        } catch(IncorrectEmailException iee){
            LOGGER.severe("Error. Incorrect email. Detailed error "
                    + iee.getMessage());
            txtEmail.setStyle("-fx-border-color: red");
            lblError.setText("Error. El email introducido no existe.");
        }
    }
    
    public void recoverEmail(ActionEvent event){
        
        try{
            //Sends a user to the logic controller with the entered parameters
            String email = txtEmail.getText();
            
            UserBean user = recoveryManager.getUserByEmail(email);
            if(user!=null){
                
                //Calls the logic method to update the password
                recoveryManager.recoverEmail(user);
            }else{
                throw new IncorrectEmailException();
            }
            
            txtEmail.setText("");
            
            stage.hide();
        } catch(IncorrectEmailException iee){
            LOGGER.severe("Error. Incorrect email. Detailed error "
                    + iee.getMessage());
            txtEmail.setStyle("-fx-border-color: red");
            lblError.setText("Error. El email introducido no existe.");
        }
    }
      
    /**
     * Method to exit the application
     * @param event event that has caused the call to the function
     */
    public void exit(ActionEvent event){
         try{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cerrar aplicación");
            alert.setContentText("¿Desea salir de la aplicación?");
        
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get()== ButtonType.OK){
                LOGGER.info("Exiting the application.");
                loginManager.close();
                Platform.exit();
            }else{
                LOGGER.info("Exit cancelled.");
            } 
        } catch(Exception ex){
            LOGGER.severe(ex.getMessage());
            showErrorAlert("Error al intentar cerrar la aplicación.");
        }
    }
    
    /**
     * Method that checks if any any of the fillable fields are empty to enable 
     * or disable the "btnLogin" button depending on the result
     * @param observable observable value
     * @param oldValue old value of the element that has called to the method
     * @param newValue new value of the element that has called to the method
     */
    public void onTextChanged(ObservableValue observable,
             String oldValue,
             String newValue){
        
         if(this.txtEmail.getText().trim().equals("")){
            //if the filed is empty the button is disabled
            btnSend.setDisable(true);
        }else{
            
            if(txtEmail.getText().matches("^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$")&&
                 txtEmail.getText().trim().length()>=userPasswordMinLength&&
                 txtEmail.getText().trim().length()<=userPasswordMaxLength){
                lblError.setText("");
                btnSend.setDisable(false);
                
            }else{
                btnSend.setDisable(true);
            }
        }         
        
    }
    
    /**
     * Method to check if the text of a fillable field that has lost the focus
     * has the correct length
     * @param observable observable value
     * @param oldValue old value of the element that has called to the method
     * @param newValue new value of the element that has called to the method
     */
    //--TOFIX
    public void onFocusChanged(ObservableValue observable,
             Boolean oldValue,
             Boolean newValue){
        if(oldValue){
            if(!txtEmail.getText().matches("^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$")){
                lblError.setText("Email no válido");
            txtEmail.setStyle("-fx-border-color: red");
        }
    }
    }
}
