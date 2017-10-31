/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.exceptions.FMBlankDateFileException;
import com.sg.flooringmastery.exceptions.FMInvalidDateException;
import com.sg.flooringmastery.exceptions.FMInvalidOrderNumException;
import com.sg.flooringmastery.exceptions.FMPersistenceException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
public class FMOrderServiceTest {
    
    ApplicationContext ctx =
            new ClassPathXmlApplicationContext("applicationContext.xml");
    FMOrderService service = ctx.getBean("orderServiceImpl", FMOrderService.class);
    
    LocalDate testDate = LocalDate.parse("2013-06-01");
    
    @Before
    public void setUp() {
        setUpTestFile();
    }
    
    @After
    public void tearDown() {
    }
    
    //Pass-through test methods
    
    @Test
    public void testAddGetOrder() throws Exception {
        Order order1 = createTestArr().get(0);
        service.addOrder(order1);
        
        Order fromDao = service.getOrderByOrderNum(1);
        
        assertTrue(order1.getCustomerName().equals(fromDao.getCustomerName()));
        //assert with all other fields
        //addOrder for dates in the past and future that do and do not exist
        //addOrder for today when file does and does not exist
    }
    
    @Test
    public void testRemoveOrder() throws Exception {
        Order order1 = createTestArr().get(0);
        service.addOrder(order1);
        
        Order order2 = createTestArr().get(1);
        service.addOrder(order2);
        
        service.removeOrder(order1);
        assertEquals(1, service.getOrdersForOneDay(order1.getOrderDate()).size());
        try {
            service.getOrderByOrderNum(1);
            fail("Expected FMInvalidOrderNumException was not thrown");
        } catch (FMInvalidOrderNumException ex) {
            //continue testing...
        }
        
        service.removeOrder(order2);
        try {
            service.getOrdersForOneDay(order2.getOrderDate()).size();
            fail("Expected FMBlankDateFileException was not thrown");
        } catch (FMBlankDateFileException ex) {
            //continue testing...
        }
        try {
            service.getOrderByOrderNum(2);
            fail("Expected FMInvalidOrderNumException was not thrown");
        } catch (FMInvalidOrderNumException ex) {
            //continue testing...
        }
    }
    
    @Test
    public void testEditOrder() throws Exception {
        Order order1 = createTestArr().get(0);
        service.addOrder(order1);
        
        Order updatedOrder1 = service.getOrderByOrderNum(order1.getOrderNum());
        updatedOrder1.setCustomerName("McSteen");
        
        service.editOrder(updatedOrder1);
        
        Order fromDao = service.getOrderByOrderNum(1);
        
        assertTrue(updatedOrder1.getCustomerName().equals(fromDao.getCustomerName()));
        //assert with all other fields
        //editOrder for dates in the past and future that do and do not exist
        //editOrder for today when file does and does not exist
    }

    @Test
    public void testGetOrdersForOneDay() throws Exception {
        //test method's ability to try-catch FMBlankDateFileException
        try {
            service.getOrdersForOneDay(testDate);
            fail("Expected FMBlankDateFileException was not thrown");
        } catch (FMBlankDateFileException ex) {
            //continue testing...
        }
        
        //test method's ability to try-catch FMInvalidDateException
        try {
            service.getOrdersForOneDay(LocalDate.parse("9999-09-30"));
            fail("Expected FMInvalidDateException was not thrown");
        } catch (FMInvalidDateException ex) {
            //continue testing...
        }
        
        Order order1 = createTestArr().get(0);
        service.addOrder(order1);
        
        Order order2 = createTestArr().get(1);
        service.addOrder(order2);
        
        List<Order> orders = service.getOrdersForOneDay(testDate);
        
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
        assertFalse(service.doesDateExist(LocalDate.parse("9999-09-30")));
        assertTrue(service.doesDateExist(testDate));
    }
    
    @Test
    public void testDoOrdersExistForDate() throws Exception {
        assertFalse(service.doOrdersExistForDate(testDate));
        
        Order order1 = createTestArr().get(0);
        service.addOrder(order1);
        assertTrue(service.doOrdersExistForDate(testDate));
    }
    
