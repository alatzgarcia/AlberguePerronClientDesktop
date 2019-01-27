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
import java.io.ObjectInputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.mail.Session;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 *
 * @author Nerea JImenez
 */
public class IRecoveryImplementation implements IRecovery{
    
   //REST users web client
    private UserREST webClient;
    private static final Logger LOGGER=Logger.getLogger("albergueperronclient");
    
     public IRecoveryImplementation(){
        webClient=new UserREST();
    }

    /**
     * Method to get the user with a given email
     * @param email The email to search
     * @return the user
     */
    @Override
    public UserBean getUserByEmail(String email) {
        UserBean user =null;
        try{
            
            user = webClient.findUserByEmail(UserBean.class,email);
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "User: Exception finding user, {0}",
                    ex.getMessage());
            //throw new BusinessLogicException("Error finding all users:\n"+ex.getMessage());
        }
        return user;
    }
    
    /**
     * Method to update the password of the user
     * @param user the user
     */
    @Override
    public void recoverEmail(UserBean user){
        
       String pass = user.getPassword();
       //encrypt the password before it is passed on to the server side
       byte[] encryptedPass =encrypt(user.getPassword());
       String passString= DatatypeConverter.printHexBinary(encryptedPass);
       user.setPassword(passString);
       //calls the pass recovery method od the UserREST
       UserBean userN = webClient.passRecovery(UserBean.class,user.getEmail(),passString); 
       
       //if the update of the password has been sucessful send an email
       if(userN!=null){
           sendEmail(userN, pass);
       }
    }

    @Override
    public void close() {
        
    }
   
    /**
     * Method to send an email to the user with the new password
     * @param user The user
     * @param pass The new password
     */
    public void sendEmail(UserBean user,String pass){
        //get the creedential of the email account
        ArrayList<String> creedentials=getEmailCredentials();
        String email =creedentials.get(0);
        String password =creedentials.get(1);
         
                             
                Properties props = System.getProperties();
                props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
                props.put("mail.smtp.user", email);
                props.put("mail.smtp.clave", password);    //La clave de la cuenta
                props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
                props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
                props.put("mail.smtp.port", "465"); //El puerto SMTP seguro de Google
         try {   
                Session session = Session.getDefaultInstance(props);
                // Create the email message
                HtmlEmail emailToSend = new HtmlEmail();
                emailToSend.setAuthentication(email, password);
                emailToSend.setHostName("smtp.gmail.com");
        
                emailToSend.addTo(user.getEmail(), user.getName());
        
                emailToSend.setFrom(email, "Albergue Perron");
                emailToSend.setSubject("Nueva contraseña");
                emailToSend.setDebug(true);
                emailToSend.setSSLCheckServerIdentity(true);
                emailToSend.setStartTLSRequired(true);

                // set the html message
                emailToSend.setHtmlMsg("<html>Esta es su nueva contraseña: "+pass+
                        "/n puede modificarla la próxima vez que inicie sesión</html>");
                
                // send the email
                emailToSend.send();
                
                } catch (EmailException ex) {
                   LOGGER.severe(ex.getMessage());
                }catch(Exception e){
                   LOGGER.severe(e.getMessage());
                }
    }
    
    /**
     * Method to get the creedentials of the email account that have been
     * encrypted with a private key
     * @return 
     */
    public ArrayList<String> getEmailCredentials(){
	ArrayList<String> creedentials = new ArrayList<>();
	ObjectInputStream ois = null;
	try {
            ois = new ObjectInputStream(new FileInputStream("privKey"));
            SecretKey privateKey =(SecretKey) ois.readObject();
	   
	    //email									
            ois = new ObjectInputStream(new FileInputStream("email"));
            byte[] iv1 =(byte[]) ois.readObject();
            byte[] encodedEmail =(byte[]) ois.readObject();
					
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv1Param = new IvParameterSpec(iv1);
            cipher.init(Cipher.DECRYPT_MODE, privateKey, iv1Param);
            byte[] decodedEmail = cipher.doFinal(encodedEmail);
            String email = new String(decodedEmail);
            
            //password
            ois = new ObjectInputStream(new FileInputStream("pass"));
            byte[] iv2 =(byte[]) ois.readObject();
            byte[] encodedPass =(byte[]) ois.readObject();
	
            IvParameterSpec iv2Param = new IvParameterSpec(iv2);
            cipher.init(Cipher.DECRYPT_MODE, privateKey, iv2Param);
            byte[] decodedPass = cipher.doFinal(encodedPass);
            String pass = new String(decodedPass);
            
            
            creedentials.add(0, email);
            creedentials.add(1, pass);
					
            } catch (NoSuchAlgorithmException e) {
                LOGGER.severe(e.getMessage());
            } catch (NoSuchPaddingException e) {
                LOGGER.severe(e.getMessage());
            } catch (InvalidKeyException e) {
		LOGGER.severe(e.getMessage());
            } catch (IllegalBlockSizeException e) {
		LOGGER.severe(e.getMessage());
            } catch (BadPaddingException e) { //Clave introducida no es correcta
		LOGGER.severe(e.getMessage());
            } catch (FileNotFoundException e) {
		LOGGER.severe(e.getMessage());
            } catch (IOException e) {
		LOGGER.severe(e.getMessage());
            } catch (ClassNotFoundException e) {
		LOGGER.severe(e.getMessage());
            } catch (InvalidAlgorithmParameterException e) {
		LOGGER.severe(e.getMessage());
            }finally {
                if(ois!=null) {
                    try {
			ois.close();
                    } catch (IOException e) {
			LOGGER.severe(e.getMessage());
                    }
                }
					
            }
       
        return creedentials;
    }

    /**
     * Method to encrypt the password
     * @param pass password
     * @return the encrypted password
     */
    public byte[] encrypt(String pass){
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
                    encodedMessage = cipher.doFinal(pass.getBytes());
	
                    LOGGER.info("Message encrypted");
	
		} catch (FileNotFoundException e) {
                    LOGGER.severe(e.getMessage());
		} catch (IOException e) {
                    LOGGER.severe(e.getMessage());
		} catch (NoSuchAlgorithmException e) {
                    LOGGER.severe(e.getMessage());
		} catch (InvalidKeySpecException e) {
                    LOGGER.severe(e.getMessage());
		} catch (NoSuchPaddingException e) {
                    LOGGER.severe(e.getMessage());
		} catch (InvalidKeyException e) {
                    LOGGER.severe(e.getMessage());
		} catch (IllegalBlockSizeException e) {
                    LOGGER.severe(e.getMessage());
		} catch (BadPaddingException e) {
                    LOGGER.severe(e.getMessage());
		}
        return encodedMessage;
    }
}
