package com.restaurant.customers.query.listeners;

import com.restaurant.customers.events.AddressAddedEvent;
import com.restaurant.customers.query.Address;
import com.restaurant.customers.query.AddressRepository;
import com.restaurant.customers.query.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Component
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AddressAddedEventListener {

    @Autowired
    CustomerRepository customers;

    @Autowired
    AddressRepository addresses;

    @EventHandler
    public void on(AddressAddedEvent event) {
        Optional
            .ofNullable(customers.findOne(event.getCustomerId()))
            .map(customer -> new Address(
                event.getAddressId(),
                event.getNickName(),
                event.getLocation(),
                customer))
            .ifPresent(addresses::save);
    }
}
