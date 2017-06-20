package com.restaurant.orders.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.axonframework.serialization.Revision;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@Revision("1")
public class ItemAddedToOrderEvent {
    String orderId;
    String orderItemId;
    String menuItemId;
    BigDecimal price;
    Integer quantity;
}
