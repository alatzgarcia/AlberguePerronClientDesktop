/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.gui.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import signupsigninuidesktop.exceptions.ConfigurationParameterNotFoundException;
import signupsigninutilities.model.User;
import signupsigninuidesktop.exceptions.IncorrectLoginException;
import signupsigninuidesktop.exceptions.IncorrectPasswordException;
import signupsigninuidesktop.exceptions.NotAvailableConnectionsException;
import signupsigninuidesktop.exceptions.ServerNotAvailableException;
import static albergueperronclient.gui.controller.GenericController.LOGGER;
/**
 * Controller class for UILogin.fxml
 * @author Alatz
 */
public class UILoginFXMLController extends GenericController {
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField pfPassword; 
    @FXML
    private Label lblUsernameError;
    @FXML
    private Label lblPasswordError;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnExit;
    @FXML
    private Hyperlink hlRegister;
    
   Date now = new Date(System.currentTimeMillis());
   String date = new SimpleDateFormat("dd-MM-yyyy").format(now);

    
    /**
     * InitStage method for the UILogin view
     * @param root 
     */
    public void initStage(Parent root){
        Scene scene = new Scene(root);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.setResizable(false);
        
        stage.setOnShowing(this::handleWindowShowing);
        
        txtUsername.textProperty().addListener(this::onTextChanged);
        pfPassword.textProperty().addListener(this::onTextChanged);
        
        txtUsername.focusedProperty().addListener(this::onFocusChanged);
        pfPassword.focusedProperty().addListener(this::onFocusChanged);
        
        btnExit.setOnAction(this::exit);
        btnLogin.setOnAction(this::login);
        hlRegister.setOnAction(this::register);
        
        stage.show();
       
    }
    
    /**
     * OnShowing handler for the UILogin view
     * @param event 
     */
    public void handleWindowShowing(WindowEvent event){
        btnLogin.setDisable(true);
        txtUsername.requestFocus();
        //Settear promptText
    }
    
    /**
     * Method for the login of a user
     * @param event 
     */
    public void login(ActionEvent event){
        
        try{
            //Sends a user to the logic controller with the entered parameters
            User user = logicManager.login(new User(txtUsername.getText(), 
                    pfPassword.getText()));
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/signupsigninuidesktop/ui/fxml/UILogged.fxml"));
            Parent root = loader.load();
            
            user.setLastAccess(now);
            
            //Get controller from the loader
            UILoggedFXMLController loggedController = loader.getController();
            /*Set a reference in the controller 
                for the UILogin view for the logic manager object           
            */
            loggedController.setLogicManager(logicManager);
            //Send the user to the controller
            loggedController.setUser(user);
            //Initialize the primary stage of the application
            loggedController.initStage(root);
            
            stage.close();
            
        } catch(IncorrectLoginException ile){
            LOGGER.severe("Error. Incorrect login. Detailed error"
                    + ile.getMessage());
            txtUsername.setStyle("-fx-border-color: red");
            lblUsernameError.setText("Error. El usuario introducido no existe.");
        } catch(IncorrectPasswordException ipe){
            LOGGER.severe("Error.Incorrect password. Detailed error: "
                    + ipe.getMessage());
            pfPassword.setStyle("-fx-border-color: red");
            lblPasswordError.setText("Error. La contrase�a introducida"
                    + " es incorrecta.");
         }catch(ServerNotAvailableException snae){
            LOGGER.severe(snae.getMessage());
            showErrorAlert("El servidor no est� disponible.");
        }catch(ConfigurationParameterNotFoundException cpnfe){
            LOGGER.severe(cpnfe.getMessage());
            showErrorAlert("Error en los par�metros de configuraci�n");
        }catch(NotAvailableConnectionsException nace){
            LOGGER.severe(nace.getMessage());
            showErrorAlert("Error. No hay conexiones disponibles");
        }catch(Exception e){
            LOGGER.severe(e.getMessage());
            showErrorAlert("Se ha producido un error en el inicio de sesi�n.");
        }
        
    }
    
    /**
     * Method for the register of a new user
     * @param event 
     */
    public void register(ActionEvent event){
        //calls the logicManager register functio
        try{
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/signupsigninuidesktop/ui/fxml/UIRegister.fxml"));
            Parent root = loader.load();
            
             user.setLastAccess(now);
            
            //Get controller from the loader
            UIRegisterFXMLController registerController = loader.getController();
            /*Set a reference in the controller 
                for the UIController view for the logic manager object           
            */
            registerController.setLogicManager(logicManager);
            //Initialize the primary stage of the application
            registerController.initStage(root);
            
            //stage.hide();
        }catch(Exception e){
            LOGGER.severe(e.getMessage());
            showErrorAlert("Error al redirigir al registro de usuario.");
        }
      
    }
    
    /**
     * Method to exit the application
     */
    public void exit(ActionEvent event){
        try {
            logicManager.close();
            Platform.exit();
        } catch (Exception ex) {
            LOGGER.severe(ex.getMessage());
            showErrorAlert("Error al intentar cerrar la aplicaci�n.");
        }
    }
    
    /**
     * Method that checks if any any of the fillable fields are empty to enable 
     * or disable the "btnLogin" button depending on the result
     * @param observable
     * @param oldValue
     * @param newValue 
     */
    public void onTextChanged(ObservableValue observable,
             String oldValue,
             String newValue){
        /*Checks if any of the fields have no text entered 
            and disables the btnLogin button if true  
        */
        if(txtUsername.getText().trim().length()<userPasswordMinLength 
                ||txtUsername.getText().trim().length()>userPasswordMaxLength
                || pfPassword.getText().trim().length()<userPasswordMinLength
                || pfPassword.getText().trim().length()>userPasswordMaxLength){
            btnLogin.setDisable(true);            
        }
        else if(txtUsername.getText().trim().length()>=userPasswordMinLength 
                && txtUsername.getText().trim().length()<=userPasswordMaxLength 
                && pfPassword.getText().trim().length()>=userPasswordMinLength
                && pfPassword.getText().trim().length()<=userPasswordMaxLength){
            btnLogin.setDisable(false);
        }
        TextField tf = ((TextField)((ReadOnlyProperty)observable).getBean());
        tf.setStyle("");
        if(tf == txtUsername){
            lblUsernameError.setText("");
        } else{
            lblPasswordError.setText("");
        }
    }
    
    /**
     * Method to check if the text of a fillable field that has lost the focus
     * has the correct length
     * @param observable
     * @param oldValue
     * @param newValue 
     */
    //--TOFIX
    public void onFocusChanged(ObservableValue observable,
             Boolean oldValue,
             Boolean newValue){
        if(oldValue){
            TextField tf = ((TextField)((ReadOnlyProperty)observable).getBean());
            if(tf.getText().length()!=0){
                if(tf.getText().length() < userPasswordMinLength ||
                    tf.getText().length() > userPasswordMaxLength){
                    if(tf == txtUsername){
                        lblUsernameError.setText("Error. El usuario "
                                + "debe contener entre 8 y 30 caracteres.");
                    } else {
                        lblPasswordError.setText("Error. La contrase�a "
                            + "debe contener entre 8 y 30 caracteres.");
                    }
                    tf.setStyle("-fx-border-color: red");
                } else{
                    tf.setStyle("");
                }
            }
        }
    }
}