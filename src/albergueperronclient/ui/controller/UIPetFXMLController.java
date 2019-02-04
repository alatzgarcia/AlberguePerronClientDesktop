/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.ui.controller;

import albergueperronclient.exceptions.BusinessLogicException;
import albergueperronclient.exceptions.ReadException;
import albergueperronclient.logic.UserManagerFactory;
import albergueperronclient.logic.UsersManager;
import java.util.Optional;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;

import javafx.scene.control.TableColumn;

import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import albergueperronclient.modelObjects.PetBean;
import albergueperronclient.modelObjects.Privilege;
import albergueperronclient.modelObjects.UserBean;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
/**
 * FXML Controller class
 *
 * @author ikerm
 */
public class UIPetFXMLController extends GenericController {
    @FXML
    private TableView<PetBean> tablePet;
    @FXML
    private TableColumn columnName;
    @FXML
    private TableColumn columnDni;
    @FXML
    private TableColumn columnRaza;
     @FXML
    private TableColumn columnSpecie;
    @FXML
    private Button btnNew;
    @FXML
    private ComboBox<UserBean> cbUsers;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSaveChanges;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnModify;
    @FXML
    private TextField txtRaza;
    @FXML
    private TextField txtEspecie;
    @FXML
    private Button btnInsert;
    @FXML
    private Button btnReturn;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtColour;
    @FXML
    private TextArea txtDescription;
    @FXML
    private MenuItem menuGuest;
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
    
    private ObservableList<PetBean> petData=FXCollections.observableArrayList();
    private ObservableList<UserBean> usersData=FXCollections.observableArrayList();
    private PetBean pet;
    private Integer petId;
    private UserBean owner;
    private int visible=1;
    private int invisible=2;
    private int enable=3;
    private int disable=4;
    private int clean=5;
    /**
     * Initializes the controller class.
     * @param root
     * @throws albergueperronclient.exceptions.BusinessLogicException
     */
    public void initStage(Parent root) throws BusinessLogicException{ 
        try{
            Scene scene = new Scene(root);
            stage=new Stage();
            stage.setScene(scene);
            stage.setTitle("Pet");
            stage.setResizable(false);
            
            stage.setOnShowing(this::handleWindowShowing);
            
            //btn actions
            btnModify.setOnAction(this::updatePet);
            btnNew.setOnAction(this::newPet);
            btnInsert.setOnAction(this::saveNewPet);
            btnReturn.setOnAction(this::toLogged);
            btnDelete.setOnAction(this::deletePet);
            btnCancel.setOnAction(this::cancelPet);
            btnSaveChanges.setOnAction(this::saveUpdatePets);
            //menu actions
            menuBlackList.setOnAction(this::goToBlackListView);
            menuExit.setOnAction(this::exit);
            menuGuest.setOnAction(this::goToGuestView);
            menuIncidences.setOnAction(this::goToIncidentsView);
            menuLogOut.setOnAction(this::toLogged);
            menuRoom.setOnAction(this::goToRoomView);
           //Attributes of columns
            columnDni.setCellValueFactory(new PropertyValueFactory<>("owner"));
            columnSpecie.setCellValueFactory(new PropertyValueFactory<>("specie"));
            columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
            columnRaza.setCellValueFactory(new PropertyValueFactory<>("race"));
            try{
                //Create an observable list for the users
                petData = FXCollections.observableArrayList(petsManager.getAllPets());
                //Set the observable data
                tablePet.setItems(petData); 
                //Create the interfaces
                usersManager=UserManagerFactory.createUserManager(); 
                //Insert the data at cbs
                usersData=FXCollections.observableArrayList(usersManager.getAllUsers());
                //Insert the combo
                cbUsers.setItems(usersData);   
            }catch(BusinessLogicException ble){
                LOGGER.severe(ble.getMessage());
            }
            petData=FXCollections.observableArrayList(petsManager.getAllPets());
            //tablePet.setItems(petData);
            tablePet.getSelectionModel().selectedItemProperty().addListener(this::handlePetsTableSelectionChanged);
            tablePet.getSelectionModel().selectedItemProperty().addListener(this::handlePetsTableFocus);
            stage.show();
        }catch(Exception e){
            e.printStackTrace(); 
        }
    }
    /**
     * Focus a row of the table and set the parameter visible in the 
     * TextFields and in the ComboBox, but disabled.
     * Set the buttons(Insert,SaveChanges,New,Cancel and Report) enabled
     * @param observable
     * @param oldValue
     * @param newValue 
     */
    public void handlePetsTableFocus(ObservableValue observable, Object oldValue
        , Object newValue ){
        
        fieldChange(visible);
        fieldChange(disable);
        btnSaveChanges.setDisable(true);
        btnSaveChanges.setVisible(false);
        btnInsert.setDisable(true);
        btnInsert.setVisible(false);
        btnNew.setDisable(false);
        btnCancel.setDisable(false);
        if(newValue!=null){
            PetBean pet=(PetBean)newValue;
            cbUsers.getSelectionModel().select(tablePet.getSelectionModel().getSelectedItem().getOwner());
            txtColour.setText(pet.getColour());
            txtEspecie.setText(pet.getSpecie());
            txtRaza.setText(pet.getRace());
            txtName.setText(pet.getName());
            txtDescription.setText(pet.getDescription());
            btnNew.setDisable(true);
        }
        tablePet.refresh();
    }
    
