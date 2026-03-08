package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository {
    private List<Order> orderData = new ArrayList<>();

    public Order save(Order order) {
        // if an order with the same id exists, replace it
        Order existing = findById(order.getId());
        if (existing != null) {
            orderData.remove(existing);
        }
        orderData.add(order);
        return order;
    }

    public Order findById(String id) {
        for (Order order : orderData) {
            if (order.getId().equals(id)) {
                return order;
            }
        }
        return null;
    }

    public List<Order> findAllByAuthor(String author) {
        List<Order> result = new ArrayList<>();
        for (Order order : orderData) {
            if (order.getAuthor().equals(author)) {
                result.add(order);
            }
        }
        return result;
    }
}