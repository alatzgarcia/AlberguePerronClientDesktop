/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.modelObjects;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Alatz
 */
public class Room implements Serializable {

    private static long serialVersionUID = 1L;
    private Integer roomNum;
    private Integer totalSpace;
    private Integer availableSpace;
    private String status;
    private List<IncidentBean> incidents;
    private List<StayBean> stays;
    /**
     * gets the number of the room
     * @return 
     */
    public Integer getRoomNum() {
        return roomNum;
    }
    
    /**
     * Sets the number of a room
     * @param roomNum the roomNumber to set
     */
    public void setRoomNum(Integer roomNum) {
        this.roomNum = roomNum;
    }

    /**
     * Gets the total space of the room
     * @return the totalSpace
     */
    public Integer getTotalSpace() {
        return totalSpace;
    }

    /**
     * Sets the total space of the room
     * @param totalSpace the totalSpace to set
     */
    public void setTotalSpace(Integer totalSpace) {
        this.totalSpace = totalSpace;
    }

    /**
     * Gets the available space in the room
     * @return the availableSpace
     */
    public Integer getAvailableSpace() {
        return availableSpace;
    }

    /**
     * Sets the available space of the room
     * @param availableSpace the availableSpace to set
     */
    public void setAvailableSpace(Integer availableSpace) {
        this.availableSpace = availableSpace;
    }
    
    /**
     * Gets the status of the room
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the satus of the room
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     * Gets the incidents of the room
     * @return the incidents
     */
    @XmlTransient
    public List<IncidentBean> getIncidents() {
        return incidents;
    }

    /**
     * Sets the incidents of a room
     * @param incidents the incidents to set
     */
    public void setIncidents(List<IncidentBean> incidents) {
        this.incidents = incidents;
    }

    /**
     * Gets all the stays of a room
     * @return the stays
     */
    @XmlTransient
    public List<StayBean> getStays() {
        return stays;
    }

    /**
     * Sets all the stays of a room
     * @param stays the stays to set
     */
    public void setStays(List<StayBean> stays) {
        this.stays = stays;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getRoomNum() != null ? getRoomNum().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Room)) {
            return false;
        }
        Room other = (Room) object;
        if ((this.getRoomNum() == null && other.getRoomNum() != null) || 
                (this.getRoomNum() != null && !this.roomNum.equals(other.roomNum))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "alberguePerronServer.entity.Room[ Room number=" + roomNum + " ]";
    }
}
