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
import java.util.List;

/**
 *
 * @author Alatz
 */
public interface IncidentManager {
    public Incident findIncidentById(Integer id) throws ReadException;
    public List<Incident> findAllIncidents() throws ReadException;
    public void createIncident(Incident incident) throws CreateException;
    public void updateIncident(Incident incident) throws UpdateException;
    public void deleteIncident(Integer id) throws DeleteException;
}
