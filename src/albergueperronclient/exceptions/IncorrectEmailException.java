/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.exceptions;

/**
 * Exception class for the no email existing error
 * @author Nerea Jimenez
 */
public class IncorrectEmailException extends Exception{
    private static final String MESSAGE = "Error. El email introducido "
            + "no existe";
    
    /**
     * Method to get the message of the exception
     * @return returns the error message
     */
    @Override
    public String getMessage(){
        return MESSAGE;
    }
}