    @Test
    public void testDoesOrderExist() throws Exception {
        assertFalse(service.doesOrderExist(999));
        
        Order order1 = createTestArr().get(0);
        service.addOrder(order1);
        assertTrue(service.doesOrderExist(1));
    }
    
    //Business Logic test methods
    
    @Test
    public void testCalculateMaterialCost() throws Exception {
        Order order1 = createTestArr().get(0);
        service.addOrder(order1);
        
        Order order2 = createTestArr().get(1);
        service.addOrder(order2);
        
        assertTrue(0 == order1.getTotalMaterialCost().compareTo(service.calculateMaterialCost(order1)));
        assertTrue(0 == order2.getTotalMaterialCost().compareTo(service.calculateMaterialCost(order2)));
    }

    @Test
    public void testCalculateLaborCost() throws Exception {
        Order order1 = createTestArr().get(0);
        service.addOrder(order1);
        
        Order order2 = createTestArr().get(1);
        service.addOrder(order2);
        
        assertTrue(0 == order1.getTotalLaborCost().compareTo(service.calculateLaborCost(order1)));
        assertTrue(0 == order2.getTotalLaborCost().compareTo(service.calculateLaborCost(order2)));
    }

    @Test
    public void testCalculatePreTaxTotal() throws Exception {
        Order order1 = createTestArr().get(0);
        service.addOrder(order1);
        
        Order order2 = createTestArr().get(1);
        service.addOrder(order2);
        
        assertTrue(0 == new BigDecimal("990.00").compareTo(service.calculatePreTaxTotal(order1)));
        assertTrue(0 == new BigDecimal("765.00").compareTo(service.calculatePreTaxTotal(order2)));
    }

    @Test
    public void testCalculateTax() throws Exception {
        Order order1 = createTestArr().get(0);
        service.addOrder(order1);
        
        Order order2 = createTestArr().get(1);
        service.addOrder(order2);
        
        BigDecimal preTaxTotal1 = service.calculatePreTaxTotal(order1);
        BigDecimal preTaxTotal2 = service.calculatePreTaxTotal(order2);
        
        BigDecimal totalTax1 = service.calculateTax(preTaxTotal1, order1);
        BigDecimal totalTax2 = service.calculateTax(preTaxTotal2, order2);
        
        assertTrue(0 == order1.getTotalTax().compareTo(totalTax1));
        assertTrue(0 == order2.getTotalTax().compareTo(totalTax2));
    }

    @Test
    public void testCalculateTotalCost() throws Exception {
        Order order1 = createTestArr().get(0);
        service.addOrder(order1);
        
        Order order2 = createTestArr().get(1);
        service.addOrder(order2);
        
        BigDecimal totalCost1 = service.calculateTotalCost(order1);
        BigDecimal totalCost2 = service.calculateTotalCost(order2);
        
        assertTrue(0 == order1.getTotalCost().compareTo(totalCost1));
        assertTrue(0 == order2.getTotalCost().compareTo(totalCost2));
    }

    @Test
    public void testCalculateTotals() throws Exception {
        Order order1 = createTestArr().get(0);
        service.addOrder(order1);
        
        Order order2 = createTestArr().get(1);
        service.addOrder(order2);
        
        Order order1Test = service.calculateTotals(order1);
        Order order2Test = service.calculateTotals(order2);
        
        assertTrue(0 == order1.getTotalMaterialCost().compareTo(order1Test.getTotalMaterialCost()));
        assertTrue(0 == order1.getTotalLaborCost().compareTo(order1Test.getTotalLaborCost()));
        //assertTrue/Equals for all other fields
        
        assertTrue(0 == order2.getTotalMaterialCost().compareTo(order2Test.getTotalMaterialCost()));
        assertTrue(0 == order2.getTotalLaborCost().compareTo(order2Test.getTotalLaborCost()));
        //assertTrue/Equals for all other fields
    }
    
    //Set up methods
    
    private void setUpTestFile() {
        try {
            List<Order> orders = service.getOrdersForOneDay(testDate);
            for (Order order : orders) {
                service.removeOrder(order);
            }
        } catch (FMPersistenceException |
                FMBlankDateFileException |
                FMInvalidDateException ex) {
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
