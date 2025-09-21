package org.example.billing.controller;

import org.example.billing.model.Payment;
import org.example.billing.service.BillingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/billing")
public class BillingController {

    private final BillingService billingService;

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    @GetMapping
    public List<Payment> getBillings() {
        return billingService.getBillings();
    }
    @PostMapping
    public Payment processPayment(@RequestBody Payment payment) {
        return billingService.processPayment(payment);
    }

    @GetMapping("/{orderId}")
    public Payment getPaymentStatus(@PathVariable Long orderId) {
        return billingService.getPaymentStatus(orderId);
    }
}
