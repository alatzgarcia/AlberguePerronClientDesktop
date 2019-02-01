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
 *
 * @author Alatz
 */
public interface BlackListManager {
    //public UserBeanMongo findRoomById(Integer roomNum) throws ReadException;
    public List<UserBeanMongo> findAllUsersFromBlackList() throws ReadException;
    public void addUserToBlackList(UserBeanMongo room) throws CreateException;
    public void updateUserOnBlackList(UserBeanMongo room) throws UpdateException;
    public void deleteUserFromBlackList(String id) throws DeleteException;
}
