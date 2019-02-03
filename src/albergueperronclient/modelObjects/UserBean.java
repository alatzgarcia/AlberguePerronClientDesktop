/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.modelObjects;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javafx.beans.property.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * User class for AlberguePerronServer application
 * @author Diego
 */
@XmlRootElement(name="user")
public class UserBean implements Serializable{
    private SimpleStringProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty surname1;
    private SimpleStringProperty surname2;
    private SimpleObjectProperty<Privilege> privilege;
    private SimpleStringProperty login;
    private SimpleStringProperty email;
    private SimpleStringProperty password;
    private SimpleObjectProperty lastPasswordChange;
    private SimpleListProperty<IncidentBean> incidents;
    private SimpleListProperty<PetBean> pets;
    private SimpleListProperty<StayBean> stays;
    
    private SimpleStringProperty fullname;
    
    /**
     * The Empty constructor for the Guest(User)
     */
    public UserBean() {
        this.id = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.surname1 =  new SimpleStringProperty();
        this.surname2 =  new SimpleStringProperty();
        this.privilege =  new SimpleObjectProperty();
        this.login =  new SimpleStringProperty();
        this.email =  new SimpleStringProperty();
        this.password =  new SimpleStringProperty();
        this.lastPasswordChange =  new SimpleObjectProperty();
        this.incidents = new SimpleListProperty<>();
        this.pets = new SimpleListProperty<>();
        this.stays = new SimpleListProperty<>();
    }
    
    
    /**
     * The full constructor for the User
     * @param id    Integer
     * @param name  String
     * @param surname1  String
     * @param surname2  String
     * @param privilege Privilege
     * @param login String
     * @param email String
     * @param password  String
     * @param lastPasswordChange    String 
     */
    public UserBean(String id, String name, String surname1, String surname2, Privilege privilege, 
            String login, String email, String password, Date lastPasswordChange) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.surname1 =  new SimpleStringProperty(surname1);
        this.surname2 =  new SimpleStringProperty(surname2);
        this.privilege =  new SimpleObjectProperty(privilege);
        this.login =  new SimpleStringProperty(login);
        this.email =  new SimpleStringProperty(email);
        this.password =  new SimpleStringProperty(password);
        this.lastPasswordChange =  new SimpleObjectProperty(lastPasswordChange);
    }
    
    /**
     * Gets the Id of User
     * @return 
     */
    public String getId(){
        return this.id.get();
    }
    
    /**
     * Sets the Id of User
     * @param id 
     */
    public void setId(String id){
        this.id.set(id);
    }
    
    /**
     * Gets the name of the User
     * @return 
     */
    public String getName(){
        return this.name.get();
    }
    
    /**
     * Sets the name for the User
     * @param name 
     */
    public void setName(String name){
        this.name.set(name);
    }
    
    /**
     * Gets the surname of the User
     * @return 
     */
    public String getSurname1(){
        return this.surname1.get();
    }
    
    /**
     * Sets the surname for the User
     * @param surname1 
     */
    public void setSurname1(String surname1){
        this.surname1.set(surname1);
    }
    /**
     * Gets the second surname of the User
     * @return 
     */
    public String getSurname2(){
        return this.surname2.get();
    }
    
    /**
     * Sets the second surname for the User
     * @param surname2 
     */
    public void setSurname2(String surname2){
        this.surname2.set(surname2);
    }
    
    /**
     * Gets the privilege of the User
     * @return 
     */
    public Privilege getPrivilege(){
        return this.privilege.get();
    }
    
    /**
     * Sets the privilege for the User
     * @param privilege 
     */
    public void setPrivilege(Privilege privilege){
        this.privilege.set(privilege);
    }
    
    /**
     * Gets the login of the User
     * @return 
     */
    public String getLogin(){
        return this.login.get();
    }
    
    /**
     * Sets the login for the User
     * @param login 
     */
    public void setLogin(String login){
        this.login.set(login);
    }
    
    /**
     * Gets the email of the User
     * @return 
     */
    public String getEmail(){
        return this.email.get();
    }
    
    /**
     * Sets the email for the User
     * @param email 
     */
    public void setEmail(String email){
        this.email.set(email);
    }
    
    /**
     * Gets the password of the User
     * @return 
     */
    public String getPassword(){
        return this.password.get();
    }
    
    /**
     * Sets the password for User
     * @param password 
     */
    public void setPassword(String password){
        this.password.set(password);
    }
    
    /**
     * Gets the last password change of User
     * @return 
     */
    public Date getLastPasswordChange(){
        return (Date)this.lastPasswordChange.get();
    }
    
    /**
     * Sets the last password change for User
     * @param lastPasswordChange 
     */
    public void setLastPasswordChange(Date lastPasswordChange){
        this.lastPasswordChange.set(lastPasswordChange);
    }
    
    /**
     * Gets the incident list of the User
     * @return 
     */
    public List<IncidentBean> getIncidents() {
        return this.incidents.get();
    }
    
    /**
     * Sets the incident list for the User
     * @param incidents 
     */
    public void setIncidents(List<IncidentBean> incidents) {
        this.incidents.setAll(incidents);
    }

    /**
     * Gets the pet list of the User
     * @return 
     */
    public List<PetBean> getPets() {
        return this.pets.get();
    }

    /**
     * Sets the pet list of the User
     * @param pets 
     */
    public void setPets(List<PetBean> pets) {
        this.pets.setAll(pets);
    }

    /**
     * Gets the stay list of the User
     * @return 
     */
    public List<StayBean> getStays() {
        return this.stays.get();
    }

    /**
     * Sets the stay list for the User
     * @param stays 
     */
    public void setStays(List<StayBean> stays) {
        this.stays.setAll(stays);
    } 
    
    /**
     * Gets the fullname of the User (name+surname)
     * @return 
     */
    public String getFullname(){      
        return this.name.get()+" "+this.surname1.get()+" "+this.surname2.get();
    }
    
    /**
     * Sets the fullname for the User (name+suename)
     * @param fullname 
     */
    public void setFullname(String fullname){
        this.fullname.set(fullname);
}

    /**
     * This method returns a String representation for a user entity instance.
     * @return The String representation for the user object. 
     */
    @Override
    public String toString() {
        return getName() + " " + getSurname1() + " " + getSurname2();
    }
}
