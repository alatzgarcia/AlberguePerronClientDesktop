/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.modelObjects;

import java.io.Serializable;

/**
 *
 * @author Diego
 */
public class PetBean implements Serializable {

    private static long serialVersionUID = 1L;
    private Integer id;
    private UserBean owner;
    private String specie;
    private String race;
    private String name;
    private String colour;
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the owner of the pet
     * @return the owner
     */
    public UserBean getOwner() {
        return owner;
    }

    /**
     * Sets the owner of the pet
     * @param owner the owner to set
     */
    public void setOwner(UserBean owner) {
        this.owner = owner;
    }

    /**
     * Get the specie of the pet
     * @return the specie
     */
    public String getSpecie() {
        return specie;
    }

    /**
     * Sets the specie of the pet
     * @param specie the specie to set
     */
    public void setSpecie(String specie) {
        this.specie = specie;
    }

    /**
     * Gets the race of the pet
     * @return the race
     */
    public String getRace() {
        return race;
    }

    /**
     * Sets the race of the pet
     * @param race the race to set
     */
    public void setRace(String race) {
        this.race = race;
    }

    /**
     * Gets the name of the pet
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the name
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets colour of teh pet
     * @return the colour
     */
    public String getColour() {
        return colour;
    }

    /**
     * @param colour the colour to set
     */
    public void setColour(String colour) {
        this.colour = colour;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
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
        if (!(object instanceof PetBean)) {
            return false;
        }
        PetBean other = (PetBean) object;
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
