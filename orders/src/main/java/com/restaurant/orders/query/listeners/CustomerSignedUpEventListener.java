package com.restaurant.orders.query.listeners;

import com.restaurant.customers.events.CustomerSignedUpEvent;
import com.restaurant.orders.query.Customer;
import com.restaurant.orders.query.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.log4j.Logger;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;

@Component
@ProcessingGroup("amqpEvents")
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CustomerSignedUpEventListener {

    @Autowired
    CustomerRepository customers;

    @EventHandler
    public void on(CustomerSignedUpEvent event) {
        Logger
            .getInstance(getClass())
            .info(format("Handling event: %s", event));

        customers.save(new Customer(event.getId()));
    }
}
