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
 * @author ikerm
 */
@XmlRootElement(name="pet")
public class PetBean implements Serializable{
    private  SimpleIntegerProperty id;
    private  SimpleObjectProperty<UserBean> owner;  
    private  SimpleStringProperty specie;
    private  SimpleStringProperty race;
    private  SimpleStringProperty colour;
    private  SimpleStringProperty description;
    private  SimpleStringProperty name;
    private  SimpleStringProperty dni;
    
    public PetBean(){
           this.id=new SimpleIntegerProperty();
           this.owner=new SimpleObjectProperty(new UserBean());
           this.specie=new SimpleStringProperty("");
           this.race=new SimpleStringProperty("");
           this.colour=new SimpleStringProperty("");
           this.description=new SimpleStringProperty("");
           this.name=new SimpleStringProperty("");
           this.dni = new SimpleStringProperty("");
    }
    
    public Integer getId(){
        return this.id.get();
    }
    public void setId(Integer id){
        this.id.set(id);
    }
    
    public UserBean getOwner(){
        return this.owner.get();
    }
    public void setOwner(UserBean owner){
       this.owner.set(owner);
    }
    
    public String getSpecie(){
        return this.specie.get();
    }
    public void setSpecie(String specie){
        this.specie.set(specie);
    }
    
    public String getRace(){
        return this.race.get();
    }
    public void setRace(String race){
        this.race.set(race);
    }
    
     public String getColour(){
        return this.colour.get();
    }
    public void setColour(String colour){
        this.colour.set(colour);
    }
    
     public String getDescription(){
        return this.description.get();
    }
    public void setDescription(String description){
        this.description.set(description);
    }
    
    public String getName(){
        return this.name.get();
    }
    public void setName(String name){
        this.name.set(name);
    }
    
    public String getDni(){
       return  this.dni.get();
    }
    public void setDni(String dni){
        this.dni.set(dni);
    }
  
}