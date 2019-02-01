/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.ui.controller;

import albergueperronclient.exceptions.CreateException;
import albergueperronclient.exceptions.DeleteException;
import albergueperronclient.exceptions.UpdateException;
import albergueperronclient.logic.BlackListManager;
import albergueperronclient.logic.RoomManagerFactory;
import albergueperronclient.logic.UsersManager;
import albergueperronclient.modelObjects.RoomBean;
import albergueperronclient.modelObjects.Status;
import albergueperronclient.modelObjects.UserBeanMongo;
import albergueperronclient.modelObjects.UserBean;
import static albergueperronclient.ui.controller.GenericController.LOGGER;
import java.util.Optional;
import javafx.application.Platform;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Controller class for the BlackList view
 * @author Alatz
 */
public class BlackListFXMLController extends GenericController {
    @FXML
    private MenuItem menuGuest;
    @FXML
    private MenuItem menuPet;
    @FXML
    private MenuItem menuIncidents;
    @FXML
    private MenuItem menuStay;
    @FXML
    private MenuItem menuBlackList;
    @FXML
    private MenuItem menuLogOut;
    @FXML
    private MenuItem menuExit;
    @FXML
    private MenuItem menuRoom;
    @FXML
    private Text lblId;
    @FXML
    private Text lblFullname;
    @FXML
    private TextArea txtReason;
    @FXML
    private Button btnReturn;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnNew;
    @FXML
    private Button btnInsert;
    @FXML
    private Button btnSaveChanges;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnDelete;
    @FXML
    private TableView tableBlackList;
    @FXML
    private TableColumn columnId;
    @FXML
    private TableColumn columnName;
    @FXML
    private TableColumn columnSurname1;
    @FXML
    private TableColumn columnSurname2;
    @FXML
    private TableColumn columnReason;
    @FXML
    private Text lblCbUsers;
    @FXML
    private ComboBox cbUsers;
    
    private BlackListManager blackListManager;
    private UsersManager userManager;
    private UserBeanMongo selectedUser;
    
    /**
     * Sets the logic manager for the room view
     * @param blackListManager logic manager
    */
    public void setLogicManager(BlackListManager blackListManager, UsersManager userManager){
        this.blackListManager = blackListManager;
        this.userManager = userManager;
    }
    
    /**
     * InitStage method for the Room view
     * @param root 
     */
    public void initStage(Parent root){
        try{
            //Create scene
            Scene scene = new Scene(root);
            //stage = new Stage();
            //Associate scene to stage
            stage.setScene(scene);
            stage.setTitle("BlackList");
            stage.setResizable(false);
            //set window's events handlers
            stage.setOnShowing(this::handleWindowShowing);
        
            ObservableList<UserBeanMongo> users =
                    FXCollections.observableArrayList(blackListManager.findAllUsersFromBlackList());
        
            columnId.setCellValueFactory(
                    new PropertyValueFactory<>("id"));
            columnName.setCellValueFactory(
                    new PropertyValueFactory<>("name"));
            columnSurname1.setCellValueFactory(
                    new PropertyValueFactory<>("surname1"));
            columnSurname2.setCellValueFactory(
                    new PropertyValueFactory<>("surname2"));
            columnReason.setCellValueFactory(
                    new PropertyValueFactory<>("reason"));
            
            tableBlackList.setItems(users);
            
            ObservableList<UserBean> usersForCb =
                    FXCollections.observableArrayList(userManager.getAllUsers());
            
            cbUsers.setItems(usersForCb);
            
            menuGuest.setOnAction(this::goToGuestsView);
            menuPet.setOnAction(this::goToPetsView);
            menuStay.setOnAction(this::goToStaysView);
            menuLogOut.setOnAction(this::logOut);
            menuExit.setOnAction(this::exit);
            menuRoom.setOnAction(this::goToRoomView);
            menuIncidents.setOnAction(this::goToIncidentView);
            btnReturn.setOnAction(this::returnToMenu);
            btnNew.setOnAction(this::enableNewInsertForm);
            btnInsert.setOnAction(this::addUserToBlackList);
            btnModify.setOnAction(this::enableUpdateBlackListForm);
            btnSaveChanges.setOnAction(this::updateUserOnBlackList);
            btnCancel.setOnAction(this::disposeBlackListForm);
            btnDelete.setOnAction(this::deleteUserFromBlackList);
            tableBlackList.getSelectionModel().selectedItemProperty().
                    addListener(this::onTableSelectionChanged);
            txtReason.textProperty().addListener(this::onTextChanged);
            cbUsers.valueProperty().addListener(this::fillFields);
            
            stage.show();
        } catch(Exception ex){
            ex.getMessage();
            String message = ex.getMessage();
        }
    }
    
