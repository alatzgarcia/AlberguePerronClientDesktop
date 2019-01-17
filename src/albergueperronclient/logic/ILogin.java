/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.logic;

import albergueperronclient.modelObjects.UserBean;
import java.util.Collection;

/**
 *
 * @author 2dam
 */
public interface ILogin {
    /**
     * This method returns a Collection of {@link UserBean}, containing all users data.
     * @return Collection The collection with all {@link UserBean} data for users. 
     * @throws BusinessLogicException If there is any error while processing.
     */
    public UserBean getUserById(String id);

    public UserBean login(UserBean userBean);

    public void close();
    public void generateKey();
    public byte[] encrypt(String pass);
}
