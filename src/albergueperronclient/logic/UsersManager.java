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
 * The interface for the userManager
 * @author Diego
 */
public interface UsersManager {
    /**
     * Gets the User by Id
     * @param id
     * @return 
     */
    public UserBean getUser(String id);
    
    /**
     * Gets all the Users
     * @return
     * @throws BusinessLogicException 
     */
    public Collection<UserBean> getAllUsers() throws BusinessLogicException;
    
    /**
     * Creates the User
     * @param user
     * @throws BusinessLogicException 
     */
    public void createUser(UserBean user) throws BusinessLogicException;
    
    /**
     * Updates the User
     * @param user
     * @throws BusinessLogicException 
     */
    public void updateUser(UserBean user) throws BusinessLogicException;
    
    /**
     * Deletes the User
     * @param id
     * @throws BusinessLogicException 
     */
    public void deleteUser(String id) throws BusinessLogicException;
    
    /**
     * Find the User by the Privilege
     * @param privilege
     * @return
     * @throws BusinessLogicException 
     */
    public Collection<UserBean> findUsersByPrivilege(Privilege privilege) throws BusinessLogicException;
}
