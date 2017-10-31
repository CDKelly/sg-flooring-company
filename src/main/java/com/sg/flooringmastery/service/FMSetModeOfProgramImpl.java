/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FMOrderNumDao;
import com.sg.flooringmastery.dao.FMOrderDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author connor
 */
public class FMSetModeOfProgramImpl implements FMSetModeOfProgram {

    ApplicationContext ctx =
            new ClassPathXmlApplicationContext("applicationContext.xml");

    @Override
    public FMOrderDao setModeOfOrderDao(Integer userInput) {
        FMOrderDao orderDao;
        
        if (userInput == 1) {
            orderDao = ctx.getBean("orderDaoProdImpl", FMOrderDao.class);
        } else {
            orderDao = ctx.getBean("orderDaoTrainingImpl", FMOrderDao.class);
        }
        return orderDao;
    }

    @Override
    public FMOrderNumDao setModeOfOrderNumDao(Integer userInput) {
        FMOrderNumDao orderNumDao;
        
        if (userInput == 1) {
            orderNumDao = ctx.getBean("orderNumProdImpl", FMOrderNumDao.class);
        } else {
            orderNumDao = ctx.getBean("orderNumTrainingImpl", FMOrderNumDao.class);
        }
        return orderNumDao;
    }
}
