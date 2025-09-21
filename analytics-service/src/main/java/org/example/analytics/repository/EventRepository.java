package org.example.analytics.repository;

import org.example.analytics.model.Event;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends CassandraRepository<Event, String> {
    // Optional custom queries
    List<Event> findByReferenceId(String referenceId);
}