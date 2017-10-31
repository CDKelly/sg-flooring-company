/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.exceptions;

/**
 *
 * @author connor
 */
public class FMInvalidOrderNumException extends Exception {
     public FMInvalidOrderNumException(String message) {
        super(message);
    }
    
    public FMInvalidOrderNumException(String message, Throwable cause) {
        super(message, cause);
    }
}
