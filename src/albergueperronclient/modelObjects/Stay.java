/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.modelObjects;

import java.io.Serializable;
import java.util.Date;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alatz
 */
@XmlRootElement(name="stay")
public class Stay implements Serializable {
    private static long serialVersionUID = 1L;
    
    private SimpleIntegerProperty id;
    private SimpleObjectProperty<User> guest;
    private SimpleObjectProperty<Room> room;
    private SimpleObjectProperty<Date> date;
    
    public Integer getId() {
        return this.id.get();
    }

    public void setId(Integer id) {
        this.id.set(id);
    }

    /**
     * Gets guest value for stay.
     * @return The guest value.
     */
    public User getGuest() {
        return this.guest.get();
    }

    /**
     * Sets guest value for stay.
     * @param guest The guest value.
     */
    public void setGuest(User guest) {
        this.guest.set(guest);
    }

    /**
     * Gets room value for stay.
     * @return The room value.
     */
    public Room getRoom() {
        return this.room.get();
    }

    /**
     * @param room the room to set
     */
    public void setRoom(Room room) {
        this.room.set(room);
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return this.date.get();
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date.set(date);
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
        if (!(object instanceof Stay)) {
            return false;
        }
        Stay other = (Stay) object;
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
