/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dto.TaxRate;
import com.sg.flooringmastery.exceptions.FMPersistenceException;
import java.util.List;

/**
 *
 * @author connor
 */
public interface FMTaxRateService {
    
    public TaxRate addTaxRate(TaxRate taxRate)
            throws FMPersistenceException;
    
    public TaxRate editTaxRate(TaxRate taxRate)
            throws FMPersistenceException;
    
    public TaxRate removeTaxRate(TaxRate taxRate)
            throws FMPersistenceException;
    
    public TaxRate getTaxRate(String state)
            throws FMPersistenceException;
    
    public List<TaxRate> getAllTaxRates()
            throws FMPersistenceException;
}
