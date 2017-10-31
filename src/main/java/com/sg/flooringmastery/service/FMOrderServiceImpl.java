/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FMOrderDao;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.OrderNum;
import com.sg.flooringmastery.exceptions.FMBlankDateFileException;
import com.sg.flooringmastery.exceptions.FMInvalidDateException;
import com.sg.flooringmastery.exceptions.FMInvalidOrderNumException;
import com.sg.flooringmastery.exceptions.FMPersistenceException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author connor
 */
public class FMOrderServiceImpl implements FMOrderService {
    
    private FMOrderDao orderDao;

    public FMOrderServiceImpl(FMOrderDao orderDao) {
        this.orderDao = orderDao;
    }
    
    @Override
    public void setOrderDao(FMOrderDao orderDao) {
        this.orderDao = orderDao;
    }
    
    //Pass-through methods

    @Override
    public Order addOrder(Order order)
            throws FMPersistenceException {
        return orderDao.addOrder(order);
    }

    @Override
    public Order removeOrder(Order order)
            throws FMPersistenceException {
        return orderDao.removeOrder(order);
    }

    @Override
    public Order editOrder(Order order)
            throws FMPersistenceException {
        return orderDao.editOrder(order);
    }

    @Override
    public List<Order> getOrdersForOneDay(LocalDate date)
            throws FMPersistenceException, FMBlankDateFileException,
                FMInvalidDateException {
        
        if (!doesDateExist(date)) {
        //if the date file does not exist
            throw new FMInvalidDateException("Invalid Date. File for given date "
                    + "does not exist.");
        } else if (!doOrdersExistForDate(date)) {
        //if there are no orders in the current date file
            throw new FMBlankDateFileException("Blank Date File. There are no "
                    + "orders within the file you have selected.");
        } else {
            return orderDao.getOrdersForOneDay(date);
        }
    }
    
    @Override
    public Order getOrderByOrderNum(Integer orderNum)
            throws FMPersistenceException, FMInvalidOrderNumException {
        //if the order does not exist
        if (!doesOrderExist(orderNum)) {    
            throw new FMInvalidOrderNumException("Invalid Order Number. No orders "
                    + "correspond to given Order Number.");
        } else {
            return orderDao.getOrderByOrderNum(orderNum);
        }
    }
    
    @Override
    public boolean doesDateExist(LocalDate date)
            throws FMPersistenceException {
        return orderDao.doesDateExist(date);
    }
    
    @Override
    public boolean doOrdersExistForDate(LocalDate date)
            throws FMPersistenceException {
        return orderDao.getOrdersForOneDay(date).size() > 0;
    }

    @Override
    public boolean doesOrderExist(Integer orderNumber)
            throws FMPersistenceException {
        return orderDao.getOrderByOrderNum(orderNumber) != null;
    }

    //Calculation methods
    
    @Override
    public BigDecimal calculateMaterialCost(Order order) {
        BigDecimal result = order.getCostPerSqFt().multiply(order.getArea());
        result.setScale(2, RoundingMode.HALF_UP);
        order.setTotalMaterialCost(result);
        return result;
    }

    @Override
    public BigDecimal calculateLaborCost(Order order) {
        BigDecimal result = order.getLaborCostPerSqFt().multiply(order.getArea());
        result.setScale(2, RoundingMode.HALF_UP);
        order.setTotalLaborCost(result);
        return result;
    }

    @Override
    public BigDecimal calculatePreTaxTotal(Order order) {
        return calculateMaterialCost(order).add(calculateLaborCost(order)).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal calculateTax(BigDecimal preTaxTotal, Order order) {
        BigDecimal taxPercent = order.getTaxRate().divide(new BigDecimal("100"));
        BigDecimal taxPercentRounded = taxPercent.setScale(2, RoundingMode.HALF_UP);
        BigDecimal result = preTaxTotal.multiply(taxPercentRounded);
        BigDecimal resultRounded = result.setScale(2, RoundingMode.HALF_UP);
        order.setTotalTax(resultRounded);
        return resultRounded;
    }

    @Override
    public BigDecimal calculateTotalCost(Order order) {
        BigDecimal preTaxTotal = calculatePreTaxTotal(order);
        BigDecimal tax = calculateTax(preTaxTotal, order);
        BigDecimal result = preTaxTotal.add(tax);
        result.setScale(2, RoundingMode.HALF_UP);
        
        order.setTotalCost(result);
        return result;
    }

    @Override
    public Order calculateTotals(Order order) {
        calculateTotalCost(order);
        return order;
    }
}
