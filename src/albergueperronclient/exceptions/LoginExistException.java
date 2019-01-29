/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.exceptions;

/**
 *
 * @author ikerm
 */
public class LoginExistException extends BusinessLogicException{
    
    public LoginExistException(String msg){
        super(msg);
    }
}
