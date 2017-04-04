package com.restaurant.orders.query.listeners;

import com.restaurant.orders.events.OrderCanceledEvent;
import com.restaurant.orders.query.Order;
import com.restaurant.orders.query.OrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderCanceledEventListenerTest {

    @Mock
    OrderRepository orders;
    OrderCanceledEventListener listener;

    @Before
    public void setUp() throws Exception {
        listener = new OrderCanceledEventListener(orders);
    }

    @Test
    public void cancelsExistingOrder() throws Exception {
        String id = randomUUID().toString();

        Order order = new Order(id, null);
        when(orders.findOne(id)).thenReturn(order);

        listener.on(new OrderCanceledEvent(id));
        verify(orders).delete(order);
    }
}