/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.logic;

/**
 *
 * @author 2dam
 */
public class UsersManagerFactory {
    public static UsersManager getUsersManager(){
        return new UserManagerImplementation();
    }
}
