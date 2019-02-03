/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.ui.controller;

import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.api.FxAssert.*;
import static org.testfx.matcher.base.NodeMatchers.*;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isFocused;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;




/**
 *
 * @author Diego
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UIGuestFXMLController extends ApplicationTest{
    
    private String largeString="XXXXXXXXXX"
                            + "XXXXXXXXXX"
                            + "XXXXXXXXXX"
                            + "XXXXXXXXXX"
                            + "XXXXXXXXXX";
    private String shortString="XXX";
    private String validString="XXXXX";
    private String DNI="12345678Z";
    private String invalidDNI="ABCDEFG8";
    private String validEmail="alcaparra@gmail.com";
    private String invalidEmail="alcaparragmail.com";
    
    /**
     * Method to start the application
     * @param primaryStage
     * @throws Exception 
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        new UIGuestFXMLController().start(primaryStage);
    }
    
    @Test
    public void tets001_init(){
        //clickOn("#btnGuest");
        verifyThat("#tableGuest", isVisible());
        verifyThat("#textName", isInvisible());
        verifyThat("#txtFirstSurname", isInvisible());
        verifyThat("#txtSecondSurname", isInvisible());
        verifyThat("#txtDni", isInvisible());
        verifyThat("#txtLogin", isInvisible());
        verifyThat("#txtEmail", isInvisible());
        verifyThat("#btnCancel", isDisabled());
        verifyThat("#btnSaveChanges", isInvisible());
        verifyThat("#btnDeleteGuest", isDisabled());
        verifyThat("#btnModifyGuest", isDisabled());
        verifyThat("#btnInsertGuest", isInvisible());
        verifyThat("#btnReturn", isEnabled());
        verifyThat("#btnNewGuest", isEnabled());
    }
    
    @Test
    public void test002_checkTableSelection(){
        //clickOn("#btnGuest");
        verifyThat("#btnModify", isDisabled());
        TableView table=lookup("#tableStay").queryTableView();
        int rowCount=table.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",
                        rowCount,0);
        Node row=lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ",row);
        clickOn(row);
        verifyThat("#btnModify", isEnabled());
        clickOn("#btnModify");
        
        verifyThat("#tableGuest", isDisabled());
        verifyThat("#textName", isVisible());
        verifyThat("#txtFirstSurname", isVisible());
        verifyThat("#txtSecondSurname", isVisible());
        verifyThat("#txtDni", isVisible());
        verifyThat("#txtLogin", isVisible());
        verifyThat("#txtEmail", isVisible());
        verifyThat("#btnCancel", isEnabled());
        verifyThat("#btnSaveChanges", isVisible());
        verifyThat("#btnDeleteGuest", isDisabled());
        verifyThat("#btnModifyGuest", isDisabled());
        verifyThat("#btnInsertGuest", isVisible());
        verifyThat("#btnReturn", isEnabled());
        verifyThat("#btnNewGuest", isDisabled());
        
        clickOn("#btnCancel");
        verifyThat("¿Desea cancelar la operación?", isVisible());
        clickOn("OK");
        
        verifyThat("#tableGuest", isEnabled());
        verifyThat("#btnNewGuest", isEnabled());
        verifyThat("#btnDeleteGuest", isDisabled());
        verifyThat("#btnModifyGuest", isDisabled());
    }
    
    @Test
    public void test003_checkFieldsEnable(){
        //clickOn("#btnGuest");
        clickOn("#btnNewGuest");
        verifyThat("#btnInsertGuest", isDisabled());
        clickOn("#txtName");
        write(validString);
        verifyThat("#btnInsertGuest", isDisabled());
        clickOn("#txtFirstSurname");
        write(validString);
        verifyThat("#btnInsertGuest", isDisabled());
        clickOn("#txtSecondSurname");
        write(validString);
        verifyThat("#btnInsertGuest", isDisabled());
        clickOn("#txtDni");
        write(DNI);
        verifyThat("#btnInsertGuest", isDisabled());
        clickOn("#txtLogin");
        write(validString);
        verifyThat("#btnInsertGuest", isDisabled());
        clickOn("#txtEmail");
        write(validEmail);
        verifyThat("#btnInsertGuest", isEnabled());
    }
    
    @Test
    public void test004_checkFieldsDisableName(){
        test003_checkFieldsEnable();
        clickOn("#txtName");
        eraseText(10);
        write(shortString);
        verifyThat("#btnInsertGuest", isDisabled());
        eraseText(10);
        write(largeString);
        verifyThat("#btnInsertGuest", isDisabled());
    }
    
    @Test
    public void test004_checkFieldsDisableSurname1(){
        test003_checkFieldsEnable();
        clickOn("#txtFirstSurname");
        eraseText(10);
        write(shortString);
        verifyThat("#btnInsertGuest", isDisabled());
        eraseText(10);
        write(largeString);
        verifyThat("#btnInsertGuest", isDisabled());
    }
    
    @Test
    public void test004_checkFieldDisableSurname2(){
        test003_checkFieldsEnable();
        clickOn("#txtSecondSurname");
        eraseText(10);
        write(shortString);
        verifyThat("#btnInsertGuest", isDisabled());
        eraseText(10);
        write(largeString);
        verifyThat("#btnInsertGuest", isDisabled());
    }
    
    @Test
    public void test004_checkFieldDisableDni(){
        test003_checkFieldsEnable();
        clickOn("#txtDni");
        eraseText(10);
        write(DNI);
        verifyThat("#btnInsertGuest", isDisabled());
        eraseText(10);
        write(DNI);
        verifyThat("#btnInsertGuest", isDisabled());
    }
    
    @Test
    public void test004_checkFieldDisableLogin(){
        test003_checkFieldsEnable();
        clickOn("#txtLogin");
        eraseText(10);
        write(shortString);
        verifyThat("#btnInsertGuest", isDisabled());
        eraseText(10);
        write(largeString);
        verifyThat("#btnInsertGuest", isDisabled());
    }
    
    @Test
    public void test004_checkFieldDisableEmail(){
        test003_checkFieldsEnable();
        clickOn("#txtEmail");
        eraseText(10);
        write(invalidEmail);
        verifyThat("#btnInsertGuest", isDisabled());
    }

    @Test
    public void test005_createNewUser(){
       clickOn("#btnNewGuest");
       
    }
}
