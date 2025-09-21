package org.example.billing.service;

import org.example.billing.model.Payment;
import org.example.billing.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillingService {

    private final PaymentRepository paymentRepository;

    public BillingService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment processPayment(Payment payment) {
        payment.setStatus("PAID"); // simple example
        return paymentRepository.save(payment);
    }

    public List<Payment> getBillings() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentStatus(Long orderId) {
        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found for orderId: " + orderId));
    }
}
