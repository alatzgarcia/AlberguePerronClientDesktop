/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.logic;

import albergueperronclient.exceptions.BusinessLogicException;
import albergueperronclient.exceptions.LoginExistException;
import albergueperronclient.modelObjects.PetBean;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

/**
 *
 * @author ikerm
 */
public class PetsManagerTestDataGenerator implements PetsManager{
    private static final Logger LOGGER=Logger.getLogger("pet");
    private ArrayList<albergueperronclient.modelObjects.PetBean> pets;
    public PetsManagerTestDataGenerator(){
        LOGGER.info("Building fake users data for testing UI.");
        pets=new ArrayList();      
    }
    
    /**
     * This method returns a Collection of {@link PetBean}, containing all pets data.
     * @return
     * @throws BusinessLogicException 
     */
    @Override
    public Collection getAllPets() throws BusinessLogicException {
        LOGGER.info("Getting all fake pets data for UI.");
        return pets;
    }
    
    /**
     * This method updates a PetBean into the test collection. 
     * @param pet
     * @param id
     * @throws BusinessLogicException 
     */
    @Override
    public void updatePet(PetBean pet, Integer id) throws BusinessLogicException {
       
    }
    /**
     * This method deletes a PetBean data from the test collection.
     * @param id
     * @throws BusinessLogicException 
     */
    @Override
    public void deletePet(Integer id) throws BusinessLogicException {
         pets.remove(id);
    }
    /**
     * Check if a pets's login already exists, throwing an Exception if that's the case.
     * @param id
     * @throws BusinessLogicException
     * @throws LoginExistException 
     */
    @Override
    public void isIdExisting(Integer id) throws BusinessLogicException, LoginExistException {
        LOGGER.info("Validating Login existence.");
        if (pets.stream().filter(user->user.getId().equals(id)).count()!=0){
            LOGGER.severe("Login already exists.");
            throw new LoginExistException("Login ya existe.");
        }
    }
    /**
     * This method adds a new created PetBean to a test collection.
     * @param pet
     * @throws BusinessLogicException 
     */
    @Override
    public void createPet(PetBean pet) throws BusinessLogicException {
       pets.add(pet);
    }
    
}
