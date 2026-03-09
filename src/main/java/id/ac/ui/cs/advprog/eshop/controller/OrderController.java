package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/create")
    public String showCreateOrderForm(Model model) {
        // If your Order class doesn’t have a no‑args constructor, just pass null
        model.addAttribute("order", new Order());
        return "order/create";
    }

    @PostMapping("/create")
    public String createOrder(@ModelAttribute Order order, Model model) {
        Order saved = orderService.createOrder(order);
        model.addAttribute("order", saved);
        return "order/create-success";
    }

    @GetMapping("/history")
    public String showHistoryForm(Model model) {
        model.addAttribute("author", "");
        return "order/history-form";
    }

    @PostMapping("/history")
    public String showHistoryResult(@RequestParam("author") String author, Model model) {
        List<Order> orders = orderService.findAllByAuthor(author);
        model.addAttribute("orders", orders);
        model.addAttribute("author", author);
        return "order/history-result";
    }

    @GetMapping("/pay/{orderId}")
    public String showPaymentPage(@PathVariable String orderId, Model model) {
        Order order = orderService.findById(orderId);
        model.addAttribute("order", order);
        return "order/pay";
    }

    @PostMapping("/pay/{orderId}")
    public String submitPayment(@PathVariable String orderId,
                                @RequestParam("method") String method,
                                @RequestParam(required = false) String voucherCode,
                                @RequestParam(required = false) String bankName,
                                @RequestParam(required = false) String referenceCode,
                                Model model) {

        Map<String, String> paymentData = new HashMap<>();
        if (voucherCode != null) paymentData.put("voucherCode", voucherCode);
        if (bankName != null) paymentData.put("bankName", bankName);
        if (referenceCode != null) paymentData.put("referenceCode", referenceCode);

        Payment payment = paymentService.addPayment(orderId, method, paymentData);
        model.addAttribute("payment", payment);
        return "order/pay-result";
    }
}