/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.utils;

import utils.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.DatatypeConverter;

/**
 * Class for the encriptation of the password in the client
 * @author Nerea Jimenez
 */
public class Encryptation {
    
    public static String encrypt(String pass){
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
        return DatatypeConverter.printHexBinary(encodedMessage);
    }
}
