/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.exceptions;

/**
 * Exception class for the not server available error
 * @author Alatz
 */
public class ServerNotAvailableException extends Exception{
    private static final String MESSAGE = "Error. El servidor no se encuentra"
            + "disponible en estos momentos. Por favor, vuelva a intentarlo "
            + "en otro momento.";
    /**
     * Method to get the message of the exception
     * @return returns the error message
     */
    @Override
    public String getMessage(){
        return MESSAGE;
    }
}
