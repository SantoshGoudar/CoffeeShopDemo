package com.netcracker.CoffeeShopApplication.ordermanagement.services;

import com.netcracker.CoffeeShopApplication.exceptions.CustomException;
import com.netcracker.CoffeeShopApplication.ordermanagement.models.Order;

import java.io.PrintWriter;
import java.util.List;

public interface IReportGenerator {

    public void writeDailyReport(PrintWriter writer, List<Order> orders) throws CustomException;
    public void writeWeeklyReport(PrintWriter writer,List<Order> orders) throws CustomException;
    public void writeMonthlyReport(PrintWriter writer,List<Order> orders) throws CustomException;

}
