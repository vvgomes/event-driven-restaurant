package com.restaurant.orders.query.listeners;

import com.restaurant.orders.events.OrderOpenedEvent;
import com.restaurant.orders.query.CustomerRepository;
import com.restaurant.orders.query.Order;
import com.restaurant.orders.query.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.log4j.Logger;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;

@Component
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class OrderOpenedEventListener {

    @Autowired
    CustomerRepository customers;

    @Autowired
    OrderRepository orders;

    @EventHandler
    public void on(OrderOpenedEvent event) {
        Logger
            .getInstance(getClass())
            .info(format("Handling event: %s", event));

        Optional
            .ofNullable(customers.findOne(event.getCustomerId()))
            .map(customer -> new Order(event.getId(), customer))
            .ifPresent(orders::save);
    }
}
