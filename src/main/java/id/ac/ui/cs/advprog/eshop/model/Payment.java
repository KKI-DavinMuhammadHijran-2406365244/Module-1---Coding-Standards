package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class Payment {
    private String id;
    private PaymentMethod method;
    private PaymentStatus status;
    private Map<String, String> paymentData;
    private String orderId;

    public Payment(String id, PaymentMethod method, PaymentStatus status,
                   Map<String, String> paymentData, String orderId) {
        this.id = id;
        this.method = method;
        this.status = status;
        this.paymentData = paymentData;
        this.orderId = orderId;
    }
}