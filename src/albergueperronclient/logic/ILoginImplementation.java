/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.logic;

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
     * This method returns the user with a given id
     * @param id the id of the user
     * @return the user
     */
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
     * Method for the login of the user
     * @param userBean the user
     * @return the user
     */
    @Override
    public UserBean login(UserBean userBean)  {
        
        //the password is encrypted before it is passed on to the server
        byte[] encryptedPass =encrypt(userBean.getPassword());
        String passString= DatatypeConverter.printHexBinary(encryptedPass);
       // String passString= encryptedPass.toString();
        LOGGER.info("Password encriptada en string en cliente: "+passString);
        userBean.setPassword(passString);
        UserBean user=null;
        try{
        
           user=webClient.login(UserBean.class,userBean.getLogin(),passString);
        
        }catch(Exception e){
           LOGGER.severe(e.getMessage());
        }
        
        return user;
        
        
    }

    @Override
    public void close() {
        
    }
   
    /**
     * Method for the encryption of the password
     * @param pass The password
     * @return encrypted password
     */
    public byte[] encrypt(String pass){
            FileInputStream fis;
            byte[] encodedMessage = null;
		try {
			
		    //gets the public key that has been previously generated
                    //with a matching private key that it is kept in the server side
                    fis = new FileInputStream("public.key");
                    byte[] publicKey = new byte[fis.available()];
                    fis.read(publicKey);
			
                    X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(publicKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			 
                    PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);
                    
                    Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
                    cipher.init(Cipher.ENCRYPT_MODE, pubKey);
                    encodedMessage = cipher.doFinal(pass.getBytes());
					
                    LOGGER.info("Message encrypted");
	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
        return encodedMessage;
    }

    
}
