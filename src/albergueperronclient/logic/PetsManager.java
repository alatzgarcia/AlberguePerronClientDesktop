/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.logic;


import albergueperronclient.exceptions.BusinessLogicException;
import albergueperronclient.exceptions.LoginExistException;
import java.util.Collection;
import albergueperronclient.modelObjects.PetBean;
import javafx.collections.ObservableList;

/**
 *
 * @author ikerm
 */
public interface PetsManager {
     /**
     * This method returns a Collection of {@link PetBean}, containing all pets data.
     * @return Collection The collection with all {@link PetBean} data for pets. 
     * @throws BusinessLogicException If there is any error while processing.
     */
    public Collection<PetBean> getAllPets() throws BusinessLogicException;
    /**
     * This method updates data for an existing UserBean data for user. 
     * @param pet The PetBean object to be updated.
     * @param id
     * @throws BusinessLogicException If there is any error while processing.
     */
    public void updatePet(PetBean pet, Integer id)throws BusinessLogicException;
    /**
     * This method deletes data for an existing user. 
     * @param pet The PetBean object to be deleted.
     * @throws BusinessLogicException If there is any error while processing.
     */
    public void deletePet(Integer pet) throws BusinessLogicException;

    /**
     * This method checks if a user's login already exists, throwing an Exception 
     * if that's the case.
     * @param id
     * @throws BusinessLogicException
     * @throws LoginExistException
     */
    public void isIdExisting(Integer id) throws BusinessLogicException , LoginExistException;

    /**
     * This method create pets
     * @param pet
     * @throws BusinessLogicException 
     */
    public void createPet(PetBean pet) throws BusinessLogicException;

}



