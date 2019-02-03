/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.ui.controller;

import albergueperronclient.App;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.api.FxAssert.*;
import org.testfx.api.FxRobot;
import static org.testfx.matcher.base.NodeMatchers.*;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isFocused;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import static org.junit.Assert.*;
/**
 *
 * @author Diego
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UIStayFXMLController extends ApplicationTest{
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        new App().start(primaryStage);
    }
    
    @Test
    public void tets001_init(){
        clickOn("#btnStay");
        verifyThat("#btnNew", isEnabled());
        verifyThat("#btnCancel", isDisabled());
        verifyThat("#btnSaveChanges", isDisabled());
        verifyThat("#btnDelete", isDisabled());
        verifyThat("#btnSaveModify", isInvisible());
        verifyThat("#btnInsert", isInvisible());
        verifyThat("#btnReturn", isEnabled());
        verifyThat("#cbGuest", isInvisible());
        verifyThat("#cbRoom", isInvisible());
        verifyThat("#datePicker", isInvisible());
    }
    
    @Test
    public void test002_operationCheckButtons(){
        clickOn("#btnStay");        
        clickOn("#btnNew");
        verifyThat("#cbGuest", isVisible());
        verifyThat("#cbRoom", isVisible());
        verifyThat("#datePicker", isVisible());
        verifyThat("#cbGuest", isEnabled());
        verifyThat("#cbRoom", isEnabled());
        verifyThat("#datePicker", isEnabled());      
        verifyThat("#btnCancel", isEnabled());
    }
    
    @Test
    public void test003_newStay(){
        clickOn("#btnStay");
        clickOn("#btnNew");
        TableView table=lookup("#tableStay").queryTableView();
        int rowCount=table.getItems().size();
        clickOn("#datePicker");
        clickOn("#datePicker");
        clickOn("#btnInsert");
        verifyThat("¿Desea borrar la estancia?",
                    isVisible()); 
        assertEquals(rowCount+1,table.getItems().size());
    }
    
    public void test004_deleteStayCancel(){
        clickOn("#btnStay");
        TableView table=lookup("#tableStay").queryTableView();
        int rowCount=table.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",
                        rowCount,0);
        Node row=lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ",row);
        clickOn(row);
        verifyThat("#btnDelete", isEnabled());
        clickOn("#btnDelete");
        verifyThat("¿Desea borrar la estancia?",
                    isVisible());    
        clickOn("Cancel");
//To check
        assertEquals(rowCount,table.getItems().size());
    }
    
     public void test005_deleteStay() {
        clickOn("#btnStay");
        TableView table=lookup("#tableStay").queryTableView();
        int rowCount=table.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",
                        rowCount,0);
        Node row=lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ",row);
        clickOn(row);
        verifyThat("#btnDelete", isEnabled());
        clickOn("#btnDelete");
        verifyThat("¿Desea borrar la estancia?",
                    isVisible());    
        clickOn("OK");
        assertEquals(rowCount-1,table.getItems().size());
    }
     
    public void test006_updateStay(){
        clickOn("#btnStay");
        TableView table=lookup("#tableStay").queryTableView();
        int rowCount=table.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",
                        rowCount,0);
        Node row=lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        clickOn("#btnModify");
        verifyThat("#btnSaveChanges", isDisabled());
    }
}
