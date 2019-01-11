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
public class ReadException extends Exception {

    public ReadException(){
        
    }
    
    public ReadException(String message) {
        super(message);
    }
    
}
