package com.innowise.orders.service;

import com.innowise.orders.entity.Customer;
import com.innowise.orders.entity.Order;
import com.innowise.orders.entity.OrderItem;
import com.innowise.orders.entity.OrderStatus;

import java.util.*;
import java.util.stream.Collectors;

public class AnalysisService {
    private AnalysisService() {}


    public static List<String> getUniqueCities(List<Order> orders){
        if(orders == null || orders.isEmpty()){
            return List.of();
        }

        return orders.stream()
                .map(order -> order.getCustomer().getCity())
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

    }

    public static Optional<Double> getTotalIncomeForDeliveredOrders(List<Order> orders){
        if(orders == null || orders.isEmpty()){
            return Optional.empty();
        }

        return orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .map(Order::getItems)
                .flatMap(Collection::stream)
                .map(item -> item.getPrice() * item.getQuantity())
                .reduce(Double::sum);
    }

    public static Optional<String> getMostPopularProductBySales(List<Order> orders){
        if(orders == null || orders.isEmpty()){
            return Optional.empty();
        }

        Map<String, Long> map = orders.stream()
                .filter(order -> order.getStatus() != OrderStatus.CANCELLED)
                .flatMap(order -> order.getItems().stream())
                .collect(Collectors.groupingBy(
                        OrderItem::getProductName,
                        Collectors.summingLong(OrderItem::getQuantity)
                ));

        return map.entrySet().stream()
                .sorted((pair1, pair2) -> Long.compare(pair2.getValue(), pair1.getValue()))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    public static OptionalDouble getAverageCheckForDeliveredOrders(List<Order> orders){
        if(orders == null || orders.isEmpty()){
            return OptionalDouble.empty();
        }

        return orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .mapToDouble(order -> order.getItems().stream()
                        .mapToDouble(item -> item.getQuantity() * item.getPrice())
                        .sum())
                .average();
    }

    public static List<Customer> getCustomersWithMoreThan5Orders(List<Order> orders) {
        if (orders == null || orders.isEmpty()) {
            return Collections.emptyList();
        }

        Map<Customer, Long> map = orders.stream()
                .collect(Collectors.groupingBy(
                        Order::getCustomer,
                        Collectors.counting()
                ));

        return map.entrySet().stream()
                .filter(pair -> pair.getValue() > 5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }


}
