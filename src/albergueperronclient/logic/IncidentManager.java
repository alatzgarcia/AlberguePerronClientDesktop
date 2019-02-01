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
import java.util.List;

/**
 * IncidentManager interface for the AlberguePerronClient application
 * @author Alatz
 */
public interface IncidentManager {
    /**
     * Method to get an incident by its id
     * @param id the id of the incident to find
     * @return the incident object
     * @throws ReadException 
     */
    public IncidentBean findIncidentById(Integer id) throws ReadException;
    /**
     * Method to get all incidents
     * @return a list with the incidents
     * @throws ReadException 
     */
    public List<IncidentBean> findAllIncidents() throws ReadException;
    /**
     * Method to create an incident
     * @param incident the incident object
     * @throws CreateException 
     */
    public void createIncident(IncidentBean incident) throws CreateException;
    /**
     * Method to update an incident
     * @param incident the incident object
     * @throws UpdateException 
     */
    public void updateIncident(IncidentBean incident) throws UpdateException;
    /**
     * Method to delete an incident by its id
     * @param id the id of the incident to delete
     * @throws DeleteException 
     */
    public void deleteIncident(Integer id) throws DeleteException;
}
