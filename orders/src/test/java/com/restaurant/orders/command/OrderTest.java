package com.restaurant.orders.command;

import com.restaurant.orders.events.*;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static java.util.UUID.randomUUID;

public class OrderTest {
    FixtureConfiguration<Order> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = new AggregateTestFixture<>(Order.class);
    }

    @Test
    public void opensNewOrder() throws Exception {
        String orderId = randomUUID().toString();
        String customerId = randomUUID().toString();

        fixture
            .givenNoPriorActivity()
            .when(new OpenOrderCommand(orderId, customerId))
            .expectEvents(new OrderOpenedEvent(orderId, customerId));
    }

    @Test
    public void addsItemToOrder() throws Exception {
        String orderId = randomUUID().toString();
        String customerId = randomUUID().toString();
        String menuItemId = randomUUID().toString();
        String orderItemId = randomUUID().toString();

        fixture
            .given(new OrderOpenedEvent(orderId, customerId))
            .when(new AddItemToOrderCommand(orderId, orderItemId, menuItemId, new BigDecimal(8.0), 2))
            .expectEvents(new ItemAddedToOrderEvent(orderId, orderItemId, menuItemId, new BigDecimal(8.0), 2));
    }

    @Test
    public void addsItemToOrder_withZeroQuantity() throws Exception {
        String orderId = randomUUID().toString();
        String customerId = randomUUID().toString();
        String menuItemId = randomUUID().toString();
        String orderItemId = randomUUID().toString();

        fixture
            .given(new OrderOpenedEvent(orderId, customerId))
            .when(new AddItemToOrderCommand(orderId, orderItemId, menuItemId, new BigDecimal(8.0), 0))
            .expectException(IllegalArgumentException.class);
    }

    @Test
    public void removesItemFromOrder() throws Exception {
        String orderId = randomUUID().toString();
        String customerId = randomUUID().toString();
        String menuItemId = randomUUID().toString();
        String orderItemId = randomUUID().toString();

        fixture
            .given(
                new OrderOpenedEvent(orderId, customerId),
                new ItemAddedToOrderEvent(orderId, orderItemId, menuItemId, new BigDecimal(8.0), 2))
            .when(new RemoveItemFromOrderCommand(orderId, orderItemId))
            .expectEvents(new ItemRemovedFromOrderEvent(orderId, orderItemId));
    }

    @Test
    public void addsDeliveryAddress() throws Exception {
        String orderId = randomUUID().toString();
        String customerId = randomUUID().toString();
        String addressId = randomUUID().toString();

        fixture
            .given(new OrderOpenedEvent(orderId, customerId))
            .when(new AddDeliveryAddressToOrderCommand(orderId, addressId))
            .expectEvents(new DeliveryAddressAddedToOrderEvent(orderId, addressId));
    }

    @Test
    public void cancelsOrder() throws Exception {
        String orderId = randomUUID().toString();
        String customerId = randomUUID().toString();

        fixture
            .given(new OrderOpenedEvent(orderId, customerId))
            .when(new CancelOrderCommand(orderId))
            .expectEvents(new OrderCanceledEvent(orderId));
    }

    @Test
    public void cancelsOrder_whenOrderIsPlaced() throws Exception {
        String orderId = randomUUID().toString();
        String customerId = randomUUID().toString();

        fixture
            .given(
                new OrderOpenedEvent(orderId, customerId),
                new OrderPlacedEvent(orderId))
            .when(new CancelOrderCommand(orderId))
            .expectException(IllegalStateException.class);
    }

    @Test
    public void cancelsOrder_whenOrderIsCanceledAlready() throws Exception {
        String orderId = randomUUID().toString();
        String customerId = randomUUID().toString();

        fixture
            .given(
                new OrderOpenedEvent(orderId, customerId),
                new OrderCanceledEvent(orderId))
            .when(new CancelOrderCommand(orderId))
            .expectException(IllegalStateException.class);
    }

    @Test
    public void placesOrder() throws Exception {
        String orderId = randomUUID().toString();
        String customerId = randomUUID().toString();

        fixture
            .given(new OrderOpenedEvent(orderId, customerId))
            .when(new PlaceOrderCommand(orderId))
            .expectEvents(new OrderPlacedEvent(orderId));
    }

    @Test
    public void placesOrder_whenOrderIsCanceled() throws Exception {
        String orderId = randomUUID().toString();
        String customerId = randomUUID().toString();

        fixture
            .given(
                new OrderOpenedEvent(orderId, customerId),
                new OrderCanceledEvent(orderId))
            .when(new PlaceOrderCommand(orderId))
            .expectException(IllegalStateException.class);
    }

    @Test
    public void placesOrder_whenOrderIsPlacedAlready() throws Exception {
        String orderId = randomUUID().toString();
        String customerId = randomUUID().toString();

        fixture
            .given(
                new OrderOpenedEvent(orderId, customerId),
                new OrderPlacedEvent(orderId))
            .when(new PlaceOrderCommand(orderId))
            .expectException(IllegalStateException.class);
    }
}