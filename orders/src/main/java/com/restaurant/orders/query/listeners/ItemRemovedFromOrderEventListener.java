package com.restaurant.orders.query.listeners;

import com.restaurant.orders.events.ItemRemovedFromOrderEvent;
import com.restaurant.orders.query.OrderItemRepository;
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
public class ItemRemovedFromOrderEventListener {

    @Autowired
    OrderItemRepository orderItems;

    @EventHandler
    public void on(ItemRemovedFromOrderEvent event) {
        Logger
            .getInstance(getClass())
            .info(format("Handling event: %s", event));

        Optional
            .ofNullable(orderItems.findOne(event.getOrderItemId()))
            .ifPresent(orderItems::delete);
    }
}
