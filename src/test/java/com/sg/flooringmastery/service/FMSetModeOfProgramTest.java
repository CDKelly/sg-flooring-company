/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FMOrderDaoProdImpl;
import com.sg.flooringmastery.dao.FMOrderDaoTrainingImpl;
import com.sg.flooringmastery.dao.FMOrderNumFileImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author connor
 */
public class FMSetModeOfProgramTest {
    
    ApplicationContext ctx =
            new ClassPathXmlApplicationContext("applicationContext.xml");
    FMSetModeOfProgram service = ctx.getBean("setModeServiceImpl", FMSetModeOfProgram.class);
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSetModeOfOrderDao() {
        assertTrue(service.setModeOfOrderDao(1) instanceof FMOrderDaoProdImpl);
        assertTrue(service.setModeOfOrderDao(2) instanceof FMOrderDaoTrainingImpl);
    }
    
    @Test
    public void testSetModeOfOrderNumDao() {
        assertTrue(service.setModeOfOrderNumDao(1) instanceof FMOrderNumFileImpl);
        assertTrue(service.setModeOfOrderNumDao(2) instanceof FMOrderNumFileImpl);
    }
}
