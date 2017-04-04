package com.restaurant.orders.query.listeners;

import com.restaurant.orders.events.ItemRemovedFromOrderEvent;
import com.restaurant.orders.query.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ItemRemovedFromOrderEventListenerTest {
    @Mock
    OrderItemRepository orderItems;
    ItemRemovedFromOrderEventListener listener;

    @Before
    public void setUp() throws Exception {
        listener = new ItemRemovedFromOrderEventListener(orderItems);
    }

    @Test
    public void removesExistingOrderItem() throws Exception {
        String orderId = randomUUID().toString();
        String menuItemId = randomUUID().toString();
        String orderItemId = randomUUID().toString();

        Order order = new Order(orderId, null);
        MenuItem menuItem = new MenuItem(menuItemId, new BigDecimal(10));
        OrderItem orderItem = new OrderItem(orderItemId, order, menuItem, 2);
        when(orderItems.findOne(orderItemId)).thenReturn(orderItem);

        listener.on(new ItemRemovedFromOrderEvent(orderId, orderItemId));
        verify(orderItems).delete(orderItem);
    }
}