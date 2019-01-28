/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.exceptions;

/**
 * This class represents any error during user object's creation in application storage.
 * @author Diego
 */
public class CreateException extends Exception {

    /**
     * Creates a new instance of <code>CreateUserException</code> without detail
     * message.
     */
    public CreateException() {
    }
    /**
     * Constructs an instance of <code>CreateUserException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CreateException(String msg) {
        super(msg);
    }
}
