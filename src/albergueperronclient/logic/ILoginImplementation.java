/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.logic;

import albergueperronclient.modelObjects.UserBean;
import albergueperronclient.rest.UserREST;
//import albergueperronclient.rest.UserREST;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.ws.rs.core.GenericType;
import javax.xml.bind.DatatypeConverter;

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
    public void login(UserBean userBean) {
        
        //generateKey();
        byte[] encryptedPass =encrypt(userBean.getPassword());
        //String encryptedPassS = new String(encryptedPass);
        userBean.setPassword(encryptedPass);
        try{
          
           //webClient.update(userBean);
           String passString= DatatypeConverter.printHexBinary(encryptedPass);
           UserBean user=webClient.login(UserBean.class,userBean.getLogin(),passString);
            
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "User: Exception finding user, {0}",
                    ex.getCause());
            
            //throw new BusinessLogicException("Error finding all users:\n"+ex.getMessage());
        }
        
        
        
        
    }

    @Override
    public void close() {
        
    }
    @Override
    public void generateKey() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024);
			
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
			
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();
			
            X509EncodedKeySpec pubspec = new X509EncodedKeySpec(publicKey.getEncoded());
            FileOutputStream fos1 = new FileOutputStream("public.key");
            fos1.write(pubspec.getEncoded());
			
            PKCS8EncodedKeySpec prispec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
            FileOutputStream fos2 = new FileOutputStream("private.key");
            fos2.write(prispec.getEncoded());
            LOGGER.info("Key pair created");

	} catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
	} catch (FileNotFoundException e) {
            e.printStackTrace();
	} catch (IOException e) {
            e.printStackTrace();
	}

	}

    public byte[] encrypt(byte[] pass){
            FileInputStream fis;
            byte[] encodedMessage = null;
		try {
			
			
                    fis = new FileInputStream("public.key");
                    byte[] publicKey = new byte[fis.available()];
                    fis.read(publicKey);
			
                    X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(publicKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			 
                    PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);
                    
                    Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
                    cipher.init(Cipher.ENCRYPT_MODE, pubKey);
                    encodedMessage = cipher.doFinal(pass);
			
                    //ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("encoded"));
                    //oos.writeObject(encodedMessage);
			
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
