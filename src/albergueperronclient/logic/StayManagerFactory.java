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
public class StayManagerFactory {
    /**
     * The mthod that creates the StayManagerImplementation
     * @return StaysManager
     */
    public static StaysManager createStayManager(){
        return new StayManagerImplementation();
    }
}
