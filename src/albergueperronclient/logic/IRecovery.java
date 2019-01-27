/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.logic;

import albergueperronclient.modelObjects.UserBean;
import java.util.Collection;

/**
 * Business logic interface encapsulating business methods for the recovery 
 * of the password
 * @author Nerea JImenez
 */
public interface IRecovery {
  
    public void close();
    
    /**
     * Method for the recovery of the user password
     * @param user The user
     */
    public void recoverEmail(UserBean user);
    
    public UserBean getUserByEmail(String email);
}
