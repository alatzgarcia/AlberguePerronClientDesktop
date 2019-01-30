/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.exceptions;

/**
 * Exception class for the invalid operation error
 * @author Alatz
 */
public class InvalidOperationException extends Exception{
    private static final String MESSAGE = "Error. Tipo de operación"
            + "no reconocido por el sistema.";
    /**
     * Method to get the message of the exception
     * @return returns the error message
     */
    @Override
    public String getMessage(){
        return MESSAGE;
    }
}
