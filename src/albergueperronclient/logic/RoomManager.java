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
 *
 * @author Alatz
 */
public interface RoomManager {
    public RoomBean findRoomById(Integer roomNum) throws ReadException;
    public List<RoomBean> findAllRooms() throws ReadException;
    public List<RoomBean> findRoomsWithAvailableSpace() throws ReadException;
    public void createRoom(RoomBean room) throws CreateException;
    public void updateRoom(RoomBean room) throws UpdateException;
    public void deleteRoom(Integer roomNum) throws DeleteException;
}
