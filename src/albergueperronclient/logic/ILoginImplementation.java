/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.logic;

import albergueperronclient.modelObjects.UserBean;
import albergueperronclient.rest.UserREST;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javax.ws.rs.core.GenericType;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/**
 *
 * @author Nerea
 */
public class ILoginImplementation implements ILogin{
    
   //REST users web client
    private UserREST webClient;
    private static final Logger LOGGER=Logger.getLogger("albergueperronclient");
    
     public ILoginImplementation(){
        webClient=new UserREST();
    }

    @Override
    public UserBean getUserById(String id) {
        UserBean user =null;
        try{
            LOGGER.info("User: Finding user by ID from REST service (XML).");
            //Ask webClient for all users' data.
            user = webClient.find(UserBean.class,id);
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "User: Exception finding user, {0}",
                    ex.getMessage());
            //throw new BusinessLogicException("Error finding all users:\n"+ex.getMessage());
        }
        return user;
    }

    /**
     *
     * @param userBean
     */
    @Override
    public UserBean login(UserBean userBean) {
        
        //encriptar contraseña
        UserBean user = null;
        //webClient.login(userBean);
        user=webClient.find(UserBean.class, userBean.getId());
        LOGGER.info(user.getName());
        return null;
        
        
        
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
