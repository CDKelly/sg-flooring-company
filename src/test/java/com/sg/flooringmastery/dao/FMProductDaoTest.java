/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
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
public class FMProductDaoTest {
    
    ApplicationContext ctx =
            new ClassPathXmlApplicationContext("applicationContext.xml");
    FMProductDao dao = ctx.getBean("productDaoFileImpl", FMProductDao.class);
    
    @Before
    public void setUp() {
        setUpTestFile();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testAddGetProduct() throws Exception {
        Product product1 = createTestArr().get(0);
        
        dao.addProduct(product1);
        Product fromDao = dao.getProduct(product1.getProductType());
        
        assertEquals(product1.getProductType(), fromDao.getProductType());
        assertEquals(0, product1.getCostPerSqFt().compareTo(fromDao.getCostPerSqFt()));
        assertEquals(0, product1.getLaborCostPerSqFt().compareTo(fromDao.getLaborCostPerSqFt()));
    }
    
    @Test
    public void testEditProduct() throws Exception {
        Product product1 = createTestArr().get(0);
        dao.addProduct(product1);
        
        Product updatedProduct = dao.getProduct("x");
        updatedProduct.setCostPerSqFt(new BigDecimal("2.75"));
        updatedProduct.setLaborCostPerSqFt(new BigDecimal("4.50"));
        dao.editProduct(updatedProduct);
        
        Product fromDao = dao.getProduct(updatedProduct.getProductType());
        
        assertTrue(fromDao.getProductType().equals("x"));
        assertEquals(0, fromDao.getCostPerSqFt().compareTo(new BigDecimal("2.75")));
        assertEquals(0, fromDao.getLaborCostPerSqFt().compareTo(new BigDecimal("4.50")));
    }
    
    @Test
    public void testRemoveProduct() throws Exception {
        Product product1 = createTestArr().get(0);
        dao.addProduct(product1);
        
        Product product2 = createTestArr().get(1);
        dao.addProduct(product2);
        
        dao.removeProduct(product1);
        assertEquals(1, dao.getAllProducts().size());
        assertNull(dao.getProduct(product1.getProductType()));
        
        dao.removeProduct(product2);
        assertEquals(0, dao.getAllProducts().size());
        assertNull(dao.getProduct(product2.getProductType()));
    }
    
    @Test
    public void testGetAllProducts() throws Exception {
        Product product1 = createTestArr().get(0);
        dao.addProduct(product1);
        
        Product product2 = createTestArr().get(1);
        dao.addProduct(product2);
        
        assertEquals(2, dao.getAllProducts().size());
        assertEquals("x", dao.getAllProducts().get(0).getProductType());
        assertEquals("y", dao.getAllProducts().get(1).getProductType());
    }
    
    private void setUpTestFile() {
        try {
            List<Product> products = dao.getAllProducts();
            for (Product product : products) {
                dao.removeProduct(product);
            }
        } catch (FMPersistenceException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private List<Product> createTestArr() {
        List<Product> testArr = new ArrayList<>();
        
        Product product1 = new Product("x");
        product1.setCostPerSqFt(new BigDecimal("2.50"));
        product1.setLaborCostPerSqFt(new BigDecimal("4.25"));
        testArr.add(product1);
        
        Product product2 = new Product("y");
        product2.setCostPerSqFt(new BigDecimal("3.50"));
        product2.setLaborCostPerSqFt(new BigDecimal("5.25"));
        testArr.add(product2);
        
        return testArr;
    }
    
}
