/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.modelObjects;

import java.io.Serializable;
import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Alatz
 */
@XmlRootElement(name="incident")
public class Incident implements Serializable {
    private static long serialVersionUID = 1L;
    
    private SimpleIntegerProperty id;
    private SimpleStringProperty incidentType;
    private List<UserBean> implicateds;
    private SimpleStringProperty description;
    private SimpleObjectProperty<Room> room;

    public Integer getId() {
        return this.id.get();
    }

    public void setId(Integer id) {
        this.id.set(id);
    }
    
    /**
     * @return the incidentType
     */
    public String getIncidentType() {
        return this.incidentType.get();
    }

    /**
     * @param incidentType the incidentType to set
     */
    public void setIncidentType(String incidentType) {
        this.incidentType.set(incidentType);
    }

    /**
     * @return the implicateds
     */
    @XmlTransient
    public List<UserBean> getImplicateds() {
        return implicateds;
    }

    /**
     * @param implicateds the implicateds to set
     */
    public void setImplicateds(List<UserBean> implicateds) {
        this.implicateds = implicateds;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return this.description.get();
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description.set(description);
    }
    
    /**
     * @return the room
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Incident)) {
            return false;
        }
        Incident other = (Incident) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "alberguePerronServer.entity.Incident[ id=" + getId() + " ]";
    }
}
