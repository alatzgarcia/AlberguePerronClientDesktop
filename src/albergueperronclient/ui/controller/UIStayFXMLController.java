/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.ui.controller;

import albergueperronclient.exceptions.BusinessLogicException;
import albergueperronclient.exceptions.ReadException;
import albergueperronclient.logic.RoomManager;
import albergueperronclient.logic.RoomManagerFactory;
import albergueperronclient.logic.StayManagerFactory;
import albergueperronclient.logic.StaysManager;
import albergueperronclient.logic.PetManagerFactory;
import albergueperronclient.logic.PetsManager;
import albergueperronclient.logic.UserManagerFactory;
import albergueperronclient.logic.UsersManager;
import albergueperronclient.logic.LoginManagerFactory;
import albergueperronclient.logic.LoginManager;
import albergueperronclient.logic.IFTPManagerFactory;
import albergueperronclient.logic.IFTPManager;
import albergueperronclient.modelObjects.RoomBean;
import albergueperronclient.modelObjects.StayBean;
import albergueperronclient.modelObjects.UserBean;
import static albergueperronclient.ui.controller.GenericController.LOGGER;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

/**
 * FXML Controller class
 *
 * @author Diego
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
    private DatePicker datePicker;
    @FXML
    private MenuItem menuGuest;
    @FXML
    private MenuItem menuPet;
    @FXML
    private MenuItem menuIncidences;
    @FXML
    private MenuItem menuRoom;
    @FXML
    private MenuItem menuStay;
    @FXML
    private MenuItem menuBlackList;
    @FXML
    private MenuItem menuLogOut;
    @FXML
    private MenuItem menuFTP;
    @FXML
    private MenuItem menuExit;
    @FXML
    private ComboBox<UserBean> cbGuest;
    @FXML
    private ComboBox<RoomBean> cbRoom;
    
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
    
        menuIncidences.setOnAction(this::openIncidentsView);
        menuGuest.setOnAction(this::openGuestsView);
        menuPet.setOnAction(this::openPetsView);
        menuStay.setOnAction(this::openStaysView);
        menuBlackList.setOnAction(this::openBlackListView);
        menuLogOut.setOnAction(this::logOut);
        menuRoom.setOnAction(this::openRoomsView);
        menuExit.setOnAction(this::exit);
        menuFTP.setOnAction(this::openFTPView);
        
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
    
    /**
     * Controls the buttons and the fields when the focus of the table changes
     * @param observable ObservableValue:
     * @param oldValue Object: The old value of the object modified
     * @param newValue Object: The new value of the object modified
     */
    public void handleUserTableFocus(ObservableValue observable, Object oldValue, Object newValue){
        fieldChange(visible);
        fieldChange(disable);
        
        btnModify.setOnAction(this::stayModify);
        btnDelete.setOnAction(this::deleteStay);
        btnReturn.setOnAction(this::returnWindow);
        if (newValue!=null){
            StayBean stay=(StayBean)newValue;
            cbGuest.getSelectionModel().select(tableStay.getSelectionModel().getSelectedItem().getGuest());
            cbRoom.getSelectionModel().select(tableStay.getSelectionModel().getSelectedItem().getRoom());
            Date dateToShow=tableStay.getSelectionModel().getSelectedItem().getDate();
            LocalDate date = dateToShow.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            datePicker.setValue(date);
            //datePicker.setValue(tableStay.getSelectionModel().getSelectedItem().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            //datePicker.setValue(LocalDate.from(tableStay.getSelectionModel().getSelectedItem().getDate().));
            
            //Enables the correspondent buttons
            btnDelete.setDisable(false);
            btnModify.setDisable(false);
            btnNew.setDisable(false);
        }
    }
    /**
     * Deletes the selected entire stay
     * @param event 
     */
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
                finishOperation();
            }else if(result.get()==ButtonType.CANCEL){
                alert.close();
            }
        }catch(BusinessLogicException ble){
            LOGGER.info("Delete failed "+ble.getMessage());
        }catch(Exception e){
            LOGGER.info("Error"+e.getMessage());
        }   
    }
    
    /**
     * Enables the buttons, comboboxes and DatePicker for the update.
     * @param event ActionEvent: The listener of the button
     */
    public void stayModify(ActionEvent event){
        btnSaveChanges.setVisible(true);
        btnSaveChanges.setDisable(false);
        btnCancel.setDisable(false);
        btnModify.setDisable(true);
        btnDelete.setDisable(true);
        btnNew.setDisable(true);
        tableStay.setDisable(true);
        fieldChange(enable);
        fieldChange(visible);
        
        btnSaveChanges.setOnAction(this::saveStayChanges);
    }
    
    /**
     * Updates a Stay with the inserted data
     * @param event ActionEvent: The listener of the button
     */
    public void saveStayChanges(ActionEvent event){
        btnDelete.setDisable(true);
        btnModify.setDisable(true);
        btnInsert.setDisable(true);
        try{
                staysManager.updateStay(getStayFromFields());
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
    
    /**
     * Enables the buttons, comboboxes and DatePicker for the new stay.
     * @param event ActionEvent: The listener of the button
     */
    public void newStay(ActionEvent event){
        btnInsert.setVisible(true);
        btnInsert.setDisable(false);
        btnModify.setDisable(true);
        btnNew.setDisable(true);
        btnDelete.setDisable(true);
        btnCancel.setDisable(false);
        tableStay.setDisable(true);
        btnInsert.setOnAction(this::saveNewStay);
        fieldChange(clean);
        fieldChange(enable);
        fieldChange(visible);
    }
    
    /**
     * Creates a Stay with the inserted data
     * @param event ActionEvent: The listener of the button
     */
    public void saveNewStay(ActionEvent event){
        if (datePicker.getValue() == null
                || cbGuest.getSelectionModel().getSelectedItem() == null
                || cbRoom.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Comprueba los campos");    
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get()== ButtonType.OK){
                alert.close();  
            }
        } else if (datePicker.getValue() != null
                && cbGuest.getSelectionModel().getSelectedItem() != null
                && cbRoom.getSelectionModel().getSelectedItem() != null) {
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
        
    }
    
    /**
     * Sets the buttons and fields that will be visible, disable or editable at
     * the start of the window
     * @param event WindowEvent: The listener of the window
     */
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
        btnCancel.setDisable(true);
        datePicker.getEditor().setEditable(false);
        fieldChange(clean);
        fieldChange(disable);
        fieldChange(invisible);
        
        btnNew.setOnAction(this::newStay);
        btnCancel.setOnAction(this::cancel);
    }
    
    /**
     * Method that controls the field, comboboxes or datepicker where the data
     * will be inserted or selected
     * @param change int: The valor of wanted modification of the fields
     */
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
                datePicker.getEditor().setDisable(true);
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
                datePicker.getEditor().clear();
                break;
        }
    }
    
     /**
      * Gets all the from the fields and it put them into a StayBean Object
      * @return stay
      */
    private StayBean getStayFromFields(){
        stay= new StayBean();
        try {
            Date date=Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            
            LOGGER.info(date.toString());
            stay.setDate(date);
            stay.setGuest(cbGuest.getSelectionModel().getSelectedItem());
            stay.setRoom(cbRoom.getSelectionModel().getSelectedItem());
            if(tableStay.getSelectionModel().getSelectedItem().getId()!=null){
                stay.setId(tableStay.getSelectionModel().getSelectedItem().getId());
            }else{
                stay.setId(9999);
            }
        }catch(Exception e){
            LOGGER.severe(e.getMessage());
        }  
        return stay;
    }
    
    /**
     * The method that cancels the creating or updating data insert
     * @param event ActionEvent: The listener of the button
     */
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
    
    /**
     * This method return all the buttons to the initial status
     */
    public void finishOperation(){
        tableStay.getSelectionModel().clearSelection();
        tableStay.setDisable(false);
        fieldChange(invisible);
        fieldChange(clean);
        btnCancel.setDisable(true);
        btnSaveChanges.setVisible(false);
        btnInsert.setVisible(false);
        btnSaveChanges.setDisable(true);
        btnInsert.setDisable(true);
        btnNew.setDisable(false);
        btnModify.setDisable(true);
        tableStay.setDisable(false);
        btnDelete.setDisable(true);
    }
    
    /**
     * Returns to the last window
     * @param event ActionEvent The listenr of the button
     */
    public void returnWindow(ActionEvent event) {
        LOGGER.info("Entra en el return");
        stage.close();
    }
    
    
    
    
    
    
    /**
     * Method that opens Pets View
     * @param event event that has caused the call to the function
     */
    public void openPetsView(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/albergueperronclient/ui/fxml/UIPet.fxml"));

        try {
            Parent root = loader.load();

            PetsManager petManager = PetManagerFactory.createPetManager();
            //Get controller from the loader
            UIPetFXMLController petController = loader.getController();

            petController.setPetsManager(petsManager);
            //Initialize the primary stage of the application
            petController.initStage(root);
            stage.hide();
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        } catch (BusinessLogicException ex) {
            LOGGER.severe(ex.getMessage());
        }
    }
    /**
     * Method that opens Guests View
     * @param event event that has caused the call to the function
     */
    public void openGuestsView(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/albergueperronclient/ui/fxml/UIGuest.fxml"));

        try {
            Parent root = loader.load();

            UsersManager usersManager = UserManagerFactory.createUserManager();
            //Get controller from the loader
            UIGuestFXMLController guestController = loader.getController();
            guestController.setUsersManager(usersManager);
            //Initialize the primary stage of the application
            guestController.initStage(root);
            stage.hide();
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }
    }

    /**
     * Method that opens Rooms View
     * @param event event that has caused the call to the function
     */
    public void openRoomsView(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/albergueperronclient/ui/fxml/UIRoom.fxml"));

        try {

            Parent root = loader.load();

            RoomManager roomManager = RoomManagerFactory.getRoomManager();
            //Get controller from the loader
            UIRoomFXMLController roomController = loader.getController();
            roomController.setLogicManager(roomManager);
            //Initialize the primary stage of the application
            roomController.initStage(root);
            stage.hide();

        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }
    }

    /**
     * Method that opens Stays View
     * @param event event that has caused the call to the function
     */
    public void openStaysView(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/albergueperronclient/ui/fxml/UIStay.fxml"));

        try {
            Parent root = loader.load();

            StaysManager staysManager = StayManagerFactory.createStayManager();
            //Get controller from the loader
            UIStayFXMLController stayController = loader.getController();
            stayController.setStaysManager(staysManager);

            //Initialize the primary stage of the application
            stayController.initStage(root);
            stage.hide();
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }
    }

    /**
     * Method that opens FTP CLient
     * @param event event that has caused the call to the function
     */
    public void openFTPView(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/albergueperronclient/ui/fxml/UIFTP.fxml"));

        IFTP ftpManager = IFTPFactory.getIFTPImplementation();

        try {
            Parent root = loader.load();

            //Get controller from the loader
            FTPController ftpController = loader.getController();
            ftpController.setFtpManager(ftpManager);

            //Initialize the primary stage of the application
            ftpController.initStage(root);

            stage.hide();
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }
    }

    /**
     * Method that opens Incidents View
     * @param event event that has caused the call to the function
     */
    public void openIncidentsView(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/albergueperronclient/ui/fxml/UIncident.fxml"));
        try {
            Parent root = loader.load();

            IncidentManager incidentManager = IncidentManagerFactory.getIncidentManager();
            RoomManager roomManager = RoomManagerFactory.getRoomManager();
            UsersManager userManager = UserManagerFactory.createUserManager();
            //Get controller from the loader
            IncidentFXMLController incidentController = loader.getController();
            incidentController.setLogicManager(incidentManager, roomManager, userManager);

            //Initialize the primary stage of the application
            incidentController.initStage(root);

            stage.hide();
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        } catch (ReadException ex) {
            LOGGER.severe(ex.getMessage());
        }
    }

    /**
     * Method that opens BlackList View
     * @param event event that has caused the call to the function
     */
    public void openBlackListView(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/albergueperronclient/ui/fxml/UIBlackList.fxml"));
        try {
            Parent root = loader.load();

            BlackListManager blackListManager = BlackListManagerFactory.getBlackListManager();
            UsersManager usersManager = UserManagerFactory.createUserManager();
            //Get controller from the loader
            BlackListFXMLController blackListController = loader.getController();
            blackListController.setLogicManager(blackListManager, usersManager);

            //Initialize the primary stage of the application
            blackListController.initStage(root);

            stage.hide();
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }
    }

    /**
     * Method that manages the log out of the user
     * @param event event that has caused the call to the function
     */
    public void logOut(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cerrar Sesión");
            alert.setContentText("¿Desea cerrar sesion?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                stage.close();
                try {
                    //Get the logic manager object for the initial stage
                    UILoginFXMLController loginManager = LoginFactory.getLoginManager();

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
            } else {
                LOGGER.info("Logout cancelled.");
            }
        } catch (Exception ex) {
            LOGGER.severe(ex.getMessage());
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
                stage.close();
                Platform.exit();
            } else {
                LOGGER.info("Exit cancelled.");
            }
        } catch (Exception ex) {
            LOGGER.severe(ex.getMessage());
            showErrorAlert("Error al intentar cerrar la aplicación.");
        }
    }
}
