/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.exceptions;

/**
 * CreateException class for the AlberguePerronClient application
 * @author Alatz
 */
public class CreateException extends Exception {

    /**
     * Empty constructor for the class
     */
    public CreateException(){
        
    }
    
    /**
     * Constructor for the class receiving an error message
     * @param message the error message
     */
    public CreateException(String message) {
        super(message);
    }
    
}