    /**
     * Set the buttons(Cancel,Delete,New,Return,SaveChanges,Insert,Modify)
     * enabled when the windows starts and set the promptText in the textFields 
     * and in the ComboBox
     * @param event 
     */
    public void handleWindowShowing(WindowEvent event){
       btnCancel.setDisable(true);
       btnDelete.setDisable(true);
       btnNew.setDisable(false);
       btnReturn.setDisable(false);
       btnInsert.setVisible(false);
       btnSaveChanges.setVisible(false);
       btnModify.setDisable(true);
       cbUsers.setDisable(true);
       fieldChange(disable);
       txtName.setPromptText("Introduce el nombre de la mascota");
       cbUsers.setPromptText("Introduce el dueño de la mascota");
       txtColour.setPromptText("Introduce el color de tu mascota");
       txtEspecie.setPromptText("Introduce la especie de tu mascota");
       txtRaza.setPromptText("Introduce la raza de tu mascota");
    }
    
    /**
     * Changes the focus of the row in the table, puting in the TextField and in 
     * the ComboBox the data of the selected row.
     * @param observable
     * @param oldValue
     * @param newValue 
     */
    public void handlePetsTableSelectionChanged (ObservableValue observable, 
        Object oldValue, Object newValue){  
        fieldChange(visible);
        fieldChange(disable);
        btnSaveChanges.setDisable(true);
        btnSaveChanges.setVisible(false);
        btnInsert.setDisable(true);
        btnInsert.setVisible(false);
        btnNew.setDisable(true);
        if(newValue!=null){
            pet=(PetBean)newValue;
            cbUsers.setItems(usersData);
            txtColour.setText(pet.getColour());
            txtDescription.setText(pet.getDescription());
            txtEspecie.setText(pet.getSpecie());
            txtName.setText(pet.getName());
            txtRaza.setText(pet.getRace());
            //set btns
            btnNew.setDisable(true);
            btnModify.setDisable(false);
            btnDelete.setDisable(false);
            btnSaveChanges.setDisable(false);
        }
    } 
    
    /**
     * Create a new Pet
     * Enable the textField to write a new pet.
     * Put the buttons(Insert,Modify,Cancel) enabled
     * BtnNew now is disabled
     * @param event 
     */
  public void newPet(ActionEvent event){
        //Set the btns
        btnInsert.setVisible(true);
        btnInsert.setDisable(false);
        btnInsert.toFront();
        btnModify.setDisable(true);
        btnNew.setDisable(true);
        btnCancel.setDisable(false);
        //Set the fieldChange
        fieldChange(enable);
        fieldChange(visible);
        cbUsers.setDisable(false);
    }
    
