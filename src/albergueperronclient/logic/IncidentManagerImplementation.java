/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.logic;

import albergueperronclient.exceptions.CreateException;
import albergueperronclient.exceptions.DeleteException;
import albergueperronclient.exceptions.ReadException;
import albergueperronclient.exceptions.UpdateException;
import albergueperronclient.modelObjects.IncidentBean;
import albergueperronclient.rest.IncidentRESTClient;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;

/**
 * IncidentManagerImplementation class for the AlberguePerronClient application
 * @author Alatz
 */
public class IncidentManagerImplementation implements IncidentManager {
    /**
     * Web REST client
     */
    private IncidentRESTClient webClient;
    /**
     * Logger for the class
     */
    private static final Logger LOGGER= Logger.
            getLogger("albergueperronclient.logic.IncidentManagerImplementation");
    
    /**
     * Constructor for the class that initializes the rest client
     */
    public IncidentManagerImplementation(){
        webClient = new IncidentRESTClient();
    }
    
    /**
     * Method to get an incident by its id
     * @param id the id of the incident to find
     * @return the incident object
     * @throws ReadException 
     */
    @Override
    public IncidentBean findIncidentById(Integer id) throws ReadException {
        IncidentBean incident = null;
        try{
            LOGGER.log(Level.INFO, "IncidentManager: Finding incident {0} "
                    + "from REST service (XML).", id);
            //Ask webClient for all users' data.
            incident = webClient.find(IncidentBean.class, id.toString());
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "IncidentManager: Exception finding incident, {0}",
                    ex.getMessage());
            throw new ReadException(ex.getMessage());
        }
        return incident;
    }
    
    /**
     * Method to get all incidents
     * @return a list with the incidents
     * @throws ReadException 
     */
    @Override
    public List<IncidentBean> findAllIncidents() throws ReadException {
        List<IncidentBean> incidents = null;
        try{
            LOGGER.info("IncidentManager: Finding all users from REST service (XML).");
            //Ask webClient for all users' data.
            incidents = webClient.findAll(new GenericType<List<IncidentBean>>() {});
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "IncidentManager: Exception finding all users, {0}",
                    ex.getMessage());
            throw new ReadException(ex.getMessage());
        }
        return incidents;
    }

    /**
     * Method to create an incident
     * @param incident the incident object
     * @throws CreateException 
     */
    @Override
    public void createIncident(IncidentBean incident) throws CreateException {
        try{
            LOGGER.log(Level.INFO,"IncidentManager: Creating incident of type " 
                    + incident.getIncidentType() + " with description "
                    + incident.getDescription() + " .");
            //Send user data to web client for creation. 
            webClient.create(incident);
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "IncidentManager: Exception creating incident, {0}",
                    ex.getMessage());
            throw new CreateException(ex.getMessage());
        }
    }

    /**
     * Method to update an incident
     * @param incident the incident object
     * @throws UpdateException 
     */
    @Override
    public void updateIncident(IncidentBean incident) throws UpdateException {
         try{
            LOGGER.log(Level.INFO,"IncidentManager: Updating incident {0}.",
                    incident.getId());
            webClient.edit(incident);
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "IncidentManager: Exception updating incident, {0}",
                    ex.getMessage());
            throw new UpdateException(ex.getMessage());
        }
    }

    /**
     * Method to delete an incident by its id
     * @param id the id of the incident to delete
     * @throws DeleteException 
     */
    @Override
    public void deleteIncident(Integer id) throws DeleteException {
       try{
            LOGGER.log(Level.INFO,"IncidentManager: Deleting incident {0}.",
                    id);
            webClient.remove(id.toString());
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "IncidentManager: Exception deleting incident, {0}",
                    ex.getMessage());
            throw new DeleteException(ex.getMessage());
        }
    }
    
}
