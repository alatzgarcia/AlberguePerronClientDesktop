/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.ui.controller;

import albergueperronclient.exceptions.BusinessLogicException;
import albergueperronclient.modelObjects.UserBean;
import java.util.Optional;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import java.lang.String;

/**
 * FXML Controller class
 *
 * @author 2dam
 */
public class UIGuestFXMLController extends GenericController{
    //FXML auto-generated
    @FXML
    private TableView<UserBean> tableGuest;
    @FXML
    private TableColumn columnName;
    @FXML
    private TableColumn columnDni;
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
    private UserBean user;
    private int visible=1;
    private int invisible=2;
    private int enable=3;
    private int disable=4;
    private int clean=5;
    
    public void initStage(Parent root) throws BusinessLogicException{
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
        btnInsertGuest.setOnAction(this::saveNewGuest);
        btnSaveChanges.setOnAction(this::saveUpdateGuest);
        btnModifyGuest.setOnAction(this::updateGuest);
        btnDeleteGuest.setOnAction(this::deleteUser);
        
        
        
         //Sets the columns the attributes to use
        columnDni.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        
        //Create an observable lis for the users
        usersData = FXCollections.observableArrayList
        (usersManager.getAllUsers());
        
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
        btnModifyGuest.setDisable(true);
        
        fieldChange(invisible); 
    }
    
    public void handleUserTableFocus(ObservableValue observable, Object oldValue, Object newValue){
        fieldChange(visible);
        fieldChange(disable);
        btnSaveChanges.setDisable(true);
        btnSaveChanges.setVisible(false);
        btnInsertGuest.setDisable(true);
        btnSaveChanges.setVisible(false);
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
            btnModifyGuest.setDisable(false);
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
    
    public void updateGuest(ActionEvent event){
        //Enable the buttons needed to modify a guest
        btnCancel.setDisable(false);
        btnSaveChanges.setVisible(true);
        btnSaveChanges.setDisable(false);
        btnSaveChanges.toFront();
        //Enables all the fields
        fieldChange(visible);
        fieldChange(enable);
        
        
    }
    
    public void newGuest(ActionEvent event){
        // Enable the buttons needed to create a new guest
        btnCancel.setDisable(false);
        btnInsertGuest.setVisible(true);
        btnInsertGuest.setDisable(false);
        
        // Enables all the fields
        fieldChange(enable);
        fieldChange(visible);        
        fieldChange(clean);
        
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
                break;
            case 3:
                //Enables the fields
                txtDni.setDisable(false);
                txtEmail.setDisable(false);
                txtFirstSurname.setDisable(false);
                txtLogin.setDisable(false);
                txtName.setDisable(false);
                txtSecondSurname.setDisable(false);
                txtDni.setEditable(true);
                txtEmail.setEditable(true);
                txtFirstSurname.setEditable(true);
                txtLogin.setEditable(true);
                txtName.setEditable(true);
                txtSecondSurname.setEditable(true);
                break;
            case 4:
                //Disables the fields
                txtDni.setDisable(true);
                txtEmail.setDisable(true);
                txtFirstSurname.setDisable(true);
                txtLogin.setDisable(true);
                txtName.setDisable(true);
                txtSecondSurname.setDisable(true);
                break;
            case 5:
                //Deletes all the existing data
                txtDni.setText("");
                txtEmail.setText("");
                txtFirstSurname.setText("");
                txtLogin.setText("");
                txtName.setText("");
                txtSecondSurname.setText("");
                break;
        }
    }
    
    private void saveNewGuest(ActionEvent event){
        if (checkFields()){
            //Cuando se quiera hacer algo con el CRUD usar userManager
            //UserBean userNew=new UserBean();
            //userNew=getUserFromFields();
        
            //Updates the user
            try{
                usersManager.createUser(getUserFromFields());
            }catch(BusinessLogicException ble){
                LOGGER.info("The update failed "+ble.getMessage());
            }
        }
    }

    private void saveUpdateGuest(ActionEvent event){
        LOGGER.info("SE HACE EL BOTON DE UPDATE");
        if (checkFields()){
            //Cuando se quiera hacer algo con el CRUD usar userManager
            //UserBean userUpdate=new UserBean();
            //userUpdate=getUserFromFields();
        
            LOGGER.info("Entrando en save update guest");
            //Updates the user
            try{
                usersManager.updateUser(getUserFromFields(),tableGuest.getSelectionModel().getSelectedItem().getId());
            }catch(BusinessLogicException ble){
                LOGGER.info("The update failed "+ble.getMessage());
            }
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
                fieldChange(invisible);
                fieldChange(clean);
                btnCancel.setDisable(true);
                btnNewGuest.setDisable(true);
                btnModifyGuest.setDisable(true);
                btnSaveChanges.setVisible(false);
                btnInsertGuest.setVisible(false);
                tableGuest.getSelectionModel().clearSelection();
            }else if(result.get()==ButtonType.CANCEL){
                alert.close();
            }
        } catch(Exception ex){
            LOGGER.severe(ex.getMessage());
        }
    }

    private UserBean getUserFromFields() {
        user= new UserBean();
        //Sets the attributes with the fields
        user.setEmail(txtEmail.getText().toString());
        user.setLogin(txtLogin.getText().toString());
        user.setName(txtEmail.getText().toString());
        user.setSurname1(txtFirstSurname.getText().toString());
        user.setSurname2(txtSecondSurname.getText().toString());
        user.setId(txtDni.getText().toString());
        
        //Gets the unmodificable attributes
        user.setPassword(tableGuest.getSelectionModel().getSelectedItem().getPassword());
        user.setLastPasswordChange(tableGuest.getSelectionModel().getSelectedItem().getLastPasswordChange());
        
        return user;
    }
    
    private void deleteUser(ActionEvent event){
        try{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION.CONFIRMATION);
            alert.setTitle("Borrar");
            alert.setContentText("¿Desea borrar el usuario?");    
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get()== ButtonType.OK){
                usersManager.deleteUser(tableGuest.getSelectionModel().getSelectedItem().getId());
            }else if(result.get()==ButtonType.CANCEL){
                alert.close();
            }
        }catch(BusinessLogicException ble){
            LOGGER.info("Delete failed "+ble.getMessage());
        }catch(Exception e){
            LOGGER.info("Error"+e.getMessage());
        }   
    }
}
