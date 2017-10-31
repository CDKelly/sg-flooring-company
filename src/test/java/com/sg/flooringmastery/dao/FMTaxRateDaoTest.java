/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.TaxRate;
import com.sg.flooringmastery.exceptions.FMPersistenceException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author connor
 */
public class FMTaxRateDaoTest {
    
    ApplicationContext ctx =
            new ClassPathXmlApplicationContext("applicationContext.xml");
    FMTaxRateDao dao = ctx.getBean("taxRateDaoFileImpl", FMTaxRateDao.class);
    
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
        
        dao.addTaxRate(taxRate1);
        TaxRate fromDao = dao.getTaxRate(taxRate1.getState());
        
        assertEquals(taxRate1.getState(), fromDao.getState());
        assertEquals(0, taxRate1.getTaxRate().compareTo(fromDao.getTaxRate()));
    }
    
    @Test
    public void testEditTaxRate() throws Exception {
        TaxRate taxRate1 = createTestArr().get(0);
        dao.addTaxRate(taxRate1);
        
        TaxRate updatedTaxRate = dao.getTaxRate("AA");
        updatedTaxRate.setState("FF");
        updatedTaxRate.setTaxRate(new BigDecimal("6.75"));
        dao.editTaxRate(updatedTaxRate);
        
        TaxRate fromDao = dao.getTaxRate(updatedTaxRate.getState());
        
        assertTrue(fromDao.getState().equals("FF"));
        assertEquals(0, fromDao.getTaxRate().compareTo(new BigDecimal("6.75")));
    }
    
    @Test
    public void testRemoveProduct() throws Exception {
        TaxRate taxRate1 = createTestArr().get(0);
        dao.addTaxRate(taxRate1);
        
        TaxRate taxRate2 = createTestArr().get(1);
        dao.addTaxRate(taxRate2);
        
        dao.removeTaxRate(taxRate1);
        assertEquals(1, dao.getAllTaxRates().size());
        assertNull(dao.getTaxRate(taxRate1.getState()));
        
        dao.removeTaxRate(taxRate2);
        assertEquals(0, dao.getAllTaxRates().size());
        assertNull(dao.getTaxRate(taxRate2.getState()));
    }
    
    @Test
    public void testGetAllTaxRates() throws Exception {
        TaxRate taxRate1 = createTestArr().get(0);
        dao.addTaxRate(taxRate1);
        
        TaxRate taxRate2 = createTestArr().get(1);
        dao.addTaxRate(taxRate2);
        
        assertEquals(2, dao.getAllTaxRates().size());
        assertEquals("AA", dao.getAllTaxRates().get(0).getState());
        assertEquals("BB", dao.getAllTaxRates().get(1).getState());
    }

    private void setUpTestFile() {
        try {
            List<TaxRate> taxes = dao.getAllTaxRates();
            for (TaxRate tax : taxes) {
                dao.removeTaxRate(tax);
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
