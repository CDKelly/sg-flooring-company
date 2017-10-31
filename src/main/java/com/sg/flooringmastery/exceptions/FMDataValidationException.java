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
public class FMDataValidationException extends Exception {
    public FMDataValidationException(String message) {
        super(message);
    }
    
    public FMDataValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
