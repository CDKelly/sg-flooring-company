/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.exceptions.FMPersistenceException;
import java.util.List;

/**
 *
 * @author connor
 */
public interface FMProductService {
    
    public Product addProduct(Product product)
            throws FMPersistenceException;
    
    public Product editProduct(Product product)
            throws FMPersistenceException;
    
    public Product removeProduct(Product product)
            throws FMPersistenceException;
    
    public Product getProduct(String productType)
            throws FMPersistenceException;
    
    public List<Product> getAllProducts()
            throws FMPersistenceException;
}
