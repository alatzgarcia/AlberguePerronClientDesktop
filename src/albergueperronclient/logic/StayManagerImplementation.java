/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.logic;

import albergueperronclient.exceptions.BusinessLogicException;
import albergueperronclient.modelObjects.StayBean;
import albergueperronclient.modelObjects.UserBean;
import albergueperronclient.rest.StayREST;
import albergueperronclient.rest.UserREST;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author 2dam
 */
public class StayManagerImplementation implements StaysManager{
    private StayREST webClient;
    private static final Logger LOGGER=Logger.getLogger("StayManagerImplementation.class");
    
    /**
     * The empty constructor of the implementation that creates the StayREST
     */
    public StayManagerImplementation(){
        webClient=new StayREST();
    }
    
    /**
     * Gets The Stay seraching by Id
     * @param id
     * @return 
     */
    @Override
    public StayBean getStay(String id) {
        StayBean stay=new StayBean();
        try{
            stay=webClient.find(StayBean.class, id);
        }catch(ClientErrorException cee){
            LOGGER.info(cee.getMessage());
        }
        return stay;
    }
    
    /**
     * Gets all the existent Stays 
     * @return
     * @throws BusinessLogicException 
     */
    @Override
    public Collection<StayBean> getAllStays() throws BusinessLogicException {
        Collection<StayBean> stays =null;
        try{
            //Ask webClient for all users' data.
            stays = webClient.findAll(new GenericType<List<StayBean>>() {});
        }catch(Exception e){
            LOGGER.log(Level.SEVERE,
                    "StaysManager: Exception finding all stays, ",
                    e.getMessage());
            throw new BusinessLogicException("Error finding all stays:\n"+e.getMessage());
        }
        return stays;    
    }
    
    /**
     * Creates a Stay
     * @param stay
     * @throws BusinessLogicException 
     */
    @Override
    public void createStay(StayBean stay) throws BusinessLogicException {
        try{
            webClient.create(stay);
        }catch(Exception e){
            LOGGER.log(Level.SEVERE,
                    "StayManager: Exception creating all stays, ",
                    e.getMessage());
            throw new BusinessLogicException("Error creating all stays: \n"+e.getMessage());
        }
    }
    
    /**
     * Updates a Stay
     * @param stay
     * @throws BusinessLogicException 
     */
    @Override
    public void updateStay(StayBean stay) throws BusinessLogicException {
        try{
           webClient.edit(stay);
        }catch(Exception e){
           LOGGER.log(Level.SEVERE,
                    "StaysImplementation: Exception updating all stays, ",
                    e.getMessage());
           throw new BusinessLogicException("Error updating all stays: \n"+e.getMessage() );
       }
    }
    
    /**
     * Deletes a Stay by an Id
     * @param id
     * @throws BusinessLogicException 
     */
    @Override
    public void deleteStay(String id) throws BusinessLogicException {
        try{
            webClient.remove(id);
        }catch(Exception e){
            LOGGER.log(Level.SEVERE,
                    "StayImplementation: Exception removing stay, ",
                    e.getMessage());
           throw new BusinessLogicException("Error deleting stay: \n"+e.getMessage() );
        }
    }
}
