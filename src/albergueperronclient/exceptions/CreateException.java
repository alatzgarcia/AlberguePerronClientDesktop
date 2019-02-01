/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.exceptions;

/**
 *
 * @author Alatz
 */
public class CreateException extends Exception {

    public CreateException(){
        
    }
    
    public CreateException(String message) {
        super(message);
    }
    
}
