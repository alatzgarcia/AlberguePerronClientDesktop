/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.logic;

/**
 *
 * @author Alatz
 */
public class RoomManagerFactory {
    public static RoomManager getRoomManager(){
        return new RoomManagerImplementation();
        //return new ILogicTestDataImplementation(); //Imp class with test data
    }
}
