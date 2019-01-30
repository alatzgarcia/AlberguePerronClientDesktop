/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.logic;

import utils.Encryptation;
import albergueperronclient.modelObjects.UserBean;
import albergueperronclient.rest.UserREST;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.DatatypeConverter;


/**
 * This class implements ILogic business logic interface using a 
 * RESTful web client to access bussines logic in an Java EE application server. 
 * @author Nerea JImenez
 */
public class ILoginImplementation implements ILogin{
    
   //REST users web client
    private UserREST webClient;
    private static final Logger LOGGER=Logger.getLogger("albergueperronclient");
    
    /**
     * Create a LoginImplementation object. It constructs a web client for 
     * accessing a RESTful service that provides business logic in an application
     * server.
     */
    public ILoginImplementation(){
        webClient=new UserREST();
    }

    
    /**
     * Method for the login of the user
     * @param userBean the user
     * @return the user
     */
    @Override
    public UserBean login(UserBean userBean)  {
        
        //the password is encrypted before it is passed on to the server
        String encryptedPass =Encryptation.encrypt(userBean.getPassword());
        
        userBean.setPassword(encryptedPass);
        UserBean user=null;
        try{
        
           user=webClient.login(UserBean.class,userBean.getLogin(),encryptedPass);
        
        }catch(Exception e){
           LOGGER.severe(e.getMessage());
        }
        
        return user;
        
        
    }

    @Override
    public void close() {
        
    }
   
    
}