    /**
     * OnShowing handler for the Room view
     * @param event 
     */
    public void handleWindowShowing(WindowEvent event){
        btnModify.setDisable(true);
        btnCancel.setDisable(true);
        btnSaveChanges.setDisable(true);
        btnSaveChanges.setVisible(false);
        btnNew.setDisable(false);
        btnInsert.setDisable(true);
        btnInsert.setVisible(false);
        lblCbUsers.setVisible(false);
        cbUsers.setDisable(true);
        cbUsers.setVisible(false);
        
        txtReason.setDisable(true);
        menuBlackList.setDisable(true);
    }
    
    /**
     * Enables the view fields for blackList user insertion
     * @param event 
     */
    public void enableNewInsertForm(ActionEvent event){
        btnInsert.setVisible(true);
        btnCancel.setDisable(false);
        btnNew.setDisable(true);
        //--TOFIX --> Pensar como meter el resto de datos
        //Puedo cargar todos los usuarios y mostrarlos en una combobox
        txtReason.setDisable(false);
        
        btnSaveChanges.setDisable(true);
        btnSaveChanges.setVisible(false);
        txtReason.setText("");
        cbUsers.getSelectionModel().clearSelection();
        tableBlackList.getSelectionModel().clearSelection();
        tableBlackList.setDisable(true);
        
        btnModify.setDisable(true);
        btnDelete.setDisable(true);
        
        cbUsers.setVisible(true);
        cbUsers.setDisable(false);
        lblCbUsers.setVisible(true);
    }
    
    /**
     * Adds a user to the blacklist
     * @param event 
     */
    public void addUserToBlackList(ActionEvent event){
        try{
            if(checkForData()){                
                if(cbUsers.getSelectionModel().getSelectedItem() instanceof UserBean){
                    Boolean alreadyOnTable = false;
                    UserBean selectedUser = (UserBean) cbUsers.getSelectionModel().getSelectedItem();
                    for(int i = 0; i < tableBlackList.getItems().size(); i++){
                        UserBeanMongo ubm = (UserBeanMongo) tableBlackList.getItems().get(i);
                        if(selectedUser.getId().equalsIgnoreCase(ubm.getId())){
                            alreadyOnTable = true;
                            break;
                        }
                    }
                    if(!alreadyOnTable){
                        String id = lblId.getText();
                        UserBean user = (UserBean)cbUsers.getSelectionModel().getSelectedItem();
                        String reason = txtReason.getText();
                
                        UserBeanMongo newUser = new UserBeanMongo(id, user.getName(), user.getSurname1(), user.getSurname2(), reason);
                        blackListManager.addUserToBlackList(newUser);
                
                        btnCancel.setDisable(true);
                        btnInsert.setDisable(true);
                        btnInsert.setVisible(false);
                        btnNew.setDisable(false);
                        txtReason.setText("");
                
                        cbUsers.getSelectionModel().clearSelection();
                        cbUsers.setDisable(true);
                        cbUsers.setVisible(false);
                        lblCbUsers.setVisible(false);
                        tableBlackList.getItems().add(newUser);
                        tableBlackList.setDisable(false); 
                        tableBlackList.refresh();                    
                    } else{
                        //--TOFIX --> Advertir al usuario de que el usuario elegido ya se encuentra en la tabla
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Inserción de usuario");
                        alert.setContentText("Aviso. El usuario seleccionado "
                                + "ya se encuentra en la lista negra. No es "
                                + "posible añadir los datos.");        
                        alert.showAndWait();
                    }
                }
            } else{
                //--TOFIX --> Advertir al usuario de que se requieren todos los datos introducidos para poder crear un nuevo incidente
            }
        }catch(CreateException ce){
            //--TOFIX --> Exception handling
        }catch(Exception ex){
            //--TOFIX --> Exception handling
        }
    }
    
