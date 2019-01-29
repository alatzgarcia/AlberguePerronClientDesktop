/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.logic;

import albergueperronclient.modelObjects.UserBean;
import java.util.Collection;

/**
 * Business logic interface encapsulating business methods for users login.
 * @author Nerea JImenez
 */
public interface ILogin {
    /**
     * This method returns an user with a given id
     * @param id The id of the user
     * @return the user
     */
  
    public UserBean login(UserBean userBean);
    /**
     * 
     */
    public void close();
    
}
