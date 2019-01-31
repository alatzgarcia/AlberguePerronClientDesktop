/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.ui.controller;

import albergueperronclient.exceptions.BusinessLogicException;
import albergueperronclient.exceptions.ReadException;
import albergueperronclient.logic.RoomManagerFactory;
import albergueperronclient.logic.UserManagerFactory;
import albergueperronclient.modelObjects.RoomBean;
import albergueperronclient.modelObjects.StayBean;
import albergueperronclient.modelObjects.UserBean;
import static albergueperronclient.ui.controller.GenericController.LOGGER;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
public class UIStayFXMLController extends GenericController{

    @FXML
    private TableView<StayBean> tableStay;
    @FXML
    private TableColumn<?, ?> columnGuests;
    @FXML
    private TableColumn<?, ?> columnRoom;
    @FXML
    private TableColumn<?, ?> columnDate;
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
    @FXML
    private ComboBox<UserBean> cbGuest;
    @FXML
    private ComboBox<RoomBean> cbRoom;
    private DatePicker datePicker;
    private ObservableList<StayBean> staysData;
    private ObservableList<UserBean> guests;
    private ObservableList<RoomBean> rooms;
    private StayBean stay;
    private final int visible=1;
    private final int invisible=2;
    private final int enable=3;
    private final int disable=4;
    private final int clean=5;
    /**
     * Initializes the controller class.
     */
     public void initStage(Parent root) {
        Scene scene = new Scene(root);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.setTitle("Estancias");
        stage.setResizable(false);
        
        stage.setOnShowing(this::handleWindowShowing);
        
        datePicker.promptTextProperty().addListener(this::onTextChanged);
        cbGuest.promptTextProperty().addListener(this::onTextChanged);
        cbRoom.promptTextProperty().addListener(this::onTextChanged);
        
        //Sets the columns the attributes to use
        columnGuests.setCellValueFactory(new PropertyValueFactory<>("guest"));
        columnRoom.setCellValueFactory(new PropertyValueFactory<>("room"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        
        try{
            //Create an observable lis for the users
            staysData = FXCollections.observableArrayList(staysManager.getAllStays());
            //Set the observable data
            tableStay.setItems(staysData);
            
            //Create the interfaces
            usersManager=UserManagerFactory.createUserManager();
            roomsManager=RoomManagerFactory.getRoomManager();
            
            //Insert the data at cbs
            guests=FXCollections.observableArrayList(usersManager.getAllUsers());
            rooms=FXCollections.observableArrayList(roomsManager.findRoomsWithAvailableSpace());
            
            //Insert the combo
            cbGuest.setItems(guests);
            cbRoom.setItems(rooms);
        }catch(BusinessLogicException ble){
            LOGGER.severe(ble.getMessage());
        }catch(ReadException re){
            LOGGER.severe(re.getMessage());
        }
        
        //Sets the selection listener
        tableStay.getSelectionModel().selectedItemProperty().addListener(this::handleUserTableFocus);
        
        stage.show();
       
    }
     
    public void handleUserTableFocus(ObservableValue observable, Object oldValue, Object newValue){
        fieldChange(visible);
        fieldChange(disable);
        
        btnModify.setOnAction(this::stayModify);
        btnDelete.setOnAction(this::deleteStay);
        
        if (newValue!=null){
            StayBean stay=(StayBean)newValue;
            cbGuest.getSelectionModel().select(tableStay.getSelectionModel().getSelectedItem().getGuest());
            cbRoom.getSelectionModel().select(tableStay.getSelectionModel().getSelectedItem().getRoom());
            datePicker.setValue(tableStay.getSelectionModel().getSelectedItem().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            
            //Enables the correspondent buttons
            btnDelete.setDisable(false);
            btnModify.setDisable(false);
            btnNew.setDisable(false);
        }
    }
    
    public void deleteStay(ActionEvent event){
        try{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION.CONFIRMATION);
            alert.setTitle("Borrar");
            alert.setContentText("¿Desea borrar la estancia?");    
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get()== ButtonType.OK){
                staysManager.deleteStay(tableStay.getSelectionModel().getSelectedItem().getId().toString());
                tableStay.getItems().remove(tableStay.getSelectionModel().getSelectedItem());
                tableStay.refresh();
            }else if(result.get()==ButtonType.CANCEL){
                alert.close();
            }
        }catch(BusinessLogicException ble){
            LOGGER.info("Delete failed "+ble.getMessage());
        }catch(Exception e){
            LOGGER.info("Error"+e.getMessage());
        }   
    }
    
    public void stayModify(ActionEvent event){
        btnSaveChanges.setVisible(true);
        btnSaveChanges.setDisable(false);
        btnCancel.setDisable(false);
        btnModify.setDisable(true);
        btnDelete.setDisable(true);
        btnNew.setDisable(true);
        
        fieldChange(enable);
        fieldChange(visible);
        
        btnSaveChanges.setOnAction(this::saveStayChanges);
    }
    
    public void saveStayChanges(ActionEvent event){
        btnDelete.setDisable(true);
        btnModify.setDisable(true);
        btnNew.setDisable(true);
        try{
                staysManager.updateStay(getStayFromFields(),tableStay.getSelectionModel().getSelectedItem().getId().toString());
                tableStay.getItems().remove(tableStay.getSelectionModel().getSelectedItem());
                tableStay.getItems().add(stay);
                tableStay.refresh();
                Alert alert = new Alert(Alert.AlertType.INFORMATION.INFORMATION);
                alert.setTitle("Modificado");
                alert.setContentText("Se modificó la estancia "+ tableStay.getSelectionModel().getSelectedItem().getId());    
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get()== ButtonType.OK){
                    finishOperation();
                    alert.close();                    
                }
            }catch(BusinessLogicException ble){
                LOGGER.info("The update failed "+ble.getMessage());
            }
    }
    
