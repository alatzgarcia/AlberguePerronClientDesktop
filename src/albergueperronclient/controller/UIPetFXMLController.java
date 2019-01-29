/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.controller;

import albergueperronclient.exceptions.BusinessLogicException;
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
import albergueperronclient.modelObjects.UserBean;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
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
    private TextField txtDni;
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
  
    private ObservableList<PetBean> petData;
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
            stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Pet");
            stage.setResizable(false);
            
            stage.setOnShowing(this::handleWindowShowing);
            
            //btn actions
            btnModify.setOnAction(this::updatePet);
            btnNew.setOnAction(this::createPet);
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
       
            petData=FXCollections.observableArrayList(petsManager.getAllPets());
            tablePet.setItems(petData);
            tablePet.getSelectionModel().selectedItemProperty().addListener(this::handlePetsTableSelectionChanged);
            tablePet.getSelectionModel().selectedItemProperty().addListener(this::handlePetsTableFocus);
            stage.show();
        }catch(Exception e){
            LOGGER.severe(e.getMessage()); 
        }
    }
    public void handlePetsTableFocus(ObservableValue observable, Object oldValue
        , Object newValue ){
        
        fieldChange(visible);
        fieldChange(disable);
        btnSaveChanges.setDisable(true);
        btnSaveChanges.setVisible(true);
        btnInsert.setDisable(true);
        btnInsert.setVisible(false);
        btnCancel.setDisable(false);
        if(newValue!=null){
            PetBean pet=(PetBean)newValue;
            txtDni.setText(pet.getOwner().toString());
            txtColour.setText(pet.getColour());
            txtEspecie.setText(pet.getSpecie());
            txtRaza.setText(pet.getRace());
            txtName.setText(pet.getName());
            txtDescription.setText(pet.getDescription());
            
            btnDelete.setDisable(false);
            btnNew.setDisable(false);
            btnModify.setDisable(false);
        }
        tablePet.refresh();
    }
    
    public void handleWindowShowing(WindowEvent event){
       btnCancel.setDisable(true);
       btnDelete.setDisable(true);
       btnNew.setDisable(false);
       btnReturn.setDisable(false);
       btnInsert.setVisible(false);
       btnSaveChanges.setVisible(false);
       btnModify.setDisable(true);
       fieldChange(visible);
       fieldChange(enable);
       txtDni.setPromptText("Introduzca el nombre del propietario");
       txtName.setPromptText("Introduce el nombre de la mascota");
       txtColour.setPromptText("Introduce el color de tu mascota");
       txtEspecie.setPromptText("Introduce la especie de tu mascota");
       txtRaza.setPromptText("Introduce la raza de tu mascota");
    }
    
    public void handlePetsTableSelectionChanged (ObservableValue observable, 
                                             Object oldValue, Object newValue){
        fieldChange(visible);
        fieldChange(disable);
        btnSaveChanges.setDisable(true);
        btnSaveChanges.setVisible(false);
        btnInsert.setDisable(true);
        btnInsert.setVisible(false);
        if(newValue!=null){
            pet=(PetBean)newValue;

            txtDni.setText(pet.getOwner().toString());//mirar
            txtColour.setText(pet.getColour());
            txtDescription.setText(pet.getDescription());
            txtEspecie.setText(pet.getSpecie());
            txtName.setText(pet.getName());
            txtRaza.setText(pet.getRace());
            
            btnNew.setDisable(true);
            btnModify.setDisable(false);
            btnDelete.setDisable(false);
            btnSaveChanges.setDisable(false);
           /* tablePet.getItems()
                    .add(new PetBean(MAX_LENGTH, columnDni.getText(), 
                            columnSpecie.getText(), columnRaza.getText(),
                            "colour", "description", columnName.getText()));   
            tablePet.refresh();*/
        }
    } 
  
    public void createPet(ActionEvent event){
        try{
            PetBean newPet = new PetBean();
            newPet.setColour(txtColour.getText());
            newPet.setDescription(txtDescription.getText());
            newPet.setDni(txtDni.getText()); //mirar esto
            newPet.setName(txtName.getText());
            newPet.setRace(txtEspecie.getText());
            newPet.setSpecie(txtEspecie.getText());
            petsManager.createPet(newPet);
            
            btnCancel.setDisable(true);
            btnInsert.setDisable(true);
            btnInsert.setVisible(false);
            txtDescription.setText("");
                //txtDescription.setDisable(true);
                txtName.setText("");
                //txtName.setDisable(true);
                txtRaza.setText("");
                //txtRaza.setDisable(true);
                txtEspecie.setText("");
                //txtEspecie.setDisable(true);
                txtColour.setText("");
                //txtColour.setDisable(true);
                txtDni.setText("");
                //txtDni.setDisable(true);
                
                tablePet.getItems().add(newPet);
                tablePet.setDisable(false);
        }catch(Exception e){
            LOGGER.info("Error: "+e);
        }
    }
     
    public void updatePet(ActionEvent event){
        //TERMINADO
        try{
            btnCancel.setDisable(false);
            btnSaveChanges.setVisible(true);
            btnSaveChanges.setDisable(false);
            btnSaveChanges.toFront();   
            //PetBean petToModify = (PetBean)tablePet.getSelectionModel().getSelectedItem());;
            PetBean petSelection =
                        ((PetBean)tablePet.getSelectionModel().getSelectedItem());  
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION,
                             "¿Modificar la fila seleccionada?\n"
                             + "Esta operacion no se puede deshacer.",
                             ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){   
                btnCancel.setDisable(false);
                btnSaveChanges.setVisible(true);
                btnSaveChanges.setDisable(false);
                btnSaveChanges.toFront();
                //Enables all the fields
                fieldChange(visible);
                fieldChange(enable);
                /*
                petSelection.setDescription(txtDescription.getText()); 
                petSelection.setName(txtName.getText());
                petSelection.setColour(txtColour.getText());
                petSelection.setRace(txtRaza.getText());
                petSelection.setSpecie(txtEspecie.getText());
                petsManager.updatePet(petSelection, petId);*/
                 txtDescription.setText("");
                //txtDescription.setDisable(true);
                txtName.setText("");
                //txtName.setDisable(true);
                txtColour.setText("");
                //txtColour.setDisable(true);
                txtRaza.setText("");
                //txtRaza.setDisable(true);
                txtEspecie.setText("");
                //txtEspecie.setDisable(true);
                btnSaveChanges.setVisible(true);
                
            }else{
                LOGGER.info("No se modificara nada");
                //tablePet.getSelectionModel().clearSelection();
                //Set the btns
                btnInsert.setDisable(true);
                btnDelete.setDisable(true);
                btnNew.setDisable(false);
                btnCancel.setDisable(true);
                btnSaveChanges.setDisable(false);
                btnSaveChanges.setVisible(false);
            }
            
        }catch(Exception e){
            LOGGER.info("Error: "+e);
        }
    }
    
    public void saveUpdatePets(ActionEvent event){
        //TERMINADO
        LOGGER.info("SE HACE EL BOTON DE UPDATE");
        try{
             btnSaveChanges.setVisible(true);
              btnSaveChanges.setDisable(true);
            //btnSaveChanges.setDisable(true);
            
            petsManager.updatePet(getPetFromFields(),tablePet.getSelectionModel().getSelectedItem().getId());
        }catch(Exception e){
            LOGGER.info("The update failed : "+e.getMessage());
        }
    }
    
    public PetBean getPetFromFields(){
        //Terminado
        pet=new PetBean();
        //sets the attributes with the fields
        if(tablePet.getSelectionModel().getSelectedItem()!=null){
            //Actualizar mascota
            pet.setId(tablePet.getSelectionModel().getSelectedItem().getId());
        }
        pet.setDni(txtDni.getText());
        pet.setOwner(owner);
        pet.setColour(txtColour.getText());
        pet.setDescription(txtDescription.getText());
        pet.setName(txtName.getText());
        pet.setRace(txtRaza.getText());
        pet.setSpecie(txtEspecie.getText());
        return pet;
    }
  
    public void deletePet(ActionEvent event){
        //TERMINADOS
        Alert alert=null;
         try{
             PetBean petSelected =
                     ((PetBean)tablePet.getSelectionModel().getSelectedItem());
             alert=new Alert(Alert.AlertType.CONFIRMATION,
                             "¿Borrar la fila seleccionada?\n"
                             + "Esta operacion no se puede deshacer.",
                             ButtonType.OK, ButtonType.CANCEL);
             Optional<ButtonType> result = alert.showAndWait();
             //if OK to delete pet
             if(result.isPresent() && result.get() == ButtonType.OK){
                //this.petsManager.deletePet(petSelected);
                tablePet.getItems().remove(petSelected);
                tablePet.refresh();
                //Clear fields
                txtDescription.setText("");
                txtName.setText("");
                txtRaza.setText("");
                txtEspecie.setText("");
                txtColour.setText("");
                txtDni.setText("");
                //clear and refresh table
                tablePet.getSelectionModel().clearSelection();
                tablePet.refresh();
                fieldChange(enable);
                fieldChange(visible);
                 
             }
        }catch(Exception e){
            LOGGER.info("Error"+e.getMessage());
        } 
    }
    
    public void cancelPet (ActionEvent event){
        //TERMINADO
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
                /*btnSaveChanges.setVisible(false);
                btnInsert.setVisible(false);*/
                tablePet.getSelectionModel().clearSelection();
            }else if(result.get()==ButtonType.CANCEL){
                alert.close();
            }
        } catch(Exception ex){
             showErrorAlert("Error al cancelar la operación:\n"+ex.getMessage());
        }
    }
    
    public void toLogged (ActionEvent event){
        //Terminado
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
            LOGGER.severe(e.getMessage());
        }
    }
     
    public void fieldChange(int change){
        switch(change){
            case 1:
                //sets visibles all the fields of the window
                txtDni.setVisible(true);
                txtColour.setVisible(true);
                txtDescription.setVisible(true);
                txtEspecie.setVisible(true);
                txtName.setVisible(true);
                txtRaza.setVisible(true);
                break;
            case 2:
                //sets invisible all the fiels of the window
                txtDni.setVisible(false);
                txtColour.setVisible(false);
                txtDescription.setVisible(false);
                txtEspecie.setVisible(false);
                txtName.setVisible(false);
                txtRaza.setVisible(false);
                break;
            case 3:
                //Enables the fields
                txtDni.setEditable(true);
                txtColour.setEditable(true);
                txtDescription.setEditable(true);
                txtEspecie.setEditable(true);
                txtName.setEditable(true);
                txtRaza.setEditable(true);
                break;
            case 4:
                //Disables the fields
                txtDni.setEditable(false);
                txtColour.setEditable(false);
                txtDescription.setEditable(false);
                txtEspecie.setEditable(false);
                txtName.setEditable(false);
                txtRaza.setEditable(false);
                break;
            case 5:
                //Deletes all the existing data
                txtDni.setText("");
                txtColour.setText("");
                txtDescription.setText("");
                txtEspecie.setText("");
                txtName.setText("");
                txtRaza.setText("");
                break;
        }
    }
    
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
    
    public void exit(ActionEvent event){
        Platform.exit();
    }
    
    public void returnToPrevious(ActionEvent event){
        stage.close();
        previousStage.show();
    }
}
