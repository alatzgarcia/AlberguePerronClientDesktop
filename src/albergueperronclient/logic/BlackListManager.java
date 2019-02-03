/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.logic;

import albergueperronclient.exceptions.CreateException;
import albergueperronclient.exceptions.DeleteException;
import albergueperronclient.exceptions.ReadException;
import albergueperronclient.exceptions.UpdateException;
import albergueperronclient.modelObjects.UserBeanMongo;
import java.util.List;

/**
 * BlackListManager interface for the AlberguePerronClient application
 * @author Alatz
 */
public interface BlackListManager {
    /**
     * Method to get all users from the blacklist
     * @return the list of users in the blacklist
     * @throws ReadException 
     */
    public List<UserBeanMongo> findAllUsersFromBlackList() throws ReadException;
    /**
     * Method to insert a user into the blacklist
     * @param user the user
     * @throws CreateException 
     */
    public void addUserToBlackList(UserBeanMongo user) throws CreateException;
    /**
     * Method to update a user information on the blacklist
     * @param user the user to be updated
     * @throws UpdateException 
     */
    public void updateUserOnBlackList(UserBeanMongo user) throws UpdateException;
    /**
     * Method to delete a user from the blacklist by its id
     * @param id the id of the user to delete
     * @throws DeleteException 
     */
    public void deleteUserFromBlackList(String id) throws DeleteException;
}