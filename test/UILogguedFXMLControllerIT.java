/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import albergueperronclient.App;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.*;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.*;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

/**
 *
 * @author nerea
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UILogguedFXMLControllerIT extends ApplicationTest  {
    
    @Override public void start(Stage primaryStage) throws Exception{
        new App().start(primaryStage);
    }
    /**
     * Load the Login window, to connect with the Logged window.
     */
    @Before
    public void login(){
        clickOn("#txtUsername");
        write("prueba1111");
        clickOn("#pfPassword");
        write("ce350b5ecc");
        clickOn("#btnLogin");
    }
    
    /**
     * The buttons are enabled
     */
    @Test
    public void test1_initStage(){
       
        verifyThat("#btnGuest", isEnabled());
        verifyThat("#btnPet", isEnabled()); 
        verifyThat("#btnIncidences", isEnabled());
        verifyThat("#btnBlackList", isEnabled()); 
        verifyThat("#btnStay", isEnabled());
        verifyThat("#btnRoom", isEnabled()); 
        verifyThat("#btnFTP", isEnabled()); 
    }
    
    /**
     * The guest view opens
     */
    @Test
    public void test2_guestViewVisible(){
        clickOn("#btnGuest");
        verifyThat("#guestPane", isVisible());
    }
    
     /**
     * The blacklist view opens
     */
    @Test
    public void test3_blackListViewVisible(){
        clickOn("#btnBlackList");
        verifyThat("#blackPane", isVisible());
    }
    
     /**
     * The ftp view opens
     */
    @Test
    public void test4_ftpViewVisible(){
        clickOn("#btnFTP");
        verifyThat("#ftpPane", isVisible());
    }
    
     /**
     * The pet view opens
     */
    @Test
    public void test5_petViewVisible(){
        clickOn("#btnPet");
        verifyThat("#petPane", isVisible());
    }
    
      /**
     * The room view opens
     */
    @Test
    public void test6_roomViewVisible(){
        clickOn("#btnRoom");
        verifyThat("#roomPane", isVisible());
    }
    
      /**
     * The stay view opens
     */
    @Test
    public void test7_stayViewVisible(){
        clickOn("#btnStay");
        verifyThat("#stayPane", isVisible());
    }
    
      /**
     * The incident view opens
     */
    @Test
    public void test8_incidentViewVisible(){
        clickOn("#btnIncidences");
        verifyThat("#incidentPane", isVisible());
    }
}
