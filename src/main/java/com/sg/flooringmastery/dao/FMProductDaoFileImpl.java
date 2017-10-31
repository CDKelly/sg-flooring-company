/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.exceptions.FMPersistenceException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 *
 * @author connor
 */
public class FMProductDaoFileImpl implements FMProductDao {
    
    Map<String, Product> productMap = new HashMap();
    public static String PRODUCTS_FILE;
    public static final String DELIMITER = ",";

    public FMProductDaoFileImpl(String fileName) {
        this.PRODUCTS_FILE = fileName;
    }
    
    @Override
    public Product addProduct(Product product)
            throws FMPersistenceException {
        Product newProduct = productMap.put(product.getProductType(), product);
        writeToFile();
        return newProduct;
    }

    @Override
    public Product editProduct(Product product)
            throws FMPersistenceException {
        Product editedProduct = productMap.put(product.getProductType(), product);
        writeToFile();
        return editedProduct;
    }

    @Override
    public Product removeProduct(Product product)
            throws FMPersistenceException {
        Product removedProduct = productMap.remove(product.getProductType());
        writeToFile();
        return removedProduct;
    }

    @Override
    public Product getProduct(String productType)
            throws FMPersistenceException {
        readFromFile();
        return productMap.get(productType);
    }

    @Override
    public List<Product> getAllProducts()
            throws FMPersistenceException {
        readFromFile();
        return new ArrayList<>(productMap.values());
    }
    
    public void readFromFile()
            throws FMPersistenceException {
        Scanner scanner;
        
        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(PRODUCTS_FILE)));
        } catch (FileNotFoundException e) {
            throw new FMPersistenceException (
                    "Could not load data into memory.", e);
        }
        
        //try-catch is for testing purposes to prevent getAllProducts from
        //not being able to find the first line of productsTest.txt, which
        //occurs during setUp
        String currentLine;
        try {
            currentLine = scanner.nextLine();
        } catch (NoSuchElementException ex) {
            //do nothing...
        }
            
        String[] currentTokens;

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTokens = currentLine.split(DELIMITER);

            Product currProduct = new Product(currentTokens[0]);
            currProduct.setCostPerSqFt(new BigDecimal(currentTokens[1]));
            currProduct.setLaborCostPerSqFt(new BigDecimal(currentTokens[2]));

            productMap.put(currProduct.getProductType(), currProduct);
        }
        scanner.close();
    }
    
    public void writeToFile() 
            throws FMPersistenceException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(PRODUCTS_FILE));
        } catch (IOException e) {
            throw new FMPersistenceException(
                    "Could not save orderNum data.", e);
        }
        
        List<Product> productsList = this.getAllProducts();
        out.println("ProductType, CostPerSquareFoot, LaborCostPerSquareFoot");
        for (Product currProduct : productsList) {
            out.println(currProduct.getProductType() + DELIMITER
                    + currProduct.getCostPerSqFt().toString() + DELIMITER
                    + currProduct.getLaborCostPerSqFt().toString());
            out.flush();
        }
        out.close();
    }
}
