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
    private String id;
    private String name;
    private String surname1;
    private String surname2;
    private Privilege privilege;
    private String login;
    private String email;
    private String password;
    private String lastPasswordChange;
    private List<IncidentBean> incidents;
    private List<PetBean> pets;
    private List<StayBean> stays;
    
    //quite las list alv

    public UserBean() {
        
    }

    public UserBean(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public UserBean(String id, String login, String email, String password) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.password = password;
    }

   

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname1() {
        return surname1;
    }

    public void setSurname1(String surname1) {
        this.surname1 = surname1;
    }

    public String getSurname2() {
        return surname2;
    }

    public void setSurname2(String surname2) {
        this.surname2 = surname2;
    }

    public Privilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastPasswordChange() {
        return lastPasswordChange;
    }

    public void setLastPasswordChange(String lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange;
    }

    public List<IncidentBean> getIncidents() {
        return incidents;
    }

    public void setIncidents(List<IncidentBean> incidents) {
        this.incidents = incidents;
    }

    public List<PetBean> getPets() {
        return pets;
    }

    public void setPets(List<PetBean> pets) {
        this.pets = pets;
    }

    public List<StayBean> getStays() {
        return stays;
    }

    public void setStays(List<StayBean> stays) {
        this.stays = stays;
    }
    
    

    
    
 }
