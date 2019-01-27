/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.exceptions;

/**
 * Exception class for the incorrect password error
 * @author Alatz
 */
public class IncorrectPasswordException extends Exception{
    private static final String MESSAGE = "Error. La contraseña introducida "
            + "es incorrecta.";
    /**
     * Method to get the message of the exception
     * @return returns the error message
     */
    @Override
    public String getMessage(){
        return MESSAGE;
    }
}
