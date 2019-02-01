/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.logic;

import albergueperronclient.exceptions.BusinessLogicException;
import albergueperronclient.modelObjects.Privilege;
import albergueperronclient.modelObjects.UserBean;
import albergueperronclient.rest.UserREST;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Diego
 */
public class UserManagerImplementation implements UsersManager{
    private UserREST webClient;
    private static final Logger LOGGER=Logger.getLogger("UserManagerImplementation.class");

    public UserManagerImplementation(){
        webClient=new UserREST();
    }
    
    @Override
    public UserBean getUser(String id) {
        UserBean user=new UserBean();
        try{
            user=webClient.find(UserBean.class, id);
        }catch(ClientErrorException cee){
            LOGGER.info(cee.getMessage());
        }
        return user;
    }

    @Override
    public Collection<UserBean> getAllUsers() throws BusinessLogicException {
        Collection<UserBean> users =null;
        try{
            //Ask webClient for all users' data.
            users = webClient.findAll(new GenericType<List<UserBean>>() {});
        }catch(Exception e){
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception finding all users, ",
                    e.getMessage());
            throw new BusinessLogicException("Error finding all users:\n"+e.getMessage());
        }
        return users;
    }

    @Override
    public void createUser(UserBean user) throws BusinessLogicException {
        try{
            webClient.create(user);
        }catch(Exception e){
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception creating all users, ",
                    e.getMessage());
            throw new BusinessLogicException("Error creating all users: \n"+e.getMessage());
        }
    }

    @Override
    public void updateUser(UserBean user) throws BusinessLogicException {
       try{
           webClient.edit(user);
       }catch(Exception e){
           LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception updating all users, ",
                    e.getMessage());
           throw new BusinessLogicException("Error updating all users: \n"+e.getMessage() );
       }
    }

    @Override
    public void deleteUser(String id) throws BusinessLogicException {
        try{
            webClient.remove(id);
        }catch(Exception e){
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception removing user, ",
                    e.getMessage());
           throw new BusinessLogicException("Error deleting user: \n"+e.getMessage() );
        }
    }

    @Override
    public Collection<UserBean> findUsersByPrivilege(Privilege privilege) throws BusinessLogicException {
        Collection<UserBean> users =null;
        try{
            //Ask webClient for all users' data.
            users = webClient.findByPrivilege(new GenericType<List<UserBean>>() {}, privilege);
        }catch(Exception e){
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception finding all users, ",
                    e.getMessage());
            throw new BusinessLogicException("Error finding all users:\n"+e.getMessage());
        }
        return users;
    }
}