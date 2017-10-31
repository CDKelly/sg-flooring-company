/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.exceptions.FMInvalidDateException;
import com.sg.flooringmastery.exceptions.FMPersistenceException;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author connor
 */
public interface FMOrderDao {
    
    public Order addOrder(Order order)
            throws FMPersistenceException;
    
    public Order removeOrder (Order order)
            throws FMPersistenceException;
    
    public Order editOrder(Order order)
            throws FMPersistenceException;
    
    public List<Order> getOrdersForOneDay(LocalDate date)
            throws FMPersistenceException;
    
    public Order getOrderByOrderNum(Integer orderNum)
            throws FMPersistenceException;
    
    public boolean doesDateExist(LocalDate date)
            throws FMPersistenceException;
}
