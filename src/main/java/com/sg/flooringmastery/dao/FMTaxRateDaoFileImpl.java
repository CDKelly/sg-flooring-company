/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.TaxRate;
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
public class FMTaxRateDaoFileImpl implements FMTaxRateDao {
    
    Map<String, TaxRate> taxMap = new HashMap();
    public static String TAX_RATES_FILE;
    public static final String DELIMITER = ",";
    
    public FMTaxRateDaoFileImpl(String fileName) {
        this.TAX_RATES_FILE = fileName;
    }

    @Override
    public TaxRate addTaxRate(TaxRate taxRate)
            throws FMPersistenceException {
        TaxRate newTaxRate = taxMap.put(taxRate.getState(), taxRate);
        writeToFile();
        return newTaxRate;
    }

    @Override
    public TaxRate editTaxRate(TaxRate taxRate)
            throws FMPersistenceException {
        TaxRate editedTaxRate = taxMap.put(taxRate.getState(), taxRate);
        writeToFile();
        return editedTaxRate;
    }

    @Override
    public TaxRate removeTaxRate(TaxRate taxRate)
            throws FMPersistenceException {
        TaxRate removedTaxRate = taxMap.remove(taxRate.getState());
        writeToFile();
        return removedTaxRate;
    }

    @Override
    public TaxRate getTaxRate(String state)
            throws FMPersistenceException {
        readFromFile();
        return taxMap.get(state);
    }

    @Override
    public List<TaxRate> getAllTaxRates()
            throws FMPersistenceException {
        readFromFile();
        return new ArrayList<>(taxMap.values());
    }
    
    public void readFromFile()
            throws FMPersistenceException {
        Scanner scanner;
        
        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(TAX_RATES_FILE)));
        } catch (FileNotFoundException e) {
            throw new FMPersistenceException (
                    "Could not load data into memory.", e);
        }
        
        //try-catch is for testing purposes to prevent getAllTaxRates from
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

            TaxRate currTaxRate =
                    new TaxRate(currentTokens[0], new BigDecimal(currentTokens[1]));
            
            taxMap.put(currTaxRate.getState(), currTaxRate);
        }
        scanner.close();
    }
    
    public void writeToFile() 
            throws FMPersistenceException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(TAX_RATES_FILE));
        } catch (IOException e) {
            throw new FMPersistenceException(
                    "Could not save orderNum data.", e);
        }
        
        List<TaxRate> taxRatesList = this.getAllTaxRates();
        out.println("State,TaxRate");
        for (TaxRate currTaxRate : taxRatesList) {
            out.println(currTaxRate.getState() + DELIMITER
                    + currTaxRate.getTaxRate().toString());
            out.flush();
        }
        out.close();
    }
}
