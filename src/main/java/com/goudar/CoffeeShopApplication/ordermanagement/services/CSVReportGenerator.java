package com.goudar.CoffeeShopApplication.ordermanagement.services;


import com.goudar.CoffeeShopApplication.exceptions.CustomException;
import com.goudar.CoffeeShopApplication.ordermanagement.models.Item;
import com.goudar.CoffeeShopApplication.ordermanagement.models.Order;
import com.opencsv.CSVWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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
