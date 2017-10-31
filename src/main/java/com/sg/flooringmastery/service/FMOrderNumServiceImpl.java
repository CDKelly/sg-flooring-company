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
public class FMOrderNumServiceImpl implements FMOrderNumService {
    
    private FMOrderNumDao dao;

    public FMOrderNumServiceImpl(FMOrderNumDao dao) {
        this.dao = dao;
    }
    
    @Override
    public void setOrderNumDao(FMOrderNumDao orderNumDao) {
        this.dao = orderNumDao;
    }

    @Override
    public OrderNum getOrderNum()
            throws FMPersistenceException {
        return dao.getOrderNum();
    }

    @Override
    public void setOrderNum(OrderNum orderNum)
            throws FMPersistenceException {
        dao.setOrderNum(orderNum);
    }

    @Override
    public void setNewMaxOrderNum()
            throws FMPersistenceException {
        dao.setNewMaxOrderNum();
    }
}
