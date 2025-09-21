package org.example.analytics.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;

@Table("events")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @PrimaryKey
    private String id;              // UUID for the event
    private String type;            // e.g., "PRODUCT_VIEW", "ORDER_PLACED"
    private String referenceId;     // productId or orderId
    private Instant timestamp;

}
