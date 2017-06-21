package com.restaurant.menu.config;

import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventsourcing.eventstore.jpa.JpaEventStorageEngine;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class Axon {

    @Autowired
    DataSource dataSource;

    @Autowired
    EntityManagerProvider entityManagerProvider;

    @Autowired
    TransactionManager transactionManager;

    @Bean
    Serializer serializer() {
        return new JacksonSerializer();
    }

    @Bean
    JpaEventStorageEngine jpaEventStorageEngine() throws SQLException {
       return new JpaEventStorageEngine(serializer(), null, dataSource, entityManagerProvider, transactionManager);
    }
}
