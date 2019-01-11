/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.modelObjects;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Diego
 */
public class StayBean implements Serializable {

    private Integer id;
    private UserBean guest;
    private Room room;
    private Date date;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets guest value for stay.
     * @return The guest value.
     */
    public UserBean getGuest() {
        return guest;
    }

    /**
     * Sets guest value for stay.
     * @param guest The guest value.
     */
    public void setGuest(UserBean guest) {
        this.guest = guest;
    }

    /**
     * Gets room value for stay.
     * @return The room value.
     */
    public Room getRoom() {
        return room;
    }

    /**
     * @param room the room to set
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StayBean)) {
            return false;
        }
        StayBean other = (StayBean) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "alberguePerronServer.entity.Stay[ id=" + getId() + " ]";
    }
}
