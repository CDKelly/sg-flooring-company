/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.exceptions.FMPersistenceException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author connor
 */
public class FMOrderDaoTest {
    
    ApplicationContext ctx =
            new ClassPathXmlApplicationContext("applicationContext.xml");
    FMOrderDao dao = ctx.getBean("orderDaoProdImpl", FMOrderDao.class);
    
    LocalDate testDate = LocalDate.parse("2013-06-01");
    
    @Before
    public void setUp() {
        setUpTestFile();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testAddGetOrder() throws Exception {
        Order order1 = createTestArr().get(0);
        dao.addOrder(order1);
        
        Order fromDao = dao.getOrderByOrderNum(1);
        
        assertTrue(order1.getCustomerName().equals(fromDao.getCustomerName()));
        //assert with all other fields
        //addOrder for dates in the past and future that do and do not exist
        //addOrder for today when file does and does not exist
    }
    
    @Test
    public void testRemoveOrder() throws Exception {
        Order order1 = createTestArr().get(0);
        dao.addOrder(order1);
        
        Order order2 = createTestArr().get(1);
        dao.addOrder(order2);
        
        dao.removeOrder(order1);
        assertEquals(1, dao.getOrdersForOneDay(order1.getOrderDate()).size());
        assertNull(dao.getOrderByOrderNum(1));
        
        dao.removeOrder(order2);
        assertEquals(0, dao.getOrdersForOneDay(order2.getOrderDate()).size());
        assertNull(dao.getOrderByOrderNum(2));
    }
    
    @Test
    public void testEditOrder() throws Exception {
        Order order1 = createTestArr().get(0);
        dao.addOrder(order1);
        
        Order updatedOrder1 = dao.getOrderByOrderNum(order1.getOrderNum());
        updatedOrder1.setCustomerName("McSteen");
        
        dao.editOrder(updatedOrder1);
        
        Order fromDao = dao.getOrderByOrderNum(1);
        
        assertTrue(updatedOrder1.getCustomerName().equals(fromDao.getCustomerName()));
        //assert with all other fields
        //editOrder for dates in the past and future that do and do not exist
        //editOrder for today when file does and does not exist
    }
    
    @Test
    public void testGetOrdersForOneDay() throws Exception {
        
        Order order1 = createTestArr().get(0);
        dao.addOrder(order1);
        
        Order order2 = createTestArr().get(1);
        dao.addOrder(order2);
        
        List<Order> orders = dao.getOrdersForOneDay(testDate);
        
        assertEquals(2, orders.size());
        
        assertEquals((Integer) 1, orders.get(0).getOrderNum());
        assertEquals("Kelly", orders.get(0).getCustomerName());
        assertEquals("OH", orders.get(0).getState());
        assertTrue(0 == new BigDecimal("6.25").compareTo(orders.get(0).getTaxRate()));
        assertEquals("Wood", orders.get(0).getProductType());
        assertTrue(0 == new BigDecimal("100.00").compareTo(orders.get(0).getArea()));
        assertTrue(0 == new BigDecimal("5.15").compareTo(orders.get(0).getCostPerSqFt()));
        assertTrue(0 == new BigDecimal("4.75").compareTo(orders.get(0).getLaborCostPerSqFt()));
        assertTrue(0 == new BigDecimal("515.00").compareTo(orders.get(0).getTotalMaterialCost()));
        assertTrue(0 == new BigDecimal("475.00").compareTo(orders.get(0).getTotalLaborCost()));
        assertTrue(0 == new BigDecimal("61.88").compareTo(orders.get(0).getTotalTax()));
        assertTrue(0 == new BigDecimal("1051.88").compareTo(orders.get(0).getTotalCost()));
    }
    
    @Test
    public void testDoesDateExist() throws Exception {
        assertFalse(dao.doesDateExist(LocalDate.parse("9999-09-30")));
        assertTrue(dao.doesDateExist(testDate));
    }
    
    private void setUpTestFile() {
        try {
            List<Order> orders = dao.getOrdersForOneDay(testDate);
            for (Order order : orders) {
                dao.removeOrder(order);
            }
        } catch (FMPersistenceException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private List<Order> createTestArr() {
        List<Order> testArr = new ArrayList<>();
        
        Order order1 = new Order(1);
        order1.setCustomerName("Kelly");
        order1.setState("OH");
        order1.setTaxRate(new BigDecimal("6.25"));
        order1.setProductType("Wood");
        order1.setArea(new BigDecimal("100.00"));
        order1.setCostPerSqFt(new BigDecimal("5.15"));
        order1.setLaborCostPerSqFt(new BigDecimal("4.75"));
        order1.setTotalMaterialCost(new BigDecimal("515.00"));
        order1.setTotalLaborCost(new BigDecimal("475.00"));
        order1.setTotalTax(new BigDecimal("61.88"));
        order1.setTotalCost(new BigDecimal("1051.88"));
        order1.setOrderDate(testDate);
        testArr.add(order1);
        
        Order order2 = new Order(2);
        order2.setCustomerName("McSteen");
        order2.setState("PA");
        order2.setTaxRate(new BigDecimal("6.75"));
        order2.setProductType("Tile");
        order2.setArea(new BigDecimal("100.00"));
        order2.setCostPerSqFt(new BigDecimal("3.50"));
        order2.setLaborCostPerSqFt(new BigDecimal("4.15"));
        order2.setTotalMaterialCost(new BigDecimal("350.00"));
        order2.setTotalLaborCost(new BigDecimal("415.00"));
        order2.setTotalTax(new BigDecimal("51.64"));
        order2.setTotalCost(new BigDecimal("816.64"));
        order2.setOrderDate(testDate);
        testArr.add(order2);
        
        return testArr;
    }
}
