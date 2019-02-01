/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.logic;

import albergueperronclient.exceptions.CreateException;
import albergueperronclient.exceptions.DeleteException;
import albergueperronclient.exceptions.ReadException;
import albergueperronclient.exceptions.UpdateException;
import albergueperronclient.modelObjects.RoomBean;
import java.util.List;

/**
 * RoomManager interface for the AlberguePerronClient application
 * @author Alatz
 */
public interface RoomManager {
    /**
     * Method to get a room by its id
     * @param roomNum the room id
     * @return the room object
     * @throws ReadException 
     */
    public RoomBean findRoomById(Integer roomNum) throws ReadException;
    /**
     * Method to find all rooms
     * @return a list with the rooms
     * @throws ReadException 
     */
    public List<RoomBean> findAllRooms() throws ReadException;
    /**
     * Method to find all rooms with available space
     * @return the room object
     * @throws ReadException 
     */
    public List<RoomBean> findRoomsWithAvailableSpace() throws ReadException;
    /**
     * Method to create a room
     * @param room the room object
     * @throws CreateException 
     */
    public void createRoom(RoomBean room) throws CreateException;
    /**
     * Method to update a room
     * @param room the room object
     * @throws UpdateException 
     */
    public void updateRoom(RoomBean room) throws UpdateException;
    /**
     * Method to delete a room by its id
     * @param roomNum the room id
     * @throws DeleteException 
     */
    public void deleteRoom(Integer roomNum) throws DeleteException;
}
