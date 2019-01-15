/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.logic;

/**
 *
 * @author Nerea Jimenez
 */
public class ILoginFactory {
    public static ILogin getLoginManager(){
        return new ILoginImplementation();
    }
}
