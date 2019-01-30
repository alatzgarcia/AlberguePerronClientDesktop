/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.logic;

import albergueperronclient.exceptions.BusinessLogicException;
import albergueperronclient.modelObjects.UserBean;

/**
 * Business logic interface encapsulating business methods for the recovery 
 * of the password
 * @author Nerea JImenez
 */
public interface IRecovery {
  
    public void recoverEmail(UserBean user) throws BusinessLogicException;
    
    public UserBean getUserByEmail(String email) throws BusinessLogicException;
}
