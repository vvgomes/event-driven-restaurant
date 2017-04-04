package com.restaurant.orders.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AddItemToOrderCommand {
    @TargetAggregateIdentifier
    String orderId;
    String orderItemId;
    String menuItemId;
    BigDecimal price;
    Integer quantity;
}
