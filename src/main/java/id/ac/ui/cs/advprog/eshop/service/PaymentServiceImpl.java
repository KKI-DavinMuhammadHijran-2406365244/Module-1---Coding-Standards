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
        PaymentStatus status = PaymentStatus.PENDING;

        if (paymentMethod == PaymentMethod.VOUCHER) {
            String voucher = paymentData == null ? null : paymentData.get("voucherCode");
            if (isValidVoucher(voucher)) {
                status = PaymentStatus.SUCCESS;
            } else {
                status = PaymentStatus.REJECTED;
            }
        } else if (paymentMethod == PaymentMethod.BANK_TRANSFER) {
            String bankName = paymentData == null ? null : paymentData.get("bankName");
            String referenceCode = paymentData == null ? null : paymentData.get("referenceCode");
            if (isEmpty(bankName) || isEmpty(referenceCode)) {
                status = PaymentStatus.REJECTED;
            } else {
                status = PaymentStatus.PENDING;
            }
        } else if (paymentMethod == PaymentMethod.CASH_ON_DELIVERY) {
            status = PaymentStatus.PENDING;
        }

        String paymentId = UUID.randomUUID().toString();
        Payment payment = new Payment(paymentId, paymentMethod, status, paymentData, orderId);
        Payment saved = paymentRepository.save(payment);

        if (saved.getStatus() == PaymentStatus.SUCCESS) {
            Order order = orderRepository.findById(orderId);
            if (order != null) {
                order.setStatus(OrderStatus.SUCCESS.getValue());
                orderRepository.save(order);
            }
        } else if (saved.getStatus() == PaymentStatus.REJECTED) {
            Order order = orderRepository.findById(orderId);
            if (order != null) {
                order.setStatus(OrderStatus.FAILED.getValue());
                orderRepository.save(order);
            }
        }

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

        Order order = orderRepository.findById(payment.getOrderId());
        if (order != null) {
            if (newStatus == PaymentStatus.SUCCESS) {
                order.setStatus(OrderStatus.SUCCESS.getValue());
                orderRepository.save(order);
            } else if (newStatus == PaymentStatus.REJECTED) {
                order.setStatus(OrderStatus.FAILED.getValue());
                orderRepository.save(order);
            }
        }

        return saved;
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