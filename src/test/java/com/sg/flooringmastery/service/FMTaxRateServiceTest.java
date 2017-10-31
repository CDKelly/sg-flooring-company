/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dto.TaxRate;
import com.sg.flooringmastery.exceptions.FMPersistenceException;
import java.math.BigDecimal;
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
public class FMTaxRateServiceTest {
    
    ApplicationContext ctx =
            new ClassPathXmlApplicationContext("applicationContext.xml");
    FMTaxRateService service = ctx.getBean("taxRateServiceImpl", FMTaxRateService.class);
    
    @Before
    public void setUp() {
        setUpTestFile();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testAddGetMethod() throws Exception {
        TaxRate taxRate1 = createTestArr().get(0);
        
        service.addTaxRate(taxRate1);
        TaxRate fromDao = service.getTaxRate(taxRate1.getState());
        
        assertEquals(taxRate1.getState(), fromDao.getState());
        assertEquals(0, taxRate1.getTaxRate().compareTo(fromDao.getTaxRate()));
    }
    
    @Test
    public void testEditTaxRate() throws Exception {
        TaxRate taxRate1 = createTestArr().get(0);
        service.addTaxRate(taxRate1);
        
        TaxRate updatedTaxRate = service.getTaxRate("AA");
        updatedTaxRate.setState("FF");
        updatedTaxRate.setTaxRate(new BigDecimal("6.75"));
        service.editTaxRate(updatedTaxRate);
        
        TaxRate fromDao = service.getTaxRate(updatedTaxRate.getState());
        
        assertTrue(fromDao.getState().equals("FF"));
        assertEquals(0, fromDao.getTaxRate().compareTo(new BigDecimal("6.75")));
    }
    
    @Test
    public void testRemoveProduct() throws Exception {
        TaxRate taxRate1 = createTestArr().get(0);
        service.addTaxRate(taxRate1);
        
        TaxRate taxRate2 = createTestArr().get(1);
        service.addTaxRate(taxRate2);
        
        service.removeTaxRate(taxRate1);
        assertEquals(1, service.getAllTaxRates().size());
        assertNull(service.getTaxRate(taxRate1.getState()));
        
        service.removeTaxRate(taxRate2);
        assertEquals(0, service.getAllTaxRates().size());
        assertNull(service.getTaxRate(taxRate2.getState()));
    }
    
    @Test
    public void testGetAllTaxRates() throws Exception {
        TaxRate taxRate1 = createTestArr().get(0);
        service.addTaxRate(taxRate1);
        
        TaxRate taxRate2 = createTestArr().get(1);
        service.addTaxRate(taxRate2);
        
        assertEquals(2, service.getAllTaxRates().size());
        assertEquals("AA", service.getAllTaxRates().get(0).getState());
        assertEquals("BB", service.getAllTaxRates().get(1).getState());
    }

    private void setUpTestFile() {
        try {
            List<TaxRate> taxes = service.getAllTaxRates();
            for (TaxRate tax : taxes) {
                service.removeTaxRate(tax);
            }
        } catch (FMPersistenceException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private List<TaxRate> createTestArr() {
        List<TaxRate> testArr = new ArrayList<>();
        
        TaxRate taxRate1 = new TaxRate("AA", new BigDecimal("6.50"));
        testArr.add(taxRate1);
        
        TaxRate taxRate2 = new TaxRate("BB", new BigDecimal("7.50"));
        testArr.add(taxRate2);
        
        return testArr;
    }
}
