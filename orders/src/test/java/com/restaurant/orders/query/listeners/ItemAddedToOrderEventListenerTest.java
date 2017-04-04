package com.restaurant.orders.query.listeners;

import com.restaurant.orders.events.ItemAddedToOrderEvent;
import com.restaurant.orders.query.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static java.util.UUID.randomUUID;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ItemAddedToOrderEventListenerTest {

    @Mock
    OrderRepository orders;

    @Mock
    MenuItemRepository menuItems;

    @Mock
    OrderItemRepository orderItems;

    ItemAddedToOrderEventListener listener;

    @Before
    public void setUp() throws Exception {
        listener = new ItemAddedToOrderEventListener(orders, menuItems, orderItems);
    }

    @Test
    public void addsNewItemToExistingOrder() throws Exception {
        String orderId = randomUUID().toString();
        String menuItemId = randomUUID().toString();
        String orderItemId = randomUUID().toString();

        Order order = new Order(orderId, null);
        when(orders.findOne(orderId)).thenReturn(order);

        MenuItem menuItem = new MenuItem(menuItemId, new BigDecimal(10));
        when(menuItems.findOne(menuItemId)).thenReturn(menuItem);

        listener.on(new ItemAddedToOrderEvent(orderId, orderItemId, menuItemId, new BigDecimal(10), 2));
        verify(orderItems).save(refEq(new OrderItem(orderItemId, order, menuItem, 2)));
    }
}