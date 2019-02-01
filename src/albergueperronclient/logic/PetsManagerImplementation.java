/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.logic;

import albergueperronclient.exceptions.BusinessLogicException;
import albergueperronclient.exceptions.LoginExistException;
import albergueperronclient.modelObjects.PetBean;
import albergueperronclient.rest.PetRESTClient;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author ikerm
 */
public class PetsManagerImplementation implements PetsManager{
    private PetRESTClient webClient;
    private static final Logger LOGGER=Logger.getLogger("PetsManagerImplementation.class");
     /**
     * Create a PetsManagerImplementation object. It constructs a web client for 
     * accessing a RESTful service that provides business logic in an application
     * server.
     */
    public PetsManagerImplementation(){
        webClient=new PetRESTClient();
    }
    /**
     * This method returns a Collection of {@link PetBean}, containing all pets data.
     * @return
     * @throws BusinessLogicException 
     */
    @Override
    public Collection<PetBean> getAllPets() throws BusinessLogicException {
       Collection<PetBean> pets = null;
        try{
            LOGGER.info("PetsManager: Finding all users from REST service (XML).");
            //Ask webClient for all users' data.
           pets = webClient.findAllPets(new GenericType<List<PetBean>>() {});   
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "PetsManager: Exception finding all pets, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error finding all pets:\n"+ex.getMessage());
        }
        return pets;
    }
    /**
     * This method updates data for an existing PetBean data for pets. 
     * @param pet
     * @param id
     * @throws BusinessLogicException 
     */
    @Override
    public void updatePet(PetBean pet, Integer id) throws BusinessLogicException {
         try{
            LOGGER.log(Level.INFO,"UsersManager: Updating user {0}.",pet.getId());
            webClient.update(pet);
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "PetsManager: Exception updating user, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error updating user:\n"+ex.getMessage());
        }
    }
    /**
     * This method deletes data for an existing pets. 
     * @param id
     * @throws BusinessLogicException 
     */
    @Override
    public void deletePet(Integer id) throws BusinessLogicException {
        try{
            LOGGER.log(Level.INFO,"PetsManager: Deleting user {0}.",id);
            webClient.delete(id);
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "PetsManager: Exception deleting user, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error deleting user:\n"+ex.getMessage());
        }
    }

    @Override
    public void isIdExisting(Integer id) throws BusinessLogicException, LoginExistException {
        try{
            if(this.webClient.find(albergueperronclient.modelObjects.PetBean.class, id)!=null)
                throw new LoginExistException("Ya existe un usuario con ese login"); 
            }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception checking login exixtence, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error finding user:\n"+ex.getMessage());
        }
    }  
    /**
    * This method adds a new created PetBean.
    * @param pet
    * @throws BusinessLogicException 
    */
    @Override
    public void createPet(PetBean pet) throws BusinessLogicException {
        try{
            webClient.create(pet);
        }catch(Exception e){
            LOGGER.log(Level.SEVERE,
                    "PetsManager: Exception creating all users, ",
                    e.getMessage());
            throw new BusinessLogicException("Error creating all users: \n"+e.getMessage());
        }
    }

   
}
