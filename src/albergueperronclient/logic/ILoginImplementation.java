/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.logic;

import albergueperronclient.exceptions.BusinessLogicException;

import albergueperronclient.modelObjects.UserBean;
import albergueperronclient.rest.UserREST;
import albergueperronclient.utils.Encryptation;
import java.util.logging.Level;

import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;

/**
 * This class implements ILogic business logic interface using a RESTful web
 * client to access bussines logic in an Java EE application server.
 *
 * @author Nerea JImenez
 */
public class ILoginImplementation implements ILogin {

    //REST users web client
    private UserREST webClient;
    private static final Logger LOGGER = Logger.getLogger("albergueperronclient");

    /**
     * Create a LoginImplementation object. It constructs a web client for
     * accessing a RESTful service that provides business logic in an
     * application server.
     */
    public ILoginImplementation() {
        webClient = new UserREST();
    }

    /**
     * Method for the login of the user
     *
     * @param login
     * @param password
     * @return the user
     * @throws albergueperronclient.exceptions.BusinessLogicException
     */
    @Override
    public UserBean login(String login, String password) throws BusinessLogicException{

        //the password is encrypted before it is passed on to the server
        String encryptedPass = Encryptation.encrypt(password);

        //userBean.setPassword(encryptedPass);
        UserBean user = null;
        try {

            user = webClient.login(UserBean.class, login, encryptedPass);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,
                    "User: Exception finding user, {0}",
                    e.getMessage());
            throw new BusinessLogicException("Error finding the user:\n"+e.getMessage());
        }

        return user;

    }

   
}
