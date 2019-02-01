/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.logic;

/**
 * IncidentManagerFactory class for the AlberguePerronClient application
 * @author Alatz
 */
public class IncidentManagerFactory {
    /**
     * Returns the logic controller of type IncidentManagerImplementation
     * as a IncidentManager
     * @return the logic controller object
     */
    public static IncidentManager getIncidentManager(){
        return new IncidentManagerImplementation();
    }
}
