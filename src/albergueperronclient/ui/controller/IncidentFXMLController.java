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
import albergueperronclient.logic.ILogin;
import albergueperronclient.logic.ILoginFactory;
import albergueperronclient.logic.IncidentManager;
import albergueperronclient.logic.RoomManager;
import albergueperronclient.logic.RoomManagerFactory;
import albergueperronclient.logic.UsersManager;
import albergueperronclient.modelObjects.IncidentBean;
import albergueperronclient.modelObjects.Privilege;
import albergueperronclient.modelObjects.RoomBean;
import albergueperronclient.modelObjects.UserBean;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.WindowEvent;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import javafx.stage.Stage;

/**
 * Controller class for the Incident view of the application
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
    @FXML
    private MenuItem menuReport;
    @FXML
    private Button btnReport;
    @FXML
    private DatePicker incidentDate;
    
    /**
     * Logic manager for incidents
     */
    private IncidentManager incidentManager;
    /**
     * Logic manager for rooms
     */
    private RoomManager roomManager;
    /**
     * Logic manager for users
     */
    private UsersManager userManager;
    /**
     * Incident selected in the table
     */
    private IncidentBean selectedIncident;
    
    /**
     * Sets the logic managers for incident view
     * @param incidentManager manager of the client application logic side
     * for incidents
     * @param roomManager manager of the client application logic side
     * for rooms
     * @param userManager manager of the client application logic side
     * for users
     */
    public void setLogicManager(IncidentManager incidentManager,
            RoomManager roomManager, UsersManager userManager){
        this.incidentManager = incidentManager;
        this.roomManager = roomManager;
        this.userManager = userManager;
    }
    
    /**
     * InitStage method for the incident view
     * @param root 
     * @throws albergueperronclient.exceptions.ReadException 
     */
     public void initStage(Parent root) throws ReadException {
        try {
            /* Code to open this window as the first one with the primaryStage
            * set in the app class
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Incidents");
            stage.setResizable(false);
            stage.setOnShowing(this::handleWindowShowing);*/
            
            //Create scene
            Scene scene = new Scene(root);
            stage = new Stage();
            //Associate scene to stage
            stage.setScene(scene);
            stage.setTitle("Incidents");
            stage.setResizable(false);
            //set window's events handlers
            stage.setOnShowing(this::handleWindowShowing);
            
            txtIncidentType.textProperty().addListener(this::onTextChanged);
            txtDescription.textProperty().addListener(this::onTextChanged);
            tableIncidents.getSelectionModel().selectedItemProperty().
                    addListener(this::onTableSelectionChanged);
            
            columnIncidentType.setCellValueFactory(
                    new PropertyValueFactory<>("incidentType"));
            columnDescription.setCellValueFactory(
                    new PropertyValueFactory<>("description"));
            ObservableList<RoomBean> rooms =
                    FXCollections.observableArrayList(roomManager.findAllRooms());
            cbRoom.setItems(rooms);
            ObservableList<UserBean> employees =
                    FXCollections.observableArrayList(userManager.findUsersByPrivilege(Privilege.ADMIN));
            cbEmployee.setItems(employees);
            ObservableList<UserBean> guests =
                    FXCollections.observableArrayList(userManager.findUsersByPrivilege(Privilege.USER));
            cbGuests.setItems(guests);
            ObservableList<IncidentBean> incidents = 
                    FXCollections.observableArrayList(incidentManager.findAllIncidents());
            tableIncidents.setItems(incidents);
           
            btnNew.setOnAction(this::enableNewIncidentForm);
            btnCancel.setOnAction(this::disposeIncidentForm);
            btnSaveChanges.setOnAction(this::updateIncident);
            btnDelete.setOnAction(this::deleteIncident);
            btnModify.setOnAction(this::enableUpdateIncidentForm);
            btnInsert.setOnAction(this::createIncident);
            btnReturn.setOnAction(this::returnToMenu);
            btnListAdd.setOnAction(this::addToList);
            btnListRemove.setOnAction(this::removeFromList);
            btnReport.setOnAction(this::generateReport);
            menuGuests.setOnAction(this::goToGuestsView);
            menuPets.setOnAction(this::goToPetsView);
            menuStays.setOnAction(this::goToStaysView);
            menuBlackList.setOnAction(this::goToBlackListView);
            menuRoom.setOnAction(this::goToRoomView);
            menuLogOut.setOnAction(this::logOut);
            menuExit.setOnAction(this::exit);
            
            stage.show();
        } catch(ReadException re){
            LOGGER.severe(re.getMessage());
            showErrorAlert("Error al cargar los datos desde el servidor.");
        } catch (Exception ex){
            LOGGER.severe(ex.getMessage());
            showErrorAlert("Error. No se ha podido completar la operación.");
        }
    }
    
    /**
     * OnShowing handler for the incident view
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
        incidentDate.setDisable(true);
    }
    
    /**
     * Enables the view fields for incident creation
     * @param event 
     */
    public void enableNewIncidentForm(ActionEvent event){
        btnInsert.setVisible(true);
        btnCancel.setDisable(false);
        btnNew.setDisable(true);
        txtIncidentType.setDisable(false);
        txtDescription.setDisable(false);
        lstImplicateds.setDisable(false);
        cbEmployee.setDisable(false);
        cbRoom.setDisable(false);
        btnListAdd.setDisable(false);
        btnListRemove.setDisable(false);
        cbGuests.setDisable(false);
        
        btnSaveChanges.setDisable(true);
        btnSaveChanges.setVisible(false);
        txtDescription.setText("");
        txtIncidentType.setText("");
        cbEmployee.getSelectionModel().clearSelection();
        cbRoom.getSelectionModel().clearSelection();
        cbGuests.getSelectionModel().clearSelection();
        lstImplicateds.getItems().clear();
        tableIncidents.getSelectionModel().clearSelection();
        tableIncidents.setDisable(true);
        
        btnModify.setDisable(true);
        btnDelete.setDisable(true);
        incidentDate.setDisable(false);
        incidentDate.getEditor().setDisable(true);
    }
    
    /**
     * Creates a new incident
     * @param event 
     */
    public void createIncident(ActionEvent event){
        try{
            if(checkForData()){
                IncidentBean newIncident = new IncidentBean();
                newIncident.setDescription(txtDescription.getText());
                List<UserBean> implicateds = lstImplicateds.getItems();
                implicateds.add((UserBean)cbEmployee.getSelectionModel().getSelectedItem());
                newIncident.setImplicateds(implicateds);
                newIncident.setIncidentType(txtIncidentType.getText());
                newIncident.setRoom((RoomBean)cbRoom.getSelectionModel().getSelectedItem());
                Date date = Date.from(incidentDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                newIncident.setDate(date);
                incidentManager.createIncident(newIncident);
            
                btnCancel.setDisable(true);
                btnInsert.setDisable(true);
                btnInsert.setVisible(false);
                btnNew.setDisable(false);
                txtDescription.setText("");
                txtDescription.setDisable(true);
                txtIncidentType.setText("");
                txtIncidentType.setDisable(true);
                cbEmployee.getSelectionModel().clearSelection();
                cbEmployee.setDisable(true);
                lstImplicateds.setDisable(true);
                cbRoom.getSelectionModel().selectFirst();
                cbRoom.setDisable(true);
                btnListAdd.setDisable(true);
                btnListRemove.setDisable(true);
                cbGuests.setDisable(true);
                incidentDate.getEditor().clear();
                incidentDate.setDisable(true);
            
                tableIncidents.getItems().add(newIncident);
                tableIncidents.setDisable(false); 
                tableIncidents.refresh();
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Creación de incidencia");
                        alert.setContentText("Se ha creado la incidencia"
                                + "con éxito.");        
                        alert.showAndWait();
            } else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Creación de incidencia");
                        alert.setContentText("Se requieren todos los datos "
                                + "para poder crear la incidencia.");        
                        alert.showAndWait();
            }
        }catch(CreateException ce){
            LOGGER.severe(ce.getMessage());
            showErrorAlert("Error al crear el incidente.");
        }catch(Exception ex){
            LOGGER.severe(ex.getMessage());
            showErrorAlert("Error. No se ha podido completar la operación.");
        }
    }
    
    /**
     * Disables the view fields for incident creation/update
     * @param event 
     */
    public void disposeIncidentForm(ActionEvent event){
        if(btnInsert.isVisible()){
            btnNew.setDisable(false);
            cbEmployee.getSelectionModel().clearSelection();
            cbRoom.getSelectionModel().clearSelection();
            cbGuests.getSelectionModel().clearSelection();
            lstImplicateds.getItems().clear();
            txtDescription.setText("");
            txtIncidentType.setText("");
            incidentDate.getEditor().clear();
        } else{
            cbEmployee.getSelectionModel().select(selectedIncident.getEmployee());
            cbRoom.getSelectionModel().select(selectedIncident.getRoom());
            cbGuests.getSelectionModel().selectFirst();
            lstImplicateds.setItems(FXCollections.observableArrayList(selectedIncident.getGuests()));
            txtDescription.setText(selectedIncident.getDescription());
            txtIncidentType.setText(selectedIncident.getIncidentType());
            LocalDate localdate = selectedIncident.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            incidentDate.setValue(localdate);
            btnDelete.setDisable(true);
        }
        
        btnSaveChanges.setDisable(true);
        btnSaveChanges.setVisible(false);
        btnInsert.setDisable(true);
        btnInsert.setVisible(false);
        btnCancel.setDisable(true);
        btnNew.setDisable(false);
        txtIncidentType.setDisable(true);
        txtDescription.setDisable(true);
        lstImplicateds.setDisable(true);
        cbEmployee.setDisable(true);
        cbRoom.setDisable(true);
        btnListAdd.setDisable(true);
        btnListRemove.setDisable(true);
        cbGuests.setDisable(true);
        tableIncidents.setDisable(false);
        incidentDate.setDisable(true);
   }
    
    /**
     * Enables the view fields for incident update of the selected room
     * @param event 
     */
    public void enableUpdateIncidentForm(ActionEvent event){
        btnSaveChanges.setDisable(false);
        btnSaveChanges.setVisible(true);
        btnCancel.setDisable(false);
        btnListAdd.setDisable(false);
        btnListRemove.setDisable(false);
        btnNew.setDisable(true);
        btnDelete.setDisable(true);
        txtIncidentType.setDisable(false);
        txtDescription.setDisable(false);
        lstImplicateds.setDisable(false);
        cbEmployee.setDisable(false);
        cbRoom.setDisable(false);
        cbGuests.setDisable(false);
        btnInsert.setDisable(true);
        btnInsert.setVisible(false);
        tableIncidents.setDisable(true);
        incidentDate.setDisable(false);
        incidentDate.getEditor().setDisable(true);
    }
    
    /**
     * Updates the selected incident
     * @param event 
     */
    public void updateIncident(ActionEvent event){
        try{
            if(checkForData()){
                IncidentBean incidentToModify = selectedIncident;
                incidentToModify.setDescription(txtDescription.getText());
                List<UserBean> implicateds = new ArrayList<UserBean>();
                implicateds.addAll(lstImplicateds.getItems());
                implicateds.add((UserBean)cbEmployee.getSelectionModel().getSelectedItem());
                incidentToModify.setImplicateds(implicateds);
                incidentToModify.setIncidentType(txtIncidentType.getText());
                incidentToModify.setRoom((RoomBean)cbRoom.getSelectionModel().getSelectedItem());
                
                Date date = Date.from(incidentDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                //java.sql.Date date = java.sql.Date.valueOf(incidentDate.getValue());
                incidentToModify.setDate(date);
                
                incidentManager.updateIncident(incidentToModify);
                
                btnCancel.setDisable(true);
                btnSaveChanges.setDisable(true);
                btnSaveChanges.setVisible(false);
                txtDescription.setDisable(true);
                txtIncidentType.setDisable(true);
                cbEmployee.setDisable(true);
                lstImplicateds.setDisable(true);
                cbRoom.getSelectionModel().selectFirst();
                cbRoom.setDisable(true);
                btnListAdd.setDisable(true);
                btnListRemove.setDisable(true);
                cbGuests.setDisable(true);
                btnNew.setDisable(false);
                btnDelete.setDisable(false);
                incidentDate.setDisable(true);
            
                tableIncidents.refresh();
                tableIncidents.setDisable(false);
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Actualización de incidencia");
                        alert.setContentText("Se ha actualizado la incidencia"
                                + "con éxito.");        
                        alert.showAndWait();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Actualización de incidencia");
                        alert.setContentText("Se requieren todos los datos "
                                + "para poder actualizar la incidencia.");        
                        alert.showAndWait();
            }
        }catch(UpdateException ue){
            LOGGER.severe(ue.getMessage());
            showErrorAlert("Error al actualizar la incidencia.");
        }catch(Exception ex){
            LOGGER.severe(ex.getMessage());
            showErrorAlert("Error. No se ha podido completar la operación.");
        }
    }
    
    /**
     * Deletes the selected incident
     * @param event 
     */
    public void deleteIncident(ActionEvent event){
        try{
            incidentManager.deleteIncident(selectedIncident.getId());
            tableIncidents.getItems().remove(selectedIncident);
            tableIncidents.refresh();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Eliminación de incidencia");
                        alert.setContentText("Se ha eliminado la incidencia "
                                + "con éxito.");        
                        alert.showAndWait();
        }catch(DeleteException de){
            LOGGER.severe(de.getMessage());
            showErrorAlert("Error al eliminar la incidencia.");
        }catch(Exception ex){
            LOGGER.severe(ex.getMessage());
            showErrorAlert("Error. No se ha podido completar la operación.");
        }
    }
    
    /**
     * Checks if the needed data to create/update a room is inserted
     * @return 
     */
    public Boolean checkForData(){
        Boolean formHasCorrectData = true;
        if(txtDescription.getText().trim().length()==0 || 
                txtIncidentType.getText().trim().length()== 0 ||
                !(cbRoom.getSelectionModel().getSelectedItem() instanceof RoomBean)||
                !(cbEmployee.getSelectionModel().getSelectedItem() instanceof UserBean )
                || lstImplicateds.getItems().isEmpty() || incidentDate.getEditor().getText().equalsIgnoreCase("") || !(incidentDate.getValue() instanceof LocalDate)){
            formHasCorrectData = false;
        }
        return formHasCorrectData;
    }
    
    /**
     * Adds the selected user to the implicateds list
     * @param event 
     */
    public void addToList(ActionEvent event){
        if(cbGuests.getSelectionModel().getSelectedItem() instanceof UserBean){
            Boolean alreadyOnList = false;
            for(int i = 0; i < lstImplicateds.getItems().size(); i++){
                if(cbGuests.getSelectionModel().getSelectedItem().equals(lstImplicateds.getItems().get(i))){
                    alreadyOnList = true;
                    break;
                }
            }
            if(!alreadyOnList){
                lstImplicateds.getItems().add(cbGuests.getSelectionModel().getSelectedItem());
            }
        }
    }
    
    /**
     * Removes the selected user from the implicateds list
     * @param event 
     */
    public void removeFromList(ActionEvent event){
        lstImplicateds.getItems().remove(lstImplicateds.getSelectionModel().getSelectedItem());
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
        //-- TOFIX
        Platform.exit();
    }
    
    /**
     * Logs out, sending the user to the login view
     * @param event 
     */
    public void logOut(ActionEvent event){
        try{
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Cerrar Sesión");
            alert.setContentText("¿Desea cerrar sesion?");        
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get()== ButtonType.OK){
                stage.close();
                try {
                    //Get the logic manager object for the initial stage
                    ILogin loginManager = ILoginFactory.getLoginManager();

                    //Load the fxml file
                    FXMLLoader loader = new FXMLLoader(getClass()
                            .getResource("/albergueperronclient/ui/fxml/UILogin.fxml"));
                    Parent root = loader.load();
                    //Get controller from the loader
                    UILoginFXMLController loginController = loader.getController();
                    /*Set a reference in the controller for the UILogin view for the logic manager object           
                     */
                    loginController.setLoginManager(loginManager);
                    //Set a reference for Stage in the UILogin view controller
                    loginController.setStage(stage);
                    //Initialize the primary stage of the application
                    loginController.initStage(root);

                } catch (Exception e) {
                    LOGGER.severe(e.getMessage());
                }
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
            stage.close();
        }catch(Exception e){
            LOGGER.severe(e.getMessage());
            showErrorAlert("Error al redirigir a la vista de la lista negra.");
        }*/
    }
    
    /**
     * Opens the room view
     * @param event 
     */
    public void goToRoomView(ActionEvent event){
        try{
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
            stage.close();
        }catch(Exception e){
            LOGGER.severe(e.getMessage());
            showErrorAlert("Error al redirigir a la vista de habitaciones.");
        }
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
        //--TOFIX
        if(newValue!=null){            
            selectedIncident = (IncidentBean)newValue;
            txtIncidentType.setText(selectedIncident.getIncidentType());
            txtDescription.setText(selectedIncident.getDescription());

            cbRoom.getSelectionModel().select(selectedIncident.getRoom());
            
            cbEmployee.getSelectionModel().select(selectedIncident.getEmployee());
            lstImplicateds.setItems(selectedIncident.getGuests());
            //--TOFIX --> Revisar
            if(selectedIncident.getDate() != null){
                LocalDate localdate = selectedIncident.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                incidentDate.setValue(localdate);
            }
            btnModify.setDisable(false);
            btnDelete.setDisable(false);
        }else{
        //If there is not a row selected, clean window fields 
        //and disable create, modify and delete buttons
            selectedIncident = null;
            txtIncidentType.setText("");
            txtDescription.setText("");
            cbEmployee.getSelectionModel().clearSelection();
            cbRoom.getSelectionModel().clearSelection();
            cbGuests.getSelectionModel().clearSelection();
            lstImplicateds.getSelectionModel().clearSelection();
            lstImplicateds.getItems().clear();
            incidentDate.getEditor().clear();
            btnModify.setDisable(true);
            btnDelete.setDisable(true);
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

    /**
     * Generates the JasperReport with information of the incidents shown 
     * in the table
     * @param event 
     */
    public void generateReport(ActionEvent event){
        try {
            JasperReport report=
                JasperCompileManager.compileReport(getClass()
                    .getResourceAsStream("/albergueperronclient/ui/report/InciReport.jrxml"));
            //Data for the report: a collection of UserBean passed as a JRDataSource 
            //implementation 
            JRBeanCollectionDataSource dataItems=
                    new JRBeanCollectionDataSource((Collection<IncidentBean>)this.tableIncidents.getItems());
            //Map of parameter to be passed to the report
            Map<String,Object> parameters=new HashMap<>();
            //Fill report with data
            JasperPrint jasperPrint = JasperFillManager.fillReport(report,parameters,dataItems);
            //Create and show the report window. The second parameter false value makes 
            //report window not to close app.
            JasperViewer jasperViewer = new JasperViewer(jasperPrint,false);
            jasperViewer.setVisible(true);
           // jasperViewer.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        } catch (JRException ex) {
            //If there is an error show message and
            //log it.
            showErrorAlert("Error al imprimir:\n"+
                            ex.getMessage());
            LOGGER.log(Level.SEVERE,
                        "UI GestionUsuariosController: Error printing report: {0}",
                        ex.getMessage());
        }
    }
}