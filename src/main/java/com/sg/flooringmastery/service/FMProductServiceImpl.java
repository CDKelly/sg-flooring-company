/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FMProductDao;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.exceptions.FMPersistenceException;
import java.util.List;

/**
 *
 * @author connor
 */
public class FMProductServiceImpl implements FMProductService {
    
    private FMProductDao dao;

    public FMProductServiceImpl(FMProductDao dao) {
        this.dao = dao;
    }
    
    @Override
    public Product addProduct(Product product)
            throws FMPersistenceException {
        return dao.addProduct(product);
    }

    @Override
    public Product editProduct(Product product)
            throws FMPersistenceException {
        return dao.editProduct(product);
    }

    @Override
    public Product removeProduct(Product product)
            throws FMPersistenceException {
        return dao.removeProduct(product);
    }

    @Override
    public Product getProduct(String productType)
            throws FMPersistenceException {
        return dao.getProduct(productType);
    }

    @Override
    public List<Product> getAllProducts()
            throws FMPersistenceException {
        return dao.getAllProducts();
    }
}
