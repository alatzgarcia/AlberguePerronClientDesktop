/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.modelObjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * IncidentBean class for the AlberguePerronClient application
 * @author Alatz
 */
@XmlRootElement(name="incident")
public class IncidentBean implements Serializable {
    private static long serialVersionUID = 1L;
    
    private SimpleIntegerProperty id;
    private SimpleStringProperty incidentType;
    private List<UserBean> implicateds;
    private SimpleStringProperty description;
    private SimpleObjectProperty<RoomBean> room;
    private SimpleObjectProperty<Date> date;

    /**
     * Constructor for the class
     */
    public IncidentBean(){
        this.id = new SimpleIntegerProperty();
        this.incidentType = new SimpleStringProperty();
        this.implicateds = new ArrayList<UserBean>();
        this.description = new SimpleStringProperty();
        this.room = new SimpleObjectProperty<RoomBean>();
        this.date = new SimpleObjectProperty<Date>();
    }
    
    /**
     * Getter for the incident id attribute
     * @return 
     */
    public Integer getId() {
        return this.id.get();
    }

    /**
     * Setter for the incident id attribute
     * @param id 
     */
    public void setId(Integer id) {
        this.id.set(id);
    }
    
    /**
     * Getter for the incidentType attribute
     * @return the incidentType
     */
    public String getIncidentType() {
        return this.incidentType.get();
    }

    /**
     * Setter for the incident incidentType attribute
     * @param incidentType the incidentType to set
     */
    public void setIncidentType(String incidentType) {
        this.incidentType.set(incidentType);
    }

    /**
     * Getter for the implicatesds attribute
     * @return the implicateds
     */
    public List<UserBean> getImplicateds() {
        return this.implicateds;
    }

    /**
     * Setter for the implicatesds attribute
     * @param implicateds the implicateds to set
     */
    public void setImplicateds(List<UserBean> implicateds) {
        try{
            this.implicateds = implicateds;
        }catch(Exception ex){
            String error = ex.getMessage();
            System.out.println("Error en el setter");
        }
    }
    
    /**
     * Getter for the description attribute
     * @return the description
     */
    public String getDescription() {
        return this.description.get();
    }

    /**
     * Setter for the description attribute
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description.set(description);
    }
    
    /**
     * Getter for the room attribute
     * @return the room
     */
    public RoomBean getRoom() {
        return this.room.get();
    }

    /**
     * Setter for the room attribute
     * @param room the room to set
     */
    public void setRoom(RoomBean room) {
        this.room.set(room);
    }
    
    /**
     * Getter for the date attribute
     * @return the date
     */
    public Date getDate(){
        return this.date.get();
    }
    
    /**
     * Setter for the date attribute
     * @param date the date
     */
    public void setDate(Date date){
        this.date.set(date);
    }

    /**
     * Hash code method
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    /**
     * Compares two objects to see if they are the same object
     * @param object
     * @return 
     */
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

    /**
     * Gets the incident in string format
     * @return the incident as a string
     */
    @Override
    public String toString() {
        return ("Incident id: " + this.getId().toString());
    }

    /**
     * Gets the user of type employee from the implicateds list
     * @return the employee user
     */
    public UserBean getEmployee(){
        UserBean employee = null;
        List<UserBean> users = this.getImplicateds();
        for(UserBean u: users){
            if(u.getPrivilege().equals(Privilege.ADMIN)){
                employee = u;
            }
        }
        return employee;
    }
    
    /**
     * Gets the users of type guest from the implicateds list
     * @return the guest users
     */
    public ObservableList<UserBean> getGuests(){
        List<UserBean> guests = new ArrayList<UserBean>();
        List<UserBean> users = this.getImplicateds();
        for(UserBean u: users){
            if(u.getPrivilege().equals(Privilege.USER)){
                guests.add(u); 
            }
        }
        ObservableList guestsObservable = FXCollections.observableArrayList(guests);
        return guestsObservable;
    }
}
