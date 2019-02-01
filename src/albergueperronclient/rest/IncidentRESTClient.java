/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.rest;

import albergueperronclient.modelObjects.IncidentBean;
import java.util.List;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Jersey REST client generated for REST resource:IncidentREST [incident]<br>
 * USAGE:
 * <pre>
 *        IncidentRESTClient client = new IncidentRESTClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 * 
 * IncidentREST class for the AlberguePerronClient application
 * @author Alatz
 */
public class IncidentRESTClient {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/albergueperronserver/webresources";

    /**
     * Constructor for the class
     */
    public IncidentRESTClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("incident");
    }

    /**
     * Connects with the web server application to update an incident
     * @param requestEntity
     * @throws ClientErrorException 
     */
    public void edit(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Connects with the web server application to find an incident by its id
     * @param <T>
     * @param responseType
     * @param id
     * @return
     * @throws ClientErrorException 
     */
    public <T> T find(Class<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Connects with the web server application to create an incident
     * @param requestEntity
     * @throws ClientErrorException 
     */
    public void create(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Connects with the web server application to find all incidents
     * @param <T>
     * @param responseType
     * @return
     * @throws ClientErrorException 
     */
    public <T> T findAll(GenericType<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Connects with the web server application to delete an incident by its id
     * @param id
     * @throws ClientErrorException 
     */
    public void remove(String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }

    /**
     * Closes the client
     */
    public void close() {
        client.close();
    }
    
}
