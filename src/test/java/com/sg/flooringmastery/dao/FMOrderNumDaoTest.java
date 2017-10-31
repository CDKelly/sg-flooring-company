/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.OrderNum;
import com.sg.flooringmastery.exceptions.FMPersistenceException;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author connor
 */
public class FMOrderNumDaoTest {
    
    ApplicationContext ctx =
            new ClassPathXmlApplicationContext("applicationContext.xml");
    FMOrderNumDao dao = ctx.getBean("orderNumTrainingImpl", FMOrderNumDao.class);
    
    @Before
    public void setUp() {
        setUpTestFile();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testGetAndSetNewMaxOrderNum() throws Exception {
        assertEquals((Integer) 1, dao.getOrderNum().getOrderNum());
        
        dao.setNewMaxOrderNum();
        assertEquals((Integer) 2, dao.getOrderNum().getOrderNum());
        
        dao.setNewMaxOrderNum();
        assertEquals((Integer) 3, dao.getOrderNum().getOrderNum());
    }
    
    public void setUpTestFile() {
        try {
            dao.setOrderNum(new OrderNum(0));
            dao.setNewMaxOrderNum();
        } catch (FMPersistenceException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
