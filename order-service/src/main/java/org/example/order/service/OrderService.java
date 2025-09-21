package org.example.order.service;

import org.example.order.model.Order;
import org.example.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public Order createOrder(Order order) {
        Order o = new Order();
        o.setCustomerName("test user");
        o.setTotalAmount(order.getTotalAmount());
        return orderRepository.save(o);
    }

    public Order getOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}
