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
public interface IRegister {
    public void register(UserBean user);
    public void generateKey();
    public byte[] encrypt(byte[] pass);
}
