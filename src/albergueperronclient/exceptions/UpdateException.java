/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.exceptions;

/**
 * UpdateException class for the AlberguePerronClient application
 * @author Alatz
 */
public class UpdateException extends Exception {

    /**
     * Empty constructor for the class
     */
    public UpdateException(){
        
    }
    
    /**
     * Constructor receiving an error message
     * @param message the error message
     */
    public UpdateException(String message) {
        super(message);
    }
    
}
