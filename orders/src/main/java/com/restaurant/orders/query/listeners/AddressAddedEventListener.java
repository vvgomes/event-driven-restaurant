package com.restaurant.orders.query.listeners;

import com.restaurant.customers.events.AddressAddedEvent;
import com.restaurant.orders.query.Address;
import com.restaurant.orders.query.AddressRepository;
import com.restaurant.orders.query.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.log4j.Logger;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;

@Component
@ProcessingGroup("amqpEvents")
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AddressAddedEventListener {

    @Autowired
    CustomerRepository customers;

    @Autowired
    AddressRepository addresses;

    @EventHandler
    public void on(AddressAddedEvent event) {
        Logger
            .getInstance(getClass())
            .info(format("Handling event: %s", event));

        Optional
            .ofNullable(customers.findOne(event.getCustomerId()))
            .map(customer -> new Address(
                event.getAddressId(),
                event.getLocation(),
                customer))
            .ifPresent(addresses::save);
    }
}
