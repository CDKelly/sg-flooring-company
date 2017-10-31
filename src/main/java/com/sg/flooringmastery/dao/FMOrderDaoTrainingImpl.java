/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import static com.sg.flooringmastery.dao.FMOrderDaoProdImpl.DIRECTORY;
import static com.sg.flooringmastery.dao.FMOrderDaoProdImpl.FILESTART;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.OrderNum;
import com.sg.flooringmastery.exceptions.FMInvalidDateException;
import com.sg.flooringmastery.exceptions.FMPersistenceException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author connor
 */
public class FMOrderDaoTrainingImpl implements FMOrderDao {

    Map<Integer, Order> orderMap = new HashMap();
    public static String DIRECTORY;
    public static final String FILESTART = "Orders_";
    public static final String DELIMITER = ",";
    
    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMddyyyy");
    
    public FMOrderDaoTrainingImpl(String directory) {
        this.DIRECTORY = directory;
    }

    @Override
    public Order addOrder(Order order)
            throws FMPersistenceException {
        saveDataAndClearHM();
        
        if (doesDateExist(order.getOrderDate())) {
            readFromFile(order.getOrderDate());
            orderMap.put(order.getOrderNum(), order);
        } else {
            orderMap.put(order.getOrderNum(), order);
        }
        
        writeToFile(order.getOrderDate());
        return order;
    }

    @Override
    public Order removeOrder(Order order)
            throws FMPersistenceException {
        saveDataAndClearHM();
        
        readFromFile(order.getOrderDate());
        
        orderMap.remove(order.getOrderNum());
        writeToFile(order.getOrderDate());
        
        return order;
    }

    @Override
    public Order editOrder(Order order)
            throws FMPersistenceException {
        if (orderMap.size() == 1 && doesDateExist(order.getOrderDate())) {
            orderMap.remove(order);
            writeToFile(order.getOrderDate());
        }
        saveDataAndClearHM();
        
        if (doesDateExist(order.getOrderDate())) {
            readFromFile(order.getOrderDate());
            orderMap.put(order.getOrderNum(), order);
        } else {
            orderMap.put(order.getOrderNum(), order);
        }
        
        writeToFile(order.getOrderDate());
        return order;
    }

    @Override
    public List<Order> getOrdersForOneDay(LocalDate date)
            throws FMPersistenceException {
        readFromFile(date);
        return new ArrayList<>(orderMap.values());
    }
    
    @Override
    public Order getOrderByOrderNum(Integer orderNum)
            throws FMPersistenceException, IndexOutOfBoundsException {
        //throws both exceptions
        readFromFile(orderMap.values().stream()
                    .collect(Collectors.toList())
                    .get(0)
                    .getOrderDate());
        
        return orderMap.get(orderNum);
    }
    
    @Override
    public boolean doesDateExist(LocalDate date)
            throws FMPersistenceException {
        String searchQuery = DIRECTORY + FILESTART + date.format(dateFormat) + ".txt";
        File file = new File(searchQuery);
        
        return file.exists();
    }
    
    //create doesOrderExist methods that parallel
    //the respective methods in the service
    
    private void saveDataAndClearHM() throws FMPersistenceException {
        if (!orderMap.isEmpty()) {
            writeToFile(orderMap.values().stream()
                    .collect(Collectors.toList())
                    .get(0)
                    .getOrderDate());
            orderMap.clear();
        }
    }
    
    private void readFromFile(LocalDate date)
            throws FMPersistenceException {
        Scanner scanner;
        
        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(DIRECTORY + FILESTART
                                    + date.format(dateFormat) + ".txt")));
        } catch (FileNotFoundException e) {
            throw new FMPersistenceException (
                    "Could not load data into memory.", e);
        }
        
        //try-catch is for testing purposes to prevent getOrdersForOneDay from
        //not being able to find the first line of Orders_06012013.txt, which
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

            Order currOrder =
                    new Order(Integer.parseInt(currentTokens[0]));
            currOrder.setCustomerName(currentTokens[1]);
            currOrder.setState(currentTokens[2]);
            currOrder.setTaxRate(new BigDecimal(currentTokens[3]));
            currOrder.setProductType(currentTokens[4]);
            currOrder.setArea(new BigDecimal(currentTokens[5]));
            currOrder.setCostPerSqFt(new BigDecimal(currentTokens[6]));
            currOrder.setLaborCostPerSqFt(new BigDecimal(currentTokens[7]));
            currOrder.setTotalMaterialCost(new BigDecimal(currentTokens[8]));
            currOrder.setTotalLaborCost(new BigDecimal(currentTokens[9]));
            currOrder.setTotalTax(new BigDecimal(currentTokens[10]));
            currOrder.setTotalCost(new BigDecimal(currentTokens[11]));
            
            currOrder.setOrderDate(date);
            
            orderMap.put(currOrder.getOrderNum(), currOrder);
        }
        scanner.close();
    }
    
    private void writeToFile(LocalDate date)
            throws FMPersistenceException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(DIRECTORY + FILESTART
                                    + date.format(dateFormat) + ".txt"));
        } catch (IOException e) {
            throw new FMPersistenceException(
                    "Could not save orderNum data.", e);
        }
        
        List<Order> orders = this.getOrdersForOneDay(date);
        out.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,"
                + "CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,"
                + "LaborCost,Tax,Total");
        for (Order currOrder : orders) {
            out.println(currOrder.getOrderNum() + DELIMITER
                    + currOrder.getCustomerName() + DELIMITER
                    + currOrder.getState() + DELIMITER
                    + currOrder.getTaxRate().toString() + DELIMITER
                    + currOrder.getProductType() + DELIMITER
                    + currOrder.getArea().toString() + DELIMITER
                    + currOrder.getCostPerSqFt().toString() + DELIMITER
                    + currOrder.getLaborCostPerSqFt().toString() + DELIMITER
                    + currOrder.getTotalMaterialCost().toString() + DELIMITER
                    + currOrder.getTotalLaborCost().toString() + DELIMITER
                    + currOrder.getTotalTax().toString() + DELIMITER
                    + currOrder.getTotalCost().toString());
            out.flush();
        }
        out.close();
    }
    
}
