/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.logic;

/**
 * BlackListManagerFactory interface for the AlberguePerronClient application
 * @author Alatz
 */
public class BlackListManagerFactory {
    /**
     * Returns the logic controller of type BlackListManagerImplementation
     * as a BlackListManager
     * @return the logic controller object
     */
    public static BlackListManager getBlackListManager(){
        return new BlackListManagerImplementation();
    }
}