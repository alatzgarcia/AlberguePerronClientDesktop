/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.ui.controller;

import albergueperronclient.modelObjects.UserBean;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author 2dam
 */
public class UIGuestController extends GenericController{
    //FXML auto-generated
    @FXML
    private TableView<UserBean> tableGuest;
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
    //attributes
    private ObservableList<UserBean> usersData;
    private int visible=1;
    private int invisible=2;
    private int enable=3;
    private int disable=4;
    private int clean=5;
    
    public void initStage(Parent root){
        Scene scene = new Scene(root);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.setResizable(false);
        
        stage.setOnShowing(this::handleWindowShowing);
        
        /*        
        //Sets the button methods when they are clicked
        btnReturn.setOnAction(this::returnWindow);*/
        btnCancel.setOnAction(this::cancel);
        btnNewGuest.setOnAction(this::newGuest);
        //btnInsertGuest.setOnAction(this::saveNewGuest);
        //btnSaveChanges.setOnAction(this::updateGuest);
        //btnModifyGuest.setOnAction(this::modifyGuest);
        
        //Sets the columns the attributes to use
        columnDni.setCellFactory(new PropertyValueFactory<>("id"));
        columnName.setCellFactory(new PropertyValueFactory<>("name"));
        
        //Create an observable lis for the users
        usersData = FXCollections.observableArrayList(usersManager.getAllUsers());
        
        //Set the observable data
        tableGuest.setItems(usersData);
        
        //Sets the selection listener
        tableGuest.getSelectionModel().selectedItemProperty().addListener(this::handleUserTableFocus);
        
        stage.show();
       
    }
    
    public void handleWindowShowing(WindowEvent event){
        btnCancel.setDisable(true);
        btnDeleteGuest.setDisable(true);
        btnNewGuest.setDisable(false);
        btnReturn.setDisable(false);
        btnInsertGuest.setVisible(false);
        btnSaveChanges.setVisible(false);
        btnModifyGuest.setVisible(false);
        
        fieldChange(disable); 
    }
    
    public void handleUserTableFocus(ObservableValue observable, Object oldValue, Object newValue){
        if (newValue!=null){
            UserBean user=(UserBean)newValue;
            txtDni.setText(user.getId());
            txtEmail.setText(user.getEmail());
            txtFirstSurname.setText(user.getSurname1());
            txtLogin.setText(user.getLogin());
            txtName.setText(user.getName());
            txtSecondSurname.setText(user.getSurname2());
            
            //Enables the correspondent buttons
            btnDeleteGuest.setDisable(false);
            btnNewGuest.setDisable(false);
        }
    }
    

    
    /*public void returnWindow(ActionEvent event) throws IOException{
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/signupsigninuidesktop/ui/fxml/UILoggedAdmin.fxml"));
            Parent root = loader.load();
            UILoggedAdmin loggedController = loader.getController();
            /*Set a reference in the controller 
                for the UILogin view for the logic manager object           
            *//*
            loggedController.setLogicManager(logicManager);
            //Send the user to the controller
            loggedController.setUser(user);
            //Initialize the primary stage of the application
            loggedController.initStage(root);
            
            stage.close();
    }
    */
    
    public void modifyGuest(){
        //Enable the buttons needed to modify a guest
        btnCancel.setDisable(false);
        btnModifyGuest.setDisable(false);
        
        //Enables all the fields
        fieldChange(enable);
    }
    
    public void newGuest(ActionEvent event){
        // Enable the buttons needed to create a new guest
        btnCancel.setDisable(false);
        btnInsertGuest.setDisable(false);
        
        // Enables all the fields
        fieldChange(enable);
        
    }
    public void fieldChange(int change){
        switch(change){
            case 1:
                //Sets visbles all the fields of the window
                txtDni.setVisible(true);
                txtEmail.setVisible(true);
                txtFirstSurname.setVisible(true);
                txtLogin.setVisible(true);
                txtName.setVisible(true);
                txtSecondSurname.setVisible(true);
        break;
            case 2:
                //Sets invisible all the fields of the window
                txtDni.setVisible(false);
                txtEmail.setVisible(false);
                txtFirstSurname.setVisible(false);
                txtLogin.setVisible(false);
                txtName.setVisible(false);
                txtSecondSurname.setVisible(false);
            case 3:
                //Enables the fields
                txtDni.setEditable(true);
                txtEmail.setEditable(true);
                txtFirstSurname.setEditable(true);
                txtLogin.setEditable(true);
                txtName.setEditable(true);
                txtSecondSurname.setEditable(true);
            case 4:
                //Disables the fields
                txtDni.setEditable(false);
                txtEmail.setEditable(false);
                txtFirstSurname.setEditable(false);
                txtLogin.setEditable(false);
                txtName.setEditable(false);
                txtSecondSurname.setEditable(false);
            case 5:
                //Deletes all the existing data
                txtDni.setText("");
                txtEmail.setText("");
                txtFirstSurname.setText("");
                txtLogin.setText("");
                txtName.setText("");
                txtSecondSurname.setText("");
        }
    }
    
// TODO
    private void saveNewGuest(){
        if (checkFields()){
            //Cuando se quiera hacer algo con el CRUD usar userManager
        }
    }
// TODO
    private void updateGuest(){
        if (checkFields()){
            //Cuando se quiera hacer algo con el CRUD usar userManager
        }
        
    }
    
    private boolean checkFields(){
        if(txtDni.getText().length()==9&&txtEmail.getText().matches("^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$")
                &&txtName.getText().length()>=fullNameMinLength&&txtName.getText().length()<=fullNameMaxLength
                &&txtFirstSurname.getText().length()>=fullNameMinLength&&txtFirstSurname.getText().length()<=fullNameMaxLength
                &&txtSecondSurname.getText().length()>=fullNameMinLength&&txtSecondSurname.getText().length()<=fullNameMaxLength
                &&txtLogin.getText().length()>=userPasswordMinLength&&txtLogin.getText().length()<=userPasswordMaxLength){
            return true;
        }else{
            return false;
        }
    }
    
    public void cancel(ActionEvent event){
        try{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION.CONFIRMATION);
            alert.setTitle("Cancelar");
            alert.setContentText("¿Desea cancelar la operación?");    
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get()== ButtonType.OK){
                fieldChange(disable);
                btnCancel.setDisable(true);
            }else if(result.get()==ButtonType.CANCEL){
                alert.close();
            }else{
            } 
        } catch(Exception ex){
            LOGGER.severe(ex.getMessage());
        }
    }
}
