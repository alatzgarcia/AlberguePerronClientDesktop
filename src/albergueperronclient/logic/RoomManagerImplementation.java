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
import albergueperronclient.rest.RoomRESTClient;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Alatz
 */
public class RoomManagerImplementation implements RoomManager {
    private RoomRESTClient webClient;
    private static final Logger LOGGER= Logger.
            getLogger("albergueperronclient.logic.RoomManagerImplementation");
    
    public RoomManagerImplementation(){
        webClient = new RoomRESTClient();
    }
    
    @Override
    public RoomBean findRoomById(Integer roomNum) throws ReadException {
        RoomBean room = null;
        try{
            LOGGER.log(Level.INFO, "RoomManager: Finding room {0} "
                    + "from REST service (XML).", roomNum);
            //Ask webClient for all users' data.
            room = webClient.find(RoomBean.class, roomNum.toString());
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "RoomManager: Exception finding room, {0}",
                    ex.getMessage());
            //--TOFIX            
            //throw new BusinessLogicException("Error finding all users:\n"+ex.getMessage());
        }
        return room;
    }

    @Override
    public List<RoomBean> findAllRooms() throws ReadException {
        List<RoomBean> rooms = null;
        try{
            LOGGER.info("RoomManager: Finding all rooms from "
                    + "REST service (XML).");
            //Ask webClient for all users' data.
            rooms = webClient.findAll(new GenericType<List<RoomBean>>() {});
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "RoomManager: Exception finding all rooms, {0}",
                    ex.getMessage());
            //-- TOFIX --> Throwear excepción
        }
        return rooms;
    }

    @Override
    public List<RoomBean> findRoomsWithAvailableSpace() throws ReadException {
        List<RoomBean> rooms = null;
        try{
            LOGGER.log(Level.INFO,"RoomManager: Finding all rooms with"
                    + "available space.");
            rooms = webClient.findRoomsWithAvailableSpace(new GenericType<List<RoomBean>>() {});
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "RoomManager: Exception finding room available, {0}",
                    ex.getMessage());
            //-- TOFIX --> Throwear excepción
        }
        return rooms;
    }

    @Override
    public void createRoom(RoomBean room) throws CreateException {
        try{
            LOGGER.log(Level.INFO,"RoomManager: Creating room, {0}",
                    room.getRoomNum());
            //Send user data to web client for creation. 
            webClient.create(room);
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "RoomManager: Exception creating room, {0}",
                    ex.getMessage());
            throw new CreateException();
        }
    }

    @Override
    public void updateRoom(RoomBean room) throws UpdateException {
        try{
            LOGGER.log(Level.INFO,"RoomManager: Updating room {0}.",
                    room.getRoomNum());
            webClient.edit(room);
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "RoomManager: Exception updating room, {0}",
                    ex.getMessage());
            //-- TOFIX --> Throwear excepción
        }
    }

    @Override
    public void deleteRoom(Integer roomNum) throws DeleteException {
        try{
            LOGGER.log(Level.INFO,"RoomManager: Deleting room {0}.",
                    roomNum);
            webClient.remove(roomNum.toString());
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "RoomManager: Exception deleting room, {0}",
                    ex.getMessage());
            //-- TOFIX --> Throwear excepción
        }
    }
    
}
