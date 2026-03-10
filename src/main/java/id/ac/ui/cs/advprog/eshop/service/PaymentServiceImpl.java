package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Payment addPayment(String orderId, String method, Map<String, String> paymentData) {
        PaymentMethod paymentMethod = parseMethod(method);
        PaymentStatus status = determinePaymentStatus(paymentMethod, paymentData);

        String paymentId = UUID.randomUUID().toString();
        Payment payment = new Payment(paymentId, paymentMethod, status, paymentData, orderId);
        Payment saved = paymentRepository.save(payment);

        updateOrderByPaymentStatus(orderId, saved.getStatus());

        return saved;
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        if (!PaymentStatus.contains(status)) {
            throw new IllegalArgumentException("Invalid payment status: " + status);
        }

        PaymentStatus newStatus = PaymentStatus.valueOf(status);
        payment.setStatus(newStatus);
        Payment saved = paymentRepository.save(payment);

        updateOrderByPaymentStatus(payment.getOrderId(), newStatus);

        return saved;
    }

    private PaymentStatus determinePaymentStatus(PaymentMethod paymentMethod, Map<String, String> paymentData) {
        if (paymentMethod == PaymentMethod.VOUCHER) {
            String voucher = paymentData == null ? null : paymentData.get("voucherCode");
            return isValidVoucher(voucher) ? PaymentStatus.SUCCESS : PaymentStatus.REJECTED;
        }
        if (paymentMethod == PaymentMethod.BANK_TRANSFER) {
            String bankName = paymentData == null ? null : paymentData.get("bankName");
            String referenceCode = paymentData == null ? null : paymentData.get("referenceCode");
            return (isEmpty(bankName) || isEmpty(referenceCode)) ? PaymentStatus.REJECTED : PaymentStatus.PENDING;
        }
        return PaymentStatus.PENDING;
    }

    private void updateOrderByPaymentStatus(String orderId, PaymentStatus paymentStatus) {
        if (paymentStatus == PaymentStatus.PENDING) {
            return;
        }
        Order order = orderRepository.findById(orderId);
        if (order == null) {
            return;
        }
        if (paymentStatus == PaymentStatus.SUCCESS) {
            order.setStatus(OrderStatus.SUCCESS.getValue());
        } else if (paymentStatus == PaymentStatus.REJECTED) {
            order.setStatus(OrderStatus.FAILED.getValue());
        }
        orderRepository.save(order);
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    private PaymentMethod parseMethod(String method) {
        if (method == null) {
            throw new IllegalArgumentException("Payment method is null");
        }
        for (PaymentMethod m : PaymentMethod.values()) {
            if (m.name().equals(method) || m.getValue().equals(method)) {
                return m;
            }
        }
        throw new IllegalArgumentException("Unknown payment method: " + method);
    }

    private boolean isValidVoucher(String voucher) {
        if (voucher == null) return false;
        if (voucher.length() != 16) return false;
        if (!voucher.startsWith("ESHOP")) return false;
        int digitCount = 0;
        for (char c : voucher.toCharArray()) {
            if (Character.isDigit(c)) digitCount++;
        }
        return digitCount == 8;
    }

    private boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
}