  /**
   * Saves the new pet sending the information to the database
   * @param event 
   */
    public void saveNewPet(ActionEvent event){
        try{
            if(checkFields()){
                petsManager.createPet(getPetFromFields());
                tablePet.getItems().add(pet);
                tablePet.refresh();
                Alert alert = new Alert(Alert.AlertType.INFORMATION.INFORMATION);
                alert.setTitle("Nueva mascota");
                alert.setContentText("La mascota fue registrada");    
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get()== ButtonType.OK){
                    tablePet.getSelectionModel().clearSelection();
                    alert.close(); 
                    fieldChange(clean);
                    fieldChange(disable);
                    btnDelete.setDisable(true);
                    btnModify.setDisable(true);
                    btnInsert.setDisable(true);
                    btnInsert.setVisible(false);
                    btnSaveChanges.setDisable(true);
                    btnSaveChanges.setVisible(false);
                    btnNew.setDisable(false);
                    cbUsers.setDisable(true);
                }
            }else{
                Alert alert=new Alert(Alert.AlertType.ERROR,"Introduce TODOS los datos."); 
                alert.showAndWait();
            }
        }catch(BusinessLogicException ble){
            LOGGER.info("The create failed "+ble.getMessage());
        }catch(Exception e){
            LOGGER.info("The create failed "+e.getMessage());
        }
    }
    
    /**
     * Shows an alert asking if you want to modify the pet, if you click yes
     * the buttons "SaveChanges" is going to be enabled and the TextField are going
     * to be enabled.
     * @param event 
     */
    public void updatePet(ActionEvent event){
        try{
            btnCancel.setDisable(false);
            btnSaveChanges.setVisible(true);
            btnSaveChanges.setDisable(false);
            btnSaveChanges.toFront();     
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION,
                             "¿Modificar la fila seleccionada?\n"
                             + "Esta operacion no se puede deshacer.",
                             ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){  
                cbUsers.setDisable(false);
                btnCancel.setDisable(false);
                btnSaveChanges.setVisible(true);
                btnSaveChanges.setDisable(false);
                btnSaveChanges.toFront();
                //Enables all the fields
                fieldChange(visible);
                fieldChange(enable);
                btnSaveChanges.setVisible(true);          
            }else{
                LOGGER.info("No se modificara nada");
                //Set the btns
                btnNew.setDisable(false);
                btnCancel.setDisable(true);
            }  
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * Saves the update pet sending the information to the database
     * @param event 
     */
    public void saveUpdatePets(ActionEvent event){
        try{
            if(checkFields()){
                Alert alert=new Alert(Alert.AlertType.CONFIRMATION,
                                 "¿Seguro de modificar la fila seleccionada?\n"
                                 + "Esta operacion no se puede deshacer.",
                                 ButtonType.OK, ButtonType.CANCEL);
                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK){  
                    petsManager.updatePet(getPetFromFields(),tablePet.getSelectionModel().getSelectedItem().getId()); 
                    tablePet.setItems(FXCollections.observableArrayList(petsManager.getAllPets()));
                    tablePet.refresh();
                    fieldChange(clean);
                    fieldChange(disable);
                    cbUsers.setDisable(true);
                    btnDelete.setDisable(true);
                    btnModify.setDisable(true);
                }else{
                    LOGGER.info("No se modificara nada");
                    //Set the btns
                    btnNew.setDisable(false);
                    btnCancel.setDisable(true);
                }
            }else{
                Alert alert=new Alert(Alert.AlertType.ERROR,"Introduce TODOS los datos."); 
                alert.showAndWait();
            }
        }catch(Exception e){
            LOGGER.info("The update failed : "+e.getMessage());
        }
    }
    
    /**
     * Control of the Fields of the view
     * @return pet
     */
    public PetBean getPetFromFields(){
        pet=new PetBean();
        //sets the attributes with the fields
        if(tablePet.getSelectionModel().getSelectedItem()!=null){
            //Actualizar mascota
            pet.setId(tablePet.getSelectionModel().getSelectedItem().getId());
        }
        pet.setOwner(cbUsers.getSelectionModel().getSelectedItem());
        pet.setColour(txtColour.getText());
        pet.setDescription(txtDescription.getText());
        pet.setName(txtName.getText());
        pet.setRace(txtRaza.getText());
        pet.setSpecie(txtEspecie.getText());
        return pet;
    }
    
    /**
     * Delete the  pet 
     * @param event 
     */
    public void deletePet(ActionEvent event){
         try{
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION,
                             "¿Borrar la fila seleccionada?\n"
                             + "Esta operacion no se puede deshacer.",
                             ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();
             //if OK to delete pet
            if(result.isPresent() && result.get() == ButtonType.OK){
                 petsManager.deletePet(tablePet.getSelectionModel().getSelectedItem().getId());
                /*tablePet.getItems().remove(tablePet.getSelectionModel().getSelectedItem().getId());
                tablePet.getSelectionModel().clearSelection();*/
                tablePet.getItems().remove(pet);
                tablePet.refresh();
                fieldChange(enable);
                fieldChange(visible);      
            }else{
                alert.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        } 
    }
    
    /**
     * Cancel the action of the window
     * @param event 
     */
    public void cancelPet (ActionEvent event){
         try{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION.CONFIRMATION);
            alert.setTitle("Cancelar");
            alert.setContentText("¿Desea cancelar la operación?");    
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get()== ButtonType.OK){
                fieldChange(visible);
                fieldChange(clean);
                fieldChange(enable);
                btnCancel.setDisable(false);
                btnNew.setDisable(false);
                btnModify.setDisable(true);
                btnDelete.setDisable(true);
                btnSaveChanges.setDisable(true);
                btnInsert.setDisable(true);
                btnInsert.setVisible(false);
                tablePet.getSelectionModel().clearSelection();
            }else if(result.get()==ButtonType.CANCEL){
                alert.close();
            }
        } catch(Exception ex){
             showErrorAlert("Error al cancelar la operación:\n"+ex.getMessage());
        }
    }
     
    /**
     * Checks that the fields are with data
     * @return 
     */
    public boolean checkFields(){
        Boolean correctData =true;
        if(txtColour.getText().trim().length()==0 
            || txtDescription.getText().trim().length()==0|| txtEspecie.getText().trim().length()==0
            ||txtName.getText().trim().length()==0 ||txtRaza.getText().trim().length()==0){
                correctData=false;
        }
        return correctData;
    }
    /**
     * Controls the changes of the  fields of the view
     * @param change 
     */
    public void fieldChange(int change){
        switch(change){
            case 1:
                //sets visibles all the fields of the window
                txtColour.setVisible(true);
                txtDescription.setVisible(true);
                txtEspecie.setVisible(true);
                txtName.setVisible(true);
                txtRaza.setVisible(true);
                break;
            case 2:
                //sets invisible all the fiels of the window
                txtColour.setVisible(false);
                txtDescription.setVisible(false);
                txtEspecie.setVisible(false);
                txtName.setVisible(false);
                txtRaza.setVisible(false);
                break;
            case 3:
                //Enables the fields
                txtColour.setEditable(true);
                txtDescription.setEditable(true);
                txtEspecie.setEditable(true);
                txtName.setEditable(true);
                txtRaza.setEditable(true);
                break;
            case 4:
                //Disables the field
                txtColour.setEditable(false);
                txtDescription.setEditable(false);
                txtEspecie.setEditable(false);
                txtName.setEditable(false);
                txtRaza.setEditable(false);
                break;
            case 5:
                //Deletes all the existing data
                txtColour.setText("");
                txtDescription.setText("");
                txtEspecie.setText("");
                txtName.setText("");
                txtRaza.setText("");
                break;
        }
    }
    /**
     * Go to RoomView
     * @param event 
     */
    public void goToRoomView(ActionEvent event){
        /*try{
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
        }*/
    }
    
    /**
     * Go to BlackListView
     * @param event 
     */
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
     
    /**
     * Go to StayView
     * @param event 
     */
    public void goToStayView(ActionEvent event){
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
            showErrorAlert("Error al redirigir a la vista de las estancias.");
        }*/
    }
    
    /**
     * Go to the IncidentsViews
     * @param event 
     */
    public void goToIncidentsView(ActionEvent event){
        /*try{
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/signupsigninuidesktop/ui/fxml/UIIncidentsFXMLController.fxml"));
            Parent root = loader.load();
            //Get controller from the loader
            UIIncidentsFXMLController incidentsController = loader.getController();
        
            incidentsController.setLogicManager(IncidentManagerFactory.getIncidentManager());
            //Send the current stage for coming back later
            incidentController.setPreviousStage(stage);
            //Initialize the primary stage of the application
            incidentController.initStage(root);
            //--TOFIX --> Decidir si esconder el stage o cerrarlo
            stage.hide();
            stage.close();
        }catch(Exception e){
            LOGGER.severe(e.getMessage());
            showErrorAlert("Error al redirigir a la vista de las incidencias.");
        }*/
    }
    
    /**
     * Go to GuestView
     * @param event 
     */
    public void goToGuestView(ActionEvent event){
        /*try{
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/signupsigninuidesktop/ui/fxml/UIGuestFXMLController.fxml"));
            Parent root = loader.load();
            //Get controller from the loader
            UIGuestFXMLController guestController = loader.getController();
        
            guestController.setLogicManager(UserManagerFactory.getUserManager());
            //Send the current stage for coming back later
            guestController.setPreviousStage(stage);
            //Initialize the primary stage of the application
            guestController.initStage(root);
            //--TOFIX --> Decidir si esconder el stage o cerrarlo
            stage.hide();
            stage.close();
        }catch(Exception e){
            LOGGER.severe(e.getMessage());
            showErrorAlert("Error al redirigir a la vista de los usuarios.");
        }*/
    }
    
    /**
     * Exit from the app
     * @param event 
     */
    public void exit(ActionEvent event){
        Platform.exit();
    }
    
    /**
     * Return to previous view
     * @param event 
     */
    public void returnToPrevious(ActionEvent event){
        stage.close();
        previousStage.show();
    }
    
    /**
     * Go to Logged view
     * @param event 
     */
    public void toLogged (ActionEvent event){
        try{
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Volver al Menú");
            alert.setContentText("¿Desea volver al menú?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get()==ButtonType.OK){
                stage.close();
            }else{
                LOGGER.severe("Operación cancelada");
            }
        }catch(Exception e){
           e.printStackTrace();
        }
    }
}