    /**
     * Disables the view fields for blacklist user insertion/update
     * @param event 
     */
    public void disposeBlackListForm(ActionEvent event){
        if(btnInsert.isVisible()){
            txtReason.setText("");
            btnInsert.setDisable(true);
            btnInsert.setVisible(false);
            
            lblCbUsers.setVisible(false);
            cbUsers.getSelectionModel().clearSelection();
            cbUsers.setDisable(true);
            cbUsers.setVisible(false);
        } else {
            txtReason.setText(selectedUser.getReason());
            btnDelete.setDisable(true);
            btnSaveChanges.setDisable(true);
            btnSaveChanges.setVisible(false);
            btnModify.setDisable(false);
            btnDelete.setDisable(false);
        }
        btnCancel.setDisable(true);
        btnNew.setDisable(false);
        txtReason.setDisable(true);
        tableBlackList.setDisable(false);
   }
    
    /**
     * Enables the view fields for the update of the selected user of the blacklist
     * @param event 
     */
    public void enableUpdateBlackListForm(ActionEvent event){
        btnSaveChanges.setDisable(false);
        btnSaveChanges.setVisible(true);
        btnCancel.setDisable(false);
        btnNew.setDisable(true);
        btnDelete.setDisable(true);
        txtReason.setDisable(false);
        btnInsert.setDisable(true);
        btnInsert.setVisible(false);
        tableBlackList.setDisable(true);
    }
    
    /**
     * Updates the selected room
     * @param event 
     */
    public void updateUserOnBlackList(ActionEvent event){
        try{
            if(checkForData()){
                UserBeanMongo userToModify = selectedUser;
                userToModify.setReason(txtReason.getText());
                blackListManager.updateUserOnBlackList(userToModify);
        
                txtReason.setDisable(true);
                btnCancel.setDisable(true);
                btnSaveChanges.setDisable(true);
                btnModify.setDisable(false);
                btnDelete.setDisable(false);
                btnNew.setDisable(false);
                tableBlackList.setDisable(false);
                tableBlackList.refresh();
            }
            else{
                //--TOFIX --> Mostrar un aviso al usuario para advertirle de que se requieren todos los datos para poder actualizar
            }
        }catch(UpdateException ue){
            //--TOFIX --> Exception handling
        }catch(Exception ex){
            //--TOFIX --> Exception handling
        }
    }

    /**
     * Deletes the selected user from the black list
     * @param event
    */
    public void deleteUserFromBlackList(ActionEvent event){
        try{
            blackListManager.deleteUserFromBlackList(selectedUser.getId());
            tableBlackList.getItems().remove(selectedUser);
            tableBlackList.refresh();
        }catch(DeleteException de){
            //--TOFIX --> Exception handling
        }catch(Exception ex){
            //--TOFIX --> Exception handling
        }
    }
    
    public void fillFields(ObservableValue value, Object oldValue, Object newValue){
        if(newValue != null){
            UserBean user = (UserBean) newValue;
            lblFullname.setText(user.getName() + " " + user.getSurname1() + " " + user.getSurname2());
            lblId.setText(user.getId().toString());
        }
    }
    
    /**
     * Checks if the needed data to create/update a room is inserted
     * @return 
     */
    public Boolean checkForData(){
        //--TOFIX --> When clicking the save changes or insert buttons,
        //check all fields are correctly filled before allowing the logic
        //manager operations to happen
        Boolean formHasCorrectData = true;
        if(txtReason.getText().trim().length()==0){
            formHasCorrectData = false;
        }
        if(btnInsert.isVisible()){
            if(!(cbUsers.getSelectionModel().getSelectedItem() instanceof UserBean)){
                formHasCorrectData = false;
            }
        }
        return formHasCorrectData;
    }
    
