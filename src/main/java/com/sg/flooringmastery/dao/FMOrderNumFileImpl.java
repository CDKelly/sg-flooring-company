/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.OrderNum;
import com.sg.flooringmastery.exceptions.FMPersistenceException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 *
 * @author connor
 */
public class FMOrderNumFileImpl implements FMOrderNumDao {
    
    private OrderNum orderNum;
    public static String ORDER_NUM_FILE;
    public static final String DELIMITER = ",";
    
    public FMOrderNumFileImpl(String fileName) {
        this.ORDER_NUM_FILE = fileName;
    }
    
    @Override
    public OrderNum getOrderNum()
            throws FMPersistenceException {
        readFromFile();
        return orderNum;
    }

    @Override
    public void setOrderNum(OrderNum orderNum)
            throws FMPersistenceException {
        this.orderNum = orderNum;
        writeToFile();
    }
    
    @Override
    public OrderNum setNewMaxOrderNum()
            throws FMPersistenceException {
//        readFromFile();
        //daoMethod().getterFromDTO() + 1
        Integer newMax = getOrderNum().getOrderNum() + 1;
        setOrderNum(new OrderNum(newMax));
        writeToFile();
        
        return orderNum;
    }
    
    public void readFromFile()
            throws FMPersistenceException {
        Scanner scanner;
        
        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(ORDER_NUM_FILE)));
        } catch (FileNotFoundException e) {
            throw new FMPersistenceException (
                    "Could not load data into memory.", e);
        }
        
        String currentLine;
//        if (ORDER_NUM_FILE == "orderNumTest.txt") {
//            try {
//                currentLine = scanner.nextLine();
//            } catch (NoSuchElementException ex) {
//                //do nothing...
//            }
//        }
        
        String[] currentTokens;
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTokens = currentLine.split(DELIMITER);
            //will have to be modified should the OrderNum DTO be modified
            //in the future
            setOrderNum(new OrderNum(Integer.parseInt(currentTokens[0])));
        }
        scanner.close();
    }
    
    //will have to be fleshed out if OrderNum DTO is modified or
    //added to in the future
    public void writeToFile()
            throws FMPersistenceException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(ORDER_NUM_FILE));
        } catch (IOException e) {
            throw new FMPersistenceException(
                    "Could not save orderNum data.", e);
        }

        out.println(Integer.toString(getOrderNum().getOrderNum()));
        out.flush();
        out.close();
    }
    
}
