package org.example.analytics.controller;

import org.example.analytics.model.Event;
import org.example.analytics.repository.EventRepository;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/events")
public class AnalyticsController {

    private final EventRepository eventRepository;

    public AnalyticsController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    // POST /events → record a new event
    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        if (event.getId() == null) {
            event.setId(UUID.randomUUID().toString());
        }
        if (event.getTimestamp() == null) {
            event.setTimestamp(Instant.now());
        }
        return eventRepository.save(event);
    }

    // GET /events/products/{productId} → get all events for a product
    @GetMapping("/products/{productId}")
    public List<Event> getEventsByProduct(@PathVariable String productId) {
        return eventRepository.findByReferenceId(productId);
    }

    // GET /events/orders/{orderId} → get all events for an order
    @GetMapping("/orders/{orderId}")
    public List<Event> getEventsByOrder(@PathVariable String orderId) {
        return eventRepository.findByReferenceId(orderId);
    }
}
