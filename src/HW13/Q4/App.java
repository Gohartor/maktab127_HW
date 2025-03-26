package HW13.Q4;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class App {

    public static Map<String, Double> calculateCustomerOrderTotals(List<Order> orders, LocalDate startDate, LocalDate endDate, double itemPriceThreshold, double customerTotalThreshold) {
        return orders.stream()
                .filter(order -> !order.getOrderDate().isBefore(startDate) && !order.getOrderDate().isAfter(endDate))
                .filter(order -> order.getItems().stream().anyMatch(item -> item.getPrice() > itemPriceThreshold))
                .collect(Collectors.groupingBy(Order::getCustomerId, Collectors.flatMapping(order -> order.getItems().stream(), Collectors.summingDouble(Item::getPrice))))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > customerTotalThreshold)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }




    public static void main(String[] args) {


        List<Item> items1 = List.of(new Item("laptop", 1200.0), new Item("mouse", 25.0));
        List<Item> items2 = List.of(new Item("keyboard", 80.0), new Item("monitor", 300.0));
        List<Item> items3 = List.of(new Item("headphones", 150.0));

        List<Order> orders = List.of(
                new Order("1", "user1", items1, LocalDate.of(2023, 10, 1)),
                new Order("2", "user2", items2, LocalDate.of(2023, 10, 5)),
                new Order("3", "user1", items3, LocalDate.of(2023, 10, 10))
        );

        LocalDate startDate = LocalDate.of(2023, 10, 1);
        LocalDate endDate = LocalDate.of(2023, 10, 20);
        double itemPriceThreshold = 100.0;
        double customerTotalThreshold = 500.0;

        Map<String, Double> result = calculateCustomerOrderTotals(orders, startDate, endDate, itemPriceThreshold, customerTotalThreshold);
        System.out.println(result);
    }
}
