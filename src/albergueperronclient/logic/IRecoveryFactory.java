/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.logic;

/**
 * This class is a factory for IRecovery interface implementing objects.
 * @author Nerea Jimenez
 */
public class IRecoveryFactory {
    public static IRecovery getRecoveryManager(){
        return new IRecoveryImplementation();
    }
}
