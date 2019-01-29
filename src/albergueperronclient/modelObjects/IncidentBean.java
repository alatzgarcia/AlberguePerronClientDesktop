/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.modelObjects;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Alatz
 */
public class IncidentBean implements Serializable {

    private Integer id;
    private String incidentType;
    private List<UserBean> implicateds;
    private String description;
    private Room room;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
     * Gets Incident Type of a room
     * @return the incidentType
     */
    public String getIncidentType() {
        return incidentType;
    }

    /**
     * Sets the Incident Type of an incidents
     * @param incidentType the incidentType to set
     */
    public void setIncidentType(String incidentType) {
        this.incidentType = incidentType;
    }

    /**
     * Gets the implicateds of an incident
     * @return the implicateds
     */
    public List<UserBean> getImplicateds() {
        return implicateds;
    }

    /**
     * Sets the implicateds of an incident
     * @param implicateds the implicateds to set
     */
    public void setImplicateds(List<UserBean> implicateds) {
        this.implicateds = implicateds;
    }

    /**
     * Gets the description of an incident
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of an incident
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Gets the room of the incident
     * @return the room
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Sets the room of the incident
     * @param room the room to set
     */
    public void setRoom(Room room) {
        this.room = room;
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
        if (!(object instanceof IncidentBean)) {
            return false;
        }
        IncidentBean other = (IncidentBean) object;
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
