/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.controller;

import com.sg.flooringmastery.dao.FMOrderDao;
import com.sg.flooringmastery.dao.FMOrderNumDao;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.TaxRate;
import com.sg.flooringmastery.exceptions.FMBlankDateFileException;
import com.sg.flooringmastery.exceptions.FMInvalidDateException;
import com.sg.flooringmastery.exceptions.FMInvalidOrderNumException;
import com.sg.flooringmastery.exceptions.FMPersistenceException;
import com.sg.flooringmastery.service.FMOrderNumService;
import com.sg.flooringmastery.service.FMOrderNumServiceImpl;
import com.sg.flooringmastery.service.FMOrderService;
import com.sg.flooringmastery.service.FMOrderServiceImpl;
import com.sg.flooringmastery.service.FMProductService;
import com.sg.flooringmastery.service.FMSetModeOfProgram;
import com.sg.flooringmastery.service.FMTaxRateService;
import com.sg.flooringmastery.ui.FMView;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author connor
 */
public class FMController {
    private FMOrderService orderService;
    private FMSetModeOfProgram modeService;
    private FMOrderNumService orderNumService;
    private FMProductService productService;
    private FMTaxRateService taxRateService;
    private FMView view;
    
    public FMController(FMOrderService orderService, FMSetModeOfProgram modeService,
                FMOrderNumService orderNumService, FMProductService productService,
                FMTaxRateService taxRateService, FMView view) {
        this.orderService = orderService;
        this.modeService = modeService;
        this.orderNumService = orderNumService;
        this.productService = productService;
        this.taxRateService = taxRateService;
        this.view = view;
    }
    
    public void run() {
        
        
        boolean iterate = true;
        
        while (iterate) {
            int menuSelection = view.printMenuAndGetSelection();
            
            try {
                switch (menuSelection) {
                    case 1:
                        displayOrders();
                        break;
                    case 2:
                        addOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder();
                        break;
                    case 5:
                        saveCurrentWork();
                        break;
                    case 6:
                        setModeOfProgram();
                        break;
                    case 7:
                        iterate = !iterate;
                        break;
                    default:
                        printUnknownCmd();
                }
            } catch (FMPersistenceException ex) {
                view.displayErrMsg(ex.getMessage());
            }
        }
        printExitBanner();
    }

    private void displayOrders() throws FMPersistenceException {
        view.displayDisplayOrdersBanner();
        LocalDate usersDate = view.getUsersDate(1);
        
        List<Order> ordersForOneDay;
        try {
            ordersForOneDay = orderService.getOrdersForOneDay(usersDate);
        } catch (FMBlankDateFileException | FMInvalidDateException ex) {
            view.displayErrMsg(ex.getMessage());
            return;
        }
        
        view.displayOrders(ordersForOneDay);
    }

    private void addOrder() throws FMPersistenceException {
        List<Product> products = productService.getAllProducts();
        List<TaxRate> taxRates = taxRateService.getAllTaxRates();

        List<Order> ordersForOneDay;
        LocalDate usersDate;
        
        view.displayAddBanner();
        
        boolean addToToday = view.addToToday();
        if (!addToToday) {
            usersDate = view.getUsersDate(2);
            try {
                ordersForOneDay = orderService.getOrdersForOneDay(usersDate);
            } catch (FMInvalidDateException ex) {
                view.displayErrMsg(ex.getMessage());
                if (view.createNewOrderFile()) {
                    //if the user would like to create new file for the date
                    //they've entered, then continue with add order process
                } else {
                    return;
                }
            } catch (FMBlankDateFileException ex) {
                //if the file the user has chosen to add to is blank, continue
                //with add order process
            }
        } else {
            usersDate = LocalDate.now();
        }
        
        Order usersOrder = new Order();
        
        usersOrder.setOrderDate(usersDate);
        usersOrder = view.getOrderInfo(usersOrder, products, taxRates, false);
        usersOrder = orderService.calculateTotals(usersOrder);
        
        view.displayOrderInfo(usersOrder);
        if (view.isConfirmed(2)) {
            orderNumService.setNewMaxOrderNum();
            usersOrder.setOrderNum(orderNumService.getOrderNum().getOrderNum());
            orderService.addOrder(usersOrder);
            view.displayAddOrderSuccess();
        } else {
            view.displayAddOrderFailure();
        }
    }

