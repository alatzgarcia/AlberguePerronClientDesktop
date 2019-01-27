/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.exceptions;

/**
 * Exception class for the register failed error
 * @author Alatz
 */
public class RegisterFailedException extends Exception{
    private static final String MESSAGE = "Error. El registro de usuario"
            + "no se ha podido completar con éxito. Vuelva a intentarlo.";
    /**
     * Method to get the message of the exception
     * @return returns the error message
     */
    public String getMessage(){
        return MESSAGE;
    }
}