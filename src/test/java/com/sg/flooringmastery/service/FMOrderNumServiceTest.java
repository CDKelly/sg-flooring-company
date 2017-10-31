/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

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
public class FMOrderNumServiceTest {
    
    ApplicationContext ctx =
            new ClassPathXmlApplicationContext("applicationContext.xml");
    FMOrderNumService service = ctx.getBean("orderNumServiceImpl", FMOrderNumService.class);
    
    @Before
    public void setUp() {
        setUpTestFile();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testGetAndSetNewMaxOrderNum() throws Exception {
        assertEquals((Integer) 1, service.getOrderNum().getOrderNum());
        
        service.setNewMaxOrderNum();
        assertEquals((Integer) 2, service.getOrderNum().getOrderNum());
        
        service.setNewMaxOrderNum();
        assertEquals((Integer) 3, service.getOrderNum().getOrderNum());
    }
    
    public void setUpTestFile() {
        try {
            service.setOrderNum(new OrderNum(0));
            service.setNewMaxOrderNum();
        } catch (FMPersistenceException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