    private void editOrder() throws FMPersistenceException {
        List<Product> products = productService.getAllProducts();
        List<TaxRate> taxRates = taxRateService.getAllTaxRates();
        
        view.displayEditOrderBanner();
        LocalDate usersDate = view.getUsersDate(3);
        
        List<Order> ordersForOneDay;
        try {
            ordersForOneDay = orderService.getOrdersForOneDay(usersDate);
        } catch (FMInvalidDateException | FMBlankDateFileException ex) {
            view.displayErrMsg(ex.getMessage());
            return;
        }
        
        view.displayOrders(ordersForOneDay);
        
        ArrayList<Integer> orderNumsForOneDay = getListOfOrderNums(ordersForOneDay);
        
        Integer usersOrderNum = view.getUsersOrderNumChoice(orderNumsForOneDay, 3);
        
        Order usersOrder = new Order();
        try {
            usersOrder = orderService.getOrderByOrderNum(usersOrderNum);
        } catch (FMInvalidOrderNumException ex) {
            view.displayErrMsg(ex.getMessage());
            return;
        }
        
        usersOrder = view.getOrderInfo(usersOrder, products, taxRates, true);
        usersOrder = orderService.calculateTotals(usersOrder);
        
        view.displayOrderInfo(usersOrder);
        if (view.isConfirmed(3)) {
            orderService.editOrder(usersOrder);
            view.displayEditOrderSuccess();
        } else {
            view.displayEditOrderFailure();
        }
    }

    private void removeOrder() throws FMPersistenceException {
        view.displayRemoveOrderBanner();
        LocalDate usersDate = view.getUsersDate(4);
        List<Order> ordersForOneDay;
        
        try {
            ordersForOneDay = orderService.getOrdersForOneDay(usersDate);
        } catch (FMInvalidDateException | FMBlankDateFileException ex) {
            view.displayErrMsg(ex.getMessage());
            return;
        }
        
        view.displayOrders(ordersForOneDay);
        
        ArrayList<Integer> orderNumsForOneDay = getListOfOrderNums(ordersForOneDay);
        
        Integer usersOrderNum = view.getUsersOrderNumChoice(orderNumsForOneDay, 4);
        
        Order usersOrder = new Order();
        try {
            usersOrder = orderService.getOrderByOrderNum(usersOrderNum);
        } catch (FMInvalidOrderNumException ex) {
            view.displayErrMsg(ex.getMessage());
            return;
        }
        
        if (view.isConfirmed(4)) {
            orderService.removeOrder(usersOrder);
            view.displayRemoveOrderSuccess();
        } else {
            view.displayRemoveOrderFailure();
        }
    }

    private void saveCurrentWork() throws FMPersistenceException {
        try {
            view.displaySaveBanner();
        } catch (InterruptedException ex) {
            view.displayErrMsg(ex.getMessage());
        }
    }
    
    private void setModeOfProgram() {
        view.displaySetModeBanner();
        int usersMode = view.getUsersMode();
        
        this.orderService.setOrderDao(modeService.setModeOfOrderDao(usersMode));
        
        this.orderNumService.setOrderNumDao(modeService.setModeOfOrderNumDao(usersMode));
    }

    private void printUnknownCmd() {
        view.printUnknownCmd();
    }

    private void printExitBanner() {
        view.printExitBanner();
    }
    
    //put in service/DAO
    private ArrayList<Integer> getListOfOrderNums(List<Order> orders) {
        return new ArrayList(orders.stream()
                .mapToInt(o -> o.getOrderNum())
                .boxed()
                .collect(Collectors.toList()));
    }
}
