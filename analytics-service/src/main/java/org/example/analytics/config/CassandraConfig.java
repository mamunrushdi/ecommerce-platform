package org.example.analytics.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;

@Configuration
public class CassandraConfig extends AbstractCassandraConfiguration {
    @Override
    protected String getKeyspaceName() {
        return "analytics";
    }

    @Override
    protected String getContactPoints() {
        return "127.0.0.1"; // local Cassandra node
    }

    @Override
    protected int getPort() {
        return 9042; // default Cassandra port
    }
}
