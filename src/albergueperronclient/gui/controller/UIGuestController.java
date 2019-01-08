/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author 2dam
 */
public class UIGuestController extends GenericController{

    @FXML
    private TableView<?> tableGuest;
    @FXML
    private TableColumn<?, ?> columnName;
    @FXML
    private TableColumn<?, ?> columnDni;
    @FXML
    private Button btnNewGuest;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtFirstSurname;
    @FXML
    private TextField txtSecondSurname;
    @FXML
    private TextField txtDni;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSaveChanges;
    @FXML
    private Button btnDeleteGuest;
    @FXML
    private Button btnModifyGuest;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtLogin;
    @FXML
    private Button btnInsertGuest;
    @FXML
    private Button btnReturn;
    @FXML
    private MenuItem menuGuest;
    @FXML
    private MenuItem menuPet;
    @FXML
    private MenuItem menuIncidences;
    @FXML
    private MenuItem menuRoom;
    @FXML
    private MenuItem menuBlackList;
    @FXML
    private MenuItem menuLogOut;
    @FXML
    private MenuItem menuExit;

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
        
        btnCancel.setOnAction(this::cancel);
        btnNewGuest.setOnAction(this::newGuest);
        btnReturn.setOnAction(this::returnWindow);
        
        stage.show();
       
    }
    
    public void handleWindowShowing(WindowEvent event){
        btnCancel.setDisable(true);
        btnDeleteGuest.setDisable(true);
        btnNewGuest.setDisable(false);
        btnReturn.setDisable(false);
        btnInsertGuest.setVisible(false);
        btnInsertGuest.setVisible(false);
        btnModifyGuest.setVisible(false);
        
        disableFields(); 
    }
    
    public void cancel(ActionEvent event){
//por hacer
        try{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION.CONFIRMATION);
            alert.setTitle("Cancelar");
            alert.setContentText("¿Desea cancelar la operación?");    
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get()== ButtonType.OK){
                disableFields();
            }else if(result.get()==ButtonType.CANCEL){
                cancel(event);
            }else{
                cancel(event);
            } 
        } catch(Exception ex){
            LOGGER.severe(ex.getMessage());
        }
    }
    
    public void returnWindow(ActionEvent event) throws IOException{
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/signupsigninuidesktop/ui/fxml/UILoggedAdmin.fxml"));
            Parent root = loader.load();
            UILoggedAdmin loggedController = loader.getController();
            /*Set a reference in the controller 
                for the UILogin view for the logic manager object           
            */
            loggedController.setLogicManager(logicManager);
            //Send the user to the controller
            loggedController.setUser(user);
            //Initialize the primary stage of the application
            loggedController.initStage(root);
            
            stage.close();
    }
    
    public void newGuest(ActionEvent event){
        
    }

    private void disableFields() {
        txtDni.setVisible(false);
        txtEmail.setVisible(false);
        txtFirstSurname.setVisible(false);
        txtLogin.setVisible(false);
        txtName.setVisible(false);
        txtSecondSurname.setVisible(false);
    }
    
    private void enableFields(){
        txtDni.setText("");
        txtEmail.setText("");
        txtFirstSurname.setText("");
        txtLogin.setText("");
        txtName.setText("");
        txtSecondSurname.setText("");
        
        txtDni.setVisible(true);
        txtEmail.setVisible(true);
        txtFirstSurname.setVisible(true);
        txtLogin.setVisible(true);
        txtName.setVisible(true);
        txtSecondSurname.setVisible(true);
    }
}
