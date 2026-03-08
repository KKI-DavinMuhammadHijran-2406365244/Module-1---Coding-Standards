package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    OrderRepository orderRepository;

    Order sampleOrder;

    @BeforeEach
    void setUp() {
        sampleOrder = new Order("order-1", null, 1700000000L, "Author A");
    }

    @Test
    void testAddPayment_Voucher_ValidCode_ShouldBeSuccessAndSaved() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP1234ABC5678");

        Payment toSave = new Payment("p-v-1", PaymentMethod.VOUCHER, PaymentStatus.PENDING, data, "order-1");
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(orderRepository.findById("order-1")).thenReturn(sampleOrder);

        Payment result = paymentService.addPayment(toSave.getOrderId(), PaymentMethod.VOUCHER.getValue(), data);

        assertNotNull(result);
        assertEquals(PaymentStatus.SUCCESS, result.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testAddPayment_Voucher_InvalidCode_ShouldBeRejectedAndSaved() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "INVALID-VOUCHER-000");

        Payment toSave = new Payment("p-v-2", PaymentMethod.VOUCHER, PaymentStatus.PENDING, data, "order-1");
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(orderRepository.findById("order-1")).thenReturn(sampleOrder);

        Payment result = paymentService.addPayment(toSave.getOrderId(), PaymentMethod.VOUCHER.getValue(), data);

        assertNotNull(result);
        assertEquals(PaymentStatus.REJECTED, result.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testAddPayment_BankTransfer_ValidData_ShouldBePendingAndSaved() {
        Map<String, String> data = new HashMap<>();
        data.put("bankName", "Bank A");
        data.put("referenceCode", "REF-12345");

        Payment toSave = new Payment("p-b-1", PaymentMethod.BANK_TRANSFER, PaymentStatus.PENDING, data, "order-1");
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(orderRepository.findById("order-1")).thenReturn(sampleOrder);

        Payment result = paymentService.addPayment(toSave.getOrderId(), PaymentMethod.BANK_TRANSFER.getValue(), data);

        assertNotNull(result);
        assertEquals(PaymentStatus.PENDING, result.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testAddPayment_BankTransfer_MissingBankName_ShouldBeRejectedAndSaved() {
        Map<String, String> data = new HashMap<>();
        data.put("bankName", "");
        data.put("referenceCode", "REF-12345");

        Payment toSave = new Payment("p-b-2", PaymentMethod.BANK_TRANSFER, PaymentStatus.PENDING, data, "order-1");
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(orderRepository.findById("order-1")).thenReturn(sampleOrder);

        Payment result = paymentService.addPayment(toSave.getOrderId(), PaymentMethod.BANK_TRANSFER.getValue(), data);

        assertNotNull(result);
        assertEquals(PaymentStatus.REJECTED, result.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testAddPayment_BankTransfer_MissingReference_ShouldBeRejectedAndSaved() {
        Map<String, String> data = new HashMap<>();
        data.put("bankName", "Bank A");
        data.put("referenceCode", null);

        Payment toSave = new Payment("p-b-3", PaymentMethod.BANK_TRANSFER, PaymentStatus.PENDING, data, "order-1");
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(orderRepository.findById("order-1")).thenReturn(sampleOrder);

        Payment result = paymentService.addPayment(toSave.getOrderId(), PaymentMethod.BANK_TRANSFER.getValue(), data);

        assertNotNull(result);
        assertEquals(PaymentStatus.REJECTED, result.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }
}