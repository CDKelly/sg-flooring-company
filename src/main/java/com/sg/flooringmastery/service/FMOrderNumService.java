/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FMOrderNumDao;
import com.sg.flooringmastery.dto.OrderNum;
import com.sg.flooringmastery.exceptions.FMPersistenceException;

/**
 *
 * @author connor
 */
public interface FMOrderNumService {
    
    public void setOrderNumDao(FMOrderNumDao orderNumDao);
    
    public OrderNum getOrderNum()
            throws FMPersistenceException;
    
    public void setOrderNum(OrderNum orderNum)
            throws FMPersistenceException;
    
    public void setNewMaxOrderNum()
            throws FMPersistenceException;
}
