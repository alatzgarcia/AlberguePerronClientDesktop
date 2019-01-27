/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.exceptions;
/**
 * Exception class for the incorrect login error
 * @author Alatz
 */
public class IncorrectLoginException extends Exception {
    private static final String MESSAGE = "Error. El login o la contraseña"
            + " introducidos son incorrectos.";
            
    /**
     * Method to get the message of the exception
     * @return returns the error message
     */
    @Override
    public String getMessage(){
        return MESSAGE;
    }
}
