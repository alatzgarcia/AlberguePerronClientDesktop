/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.modelObjects;

import java.io.Serializable;
import javafx.beans.property.SimpleStringProperty;

/**
 * UserBeanMongo class for AlberguePerronClient application
 * @author Alatz
 */
public class UserBeanMongo implements Serializable {
    private SimpleStringProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty surname1;
    private SimpleStringProperty surname2;
    private SimpleStringProperty reason;
    
    public UserBeanMongo(String id, String name, String surname1, String surname2,
            String reason) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.surname1 =  new SimpleStringProperty(surname1);
        this.surname2 =  new SimpleStringProperty(surname2);
        this.reason = new SimpleStringProperty(reason);
    }
    
    /**
     * Gets id value for user.
     * @return The id value.
     */
    public String getId() {
        return this.id.get();
    }

    /**
     * Sets id value for user.
     * @param id The id value.
     */
    public void setId(String id) {
        this.id.set(id);
    }

    /**
     * Gets name value for user.
     * @return The name value.
     */
    public String getName() {
        return this.name.get();
    }
    
    /**
     * Sets name value for user.
     * @param name The name value.
     */
    public void setName(String name) {
        this.name.set(name);
    }
    
     /**
      * Gets surname1 value for user.
      * @return The surname1 value.
      */
    public String getSurname1() {
        return this.surname1.get();
    }

    /**
     * Sets surname1 value for user.
     * @param surname1 The surname1 Vvalue.
     */
    public void setSurname1(String surname1) {
        this.surname1.set(surname1);
    }

    /**
     * Gets surname2 value for user.
     * @return The surname2 value.
     */
    public String getSurname2() {
        return this.surname2.get();
    }

    /**
     * Sets surname2 value for user.
     * @param surname2 The surname2 value.
     */
    public void setSurname2(String surname2) {
        this.surname2.set(surname2);
    }
    
    /**
     * @return the reason
     */
    public String getReason() {
        return this.reason.get();
    }

    /**
     * @param reason the reason to set
     */
    public void setReason(String reason) {
        this.reason.set(reason);
    }
    
    /**
     * Compares two objects to see if they are the same object
     * @param object object to compare with
     * @return 
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserBeanMongo)) {
            return false;
        }
        UserBeanMongo other = (UserBeanMongo) object;
        /*if ((this.id == null && other.id != null) || (this.id != null && !this.login.equals(other.login))) {
            return false;
        }*/
        if(this.id == null){
            if(other.id!=null){
                return false;
            }
        }
        if(this.id != null){
            if(!(this.getId().equalsIgnoreCase(other.getId()))){
                return false;
            }
        }
        return true;
    }
}
