/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.ui.controller;

import albergueperronclient.exceptions.BusinessLogicException;
import albergueperronclient.exceptions.IncorrectLoginException;
import albergueperronclient.logic.IRecovery;
import albergueperronclient.logic.IRecoveryFactory;
import albergueperronclient.modelObjects.Privilege;
import java.util.Optional;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.WindowEvent;
import albergueperronclient.modelObjects.UserBean;
import static albergueperronclient.ui.controller.GenericController.LOGGER;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller class for UILogin.fxml
 *
 * @author Nerea Jimenez
 */
public class UILoginFXMLController extends GenericController {

    /**
     * TextField for the login of the user
     */
    @FXML
    private TextField txtUsername;
    /**
     * PasswordField for the password of the user
     */
    @FXML
    private PasswordField pfPassword;
    /**
     * Label to show error on incorrect login
     */
    @FXML
    private Label lblUsernameError;
    /**
     * Label to show error on incorrect password
     */
    @FXML
    private Label lblPasswordError;
    /**
     * Button for the login operation
     */
    @FXML
    private Button btnLogin;
    /**
     * Button to exit the application
     */
    @FXML
    private Button btnExit;
    /**
     * HyperLink to go to the recovery password view
     */
    @FXML
    private Hyperlink hlRemindPass;

    /**
     * InitStage method for the UILogin view
     *
     * @param root parent object to initialize the new scene
     */
    public void initStage(Parent root) {
        Scene scene = new Scene(root);
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
        hlRemindPass.setOnAction(this::passwordRecovery);

        stage.show();
    }

    /**
     * OnShowing handler for the UILogin view
     *
     * @param event event of window showing/opening that calls to the method
     */
    public void handleWindowShowing(WindowEvent event) {
        btnLogin.setDisable(true);
        btnLogin.setMnemonicParsing(true);
        btnLogin.setText("_Iniciar Sesión");
        btnExit.setMnemonicParsing(true);
        btnExit.setText("_Salir");
        txtUsername.requestFocus();

    }

    /**
     * Method for the login of the user
     *
     * @param event event that has caused the call to the function
     */
    public void login(ActionEvent event) {

        try {
            //Sends the entered parameters to the logic controller
          

            user = loginManager.login(txtUsername.getText(),pfPassword.getText());

            //if the user exists and it is an admin the loggued view opens
            if (user != null && user.getPrivilege() == Privilege.ADMIN) {

                FXMLLoader loader = new FXMLLoader(getClass()
                        .getResource("/albergueperronclient/ui/fxml/UILoggedAdmin.fxml"));

                Parent root;
                try {
                    root = loader.load();

                    //Get controller from the loader
                    UILogguedFXMLController loggedController = loader.getController();
                    /*Set a reference in the controller 
                        for the UILogin view for the logic manager object           
                     */
                    loggedController.setUsersManager(usersManager);
                    //Send the user to the controller
                    loggedController.setUser(user);
                    //Send the current stage for coming back later
                    //loggedController.setPreviousStage(stage);
                    //Initialize the primary stage of the application
                    loggedController.initStage(root);
                    txtUsername.setText("");
                    pfPassword.setText("");
                    stage.hide();

                } catch (IOException ex) {
                    LOGGER.severe(ex.getMessage());
                }
          
           } else if (user != null||user.getPrivilege() != Privilege.ADMIN) {
                showErrorAlert("Tiene que ser administrador para acceder");
            }

        
        }   catch (BusinessLogicException ble) {
            LOGGER.severe(ble.getMessage());
            lblUsernameError.setText("Error en el inicio de sesión");
        }
    }

    /**
     * Method for the recovery of the password
     *
     * @param event event that has caused the call to the function
     */
    public void passwordRecovery(ActionEvent event) {
        //opens the password recovery view
        try {
            IRecovery recoveryManager = IRecoveryFactory.getRecoveryManager();
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/albergueperronclient/ui/fxml/UIPasswordRecovery.fxml"));
            Parent root = loader.load();
            //Get controller from the loader
            UIPassRecoveryController recoveryController = loader.getController();
            /*Set a reference in the controller 
                for the UIController view for the logic manager object           
             */
            recoveryController.setRecoveryManager(recoveryManager);
            
            //Initialize the primary stage of the application
            recoveryController.initStage(root);
            txtUsername.setText("");
            pfPassword.setText("");
            stage.hide();
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
            showErrorAlert("Error al redirigir");
        }
    }

    /**
     * Method to exit the application
     *
     * @param event event that has caused the call to the function
     */
    public void exit(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cerrar aplicación");
            alert.setContentText("¿Desea salir de la aplicación?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                LOGGER.info("Exiting the application.");
                Platform.exit();
            } else {
                LOGGER.info("Exit cancelled.");
            }
        } catch (Exception ex) {
            LOGGER.severe(ex.getMessage());
            showErrorAlert("Error al intentar cerrar la aplicación.");
        }
    }

    /**
     * Method that checks if any any of the fillable fields are empty to enable
     * or disable the "btnLogin" button depending on the result
     *
     * @param observable observable value
     * @param oldValue old value of the element that has called to the method
     * @param newValue new value of the element that has called to the method
     */
    public void onTextChanged(ObservableValue observable,
            String oldValue,
            String newValue) {
        /*Checks if any of the fields have no text entered 
            and disables the btnLogin button if true  
         */
        if (txtUsername.getText().trim().length() < userPasswordMinLength
                || txtUsername.getText().trim().length() > userPasswordMaxLength
                || pfPassword.getText().trim().length() < userPasswordMinLength
                || pfPassword.getText().trim().length() > userPasswordMaxLength) {
            btnLogin.setDisable(true);
        } else if (txtUsername.getText().trim().length() >= userPasswordMinLength
                && txtUsername.getText().trim().length() <= userPasswordMaxLength
                && pfPassword.getText().trim().length() >= userPasswordMinLength
                && pfPassword.getText().trim().length() <= userPasswordMaxLength) {
            btnLogin.setDisable(false);
        }
        TextField tf = ((TextField) ((ReadOnlyProperty) observable).getBean());
        tf.setStyle("");
        if (tf == txtUsername) {
            lblUsernameError.setText("");
        } else {
            lblPasswordError.setText("");
        }
    }

    /**
     * Method to check if the text of a fillable field that has lost the focus
     * has the correct length
     *
     * @param observable observable value
     * @param oldValue old value of the element that has called to the method
     * @param newValue new value of the element that has called to the method
     */
    //--TOFIX
    public void onFocusChanged(ObservableValue observable,
            Boolean oldValue,
            Boolean newValue) {
        if (oldValue) {
            TextField tf = ((TextField) ((ReadOnlyProperty) observable).getBean());
            if (tf.getText().length() != 0) {
                if (tf.getText().length() < userPasswordMinLength
                        || tf.getText().length() > userPasswordMaxLength) {
                    if (tf == txtUsername) {
                        lblUsernameError.setText("Error. El usuario "
                                + "debe contener entre 8 y 30 caracteres.");
                    } else {
                        lblPasswordError.setText("Error. La contraseña "
                                + "debe contener entre 8 y 30 caracteres.");
                    }
                    tf.setStyle("-fx-border-color: red");
                } else {
                    tf.setStyle("");
                }
            }
        }
    }
}
