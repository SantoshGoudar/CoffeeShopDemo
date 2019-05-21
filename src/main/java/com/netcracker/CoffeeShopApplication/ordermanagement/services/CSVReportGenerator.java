package com.netcracker.CoffeeShopApplication.ordermanagement.services;


import com.netcracker.CoffeeShopApplication.exceptions.CustomException;
import com.netcracker.CoffeeShopApplication.ordermanagement.models.Item;
import com.netcracker.CoffeeShopApplication.ordermanagement.models.Order;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

@Service
@Slf4j
public class CSVReportGenerator implements IReportGenerator {


    @Override
    public void writeDailyReport(PrintWriter writer, List<Order> orders) throws CustomException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String date[] = {"Date", format.format(orders.get(0).getDate())};
        String[] CSV_HEADER = {"OrderNumber", "Items", "Sales"};
        try (
                CSVWriter csvWriter = new CSVWriter(writer,
                        CSVWriter.DEFAULT_SEPARATOR,
                        CSVWriter.NO_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_LINE_END);
        ) {
            csvWriter.writeNext(date);
            csvWriter.writeNext(CSV_HEADER);
            double totalQty = 0, totalPrice = 0;
            for (Order order : orders) {
                double qty = order.getItems().stream().collect(Collectors.summingDouble(Item::getQty));
                double price = order.getItems().stream().collect(Collectors.summingDouble(Item::getPrice));
                String[] data = {
                        order.getOrderId(),
                        String.valueOf(qty),
                        String.valueOf(price)
                };
                totalPrice += price;
                totalQty += qty;
                csvWriter.writeNext(data);
            }
            csvWriter.writeNext(new String[]{"Total", String.valueOf(totalQty), String.valueOf(totalPrice)});

        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new CustomException(e.getMessage(), e);
        }
    }


    @Override
    public void writeMonthlyReport(PrintWriter writer, List<Order> orders) throws CustomException {
        writeWeeklyReport(writer, orders);
    }

    @Override
    public void writeWeeklyReport(PrintWriter writer, List<Order> orders) throws CustomException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String[] CSV_HEADER = {"Date", "Orders", "Items", "Sales"};
        try (
                CSVWriter csvWriter = new CSVWriter(writer,
                        CSVWriter.DEFAULT_SEPARATOR,
                        CSVWriter.NO_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_LINE_END);
        ) {
            csvWriter.writeNext(CSV_HEADER);

            Map<String, List<Order>> dateListMap = orders.stream().collect(Collectors.groupingBy(order -> {
                return format.format(order.getDate());
            }));
            dateListMap.forEach((date, orders1) -> {
                double totalQty = 0, totalPrice = 0;
                for (Order order : orders1) {
                    double qty = order.getItems().stream().collect(Collectors.summingDouble(Item::getQty));
                    double price = order.getItems().stream().collect(Collectors.summingDouble(Item::getPrice));
                    totalPrice += price;
                    totalQty += qty;
                }
                String[] data = {
                        date,
                        String.valueOf(orders1.size()),
                        String.valueOf(totalQty),
                        String.valueOf(totalPrice)
                };
                csvWriter.writeNext(data);
            });


        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new CustomException(e.getMessage(), e);
        }
    }
}
