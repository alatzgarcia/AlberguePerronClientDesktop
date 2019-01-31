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

    public UserBean() {
        this.id = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.surname1 =  new SimpleStringProperty();
        this.surname2 =  new SimpleStringProperty();
        this.privilege =  new SimpleObjectProperty<Privilege>();
        this.login =  new SimpleStringProperty();
        this.email =  new SimpleStringProperty();
        this.password =  new SimpleStringProperty();
        this.lastPasswordChange =  new SimpleObjectProperty();
        this.incidents = new SimpleListProperty<>();
        this.pets = new SimpleListProperty<>();
        this.stays = new SimpleListProperty<>();
    }
    
    

    public UserBean(String id, String name, String surname1, String surname2, Privilege privilege, 
            String login, String email, String password, String lastPasswordChange) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.surname1 =  new SimpleStringProperty(surname1);
        this.surname2 =  new SimpleStringProperty(surname2);
        this.privilege =  new SimpleObjectProperty<Privilege>(privilege);
        this.login =  new SimpleStringProperty(login);
        this.email =  new SimpleStringProperty(email);
        this.password =  new SimpleStringProperty(password);
        this.lastPasswordChange =  new SimpleObjectProperty(lastPasswordChange);
        /*this.fullname = new SimpleStringProperty(name+" "+surname1+" "+surname2);*/
    }

    public String getId(){
        return this.id.get();
    }
    
    public void setId(String id){
        this.id.set(id);
    }
    
    public String getName(){
        return this.name.get();
    }
    
    public void setName(String name){
        this.name.set(name);
    }
    
    public String getSurname1(){
        return this.surname1.get();
    }
    
    public void setSurname1(String surname1){
        this.surname1.set(surname1);
    }
    
    public String getSurname2(){
        return this.surname2.get();
    }
    
    public void setSurname2(String surname2){
        this.surname2.set(surname2);
    }
    
    public Privilege getPrivilege(){
        return this.privilege.get();
    }
    
    public void setPrivilege(Privilege privilege){
        this.privilege.set(privilege);
    }
    
    public String getLogin(){
        return this.login.get();
    }
    
    public void setLogin(String login){
        this.login.set(login);
    }
    
    public String getEmail(){
        return this.email.get();
    }
    
    public void setEmail(String email){
        this.email.set(email);
    }
    
    public String getPassword(){
        return this.password.get();
    }
    
    public void setPassword(String password){
        this.password.set(password);
    }
//TOCHECK
    public Date getLastPasswordChange(){
        return (Date)this.lastPasswordChange.get();
    }
    
    public void setLastPasswordChange(Date lastPasswordChange){
        this.lastPasswordChange.set(lastPasswordChange);
    }

    public List<IncidentBean> getIncidents() {
        return this.incidents.get();
    }
//
    public void setIncidents(List<IncidentBean> incidents) {
        this.incidents.setAll(incidents);
    }

    public List<PetBean> getPets() {
        return this.pets.get();
    }

    public void setPets(List<PetBean> pets) {
        this.pets.setAll(pets);
    }

    public List<StayBean> getStays() {
        return this.stays.get();
    }

    public void setStays(List<StayBean> stays) {
        this.stays.setAll(stays);
    } 
    
    public String getFullname(){      
        return this.name.get()+" "+this.surname1.get()+" "+this.surname2.get();
    }
    
    public void setFullname(String fullname){
        this.fullname.set(fullname);
    }
    
    @Override
    public String toString(){
        return this.getFullname();
    }
    
 }
