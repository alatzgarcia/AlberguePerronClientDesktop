/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.logic;

/**
 * RoomManagerFactory class for the AlberguePerronClient application
 * @author Alatz
 */
public class RoomManagerFactory {
    /**
     * Returns the logic controller of type RoomManagerImplementation
     * as a RoomManager
     * @return the logic controller object
     */
    public static RoomManager getRoomManager(){
        return new RoomManagerImplementation();
    }
}