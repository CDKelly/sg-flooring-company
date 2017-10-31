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
public class FMInvalidDateException extends Exception {
     public FMInvalidDateException(String message) {
        super(message);
    }
    
    public FMInvalidDateException(String message, Throwable cause) {
        super(message, cause);
    }
}
