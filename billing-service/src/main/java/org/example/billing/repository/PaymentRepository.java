package org.example.billing.repository;

import org.example.billing.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Find payment by orderId
    Optional<Payment> findByOrderId(Long orderId);
}