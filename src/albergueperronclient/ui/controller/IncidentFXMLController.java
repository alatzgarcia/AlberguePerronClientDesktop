/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.ui.controller;

import albergueperronclient.exceptions.CreateException;
import albergueperronclient.exceptions.DeleteException;
import albergueperronclient.exceptions.ReadException;
import albergueperronclient.exceptions.UpdateException;
import albergueperronclient.logic.IncidentManager;
import albergueperronclient.logic.RoomManager;
import albergueperronclient.logic.RoomManagerFactory;
import albergueperronclient.logic.UsersManager;
import albergueperronclient.modelObjects.IncidentBean;
import albergueperronclient.modelObjects.Privilege;
import albergueperronclient.modelObjects.RoomBean;
import albergueperronclient.modelObjects.UserBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.WindowEvent;

/**
 * Controller class for the IncidentBean view of the application
 * @author Alatz
 */
public class IncidentFXMLController extends GenericController {
    @FXML
    private Button btnNew;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSaveChanges;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnInsert;
    @FXML
    private Button btnReturn;
    @FXML
    private TextField txtIncidentType;
    @FXML
    private TextArea txtDescription;
    @FXML
    private ComboBox cbEmployee;
    @FXML
    private ListView lstImplicateds;
    @FXML
    private TableView tableIncidents;
    @FXML
    private TableColumn columnIncidentType;
    @FXML
    private TableColumn columnDescription;
    @FXML
    private ComboBox cbRoom;
    @FXML
    private MenuItem menuGuests;
    @FXML
    private MenuItem menuPets;
    @FXML
    private MenuItem menuIncidents;
    @FXML
    private MenuItem menuStays;
    @FXML
    private MenuItem menuBlackList;
    @FXML
    private MenuItem menuLogOut;
    @FXML
    private MenuItem menuExit;
    @FXML
    private MenuItem menuRoom;
    @FXML
    private Button btnListAdd;
    @FXML
    private Button btnListRemove;
    @FXML
    private ComboBox cbGuests;
    
    private IncidentManager incidentManager;
    private RoomManager roomManager;
    private UsersManager userManager;
    private IncidentBean selectedIncident;
    
    /**
     * Sets the logic Manager for IncidentBean
     * @param incidentManager manager of the client application logic side
     * for incidents
     * @param roomManager
     * @param userManager
     */
    public void setLogicManager(IncidentManager incidentManager,
            RoomManager roomManager, UsersManager userManager){
        this.incidentManager = incidentManager;
        this.roomManager = roomManager;
        this.userManager = userManager;
    }
    
