package com.restaurant.orders.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AddDeliveryAddressToOrderCommand {
    @TargetAggregateIdentifier
    String orderId;
    String addressId;
}

