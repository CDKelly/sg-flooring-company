/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.TaxRate;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author connor
 */
public class FMView {
    
    private UserIO io;

    public FMView(UserIO io) {
        this.io = io;
    }
    
    public int printMenuAndGetSelection() {
        io.tabPrintLn("=== Main Menu ===");
        io.println("");
        io.println("1. Display Orders by Date");
        io.println("2. Add Order");
        io.println("3. Edit Order");
        io.println("4. Remove Order");
        io.println("5. Save Current Work");
        io.println("");
        io.println("6. Change Mode of Program (Production vs. Training)");
        io.println("7. QUIT PROGRAM");
        io.println("");
        io.tabPrintLn("=================");
        
        return io.readInt("Please select from the above choices:\t", 1, 7);
    }

    public void displayDisplayOrdersBanner() {
        io.tabPrintLn("=== Display Orders by Date ===");
    }

    public LocalDate getUsersDate(int menuSelection) {
        String inputStr = "";
        
        switch (menuSelection) {
            case 1:
                inputStr += "view";
                break;
            case 2:
                inputStr += "add to";
                break;
            case 3:
                inputStr += "edit";
                break;
            case 4:
                inputStr += "remove from";
                break;
            default:
                inputStr += "view";
        }
        
        if (menuSelection == 1 || menuSelection == 2 || menuSelection == 4) {
            return io.readLocalDate("Please enter the date of the order(s) you'd "
                    + "like to " + inputStr, "MM/dd/yyyy", false);
        } else {
            return io.readLocalDate("Please enter the date of the order(s) you'd "
                    + "like to " + inputStr, "MM/dd/yyyy", true);
        }
    }

    public void displayOrders(List<Order> ordersForOneDay) {
        for (Order order : ordersForOneDay) {
            displayOrderInfo(order);
        }
    }
    