    /**
     * InitStage method for the UILogin view
     * @param root 
     * @throws albergueperronclient.exceptions.ReadException 
     */
     public void initStage(Parent root) throws ReadException {
        try {
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Incidents");
            stage.setResizable(false);
            
            stage.setOnShowing(this::handleWindowShowing);
            
            txtIncidentType.textProperty().addListener(this::onTextChanged);
            //--TOFIX Controllar que no escriba más de X caracteres en la descripción
            txtDescription.textProperty().addListener(this::onTextChanged);
            tableIncidents.getSelectionModel().selectedItemProperty().
                    addListener(this::onTableSelectionChanged);
            
            columnIncidentType.setCellValueFactory(
                    new PropertyValueFactory<>("incidentType"));
            columnDescription.setCellValueFactory(
                    new PropertyValueFactory<>("description"));
            /*ObservableList<Incident> incidents =
                    FXCollections.observableArrayList(incidentManager.findAllIncidents());
            //--TOFIX --> Insertar incidentes en la tabla
            */ObservableList<RoomBean> rooms =
                    FXCollections.observableArrayList(roomManager.findAllRooms());
            cbRoom.setItems(rooms);
            ObservableList<UserBean> employees =
                    FXCollections.observableArrayList(userManager.findUsersByPrivilege(Privilege.EMPLOYEE));
            cbEmployee.setItems(employees);
            //--TOFIX --> Arreglar            
            //employees.add(userManager.findUsersByPrivilege(Privilege.ADMIN));
            ObservableList<UserBean> guests =
                    FXCollections.observableArrayList(userManager.findUsersByPrivilege(Privilege.USER));
            cbGuests.setItems(guests);
            
            //--TOFIX Coger datos para la tabla
            ObservableList<IncidentBean> incidents = 
                    FXCollections.observableArrayList(incidentManager.findAllIncidents());
            tableIncidents.setItems(incidents);
           
            btnNew.setOnAction(this::enableNewIncidentForm);
            btnCancel.setOnAction(this::disposeIncidentForm);
            btnSaveChanges.setOnAction(this::updateIncident);
            btnDelete.setOnAction(this::deleteIncident);
            btnModify.setOnAction(this::enableUpdateIncidentForm);
            btnInsert.setOnAction(this::createIncident);
            btnReturn.setOnAction(this::returnToPrevious);
            btnListAdd.setOnAction(this::addToList);
            btnListRemove.setOnAction(this::removeFromList);
            menuGuests.setOnAction(this::goToGuestsView);
            menuPets.setOnAction(this::goToPetsView);
            menuStays.setOnAction(this::goToStaysView);
            menuBlackList.setOnAction(this::goToBlackListView);
            menuLogOut.setOnAction(this::logOut);
            menuExit.setOnAction(this::exit);
            
            stage.show();
        } catch (Exception ex){
//} catch (BusinessLogicException ex) {
            Logger.getLogger(IncidentFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * OnShowing handler for the UILogin view
     * @param event event of window showing/opening that calls to the method
     */
    public void handleWindowShowing(WindowEvent event){
        btnDelete.setDisable(true);
        btnModify.setDisable(true);
        btnCancel.setDisable(true);
        
        btnSaveChanges.setDisable(true);
        btnSaveChanges.setVisible(false);
        btnInsert.setDisable(true);
        btnInsert.setVisible(false);
        btnListAdd.setDisable(true);
        btnListRemove.setDisable(true);
        txtIncidentType.setDisable(true);
        txtDescription.setDisable(true);
        lstImplicateds.setDisable(true);
        lstImplicateds.setEditable(true);
        cbEmployee.setDisable(true);
        cbEmployee.getSelectionModel().selectFirst();
        cbGuests.setDisable(true);
        cbRoom.setDisable(true);
        cbRoom.getSelectionModel().selectFirst();
        menuIncidents.setDisable(true);
        
        /*btnLogin.setDisable(true);
        btnLogin.setMnemonicParsing(true);
        btnLogin.setText("_Iniciar Sesión");
        btnExit.setMnemonicParsing(true);
        btnExit.setText("_Salir");
        txtUsername.requestFocus();*/
        //Settear promptText
    }
    
    public void enableNewIncidentForm(ActionEvent event){
        //Must be disabled until all data is inserted
        //btnInsert.setDisable(false);
        btnInsert.setVisible(true);
        btnCancel.setDisable(false);
        txtIncidentType.setDisable(false);
        txtDescription.setDisable(false);
        lstImplicateds.setDisable(false);
        cbEmployee.setDisable(false);
        cbRoom.setDisable(false);
        btnListAdd.setDisable(false);
        btnListRemove.setDisable(false);
        cbGuests.setDisable(false);
        
        //selectedIncident = null;
        btnSaveChanges.setDisable(true);
        btnSaveChanges.setVisible(false);
        txtDescription.setText("");
        txtIncidentType.setText("");
        tableIncidents.getSelectionModel().clearSelection();
        tableIncidents.setDisable(true);
        
        btnModify.setDisable(true);
        btnDelete.setDisable(true);
    }
    
    public void createIncident(ActionEvent event){
        try{
            //--TOFIX --> Send data of the form
            IncidentBean newIncident = new IncidentBean();
            newIncident.setDescription(txtDescription.getText());
            //--TOFIX --> Conseguir juntar cbEmpleado + listaImplicados
            List<UserBean> implicateds = new ArrayList<UserBean>();
            implicateds.addAll(lstImplicateds.getItems());
            //implicateds.add((UserBean)cbEmployee.getSelectionModel().getSelectedItem());
            //List<UserBean> implicateds = new ArrayList<UserBean>();
            implicateds.add((UserBean)cbEmployee.getSelectionModel().getSelectedItem());
            newIncident.setImplicateds(implicateds);
            newIncident.setIncidentType(txtIncidentType.getText());
            newIncident.setRoom((RoomBean)cbRoom.getSelectionModel().getSelectedItem());
            incidentManager.createIncident(newIncident);
            
            btnCancel.setDisable(true);
            btnInsert.setDisable(true);
            btnInsert.setVisible(false);
            txtDescription.setText("");
            txtDescription.setDisable(true);
            txtIncidentType.setText("");
            txtIncidentType.setDisable(true);
            cbEmployee.setItems(null);
            cbEmployee.setDisable(true);
            lstImplicateds.setItems(null);
            lstImplicateds.setDisable(true);
            cbRoom.getSelectionModel().selectFirst();
            cbRoom.setDisable(true);
            btnListAdd.setDisable(true);
            btnListRemove.setDisable(true);
            cbGuests.setDisable(true);
            
            tableIncidents.getItems().add(newIncident);
            tableIncidents.setDisable(false);
        }catch(CreateException ce){
            //--TOFIX --> Exception handling
        }catch(Exception ex){
            //--TOFIX --> Exception handling
        }
    }
    
    public void disposeIncidentForm(ActionEvent event){
        btnSaveChanges.setDisable(true);
        btnSaveChanges.setVisible(false);
        btnInsert.setDisable(true);
        btnInsert.setVisible(false);
        btnCancel.setDisable(true);
        
        txtIncidentType.setDisable(true);
        txtDescription.setDisable(true);
        lstImplicateds.setDisable(true);
        cbEmployee.setDisable(true);
        cbRoom.setDisable(true);
        
        btnListAdd.setDisable(true);
        btnListRemove.setDisable(true);
        cbGuests.setDisable(true);
        
        cbEmployee.getSelectionModel().selectFirst();
        cbRoom.getSelectionModel().selectFirst();
        lstImplicateds.getSelectionModel().selectFirst();
        txtDescription.setText("");
        txtIncidentType.setText(""); 
        
        tableIncidents.setDisable(false);
   }
    
    public void enableUpdateIncidentForm(ActionEvent event){
        btnSaveChanges.setDisable(false);
        btnSaveChanges.setVisible(true);
        btnCancel.setDisable(false);
        btnListAdd.setDisable(false);
        btnListRemove.setDisable(false);
        cbGuests.setDisable(false);
        
        txtIncidentType.setDisable(false);
        txtDescription.setDisable(false);
        lstImplicateds.setDisable(false);
        cbEmployee.setDisable(false);
        cbRoom.setDisable(false);
        
        btnInsert.setDisable(true);
        btnInsert.setVisible(false);
        //selectedIncident = (IncidentBean) tableIncidents.getSelectionModel().getSelectedItem();
    }
    
    public void updateIncident(ActionEvent event){
        try{
            //--TOFIX --> Send data of the form
            IncidentBean incidentToModify = selectedIncident;
            incidentToModify.setDescription(txtDescription.getText());
            //--TOFIX -- Conseguir loopear la selección de combobox de empleado + lista de implicados
            List<UserBean> implicateds = lstImplicateds.getItems();
            implicateds.add((UserBean)cbEmployee.getSelectionModel().getSelectedItem());
            incidentToModify.setImplicateds(implicateds);
            incidentToModify.setIncidentType(txtIncidentType.getText());
            incidentToModify.setRoom((RoomBean)cbRoom.getSelectionModel().getSelectedItem());
            incidentManager.updateIncident(incidentToModify);
        
            btnCancel.setDisable(true);
            btnInsert.setDisable(true);
            btnInsert.setVisible(false);
            txtDescription.setText("");
            txtDescription.setDisable(true);
            txtIncidentType.setText("");
            txtIncidentType.setDisable(true);
            cbEmployee.setItems(null);
            cbEmployee.setDisable(true);
            lstImplicateds.setItems(null);
            lstImplicateds.setDisable(true);
            cbRoom.getSelectionModel().selectFirst();
            cbRoom.setDisable(true);
            btnListAdd.setDisable(true);
            btnListRemove.setDisable(true);
            cbGuests.setDisable(true);
            
            tableIncidents.refresh();
        }catch(UpdateException ue){
            //--TOFIX --> Exception handling
        }catch(Exception ex){
            //--TOFIX --> Exception handling
        }
    }
    
    public void deleteIncident(ActionEvent event){
        try{
            tableIncidents.getItems().remove(selectedIncident);
            incidentManager.deleteIncident(selectedIncident.getId());
            //tableIncidents.refresh();
        }catch(DeleteException de){
            //--TOFIX --> Exception handling
        }catch(Exception ex){
            //--TOFIX --> Exception handling
        }
    }
    
    public void addToList(ActionEvent event){
        //--TOFIX --> Controlar que si el elemento ya está dentro no se pueda volver a introducir
        lstImplicateds.getItems().add(cbGuests.getSelectionModel().getSelectedItem());
    }
    
    public void removeFromList(ActionEvent event){
        lstImplicateds.getItems().remove(lstImplicateds.getSelectionModel().getSelectedItem());
    }
    
    public void returnToPrevious(ActionEvent event){
        stage.close();
        previousStage.show();
    }
    
    public void logOut(ActionEvent event){
        try{
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Cerrar Sesión");
            alert.setContentText("¿Desea cerrar sesion?");        
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get()== ButtonType.OK){
                stage.close();
                //--TOFIX --> Abrir la ventana de login
            }else{
                LOGGER.info("Logout cancelled.");
            } 
        }catch(Exception ex){
            LOGGER.severe(ex.getMessage());
        }
    }
    
    /**
    * Exit from the application
     * @param event
    */
    public void exit(ActionEvent event){
        Platform.exit();
    }
    
    public void goToGuestsView(ActionEvent event){
        //calls the logicManager register functio
        /*try{
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/signupsigninuidesktop/ui/fxml/UIGuestFXMLController.fxml"));
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
    
    public void goToPetsView(ActionEvent event){
         /*try{
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/signupsigninuidesktop/ui/fxml/UIPetFXMLController.fxml"));
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
    
    public void goToStaysView(ActionEvent event){
        /*try{
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/signupsigninuidesktop/ui/fxml/UIStayFXMLController.fxml"));
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
    
    public void goToBlackListView(ActionEvent event){
        /*try{
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/signupsigninuidesktop/ui/fxml/UIBlackListFXMLController.fxml"));
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
    
    public void goToRoomView(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/signupsigninuidesktop/ui/fxml/RoomFXMLController.fxml"));
            Parent root = loader.load();
            //Get controller from the loader
            RoomFXMLController roomController = loader.getController();
        
            roomController.setLogicManager(RoomManagerFactory.getRoomManager());
            //Send the current stage for coming back later
            roomController.setPreviousStage(stage);
            //Initialize the primary stage of the application
            roomController.initStage(root);
            //--TOFIX --> Decidir si esconder el stage o cerrarlo
            stage.hide();
            stage.close();
        }catch(Exception e){
            LOGGER.severe(e.getMessage());
            showErrorAlert("Error al redirigir a la vista de estancias.");
        }
    }
    
    public void onTableSelectionChanged(ObservableValue observable,
             Object oldValue,
             Object newValue){
        //--TOFIX
        if(newValue!=null){            
            selectedIncident = (IncidentBean)newValue;
            txtIncidentType.setText(selectedIncident.getIncidentType());
            txtDescription.setText(selectedIncident.getDescription());
            //--TOFIX --> Crear una forma de recibir solo el empleado
            //List<UserBean> users = selectedIncident.getImplicateds();
            //List<UserBean> guests = null;
            //List<UserBean> employees = null;
            /*for(UserBean u: users){
                if(u.getPrivilege().equals(Privilege.EMPLOYEE)){
                    employees.add(u);
                }
                else{
                    guests.add(u);
                }
            }
            cbEmployee.getSelectionModel().select(employees);*/
            cbRoom.getSelectionModel().select(selectedIncident.getRoom());
            
            cbEmployee.getSelectionModel().select(selectedIncident.getEmployee());
            lstImplicateds.setItems(FXCollections.observableArrayList(selectedIncident.getGuests()));
            
            btnModify.setDisable(false);
            btnDelete.setDisable(false);
            
            //--TOFIX Pensar si quiero pedir el foco ahí
            //btnModify.requestFocus();
        }else{
        //If there is not a row selected, clean window fields 
        //and disable create, modify and delete buttons
            selectedIncident = null;
            txtIncidentType.setText("");
            txtDescription.setText("");
            cbEmployee.getSelectionModel().clearSelection();
            cbRoom.getSelectionModel().clearSelection();
            lstImplicateds.getSelectionModel().clearSelection();
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
        /*Checks if any of the fields have no text entered 
            and disables the btnLogin button if true  
        */
        if(txtDescription.getText().trim().length()>250 ||
                txtDescription.getText().trim().isEmpty() ||
                txtIncidentType.getText().trim().length()>30 ||
                txtIncidentType.getText().trim().isEmpty()){
            if(btnSaveChanges.isVisible()){
                btnSaveChanges.setDisable(true);
            }
            else if(btnInsert.isVisible()){
                btnInsert.setDisable(true);
            }
        }
        else if(!(txtIncidentType.getText().trim().isEmpty()) &&
                txtDescription.getText().trim().length()<250 &&
                !(txtDescription.getText().trim().isEmpty()) &&
                txtIncidentType.getText().trim().length()<30){
            if(btnSaveChanges.isVisible()){
                btnSaveChanges.setDisable(false);
            }
            else if(btnInsert.isVisible()){
                btnInsert.setDisable(false);
            }
        }
    }
}
