/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.logic;

import albergueperronclient.exceptions.BusinessLogicException;
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

import javax.ws.rs.ClientErrorException;

/**
 * This class implements IRecovery business logic interface using a RESTful web
 * client to access bussines logic in an Java EE application server.
 *
 * @author Nerea Jimenez
 */
public class IRecoveryImplementation implements IRecovery {

    //REST users web client
    private UserREST webClient;
    private static final Logger LOGGER = Logger.getLogger("albergueperronclient");

    /**
     * Create a IRecoveryImplementation object. It constructs a web client for
     * accessing a RESTful service that provides business logic in an
     * application server.
     */
    public IRecoveryImplementation() {
        webClient = new UserREST();
    }

    /**
     * Method to get the user with a given email
     *
     * @param email The email to search
     * @return the user
     * @throws albergueperronclient.exceptions.BusinessLogicException
     */
    @Override
    public UserBean getUserByEmail(String email) throws BusinessLogicException {
        UserBean user = null;
        try {

            user = webClient.findUserByEmail(UserBean.class, email);
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "User: Exception finding user, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error finding the user:\n" + ex.getMessage());
        }
        return user;
    }

    /**
     * Method to update the password of the user
     *
     * @param user the user
     */
    @Override
    public void recoverEmail(UserBean user) throws BusinessLogicException {

        webClient.passRecovery(UserBean.class, user.getEmail());

    }

}
