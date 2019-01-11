/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albergueperronclient.modelObjects;

/**
 *
 * @author Alatz
 */
public enum Privilege {
    /**
     * The user is a regular user.
     */
    USER,
    /**
     * The user is a regular employee
     */
    EMPLOYEE,
    /**
     * The user is a privileged employee.
     */
    ADMIN;
}
