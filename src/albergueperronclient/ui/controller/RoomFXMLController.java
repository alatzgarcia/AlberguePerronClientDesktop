/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.ui.controller;

import albergueperronclient.logic.IncidentManager;
import albergueperronclient.logic.IncidentManagerFactory;
import albergueperronclient.logic.RoomManager;
import albergueperronclient.logic.RoomManagerFactory;
import albergueperronclient.logic.UsersManager;
import albergueperronclient.logic.UsersManagerFactory;
import albergueperronclient.modelObjects.RoomBean;
import albergueperronclient.modelObjects.UserBean;
import static albergueperronclient.ui.controller.GenericController.LOGGER;
import java.util.Optional;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
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
    
    private RoomManager roomManager;
    
    public void setLogicManager(RoomManager roomManager){
        this.roomManager = roomManager;
    }
    
    /**
     * InitStage method for the UILogin view
     * @param root 
     */
    public void initStage(Parent root){
        //Create scene
        Scene scene = new Scene(root);
        stage = new Stage();
         //Associate scene to stage
        stage.setScene(scene);
        stage.setTitle("Room");
        stage.setResizable(false);
        //set window's events handlers
        stage.setOnShowing(this::handleWindowShowing);
        
        txtTotal.textProperty().addListener(this::onTextChanged);
        menuGuest.setOnAction(this::goToGuestsView);
        menuPet.setOnAction(this::goToPetsView);
        menuStay.setOnAction(this::goToStaysView);
        menuBlackList.setOnAction(this::goToBlackListView);
        menuLogOut.setOnAction(this::logOut);
        menuExit.setOnAction(this::exit);
        
        stage.show();
    }
    
    /**
     * OnShowing handler for the UILogin view
     * @param event 
     */
    public void handleWindowShowing(WindowEvent event){
        btnModify.setDisable(true);
        btnCancel.setDisable(true);
        btnSaveChanges.setDisable(true);
        
        txtTotal.setDisable(true);
        cbStatus.setDisable(true);
        menuIncidents.setDisable(true);
        /*btnLogin.setDisable(true);
        btnLogin.setMnemonicParsing(true);
        btnLogin.setText("_Iniciar Sesión");
        btnExit.setMnemonicParsing(true);
        btnExit.setText("_Salir");
        txtUsername.requestFocus();*/
        //Settear promptText
    }
    
    /**
     * Method for the login of a user
     * @param event 
     */
    /*public void login(ActionEvent event){
        
        try{
            //Sends a user to the logic controller with the entered parameters
            logicManager.login(new UserBean(txtUsername.getText(), 
                    pfPassword.getText()));
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/signupsigninuidesktop/ui/fxml/UILogged.fxml"));
            Parent root = loader.load();
            //Get controller from the loader
            UILoggedFXMLController loggedController = loader.getController();
            loggedController.setLogicManager(logicManager);
            //Initialize the primary stage of the application
            loggedController.initStage(root);
            
            stage.hide();
        } catch(IncorrectLoginException ile){
            LOGGER.info("Error. Incorrect login.");
            txtUsername.setStyle("-fx-border-color: red");
            showErrorAlert(ile.getMessage());
        } catch(IncorrectPasswordException ipe){
            LOGGER.info("Error.Incorrect password.");
            pfPassword.setStyle("-fx-border-color: red");
            showErrorAlert(ipe.getMessage());
        } catch(Exception e){
            LOGGER.info(e.getMessage());
            showErrorAlert("Error en el inicio de sesión.");
        }
    }*/
    
    /**
     * Method for the register of a new user
     * @param event 
     */
    /*public void register(ActionEvent event){
        //calls the logicManager register functio
        try{
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/signupsigninuidesktop/ui/fxml/UIRegister.fxml"));
            Parent root = loader.load();
            //Get controller from the loader
            UIRegisterFXMLController registerController = loader.getController();
            registerController.setLogicManager(logicManager);
            //Initialize the primary stage of the application
            registerController.initStage(root);
            
            stage.hide();
        }catch(Exception e){
            LOGGER.info(e.getMessage());
            showErrorAlert("Error al redirigir al registro de usuario.");
        }
    }*/
    
    /**
     * 
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
            }else{
                LOGGER.info("Logout cancelled.");
            } 
        }catch(Exception ex){
            LOGGER.severe(ex.getMessage());
        }
    }
    
    /**
     * Method to exit the application
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
    
    public void onTableSelectionChanged(ObservableValue observable,
             Object oldValue,
             Object newValue){
        //--TOFIX
        /*if(newValue!=null){
            selectedRoom = (RoomBean)newValue;
            txtTotal.setText(selectedRoom.get());
            txtDescription.setText(selectedRoom.getDescription());
            //--TOFIX --> Crear una forma de recibir solo el empleado
            List<UserBean> users = selectedIncident.getImplicateds();
            List<UserBean> guests = null;
            List<UserBean> employees = null;
            for(UserBean u: users){
                if(u.getPrivilege().equals(Privilege.EMPLOYEE)){
                    employees.add(u);
                }
                else{
                    guests.add(u);
                }
            }
            cbEmployee.getSelectionModel().select(employees);
            cbRoom.getSelectionModel().select(selectedIncident.getRoom());
            lstImplicateds.getSelectionModel().select(guests);
            
            btnModify.setDisable(false);
            btnDelete.setDisable(false);
            
            //--TOFIX Pensar si quiero pedir el foco ahí
            //btnModify.requestFocus();
        }else{
        //If there is not a row selected, clean window fields 
        //and disable create, modify and delete buttons
            selectedRoom = null;
            txtIncidentType.setText("");
            txtDescription.setText("");
            cbEmployee.getSelectionModel().clearSelection();
            cbRoom.getSelectionModel().clearSelection();
            lstImplicateds.getSelectionModel().clearSelection();
        }*/
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
        /*if(true){
            
        }*/
    }
}