    /**
     * Returns to the menu view
     * @param event 
     */
    public void returnToMenu(ActionEvent event){
        /*try{
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/albergueperronclient/ui/fxml/UILogged.fxml"));
            Parent root = loader.load();
            //Get controller from the loader
            UILoggedFXMLController menuController = loader.getController();
        
            menuController.setLogicManager(UILoggedManagerFactory.getLoggedManager());
            //Send the current stage for coming back later
            //roomController.setPreviousStage(stage);
            //Initialize the primary stage of the application
            menuController.initStage(root);
            //--TOFIX --> Decidir si esconder el stage o cerrarlo
            stage.hide();
            stage.close();
        }catch(Exception e){
            LOGGER.severe(e.getMessage());
            showErrorAlert("Error al redirigir al menú.");
        }*/
    }
    
    /**
     * Logs out, sending the user to the login page
     * @param event 
     */
    public void logOut(ActionEvent event){
        try{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cerrar Sesión");
            alert.setContentText("¿Desea cerrar sesion?");        
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get()== ButtonType.OK){
                stage.close();
                //--TOFIX --> Abrir la ventana de login
                /*try{
                    FXMLLoader loader = new FXMLLoader(getClass()
                            .getResource("/albergueperronclient/ui/fxml/UILogin.fxml"));
                    Parent root = loader.load();
                    //Get controller from the loader
                    UILoginFXMLController menuController = loader.getController();
        
                    menuController.setLogicManager(UILoginManagerFactory.getLoginManager());
                    //Send the current stage for coming back later
                    //roomController.setPreviousStage(stage);
                    //Initialize the primary stage of the application
                    menuController.initStage(root);
                    //--TOFIX --> Decidir si esconder el stage o cerrarlo
                    stage.hide();
                    stage.close();
                }catch(Exception e){
                    LOGGER.severe(e.getMessage());
                    showErrorAlert("Error al redirigir al menú.");
                }*/
            }else{
                LOGGER.info("Logout cancelled.");
            } 
        }catch(Exception ex){
            LOGGER.severe(ex.getMessage());
        }
    }
    
    /**
     * Method to exit the application
     * @param event
     */
    public void exit(ActionEvent event){
        Platform.exit();
    }
    
    /**
     * Opens the guest view
     * @param event 
     */
    public void goToGuestsView(ActionEvent event){
        //calls the logicManager register functio
        /*try{
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/albergueperronclient/ui/fxml/UIGuest.fxml"));
            Parent root = loader.load();
            //Get controller from the loader
            UIGuestFXMLController guestController = loader.getController();
        
            guestController.setLogicManager(GuestManagerFactory.getGuestManager());
            //Send the current stage for coming back later
            guestController.setPreviousStage(stage);
            //Initialize the primary stage of the application
            guestController.initStage(root);
            //--TOFIX --> Decidir si esconder el stage o cerrarlo
            stage.hide();
            stage.close();
        }catch(Exception e){
            LOGGER.severe(e.getMessage());
            showErrorAlert("Error al redirigir a la vista de huéspedes.");
        }*/
    }
    
    /**
     * Opens the pet view
     * @param event 
     */
    public void goToPetsView(ActionEvent event){
         /*try{
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/albergueperronclient/ui/fxml/UIPet.fxml"));
            Parent root = loader.load();
            //Get controller from the loader
            UIPetFXMLController petController = loader.getController();
        
            petController.setLogicManager(PetManagerFactory.getPetManager());
            //Send the current stage for coming back later
            petController.setPreviousStage(stage);
            //Initialize the primary stage of the application
            petController.initStage(root);
            //--TOFIX --> Decidir si esconder el stage o cerrarlo
            stage.hide();
            stage.close();
        }catch(Exception e){
            LOGGER.severe(e.getMessage());
            showErrorAlert("Error al redirigir a la vista de mascotas.");
        }*/
    }
    
    /**
     * Opens the stay view
     * @param event 
     */
    public void goToStaysView(ActionEvent event){
        /*try{
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/albergueperronclient/ui/fxml/UIStay.fxml"));
            Parent root = loader.load();
            //Get controller from the loader
            UIStayFXMLController stayController = loader.getController();
        
            stayController.setLogicManager(StayManagerFactory.getStayManager());
            //Send the current stage for coming back later
            stayController.setPreviousStage(stage);
            //Initialize the primary stage of the application
            stayController.initStage(root);
            //--TOFIX --> Decidir si esconder el stage o cerrarlo
            stage.hide();
            stage.close();
        }catch(Exception e){
            LOGGER.severe(e.getMessage());
            showErrorAlert("Error al redirigir a la vista de estancias.");
        }*/
    }
    
    /**
     * Opens the room view
     * @param event 
     */
    public void goToRoomView(ActionEvent event){
        /*try{
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/albergueperronclient/ui/fxml/Room.fxml"));
            Parent root = loader.load();
            //Get controller from the loader
            RoomFXMLController roomController = loader.getController();
        
            roomController.setLogicManager(RoomManagerFactory.getRoomManager());
            //Send the current stage for coming back later
            //roomController.setPreviousStage(stage);
            //Initialize the primary stage of the application
            roomController.initStage(root);
            //--TOFIX --> Decidir si esconder el stage o cerrarlo
            stage.hide();
            stage.close();
        }catch(Exception e){
            LOGGER.severe(e.getMessage());
            showErrorAlert("Error al redirigir a la vista de habitaciones.");
        }*/
    }
    
    /**
     * Opens the incident view
     * @param event 
     */
    public void goToIncidentView(ActionEvent event){
        /*try{
            //Get the logic manager object for the initial stage
            IncidentManager incidentManager = IncidentManagerFactory.getIncidentManager();
            UsersManager userManager = UsersManagerFactory.getUsersManager();
            
            //Load the fxml file
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/albergueperronclient/ui/fxml/Incident.fxml"));
            Parent root = loader.load();
            //Get controller from the loader
            IncidentFXMLController incidentController = loader.getController();

            incidentController.setLogicManager(incidentManager, roomManager,
                    userManager);

            //Send the current stage for coming back later
            incidentController.setPreviousStage(stage);
            //Initialize the primary stage of the application
            incidentController.initStage(root);
            //--TOFIX --> Decidir si esconder el stage o cerrarlo
            stage.hide();
            stage.close();
        }catch(Exception e){
            LOGGER.severe(e.getMessage());
            showErrorAlert("Error al redirigir a la vista de estancias.");
        }*/
    }
    
    /**
     * Handles the table selection action
     * @param observable
     * @param oldValue
     * @param newValue 
     */
    public void onTableSelectionChanged(ObservableValue observable,
             Object oldValue,
             Object newValue){
        if(newValue!=null){            
            selectedUser = (UserBeanMongo)newValue;
            txtReason.setText(selectedUser.getReason().toString());
            lblFullname.setText(selectedUser.getName() + " " + selectedUser.getSurname1() + " " + selectedUser.getSurname2());
            lblId.setText(selectedUser.getId().toString());
            
            btnModify.setDisable(false);
            btnDelete.setDisable(false);
        }else{
        //If there is not a row selected, clean window fields 
        //and disable create, modify and delete buttons
            txtReason.setText("");
            lblFullname.setText("");
            lblId.setText("");
            
            btnModify.setDisable(true);
            btnDelete.setDisable(true);
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
        if(txtReason.getText().trim().isEmpty() || txtReason.getText().trim().length() > 200){
            if(btnSaveChanges.isVisible()){
                btnSaveChanges.setDisable(true);
            }
            else if(btnInsert.isVisible()){
                btnInsert.setDisable(true);
            }
        }else{
            if(btnSaveChanges.isVisible() && !(newValue.equalsIgnoreCase(selectedUser.getReason()))){
                btnSaveChanges.setDisable(false);
            }
            else if(btnInsert.isVisible()){
                btnInsert.setDisable(false);
            }
        }
    }
}
