package com.restaurant.orders.query.listeners;

import com.restaurant.orders.events.OrderPlacedEvent;
import com.restaurant.orders.query.Order;
import com.restaurant.orders.query.OrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.Collections.emptyList;
import static java.util.UUID.randomUUID;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderPlacedEventListenerTest {

    @Mock
    OrderRepository orders;
    OrderPlacedEventListener listener;

    @Before
    public void setUp() throws Exception {
        listener = new OrderPlacedEventListener(orders);
    }

    @Test
    public void placesExistingOrder() throws Exception {
        String id = randomUUID().toString();

        Order order = new Order(id, null);
        when(orders.findOne(id)).thenReturn(order);

        listener.on(new OrderPlacedEvent(id));
        verify(orders).save(refEq(new Order(id, null, null, emptyList(), true)));
    }
}