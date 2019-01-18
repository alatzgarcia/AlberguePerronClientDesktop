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
import albergueperronclient.modelObjects.Incident;
import albergueperronclient.rest.IncidentRESTClient;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Alatz
 */
public class IncidentManagerImplementation implements IncidentManager {
    private IncidentRESTClient webClient;
    private static final Logger LOGGER= Logger.
            getLogger("albergueperronclient.logic.IncidentManagerImplementation");
    
    public IncidentManagerImplementation(){
        webClient = new IncidentRESTClient();
    }
    
    @Override
    public Incident findIncidentById(Integer id) throws ReadException {
        Incident incident = null;
        try{
            LOGGER.log(Level.INFO, "IncidentManager: Finding incident {0} "
                    + "from REST service (XML).", id);
            //Ask webClient for all users' data.
            incident = webClient.find(Incident.class, id.toString());
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "IncidentManager: Exception finding incident, {0}",
                    ex.getMessage());
            //--TOFIX            
            //throw new BusinessLogicException("Error finding all users:\n"+ex.getMessage());
        }
        return incident;
    }

    @Override
    public List<Incident> findAllIncidents() throws ReadException {
        List<Incident> incidents = null;
        try{
            LOGGER.info("IncidentManager: Finding all users from REST service (XML).");
            //Ask webClient for all users' data.
            incidents = webClient.findAll(new GenericType<List<Incident>>() {});
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "IncidentManager: Exception finding all users, {0}",
                    ex.getMessage());
            //-- TOFIX --> Throwear excepción
        }
        return incidents;
    }

    @Override
    public void createIncident(Incident incident) throws CreateException {
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
            //-- TOFIX --> Throwear excepción
        }
    }

    @Override
    public void updateIncident(Incident incident) throws UpdateException {
         try{
            LOGGER.log(Level.INFO,"IncidentManager: Updating incident {0}.",
                    incident.getId());
            webClient.edit(incident);
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "IncidentManager: Exception updating incident, {0}",
                    ex.getMessage());
            //-- TOFIX --> Throwear excepción
        }
    }

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
            //-- TOFIX --> Throwear excepción
        }
    }
    
}
