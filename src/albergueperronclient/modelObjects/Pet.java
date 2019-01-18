/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.modelObjects;

import java.io.Serializable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alatz
 */
@XmlRootElement(name="pet")
public class Pet implements Serializable {
    private static long serialVersionUID = 1L;
    
    private SimpleIntegerProperty id;
    private SimpleObjectProperty<UserBean> owner;
    private SimpleStringProperty specie;
    private SimpleStringProperty race;
    private SimpleStringProperty name;
    private SimpleStringProperty colour;
    private SimpleStringProperty description;

    public Integer getId() {
        return this.id.get();
    }

    public void setId(Integer id) {
        this.id.set(id);
    }

    /**
     * @return the owner
     */
    public UserBean getOwner() {
        return this.owner.get();
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(UserBean owner) {
        this.owner.set(owner);
    }

    /**
     * @return the specie
     */
    public String getSpecie() {
        return this.specie.get();
    }

    /**
     * @param specie the specie to set
     */
    public void setSpecie(String specie) {
        this.specie.set(specie);
    }

    /**
     * @return the race
     */
    public String getRace() {
        return this.race.get();
    }

    /**
     * @param race the race to set
     */
    public void setRace(String race) {
        this.race.set(race);
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.name.get();
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * @return the colour
     */
    public String getColour() {
        return this.colour.get();
    }

    /**
     * @param colour the colour to set
     */
    public void setColour(String colour) {
        this.colour.set(colour);
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
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pet)) {
            return false;
        }
        Pet other = (Pet) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "alberguePerronServer.entity.Pet[ id=" + getId() + " ]";
    }
}
