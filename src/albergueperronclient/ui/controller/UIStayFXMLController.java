/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.ui.controller;

import albergueperronclient.exceptions.BusinessLogicException;
import albergueperronclient.logic.StaysManager;
import albergueperronclient.modelObjects.StayBean;
import albergueperronclient.modelObjects.UserBean;
import static albergueperronclient.ui.controller.GenericController.LOGGER;
import java.net.URL;
import java.util.Collection;
import java.util.EventListener;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Vector;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import javafx.stage.Stage;

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
    private ComboBox<StayBean> cbGuest;
    @FXML
    private ComboBox<StayBean> cbRoom;
    @FXML
    private TextField txtDate;
    @FXML
    private Button btnDateToToday;
    private ObservableList<StayBean> staysData;
    private StayBean stay;
    private int visible=1;
    private int invisible=2;
    private int enable=3;
    private int disable=4;
    private int clean=5;
    /**
     * Initializes the controller class.
     */
     public void initStage(Parent root) throws BusinessLogicException{
        Scene scene = new Scene(root);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.setResizable(false);
        
        //stage.setOnShowing(this::handleWindowShowing);
        
        //Sets the columns the attributes to use
        columnGuests.setCellValueFactory(new PropertyValueFactory<>("guest"));
        columnRoom.setCellValueFactory(new PropertyValueFactory<>("room"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        
        //Create an observable lis for the users
        staysData = FXCollections.observableArrayList(staysManager.getAllStays());
        //Set the observable data
        tableStay.setItems(staysData);
        
        //Insertar datos en los cb
        ObservableList<StayBean> stay;
        
        
        cbGuest=new ComboBox();
        cbRoom=new ComboBox();
        
        for (StayBean s : staysData) {
            //guest.addElement(s.getGuest());
            //room.addElement(s.getRoom());
            cbGuest.setItems();
            stay=
        }
        
        
        
        btnNew.setOnAction(this::newStay);
        
        //Sets the selection listener
        tableStay.getSelectionModel().selectedItemProperty().addListener(this::handleUserTableFocus);
        
        stage.show();
       
    }
    
    public void handleUserTableFocus(ObservableValue observable, Object oldValue, Object newValue){
        btnDelete.setDisable(false);
        btnModify.setDisable(false);
        
        btnSaveChanges.setOnAction(this::stayModify);
    }
    
    public void stayModify(){
        btnSaveChanges.setVisible(true);
        btnSaveChanges.setDisable(false);
        
        fieldChange(enable);
        fieldChange(visible);
        
        btnSaveChanges.setOnAction(this::saveStayChanges);
    }
    
    public void saveStayChanges(){
        btnDelete.setDisable(true);
        btnModify.setDisable(true);
        btnNew.setDisable(true);
        
        try{
                staysManager.updateStay(getStayFromFields(),tableStay.getSelectionModel().getSelectedItem().getId().toString());
                tableStay.getItems().remove(tableStay.getSelectionModel().getSelectedItem());
                tableStay.getItems().add(stay);
                tableStay.refresh();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION.INFORMATION);
                alert.setTitle("Modificado");
                alert.setContentText("Se modificó el usuario "+ tableStay.getSelectionModel().getSelectedItem().getId());    
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get()== ButtonType.OK){
                    tableStay.getSelectionModel().clearSelection();
                    alert.close();                    
                }
            }catch(BusinessLogicException ble){
                LOGGER.info("The update failed "+ble.getMessage());
            }
    }
    
    public void newStay(EventListener event){
        btnInsert.setVisible(true);
        btnInsert.setDisable(false);
        
        fieldChange(enable);
        fieldChange(visible);
        
    }
    
    public void saveNewStay(){
        
    }
     
    public void handleWindowShowing(){
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
    }
    
     public void fieldChange(int change){
        switch(change){
            case 1:
                //Sets visbles all the fields of the window
                cbGuest.setVisible(true);
                cbRoom.setVisible(true);
                txtDate.setVisible(true);
        break;
            case 2:
                //Sets invisible all the fields of the window
                cbGuest.setVisible(false);
                cbRoom.setVisible(false);
                txtDate.setVisible(false);
                break;
            case 3:
                //Enables the fields
                cbGuest.setDisable(false);
                cbRoom.setDisable(false);
                txtDate.setDisable(false);
                break;
            case 4:
                //Disables the fields
                cbGuest.setDisable(true);
                cbRoom.setDisable(true);
                txtDate.setDisable(true);
                break;
            case 5:
                //Deletes all the existing data
//QUITAR LA SELECCION
                //cbGuest.
                //cbRoom.setText("");
                txtDate.setText("");
                break;
        }
    }
     
    private StayBean getStayFromFields(){
        
 //TODO
        stay= new StayBean();
        
        //Sets the attributes with the fields
        stay.setDate(new Date(txtDate.getText()));
        stay.setGuest(cbGuest.getValue().toString());
        stay.setRoom(cbRoom.getValue());
        stay.setId(tableStay.getSelectionModel().getSelectedItem().getId());
        
        return stay;
    }
}
