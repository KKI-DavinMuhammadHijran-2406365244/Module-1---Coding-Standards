package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

import java.util.Map;

public class Payment {
    private String id;
    private PaymentMethod method;
    private PaymentStatus status;
    private Map<String, String> paymentData;
    private String orderId;

    public Payment() {}

    public Payment(String id, PaymentMethod method, PaymentStatus status,
                   Map<String, String> paymentData, String orderId) {
        this.id = id;
        this.method = method;
        this.status = status;
        this.paymentData = paymentData;
        this.orderId = orderId;
    }

    public String getId() {
        return id;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public Map<String, String> getPaymentData() {
        return paymentData;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMethod(PaymentMethod method) {
        this.method = method;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public void setPaymentData(Map<String, String> paymentData) {
        this.paymentData = paymentData;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}