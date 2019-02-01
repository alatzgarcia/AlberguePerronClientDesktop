/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.rest;

import albergueperronclient.modelObjects.Privilege;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Jersey REST client generated for REST resource:UserREST [users]<br>
 * USAGE:
 * <pre>
 *        UserREST client = new UserREST();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Diego
 */
public class UserREST {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/alberguePerronServer/webresources";
    
    /**
     * The empty constructor of the UserREST
     */
    public UserREST() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("users");
    }
    
    /**
     * The method for user editing
     * @param requestEntity
     * @throws ClientErrorException 
     */
    public void edit(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }
    
    /**
     * The method for the user finding
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
     * The method for user creating 
     * @param requestEntity
     * @throws ClientErrorException 
     */
    public void create(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * The method used to find all the users
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
     * The method used to remove an user by id
     * @param id
     * @throws ClientErrorException 
     */
    public void remove(String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }
    
    /**
     * The method that find an user by the privilege
     * @param <T>
     * @param responseType
     * @param privilege
     * @return
     * @throws ClientErrorException 
     */
    public <T> T findByPrivilege(GenericType<T> responseType, Privilege privilege) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("privilege/{0}", new Object[]{privilege}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }
    
    /**
     * The method used to close the project
     */
    public void close() {
        client.close();
    }
    
}
