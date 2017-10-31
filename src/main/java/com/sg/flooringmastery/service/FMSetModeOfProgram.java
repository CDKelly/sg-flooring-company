/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FMOrderDao;
import com.sg.flooringmastery.dao.FMOrderNumDao;

/**
 *
 * @author connor
 */
public interface FMSetModeOfProgram {

    public FMOrderDao setModeOfOrderDao(Integer userInput);
    
    public FMOrderNumDao setModeOfOrderNumDao(Integer userInput);
}
