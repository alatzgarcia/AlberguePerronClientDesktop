/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.logic;

import albergueperronclient.modelObjects.UserBean;
import java.util.Collection;
import javafx.collections.FXCollections;

/**
 *
 * @author Diego
 */
public interface UsersManager {
    
    public Collection<UserBean> getAllUsers();
}
