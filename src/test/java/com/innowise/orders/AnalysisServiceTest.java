package com.innowise.orders;

import com.innowise.orders.entity.*;
import com.innowise.orders.service.AnalysisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnalysisServiceTest {
    private List<Order> orders;
    private Customer customer1;
    private Customer customer2;
    private Customer customer3;

    @BeforeEach
    void setUp() {
        customer1 = new Customer("N001", "Воюнков Федор", "voinkaff@mail.com",
                LocalDateTime.now().minusDays(1), 20, "Минск");
        customer2 = new Customer("N002", "Коваль Вадим", "cap.stop@mail.com",
                LocalDateTime.now().minusDays(1), 19, "Могилев");
        customer3 = new Customer("N003", "Морозов Ярослав", "morozzoff@mail.com",
                LocalDateTime.now().minusDays(1), 19, "Борисов");

        Order order1 = new Order("R001", LocalDateTime.now().minusDays(10),
                customer1, Arrays.asList(
                new OrderItem("Ноутбук", 2, 60000.0,
                        Category.ELECTRONICS),
                new OrderItem("Книга", 2, 800.0,
                        Category.BOOKS)
        ), OrderStatus.DELIVERED);

        Order order2 = new Order("R002", LocalDateTime.now().minusDays(8),
                customer2, Arrays.asList(
                new OrderItem("Смартфон", 1, 30000.0,
                        Category.ELECTRONICS),
                new OrderItem("Футболка", 4, 2000.0,
                        Category.CLOTHING)
        ), OrderStatus.DELIVERED);

        Order order3 = new Order("R003", LocalDateTime.now().minusDays(5),
                customer3, Arrays.asList(
                new OrderItem("Конструктор", 3, 2500.0,
                        Category.TOYS),
                new OrderItem("Книга", 2, 800.0,
                        Category.BOOKS)
        ), OrderStatus.PROCESSING);

        Order order4 = new Order("R004", LocalDateTime.now().minusDays(3),
                customer1, Arrays.asList(
                new OrderItem("Смартфон", 2, 30000.0,
                        Category.ELECTRONICS)
        ), OrderStatus.DELIVERED);

        Order order5 = new Order("R005", LocalDateTime.now().minusDays(2),
                customer2, Arrays.asList(
                new OrderItem("Футболка", 3, 2000.0,
                        Category.CLOTHING),
                new OrderItem("Книга", 1, 800.0,
                        Category.BOOKS)
        ), OrderStatus.CANCELLED);

        Order order6 = new Order("R006", LocalDateTime.now().minusDays(1),
                customer1, Arrays.asList(
                new OrderItem("Ноутбук", 1, 60000.0,
                        Category.ELECTRONICS)
        ), OrderStatus.SHIPPED);

        Order order7 = new Order("R007", LocalDateTime.now(), customer1,
                Arrays.asList(new OrderItem("Смартфон", 1, 30000.0,
                        Category.ELECTRONICS)),
                OrderStatus.DELIVERED);

        Order order8 = new Order("R008", LocalDateTime.now(), customer1,
                Arrays.asList(new OrderItem("Книга", 1, 800.0,
                        Category.BOOKS)),
                OrderStatus.DELIVERED);

        Order order9 = new Order("R009", LocalDateTime.now(), customer1,
                Arrays.asList(new OrderItem("Футболка", 2, 2000.0,
                        Category.CLOTHING)),
                OrderStatus.DELIVERED);

        orders = List.of(order1, order2, order3, order4, order5, order6, order7, order8, order9);
    }


    @Test
    void getUniqueCities_ok() {
        List<String> cities = AnalysisService.getUniqueCities(orders);

        assertEquals(3, cities.size());
        assertTrue(cities.contains("Минск"));
        assertTrue(cities.contains("Могилев"));
        assertTrue(cities.contains("Борисов"));
    }


    @Test
    void getUniqueCities_nullAndEmpty() {
        assertTrue(AnalysisService.getUniqueCities(null).isEmpty());
        assertTrue(AnalysisService.getUniqueCities(List.of()).isEmpty());
    }


    @Test
    void getTotalIncomeForDeliveredOrders_ok() {
        Optional<Double> income =
                AnalysisService.getTotalIncomeForDeliveredOrders(orders);

        assertTrue(income.isPresent());
        assertEquals(254400.0, income.get(), 0.001);
    }


    @Test
    void getTotalIncomeForDeliveredOrders_noDelivered() {
        List<Order> cancelled = List.of(
                new Order(
                        "X1",
                        LocalDateTime.now(),
                        customer1,
                        List.of(new OrderItem("Товар", 1, 1000,
                                Category.ELECTRONICS)),
                        OrderStatus.CANCELLED
                )
        );

        assertTrue(
                AnalysisService.getTotalIncomeForDeliveredOrders(cancelled).isEmpty()
        );
    }


    @Test
    void getMostPopularProductBySales_ok() {
        Optional<String> product =
                AnalysisService.getMostPopularProductBySales(orders);

        assertTrue(product.isPresent());
        assertEquals("Футболка", product.get());
    }


    @Test
    void getMostPopularProductBySales_onlyCancelled() {
        List<Order> cancelled = List.of(
                new Order(
                        "X1",
                        LocalDateTime.now(),
                        customer1,
                        List.of(new OrderItem("Товар", 10, 1000,
                                Category.ELECTRONICS)),
                        OrderStatus.CANCELLED
                )
        );

        assertTrue(
                AnalysisService.getMostPopularProductBySales(cancelled).isEmpty()
        );
    }


    @Test
    void getAverageCheckForDeliveredOrders_ok() {
        OptionalDouble avg =
                AnalysisService.getAverageCheckForDeliveredOrders(orders);

        assertTrue(avg.isPresent());
        assertEquals(42400.0, avg.getAsDouble(), 0.001);
    }


    @Test
    void getAverageCheckForDeliveredOrders_noDelivered() {
        List<Order> processing = List.of(
                new Order(
                        "X1",
                        LocalDateTime.now(),
                        customer1,
                        List.of(new OrderItem("Товар", 1, 1000,
                                Category.ELECTRONICS)),
                        OrderStatus.PROCESSING
                )
        );

        assertTrue(
                AnalysisService.getAverageCheckForDeliveredOrders(processing).isEmpty()
        );
    }


    @Test
    void getCustomersWithMoreThan5Orders_ok() {
        List<Customer> customers =
                AnalysisService.getCustomersWithMoreThan5Orders(orders);

        assertEquals(1, customers.size());
        assertEquals(customer1, customers.get(0));
    }


    @Test
    void getCustomersWithMoreThan5Orders_exactly5() {
        List<Order> exactly5 = List.of(
                new Order("wdw", LocalDateTime.now(), customer2,
                        List.of(new OrderItem("A", 1, 1,
                                Category.BOOKS)), OrderStatus.DELIVERED),
                new Order("wdwsw", LocalDateTime.now(), customer2,
                        List.of(new OrderItem("Awwww", 1, 1,
                                Category.BOOKS)), OrderStatus.DELIVERED),
                new Order("eece", LocalDateTime.now(), customer2,
                        List.of(new OrderItem("Awsxqwx", 1, 1,
                                Category.BOOKS)), OrderStatus.DELIVERED),
                new Order("wqdqwd", LocalDateTime.now(), customer2,
                        List.of(new OrderItem("Adwdq", 1, 1,
                                Category.BOOKS)), OrderStatus.DELIVERED),
                new Order("efefe", LocalDateTime.now(), customer2,
                        List.of(new OrderItem("Aqdqwdwq", 1, 1,
                                Category.BOOKS)), OrderStatus.DELIVERED)
        );

        assertTrue(
                AnalysisService.getCustomersWithMoreThan5Orders(exactly5).isEmpty()
        );
    }


    @Test
    void getCustomersWithMoreThan5Orders_null() {
        assertTrue(
                AnalysisService.getCustomersWithMoreThan5Orders(null).isEmpty()
        );
    }


    //три теста на null дописал
    @Test
    void getMostPopularProductBySales_null() {
        assertTrue(
                AnalysisService.getMostPopularProductBySales(null).isEmpty()
        );
    }


    @Test
    void getAverageCheckForDeliveredOrders_null() {
        assertTrue(
                AnalysisService.getAverageCheckForDeliveredOrders(null).isEmpty()
        );
    }


    @Test
    void getTotalIncomeForDeliveredOrders_null() {
        assertTrue(
                AnalysisService.getTotalIncomeForDeliveredOrders(null).isEmpty()
        );
    }
}
