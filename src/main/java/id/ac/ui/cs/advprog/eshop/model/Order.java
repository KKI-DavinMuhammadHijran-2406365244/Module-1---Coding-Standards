package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class Order {
    String id;
    List<Product> products;
    Long orderTime;
    String author;
    String status;
    Long totalPrice;

    /**
     * No-args constructor for Spring/Thymeleaf form binding.
     * Initializes products to an empty list and sets a default status.
     * Does NOT perform the strict validation that the parameterized constructor does.
     */
    public Order() {
        this.products = new ArrayList<>();
        this.status = OrderStatus.WAITING_PAYMENT.getValue();
    }

    public Order(String id, List<Product> products, Long orderTime, String author) {
        this.id = id;
        this.orderTime = orderTime;
        this.author = author;
        this.status = OrderStatus.WAITING_PAYMENT.getValue();

        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            this.products = products;
        }
    }

    public Order(String id, List<Product> products, Long orderTime, String author, String status) {
        this(id, products, orderTime, author);
        this.setStatus(status);
    }

    /** All-args constructor for Lombok @Builder (id, products, orderTime, author, status, totalPrice). */
    public Order(String id, List<Product> products, Long orderTime, String author, String status, Long totalPrice) {
        this.id = id;
        this.products = products != null ? products : new ArrayList<>();
        this.orderTime = orderTime;
        this.author = author;
        this.totalPrice = totalPrice;
        this.status = (status != null && OrderStatus.contains(status)) ? status : OrderStatus.WAITING_PAYMENT.getValue();
    }

    public void setStatus(String status) {
        if (OrderStatus.contains(status)) {
            this.status = status;
        } else {
            throw new IllegalArgumentException();
        }
    }

    // Add setters so form binding can populate fields
    public void setId(String id) {
        this.id = id;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setOrderTime(Long orderTime) {
        this.orderTime = orderTime;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }
}