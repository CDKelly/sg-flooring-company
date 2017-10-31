/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FMOrderDao;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.exceptions.FMBlankDateFileException;
import com.sg.flooringmastery.exceptions.FMInvalidDateException;
import com.sg.flooringmastery.exceptions.FMInvalidOrderNumException;
import com.sg.flooringmastery.exceptions.FMPersistenceException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author connor
 */
public interface FMOrderService {

    public void setOrderDao(FMOrderDao orderDao);
    
    public Order addOrder(Order order)
            throws FMPersistenceException;

    public Order removeOrder(Order order)
            throws FMPersistenceException;

    public Order editOrder(Order order)
            throws FMPersistenceException;

    public List<Order> getOrdersForOneDay(LocalDate date)
            throws FMPersistenceException, FMBlankDateFileException,
                FMInvalidDateException;
    
    public Order getOrderByOrderNum(Integer orderNum)
            throws FMPersistenceException, FMInvalidOrderNumException;
    
    public boolean doesDateExist(LocalDate date)
            throws FMPersistenceException;
    
    public boolean doOrdersExistForDate(LocalDate date)
            throws FMPersistenceException;

    public boolean doesOrderExist(Integer orderNumber)
            throws FMPersistenceException;

    public BigDecimal calculateMaterialCost(Order order);

    public BigDecimal calculateLaborCost(Order order);

    public BigDecimal calculatePreTaxTotal(Order order);

    public BigDecimal calculateTax(BigDecimal preTaxTotal, Order order);

    public BigDecimal calculateTotalCost(Order order);

    public Order calculateTotals(Order order);
}
