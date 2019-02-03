/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.ui.controller;

import albergueperronclient.App;
import javafx.stage.Stage;
import org.junit.Test;

import javafx.stage.Stage;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.*;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.*;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;


/**
 * Test class for the UILoginFXMLController class
 * @author Nerea
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UILoginFXMLControllerIT extends ApplicationTest{
    
    /**
     * Method to start the application
     * @param primaryStage
     * @throws Exception 
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        new App().start(primaryStage);
    }
    
    /**
     * Method to test the init stage of the UILogin
     */
    @Test
    public void test01_InitStage(){
        verifyThat("#txtUsername", hasText(""));
        verifyThat("#pfPassword", hasText(""));
        verifyThat("#txtUsername", isEnabled());
        verifyThat("#pfPassword", isEnabled());
        verifyThat("#btnLogin", isDisabled());
        verifyThat("#btnExit", isEnabled()); 
        verifyThat("#txtUsername", isFocused());
        verifyThat("#loginPane", isVisible());
    }
    
    /**
     * Method to test the button login is enabled when both textfields are
     * correctly filled
     */
    @Test
    public void test02_BtnLoginEnabled(){
        clickOn("#txtUsername");
        write("prueba1111");
        clickOn("#pfPassword");
        write("ce350b5ecc");
        verifyThat("#btnLogin", isEnabled());
    }
    
    /**
     * Method to test that the button login is disabled if the login textfield
     * is filled with a shorter text than needed
     */
    @Test
    public void test03_BtnLoginDisabledOnShortLogin(){
        clickOn("#txtUsername");
        write("login");
        clickOn("#pfPassword");
        write("asvfgteedede");
        verifyThat("#btnLogin", isDisabled());
    }
    
    /**
     * Method to test that the button login is disabled if the login textfield
     * is filled with a longer text than needed
     */
    @Test
    public void test04_BtnLoginDisabledOnLongLogin(){
        clickOn("#txtUsername");
        write("aadadjadapdajdpajdadjasdjadjñadajdasjñdadjasñjkda");
        clickOn("#pfPassword");
        write("asvfgteedede");
        verifyThat("#btnLogin", isDisabled());
    }
    
    /**
     * Method to test that the button login is disabled if the password
     * passwordfield is filled with a shorter text than needed
     */
    @Test
    public void test05_BtnLoginDisabledOnShortPassword(){
        clickOn("#txtUsername");
        write("prueba1111");
        clickOn("#pfPassword");
        write("pass");
        verifyThat("#btnLogin", isDisabled());
    }
    
    /**
     * Method to test that the button login is disabled if the password
     * passwordfield is filled with a larger text than needed
     */
    @Test
    public void test06_BtnLoginDisabledOnLongPassword(){
        clickOn("#txtUsername");
        write("prueba1111");
        clickOn("#pfPassword");
        write("passwdadaldjadadadpasdjapsdjasdadsadadadadadsad");
        verifyThat("#btnLogin", isDisabled());
    }
    
    /**
     * Method to test that after enabling the button login, it gets disabled
     * again when the txtUsername textfield's text goes back to being too
     * short again
     */
    @Test
    public void test07_BtnLoginDisablesOnShortLogin(){
        clickOn("#txtUsername");
        write("loginName1");
        clickOn("#pfPassword");
        write("password1");
        verifyThat("#btnLogin", isEnabled());
        clickOn("#txtUsername");
        eraseText(10);
        write("login"); 
        verifyThat("#btnLogin", isDisabled());
    }
    
    /**
     * Method to test that after enabling the button login, it gets disabled
     * again when the txtUsername textfield's text goes back to being too
     * long again
     */
    @Test
    public void test08_BtnLoginDisablesOnLongLogin(){
        clickOn("#txtUsername");
        write("loginName1");
        clickOn("#pfPassword");
        write("password1");
        verifyThat("#btnLogin", isEnabled());
        clickOn("#txtUsername");
        eraseText(10);
        write("loginqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq"); 
        verifyThat("#btnLogin", isDisabled());
    }
    
    /**
     * Method to test that after enabling the button login it gets disabled
     * again when the pfPassword passwordfield's text goes back to being
     * too short again
     */
    @Ignore
    @Test
    public void test09_BtnLoginDisablesOnShortPassword(){
        clickOn("#txtUsername");
        write("loginName1");
        clickOn("#pfPassword");
        write("password1");
        verifyThat("#btnLogin", isEnabled());
        clickOn("#pfPassword");
        eraseText(10);
        write("passwd");
        verifyThat("#btnLogin", isDisabled());
    }
    
    /**
     * Method to test that after enabling the button login it gets disabled
     * again when the pfPassword passwordfield's text goes back to being
     * too long again
     */
    @Test
    public void test10_BtnLoginDisablesOnLongPassword(){
        clickOn("#txtUsername");
        write("loginName1");
        clickOn("#pfPassword");
        write("password1");
        verifyThat("#btnLogin", isEnabled());
        clickOn("#pfPassword");
        eraseText(10);
        write("passwdasasasasasasasasdasdasdasdasdasdasdasdasdasd");
        verifyThat("#btnLogin", isDisabled());
    }
    
    /**
     * Method to test that the correct message is shown when txtUsername
     * textfield loses the focus with wrong data on it
     */
    @Test
    public void test11_LoginErrorTextAppearsOnFocusChange(){
        clickOn("#txtUsername");
        write("login");
        clickOn("#pfPassword");
        verifyThat("#lblUsernameError", org.testfx.matcher.control.
                LabeledMatchers.hasText("Error. El campo usuario "
                                + "debe contener entre 8 y 30 caracteres."));
    }
    
    /**
     * Method to test that the correct message is shown when pfPassword
     * passwordfield loses the focus with wrong data on it
     */
    @Test
    public void test12_PasswordErrorTextAppearsOnFocusChange(){
        clickOn("#pfPassword");
        write("passwd");
        clickOn("#txtUsername");
        
        verifyThat("#lblPasswordError", org.testfx.matcher.control.
                LabeledMatchers.hasText("Error. El campo contraseña "
                                + "debe contener entre 8 y 30 caracteres."));
    }
    
    /**
     * Method to test that the correct message is shown when login
     * is wrong on login operation
     */
    @Test
    public void test13_LoginError(){
        clickOn("#txtUsername");
        write("lasfjafsa");
        clickOn("#pfPassword");
        write("password1");
        verifyThat("#btnLogin", isEnabled());
        clickOn("#btnLogin");
        //verifyThat("#loginPane", isVisible());
        
        verifyThat("#lblUsernameError", org.testfx.matcher.control.
                LabeledMatchers.hasText("Error en el inicio de sesión"));
       
    }
    

    
    /**
     * Method to test that the UILogged is visible when the login is correctly
     * made
     */
    @Test
    public void test14_IsLoggedViewVisible(){
        clickOn("#txtUsername");
        write("prueba1111");
        clickOn("#pfPassword");
        write("ce350b53cc");
        verifyThat("#btnLogin", isEnabled());
        clickOn("#btnLogin");
        verifyThat("#loggedPane", isVisible());
    }
    
    /**
     * Method to test that the UIRecovery is visible when the 
     * hyperlink is clicked
     */
    @Test 
    public void test15_IsRecoverPassViewVisible(){
        clickOn("#hlRemindPass");
        verifyThat("#recoverPane", isVisible());
    }
}
