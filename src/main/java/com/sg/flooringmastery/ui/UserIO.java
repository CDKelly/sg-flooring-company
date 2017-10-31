/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author connor
 */
public interface UserIO {
    void print(String message);
    
    void println(String message);
    
    void tabPrintLn(String message);

    double readDouble(String prompt);

    double readDouble(String prompt, double min, double max);

    float readFloat(String prompt);

    float readFloat(String prompt, float min, float max);

    int readInt(String prompt);

    int readInt(String prompt, int min, int max);
    
    Integer readInt(String prompt, ArrayList<Integer> ints);
    
    int readInt(String promp, int min, int max, boolean acceptsNullInput);

    long readLong(String prompt);

    long readLong(String prompt, long min, long max);

    BigDecimal readBigDecimal(String prompt);

    BigDecimal readBigDecimal(String prompt, boolean acceptsNullInput);
    
    String readString(String prompt, boolean acceptsNullInput);
    
    String readString(String prompt, String choice1, String choice2);
    
    String readString(String prompt, ArrayList<String> strings, boolean acceptsNullInput);
    
    LocalDate readLocalDate(String prompt, String format);
    
    LocalDate readLocalDate(String prompt, String format,
            boolean acceptsNullInput);
}
