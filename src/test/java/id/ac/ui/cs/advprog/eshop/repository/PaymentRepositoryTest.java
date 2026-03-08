package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    private PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
    }

    @Test
    void testSaveCreateAndFindById() {
        Map<String, String> data = new HashMap<>();
        data.put("bankName", "Bank A");
        data.put("referenceCode", "REF123");

        Payment payment = new Payment("p-1", PaymentMethod.BANK_TRANSFER, PaymentStatus.PENDING, data, "order-1");
        Payment saved = paymentRepository.save(payment);

        assertNotNull(saved);
        assertEquals("p-1", saved.getId());

        Payment found = paymentRepository.findById("p-1");
        assertNotNull(found);
        assertEquals(PaymentMethod.BANK_TRANSFER, found.getMethod());
        assertEquals(PaymentStatus.PENDING, found.getStatus());
        assertEquals("order-1", found.getOrderId());
    }

    @Test
    void testSaveUpdateExisting() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment("p-2", PaymentMethod.VOUCHER, PaymentStatus.PENDING, data, "order-2");
        paymentRepository.save(payment);

        // update status and save again
        payment.setStatus(PaymentStatus.SUCCESS);
        Payment updated = paymentRepository.save(payment);

        assertNotNull(updated);
        assertEquals(PaymentStatus.SUCCESS, updated.getStatus());

        Payment found = paymentRepository.findById("p-2");
        assertEquals(PaymentStatus.SUCCESS, found.getStatus());
    }

    @Test
    void testFindByIdNotFound() {
        Payment found = paymentRepository.findById("non-existent");
        assertNull(found);
    }
}