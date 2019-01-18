/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.modelObjects;

import java.io.Serializable;
import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Alatz
 */
@XmlRootElement(name="room")
public class Room implements Serializable {
    private static long serialVersionUID = 1L;
    private SimpleIntegerProperty roomNum;
    private SimpleIntegerProperty totalSpace;
    private SimpleIntegerProperty availableSpace;
    private SimpleStringProperty status;
    private List<Incident> incidents;
    private List<Stay> stays;
    
    public Room(){
    }
    
    /**
     * 
     * @return 
     */
    public Integer getRoomNum() {
        return this.roomNum.get();
    }
    
    /**
     * @param roomNum the roomNumber to set
     */
    public void setRoomNum(Integer roomNum) {
        this.roomNum.set(roomNum);
    }

    /**
     * @return the totalSpace
     */
    public Integer getTotalSpace() {
        return this.totalSpace.get();
    }

    /**
     * @param totalSpace the totalSpace to set
     */
    public void setTotalSpace(Integer totalSpace) {
        this.totalSpace.set(totalSpace);
    }

    /**
     * @return the availableSpace
     */
    public Integer getAvailableSpace() {
        return this.availableSpace.get();
    }

    /**
     * @param availableSpace the availableSpace to set
     */
    public void setAvailableSpace(Integer availableSpace) {
        this.availableSpace.set(availableSpace);
    }
    
    /**
     * @return the status
     */
    public String getStatus() {
        return this.status.get();
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status.set(status);
    }
    
    /**
     * @return the incidents
     */
    @XmlTransient
    public List<Incident> getIncidents() {
        return incidents;
    }

    /**
     * @param incidents the incidents to set
     */
    public void setIncidents(List<Incident> incidents) {
        this.incidents = incidents;
    }

    /**
     * @return the stays
     */
    @XmlTransient
    public List<Stay> getStays() {
        return stays;
    }

    /**
     * @param stays the stays to set
     */
    public void setStays(List<Stay> stays) {
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
