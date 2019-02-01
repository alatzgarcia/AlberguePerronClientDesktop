/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.ui.controller;

import albergueperronclient.exceptions.UpdateException;
import albergueperronclient.logic.IncidentManager;
import albergueperronclient.logic.IncidentManagerFactory;
import albergueperronclient.logic.RoomManager;
import albergueperronclient.logic.RoomManagerFactory;
import albergueperronclient.logic.UsersManager;

import albergueperronclient.modelObjects.Privilege;
import albergueperronclient.modelObjects.RoomBean;
import albergueperronclient.modelObjects.Status;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Controller class for the Room view
 * @author Alatz
 */
public class RoomFXMLController extends GenericController {
    @FXML
    private Text lblRoomNum;
    @FXML
    private Text lblAvailableSpace;
    @FXML
    private TableColumn columnRoomNum;
    @FXML
    private TableColumn columnTotal;
    @FXML
    private TableColumn columnFree;
    @FXML
    private TableColumn columnStatus;
    @FXML
    private TextField txtTotal;
    @FXML
    private ComboBox cbStatus;
    @FXML
    private Button btnReturn;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnSaveChanges;
    @FXML
    private Button btnCancel;
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
    private MenuItem menuRoom;
    @FXML
    private MenuItem menuLogOut;
    @FXML
    private MenuItem menuExit;
    @FXML
    private TableView tableRoom;
    
    private RoomManager roomManager;
    private RoomBean selectedRoom;
    
    /**
     * Sets the logic manager for the room view
     * @param roomManager logic manager
    */
    public void setLogicManager(RoomManager roomManager){
        this.roomManager = roomManager;
    }
    
    /**
     * InitStage method for the Room view
     * @param root 
     */
    public void initStage(Parent root){
        try{
            //Create scene
            Scene scene = new Scene(root);
            stage = new Stage();
            //Associate scene to stage
            stage.setScene(scene);
            stage.setTitle("Room");
            stage.setResizable(false);
            //set window's events handlers
            stage.setOnShowing(this::handleWindowShowing);
        
            ObservableList<RoomBean> rooms =
                    FXCollections.observableArrayList(roomManager.findAllRooms());
        
            columnRoomNum.setCellValueFactory(
                    new PropertyValueFactory<>("roomNum"));
            columnTotal.setCellValueFactory(
                    new PropertyValueFactory<>("totalSpace"));
            columnFree.setCellValueFactory(
                    new PropertyValueFactory<>("availableSpace"));
            columnStatus.setCellValueFactory(
                    new PropertyValueFactory<>("status"));
            
            tableRoom.setItems(rooms);
            
            cbStatus.setItems(FXCollections.observableArrayList(Status.values()));
            
            menuGuest.setOnAction(this::goToGuestsView);
            menuPet.setOnAction(this::goToPetsView);
            menuStay.setOnAction(this::goToStaysView);
            menuBlackList.setOnAction(this::goToBlackListView);
            menuLogOut.setOnAction(this::logOut);
            menuExit.setOnAction(this::exit);
            btnReturn.setOnAction(this::returnToMenu);
            btnModify.setOnAction(this::enableUpdateForm);
            btnSaveChanges.setOnAction(this::updateRoom);
            btnCancel.setOnAction(this::disposeUpdateForm);
            tableRoom.getSelectionModel().selectedItemProperty().
                    addListener(this::onTableSelectionChanged);
            txtTotal.textProperty().addListener(this::onTextChanged);
        
            stage.show();
        } catch(Exception ex){
            
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
        
        txtTotal.setDisable(true);
        cbStatus.setDisable(true);
        menuRoom.setDisable(true);
    }
    
    /**
     * Enables the view fields for room update of the selected room
     * @param event 
     */
    public void enableUpdateForm(ActionEvent event){
        txtTotal.setDisable(false);
        btnCancel.setDisable(false);
        btnSaveChanges.setDisable(false);
        cbStatus.setDisable(false);
        btnModify.setDisable(true);
        tableRoom.setDisable(true);
    }
    
    /**
     * Updates the selected room
     * @param event 
     */
    public void updateRoom(ActionEvent event){
        try{
            if(checkForData()){
                RoomBean roomToModify = selectedRoom;
                roomToModify.setTotalSpace(Integer.parseInt(txtTotal.getText()));
                roomToModify.setStatus((Status)cbStatus.getSelectionModel().getSelectedItem());
                roomManager.updateRoom(roomToModify);
        
                txtTotal.setDisable(true);
                btnCancel.setDisable(true);
                btnSaveChanges.setDisable(true);
                cbStatus.setDisable(true);
                btnModify.setDisable(false);
                tableRoom.setDisable(false);
                tableRoom.refresh();
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
     * Checks if the needed data to create/update a room is inserted
     * @return 
     */
    public Boolean checkForData(){
        //--TOFIX --> When clicking the save changes or insert buttons,
        //check all fields are correctly filled before allowing the logic
        //manager operations to happen
        Boolean formHasCorrectData = true;
        if(txtTotal.getText().trim().length()==0){
            formHasCorrectData = false;
        }
        return formHasCorrectData;
    }
    
    /**
     * Disables the view fields for the room update
     * @param event 
     */
    public void disposeUpdateForm(ActionEvent event){
        txtTotal.setDisable(true);
        btnCancel.setDisable(true);
        btnSaveChanges.setDisable(true);
        cbStatus.setDisable(true);
        btnModify.setDisable(false);
        tableRoom.setDisable(false);
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
     * Opens the blacklist view
     * @param event 
     */
    public void goToBlackListView(ActionEvent event){
        /*try{
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/albergueperronclient/ui/fxml/UIBlackList.fxml"));
            Parent root = loader.load();
            //Get controller from the loader
            UIBlackListFXMLController blackListController = loader.getController();
        
            blackListController.setLogicManager(BlackListManagerFactory.getBlackListManager());
            //Send the current stage for coming back later
            blackListController.setPreviousStage(stage);
            //Initialize the primary stage of the application
            blackListController.initStage(root);
            //--TOFIX --> Decidir si esconder el stage o cerrarlo
            stage.hide();
            stage.close();
        }catch(Exception e){
            LOGGER.severe(e.getMessage());
            showErrorAlert("Error al redirigir a la vista de la lista negra.");
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
            selectedRoom = (RoomBean)newValue;
            txtTotal.setText(selectedRoom.getTotalSpace().toString());
            cbStatus.getSelectionModel().select(selectedRoom.getStatus());
            lblAvailableSpace.setText(selectedRoom.getAvailableSpace().toString());
            
            btnModify.setDisable(false);
        }else{
        //If there is not a row selected, clean window fields 
        //and disable create, modify and delete buttons
            lblRoomNum.setText("");
            txtTotal.setText("");
            lblAvailableSpace.setText("");
            cbStatus.getSelectionModel().clearSelection();
            btnModify.setDisable(true);
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
        if (!newValue.matches("\\d*")) {
            txtTotal.setText(newValue.replaceAll("[^\\d]", ""));
        }
        if(!(newValue.equalsIgnoreCase(selectedRoom.getTotalSpace().toString())) || oldValue.length() == 3){
        //if(!oldValue.equals("")){
            if(txtTotal.getText().trim().isEmpty() || txtTotal.getText().trim().length() > 2 || txtTotal.getText().trim().equals("0") || txtTotal.getText().trim().equals("00")){
                btnSaveChanges.setDisable(true);
            }else{
                btnSaveChanges.setDisable(false);
            }
        }
    }
}
