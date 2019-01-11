/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author 2dam
 */
public class UIRoomController implements Initializable {

    @FXML
    private TableView<?> tablePet;
    @FXML
    private TableColumn<?, ?> columnRoomNum;
    @FXML
    private TableColumn<?, ?> columnTotal;
    @FXML
    private TableColumn<?, ?> columnFree;
    @FXML
    private TableColumn<?, ?> columnStatus;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSaveChanges;
    @FXML
    private Button btnModify;
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
    private TextField txtTotal;
    @FXML
    private Text lblNumAvailable;
    @FXML
    private ComboBox<?> cbStatus;
    @FXML
    private Text lblRoomNum;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
