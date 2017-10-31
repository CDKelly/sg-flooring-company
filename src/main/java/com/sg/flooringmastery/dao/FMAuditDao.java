/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.exceptions.FMPersistenceException;

/**
 *
 * @author connor
 */
public interface FMAuditDao {
    
    public void writeAuditEntry(String entry)
            throws FMPersistenceException;
}
