/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.logic;

import albergueperronclient.exceptions.BusinessLogicException;
import albergueperronclient.modelObjects.Privilege;
import albergueperronclient.modelObjects.UserBean;
import java.util.Collection;
import javafx.collections.FXCollections;

/**
 *
 * @author Diego
 */
public interface UsersManager {
    
    public UserBean getUser(String id);
    
    public Collection<UserBean> getAllUsers() throws BusinessLogicException;
    
    public void createUser(UserBean user) throws BusinessLogicException;
    
    public void updateUser(UserBean user) throws BusinessLogicException;
    
    public void deleteUser(String id) throws BusinessLogicException;
    
    public Collection<UserBean> findUsersByPrivilege(Privilege privilege) throws BusinessLogicException;
}
