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
import java.sql.Date;
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
                    FXCollections.observableArrayList(userManager.findUsersByPrivilege(Privilege.EMPLOYEE));
            cbEmployee.setItems(employees);
            //--TOFIX --> Arreglar            
            //employees.add(userManager.findUsersByPrivilege(Privilege.ADMIN));
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
        } catch (Exception ex){
            //--TOFIX
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
        incidentDate.setDisable(true);
        
        //--TOFIX
        /*btnLogin.setDisable(true);
        btnLogin.setMnemonicParsing(true);
        btnLogin.setText("_Iniciar Sesión");
        btnExit.setMnemonicParsing(true);
        btnExit.setText("_Salir");
        txtUsername.requestFocus();*/
        //Settear promptText
    }
    
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
    }
    
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
                //lstImplicateds.setItems(null);
                lstImplicateds.setDisable(true);
                cbRoom.getSelectionModel().selectFirst();
                cbRoom.setDisable(true);
                btnListAdd.setDisable(true);
                btnListRemove.setDisable(true);
                cbGuests.setDisable(true);
                incidentDate.getEditor().clear();
                //incidentDate.setValue(null);
                incidentDate.setDisable(true);
            
                tableIncidents.getItems().add(newIncident);
                tableIncidents.setDisable(false); 
                tableIncidents.refresh();
            } else{
                //--TOFIX --> Advertir al usuario de que se requieren todos los datos introducidos para poder crear un nuevo incidente
            }
        }catch(CreateException ce){
            //--TOFIX --> Exception handling
        }catch(Exception ex){
            //--TOFIX --> Exception handling
        }
    }
    
    public void disposeIncidentForm(ActionEvent event){
        if(btnInsert.isVisible()){
            btnNew.setDisable(false);
            cbEmployee.getSelectionModel().clearSelection();
            cbRoom.getSelectionModel().clearSelection();
            cbGuests.getSelectionModel().clearSelection();
            lstImplicateds.getItems().clear();
            txtDescription.setText("");
            txtIncidentType.setText("");
            //incidentDate.setValue(null);
            incidentDate.getEditor().clear();
        } else{
            cbEmployee.getSelectionModel().select(selectedIncident.getEmployee());
            cbRoom.getSelectionModel().select(selectedIncident.getRoom());
            cbGuests.getSelectionModel().selectFirst();
            lstImplicateds.setItems(FXCollections.observableArrayList(selectedIncident.getGuests()));
            txtDescription.setText(selectedIncident.getDescription());
            txtIncidentType.setText(selectedIncident.getIncidentType());
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
        selectedIncident.getDescription();
        tableIncidents.setDisable(true);
        incidentDate.setDisable(false);
    }
    
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
                
                java.sql.Date date = java.sql.Date.valueOf(incidentDate.getValue());
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
                incidentDate.getEditor().clear();
                //incidentDate.setValue(null);
                incidentDate.setDisable(true);
            
            tableIncidents.refresh();
            tableIncidents.setDisable(false);
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
    
    public void deleteIncident(ActionEvent event){
        try{
            incidentManager.deleteIncident(selectedIncident.getId());
            tableIncidents.getItems().remove(selectedIncident);
            tableIncidents.refresh();
        }catch(DeleteException de){
            //--TOFIX --> Exception handling
        }catch(Exception ex){
            //--TOFIX --> Exception handling
        }
    }
    
    public Boolean checkForData(){
        Boolean formHasCorrectData = true;
        if(txtDescription.getText().trim().length()==0 || 
                txtIncidentType.getText().trim().length()== 0 ||
                !(cbRoom.getSelectionModel().getSelectedItem() instanceof RoomBean)||
                !(cbEmployee.getSelectionModel().getSelectedItem() instanceof UserBean )
                || lstImplicateds.getItems().isEmpty() || incidentDate.getValue() == null){
            formHasCorrectData = false;
        }
        return formHasCorrectData;
    }
    
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
    
    public void removeFromList(ActionEvent event){
        lstImplicateds.getItems().remove(lstImplicateds.getSelectionModel().getSelectedItem());
    }
    
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
    
    /*public void returnToPrevious(ActionEvent event){
        stage.close();
        previousStage.show();
    }*/
    
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
            stage.hide();
            stage.close();
        }catch(Exception e){
            LOGGER.severe(e.getMessage());
            showErrorAlert("Error al redirigir a la vista de habitaciones.");
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

            cbRoom.getSelectionModel().select(selectedIncident.getRoom());
            
            cbEmployee.getSelectionModel().select(selectedIncident.getEmployee());
            lstImplicateds.setItems(selectedIncident.getGuests());
            btnModify.setDisable(false);
            btnDelete.setDisable(false);
            //incidentDate.setValue(selectedIncident.getDate());
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
            //incidentDate.setValue(null);
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