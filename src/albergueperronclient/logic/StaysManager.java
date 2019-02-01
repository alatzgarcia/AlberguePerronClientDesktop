/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.logic;

import albergueperronclient.exceptions.BusinessLogicException;
import albergueperronclient.modelObjects.StayBean;
import java.util.Collection;

/**
 * The interface for the stayManager
 * @author Diego
 */
public interface StaysManager {
    /**
     * Get the Stay by ID
     * @param id 
     * @return 
     */
    public StayBean getStay(String id);
    
    /**
     * Gets all the Stays
     * @return
     * @throws BusinessLogicException 
     */
    public Collection<StayBean> getAllStays() throws BusinessLogicException;
    
    /**
     * Creates all the Stays
     * @param stay
     * @throws BusinessLogicException 
     */
    public void createStay(StayBean stay) throws BusinessLogicException;
    
    /**
     * Updates the Stay
     * @param stay
     * @throws BusinessLogicException 
     */
    public void updateStay(StayBean stay) throws BusinessLogicException;
    
    /**
     * Deletes the Stay by id
     * @param id
     * @throws BusinessLogicException 
     */
    public void deleteStay(String id) throws BusinessLogicException;
}
