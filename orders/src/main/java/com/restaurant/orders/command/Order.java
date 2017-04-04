package com.restaurant.orders.command;

import com.restaurant.orders.events.*;
import lombok.experimental.FieldDefaults;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.common.Assert;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import static lombok.AccessLevel.PRIVATE;
import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
@FieldDefaults(level = PRIVATE)
public class Order {
    @AggregateIdentifier
    String id;
    String customerId;
    OrderStatus status;

    private Order() {
    }

    @CommandHandler
    public Order(OpenOrderCommand command) {
        apply(new OrderOpenedEvent(command.getId(), command.getCustomerId()));
    }

    @CommandHandler
    public void on(AddItemToOrderCommand command) {
        checkStatus();
        Assert.isTrue(command.getQuantity() > 0, () -> "Must add at least one item.");
        apply(new ItemAddedToOrderEvent(id, command.getOrderItemId(), command.getMenuItemId(), command.getPrice(), command.getQuantity()));
    }

    @CommandHandler
    public void on(RemoveItemFromOrderCommand command) {
        checkStatus();
        apply(new ItemRemovedFromOrderEvent(id, command.getOrderItemId()));
    }

    @CommandHandler
    public void on(AddDeliveryAddressToOrderCommand command) {
        checkStatus();
        apply(new DeliveryAddressAddedToOrderEvent(id, command.getAddressId()));
    }

    @CommandHandler
    public void on(CancelOrderCommand command) {
        checkStatus();
        apply(new OrderCanceledEvent(id));
    }

    @CommandHandler
    public void on(PlaceOrderCommand command) {
        checkStatus();
        apply(new OrderPlacedEvent(id));
    }

    @EventSourcingHandler
    public void on(OrderOpenedEvent event) {
        id = event.getId();
        customerId = event.getCustomerId();
        status = OrderStatus.OPEN;
    }

    @EventSourcingHandler
    public void on(ItemAddedToOrderEvent event) {
    }

    @EventSourcingHandler
    public void on(ItemRemovedFromOrderEvent event) {
    }

    @EventSourcingHandler
    public void on(OrderCanceledEvent event) {
        status = OrderStatus.CANCELED;
    }

    @EventSourcingHandler
    public void on(OrderPlacedEvent event) {
        status = OrderStatus.PLACED;
    }

    private void checkStatus() {
        Assert.state(OrderStatus.OPEN == status, () -> "Order must be open.");
    }

    enum OrderStatus {
        OPEN, CANCELED, PLACED
    }
}
