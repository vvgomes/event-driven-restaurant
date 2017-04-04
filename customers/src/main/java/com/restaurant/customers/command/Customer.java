package com.restaurant.customers.command;

import com.restaurant.customers.events.CustomerSignedUpEvent;
import com.restaurant.customers.events.AddressAddedEvent;
import lombok.experimental.FieldDefaults;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.common.Assert;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.HashMap;
import java.util.Map;

import static java.util.function.Predicate.isEqual;
import static lombok.AccessLevel.PRIVATE;
import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
@FieldDefaults(level = PRIVATE)
public class Customer {
    @AggregateIdentifier
    String id;
    String email;
    Map<String, Address> addresses;

    private Customer() {
    }

    @CommandHandler
    public Customer(SignUpCustomerCommand command) {
        Assert.isTrue(command.getEmail().contains("@"), () -> "Email must be valid.");
        apply(new CustomerSignedUpEvent(command.getId(), command.getEmail()));
    }

    @CommandHandler
    public void handle(AddAddressCommand command) {
        boolean duplicated =
            addresses
                .values()
                .stream()
                .map(Address::getNickName)
                .anyMatch(isEqual(command.getNickName()));

        Assert.state(!duplicated, () -> "Address nickname must be unique.");

        apply(new AddressAddedEvent(
            command.getCustomerId(),
            command.getAddressId(),
            command.getNickName(),
            command.getLocation()));
    }

    @EventSourcingHandler
    public void on(CustomerSignedUpEvent event) {
        id = event.getId();
        email = event.getEmail();
        addresses = new HashMap<>();
    }

    @EventSourcingHandler
    public void on(AddressAddedEvent event) {
        addresses.put(event.getAddressId(),
            new Address(
                event.getAddressId(),
                event.getNickName(),
                event.getLocation()));
    }
}
