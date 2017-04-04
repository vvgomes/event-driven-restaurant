package com.restaurant.orders.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ItemAddedToOrderEvent {
    String orderId;
    String orderItemId;
    String menuItemId;
    BigDecimal price;
    Integer quantity;
}