    public void newStay(ActionEvent event){
        btnInsert.setVisible(true);
        btnInsert.setDisable(false);
        btnModify.setDisable(true);
        btnNew.setDisable(true);
        btnCancel.setDisable(true);
        
        
        fieldChange(enable);
        fieldChange(visible);
    }
    
    public void saveNewStay(){
        try{
            staysManager.createStay(getStayFromFields());
            tableStay.getItems().add(stay);
            tableStay.refresh();
            Alert alert = new Alert(Alert.AlertType.INFORMATION.INFORMATION);
            alert.setTitle("Crear estancia");
            alert.setContentText("La estancia fue creada");    
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get()== ButtonType.OK){
                finishOperation();
                alert.close();  
            }
        }catch(BusinessLogicException ble){
            LOGGER.info("The create failed "+ble.getMessage());
        }catch(Exception e){
            LOGGER.info("The create failed "+e.getMessage());
        }
    }
     
    public void handleWindowShowing(WindowEvent event){
        btnNew.setVisible(true);
        btnNew.setDisable(false);
        btnCancel.setVisible(true);
        btnCancel.setDisable(false);
        btnSaveChanges.setVisible(false);
        btnSaveChanges.setDisable(true);
        btnDelete.setVisible(true);
        btnDelete.setDisable(true);
        btnModify.setVisible(true);
        btnModify.setDisable(true);
        btnInsert.setVisible(false);
        btnInsert.setDisable(true);
        btnReturn.setVisible(true);
        btnReturn.setDisable(false);
        fieldChange(clean);
        fieldChange(disable);
        fieldChange(invisible);
        
        btnNew.setOnAction(this::newStay);
        btnCancel.setOnAction(this::cancel);
    }
    
     public void fieldChange(int change){
        switch(change){
            case 1:
                //Sets visbles all the fields of the window
                cbGuest.setVisible(true);
                cbRoom.setVisible(true);
                datePicker.setVisible(true);
        break;
            case 2:
                //Sets invisible all the fields of the window
                cbGuest.setVisible(false);
                cbRoom.setVisible(false);
                datePicker.setVisible(false);
                break;
            case 3:
                //Enables the fields
                cbGuest.setDisable(false);
                cbRoom.setDisable(false);
                datePicker.setDisable(false);
                break;
            case 4:
                //Disables the fields
                cbGuest.setDisable(true);
                cbRoom.setDisable(true);
                datePicker.setDisable(true);
                break;
            case 5:
                //Deletes all the existing data
                cbGuest.getSelectionModel().clearSelection();
                cbRoom.getSelectionModel().clearSelection();
                datePicker.setValue(null);
                break;
        }
    }
     
    private StayBean getStayFromFields(){
 //TODO
        stay= new StayBean();
        
        //Sets the attributes with the fields
        SimpleDateFormat parser=new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            /*date = parser.parse(txtDate.getText().toString());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = formatter.format(date);*/
            
            LocalDate localDate = datePicker.getValue();
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date dateFinal = Date.from(instant);
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            Date date2=sdf.parse(dateFinal.toString());
            LOGGER.info(date2.toString());
            stay.setDate(date2);
            stay.setGuest(cbGuest.getSelectionModel().getSelectedItem());
            stay.setRoom(cbRoom.getSelectionModel().getSelectedItem());
            stay.setId(tableStay.getSelectionModel().getSelectedItem().getId());
        /*} catch (ParseException ex) {
            LOGGER.info(ex.getMessage());*/ 
        }catch(Exception e){
            LOGGER.severe(e.getMessage());
        }  
        return stay;
    }
    
    public void onTextChanged(ObservableValue observable,
            String oldValue,
            String newValue){
//preguntar como combrobar el combo
        if (datePicker.getValue() == null
                || cbGuest.getSelectionModel().getSelectedItem() == null
                || cbRoom.getSelectionModel().getSelectedItem() == null) {
            if (btnSaveChanges.isVisible()) {
                btnSaveChanges.setDisable(true);
            }
            if (btnInsert.isVisible()) {
                btnInsert.setDisable(true);
            }
        } else if (datePicker.getValue() != null
                && cbGuest.getSelectionModel().getSelectedItem() != null
                && cbRoom.getSelectionModel().getSelectedItem() != null) {
            if (btnSaveChanges.isVisible()) {
                btnSaveChanges.setDisable(false);
            }
            if (btnInsert.isVisible()) {
                btnInsert.setDisable(false);
            }
        }
    }
    
    public void cancel(ActionEvent event){
        try{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION.CONFIRMATION);
            alert.setTitle("Cancelar");
            alert.setContentText("¿Desea cancelar la operación?");    
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get()== ButtonType.OK){
                finishOperation();
            }else if(result.get()==ButtonType.CANCEL){
                alert.close();
            }
        } catch(Exception ex){
            LOGGER.severe(ex.getMessage());
        }
    }
    
    public void finishOperation(){
        fieldChange(invisible);
        fieldChange(clean);
        btnCancel.setDisable(true);
        btnSaveChanges.setVisible(false);
        btnInsert.setVisible(false);
        btnSaveChanges.setDisable(true);
        btnInsert.setDisable(true);
        btnNew.setDisable(false);
        tableStay.getSelectionModel().clearSelection();
        tableStay.setDisable(false);
    }

    @FXML
    private void datePicker(ActionEvent event) {
    }
}
