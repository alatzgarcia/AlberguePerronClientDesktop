/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.logic;

import albergueperronclient.exceptions.BusinessLogicException;
import albergueperronclient.modelObjects.UserBean;

/**
 * Business logic interface encapsulating business methods for users login.
 * @author Nerea Jimenez
 */
public interface ILogin {
   
    public UserBean login(String login, String password)throws BusinessLogicException;

    
}
