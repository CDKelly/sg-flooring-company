/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FMTaxRateDao;
import com.sg.flooringmastery.dto.TaxRate;
import com.sg.flooringmastery.exceptions.FMPersistenceException;
import java.util.List;

/**
 *
 * @author connor
 */
public class FMTaxRateServiceImpl implements FMTaxRateService {
    
    private FMTaxRateDao dao;

    public FMTaxRateServiceImpl(FMTaxRateDao dao) {
        this.dao = dao;
    }
    
    @Override
    public TaxRate addTaxRate(TaxRate taxRate)
            throws FMPersistenceException {
        return dao.addTaxRate(taxRate);
    }

    @Override
    public TaxRate editTaxRate(TaxRate taxRate)
            throws FMPersistenceException {
        return dao.editTaxRate(taxRate);
    }

    @Override
    public TaxRate removeTaxRate(TaxRate taxRate)
            throws FMPersistenceException {
        return dao.removeTaxRate(taxRate);
    }

    @Override
    public TaxRate getTaxRate(String state)
            throws FMPersistenceException {
        return dao.getTaxRate(state);
    }

    @Override
    public List<TaxRate> getAllTaxRates()
            throws FMPersistenceException {
        return dao.getAllTaxRates();
    }
}
