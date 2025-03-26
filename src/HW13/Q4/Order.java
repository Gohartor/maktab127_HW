package HW13.Q4;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private String orderId;
    private String customerId;
    private List<Item> items;
    private LocalDate orderDate;

    public Order(String orderId, String customerId, List<Item> items, LocalDate orderDate) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.items = items;
        this.orderDate = orderDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<Item> getItems() {
        return items;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }
}
