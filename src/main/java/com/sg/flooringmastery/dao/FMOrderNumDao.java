/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.OrderNum;
import com.sg.flooringmastery.exceptions.FMPersistenceException;

/**
 *
 * @author connor
 */
public interface FMOrderNumDao {
    
    public OrderNum getOrderNum()
            throws FMPersistenceException;
    
    public void setOrderNum(OrderNum orderNum)
            throws FMPersistenceException;
    
    public OrderNum setNewMaxOrderNum()
            throws FMPersistenceException;
}