    public void displayOrderInfo(Order order) {
        io.println("Order Number: " + order.getOrderNum());
        io.println("Date: " + order.getOrderDate()
                .format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        io.println("Customer Name: " + order.getCustomerName());
        io.println("State: " + order.getState());
        io.println("Tax Rate: " + order.getTaxRate());
        io.println("Product Type: " + order.getProductType());
        io.println("Cost/SqFt.: " + order.getCostPerSqFt());
        io.println("Labor Cost/SqFt.: " + order.getLaborCostPerSqFt());
        io.println("Area: " + order.getArea());
        io.tabPrintLn("=== COSTS ===");
        io.println("TL Material Cost: " + order.getTotalMaterialCost());
        io.println("TL Labor Cost: " + order.getTotalLaborCost());
        io.println("TL Tax: " + order.getTotalTax());
        io.println("TL Cost: " + order.getTotalCost());
        io.println("");
    }
    
    public Integer getUsersOrderNumChoice(ArrayList<Integer> orderNums, int menuSelection) {
        String str1 = "Please select the Order you'd like to ";
        String str2;
        
        switch (menuSelection) {
            case 3:
                str2 = "edit ";
                break;
            case 4:
                str2 = "remove ";
                break;
            default:
                str2 = "edit ";
        }
        String str3 = "by entering\nthe corresponding Order Number from the above "
                + "list:\t";
        return io.readInt(str1 + str2 + str3, orderNums);
    }
    
    public boolean isConfirmed(int menuSelection) {
        String inputStr = "";
        
        switch (menuSelection) {
            case 2:
                inputStr += "add";
                break;
            case 3:
                inputStr += "edit";
                break;
            case 4:
                inputStr += "remove";
                break;
            default:
                inputStr += "add";
        }
        String userConfirmation = io.readString("Are you sure you would like to " + inputStr + " order", "y", "n");
        return userConfirmation.equalsIgnoreCase("y");
    }
    
    public Order getOrderInfo(Order order, List<Product> products,
            List<TaxRate> taxRates, boolean acceptsNullInput) {
        getCustomerName(order, acceptsNullInput);
        displayAndGetTaxRate(order, taxRates, acceptsNullInput);
        displayAndGetProduct(order, products, acceptsNullInput);
        displayAndGetArea(order, acceptsNullInput);
        return order;
    }
    
    public void getCustomerName(Order order, boolean acceptsNullInput) {
        String prompt = "";
        if (acceptsNullInput) {
            io.println("Current Customer Name: " + order.getCustomerName());
            prompt = " (Hit Enter if you would like to NOT make any changes)";
        }
        
        String userInput = io.readString("Customer Name" + prompt + ": ", acceptsNullInput);
        io.println("");

        if (userInput.equals("") && acceptsNullInput) {
            //don't setCustomerName
        } else {
            order.setCustomerName(userInput);
        }
    }
    
    public void displayAndGetTaxRate(Order order, List<TaxRate> taxRates,
            boolean acceptsNullInput) {
        
        for (TaxRate taxRate : taxRates) {
            io.println("State:\t\t" + taxRate.getState());
            io.println("Tax Rate:\t" + taxRate.getTaxRate().toString());
            io.println("");
        }
        io.println("");
        
        String prompt = "";
        if (acceptsNullInput) {
            io.println("Current State/Tax Rate: " + order.getState() + "/" + order.getTaxRate().toString());
            prompt = " (Hit Enter if you would like to NOT make any changes)";
        }
        
        List<String> states = taxRates.stream().map(t -> t.getState())
                .collect(Collectors.toList());
        
        String userInput = io.readString("State Abbrv." + prompt + ": ",
                new ArrayList(states), acceptsNullInput);
        io.println("");
        
        if (userInput.equals("") && acceptsNullInput) {
            //don't setState/TaxRate
        } else {
            BigDecimal userTaxRate = taxRates.stream()
                            .filter(t -> t.getState().equals(userInput))
                            .collect(Collectors.toList())
                            .get(0)
                            .getTaxRate();
            order.setState(userInput);
            order.setTaxRate(userTaxRate);
        }
    }
    
    public void displayAndGetProduct(Order order, List<Product> products,
            boolean acceptsNullInput) {
        for (Product product : products) {
            io.println("Product Type:\t\t" + product.getProductType());
            io.println("Cost/SqFt:\t\t" + product.getCostPerSqFt().toString());
            io.println("Labor Cost/SqFt:\t" + product.getLaborCostPerSqFt().toString());
            io.println("");
        }
        io.println("");
        
        String prompt = "";
        if (acceptsNullInput) {
            io.println("Current Product Type: " + order.getProductType());
            prompt = " (Hit Enter if you would like to NOT make any changes)";
        }
        
        List<String> productTypes = products.stream().map(p -> p.getProductType())
                .collect(Collectors.toList());
        
        String userInput = io.readString("Product Type" + prompt + ": ",
                new ArrayList(productTypes), acceptsNullInput);
        io.println("");
        
        if (userInput.equals("") && acceptsNullInput) {
            //don't setProductType
        } else {
            BigDecimal userCostPerSqFt = products.stream()
                            .filter(p -> p.getProductType().equals(userInput))
                            .collect(Collectors.toList())
                            .get(0)
                            .getCostPerSqFt();
            BigDecimal userLaborCost = products.stream()
                            .filter(p -> p.getProductType().equals(userInput))
                            .collect(Collectors.toList())
                            .get(0)
                            .getLaborCostPerSqFt();
            order.setProductType(userInput);
            order.setCostPerSqFt(userCostPerSqFt);
            order.setLaborCostPerSqFt(userLaborCost);
        }
    }
    
    public void displayAndGetArea(Order order, boolean acceptsNullInput) {
        String prompt = "";
        if (acceptsNullInput) {
            io.println("Current Area: " + order.getArea().toString());
            prompt = " (Hit Enter if you would like to NOT make any changes)";
        }
        
        BigDecimal userInput = io.readBigDecimal("Area" + prompt + ": ", acceptsNullInput);
        io.println("");

        if (userInput == null && acceptsNullInput) {
            //don't setArea
        } else {
            order.setArea(userInput);
        }
    }
    
    public void displayAddBanner() {
        io.tabPrintLn("=== Add Order ===");
    }
    
    public boolean addToToday() {
        String userConfirmation = io.readString("Would you like to add Order "
                + "to today", "y", "n");
        return userConfirmation.equalsIgnoreCase("y");
    }
    
    public boolean createNewOrderFile() {
        String userConfirmation = io.readString("Would you like to create a "
                + "new order for the date you've entered", "y", "n");
        return userConfirmation.equalsIgnoreCase("y");
    }
    
    public void displayAddOrderSuccess() {
        io.tabPrintLn("Order was successfully added to file");
    }
    
    public void displayAddOrderFailure() {
        io.tabPrintLn("Order was NOT added to file");
    }
    
    public void displayEditOrderBanner() {
        io.tabPrintLn("=== Edit Order ===");
    }
    
    public void displayEditOrderSuccess() {
        io.tabPrintLn("Order was successfully edited");
    }
    
    public void displayEditOrderFailure() {
        io.tabPrintLn("Order was NOT edited");
    }
    
    public void displayRemoveOrderBanner() {
        io.tabPrintLn("=== Remove Order ===");
    }
    
    public void displayRemoveOrderSuccess() {
        io.tabPrintLn("Order was successfully removed from file");
    }

    public void displayRemoveOrderFailure() {
        io.tabPrintLn("Order was NOT removed from file");
    }
    
    public void displaySaveBanner() throws InterruptedException {
        io.tabPrintLn("=== Save Current Work ===");
        io.println("Loading...");
        Thread.sleep(2100);
        io.println("Scanning for changes...");
        Thread.sleep(2900);
        io.println("Your work has been saved and updated.");
        Thread.sleep(2500);
    }
    
    public void displaySetModeBanner() {
        io.tabPrintLn("=== Set Mode ===");
    }

    public int getUsersMode() {
        io.println("Please select which mode you would like to operate in:");
        io.tabPrintLn("(1) Production Mode");
        io.tabPrintLn("(2) Training Mode");
        io.println("");
        return io.readInt("Please enter (1) or (2):", 1, 2);
    }
    
    public void printUnknownCmd() {
        io.tabPrintLn("Invalid command. Please try again.");
    }
    
    public void printExitBanner() {
        io.tabPrintLn("Have a nice day!!!");
    }
    
    public void displayErrMsg(String errMsg) {
        io.tabPrintLn("=== ERROR ===");
        io.println(errMsg);
    }


}
