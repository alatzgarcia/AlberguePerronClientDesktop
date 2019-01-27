/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.exceptions;

/**
 * Exception class for FTP connection error
 * @author Nerea Jimenez
 */
public class FTPException extends Exception{
    private static final String MESSAGE = "Error en la conexión FTP";
    
    /**
     * Method to get the message of the exception
     * @return returns the error message
     */
    @Override
    public String getMessage(){
        return MESSAGE;
    }
}