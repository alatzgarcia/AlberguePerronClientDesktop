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
public class FTPControllerIT extends ApplicationTest{
    
    
    @Override 
    public void start(Stage primaryStage) throws Exception{
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
        clickOn("#btnFTP");
    }
    
    /**
     * The buttons are enabled
     */
    @Test
    public void test1_initStage(){
       
        verifyThat("#btnBack", isEnabled());
        verifyThat("#btnSearch", isEnabled()); 
        verifyThat("#btnUpload", isDisabled()); 
        verifyThat("#btnDownload", isDisabled()); 
        verifyThat("#btnDeleteF", isDisabled()); 
        verifyThat("#btnDeleteD", isDisabled()); 
        verifyThat("#btnCrear", isDisabled()); 
        
        
    }
    
}
