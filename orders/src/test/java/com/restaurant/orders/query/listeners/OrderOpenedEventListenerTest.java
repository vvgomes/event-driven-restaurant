package com.restaurant.orders.query.listeners;

import com.restaurant.orders.events.OrderOpenedEvent;
import com.restaurant.orders.query.Customer;
import com.restaurant.orders.query.CustomerRepository;
import com.restaurant.orders.query.Order;
import com.restaurant.orders.query.OrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.UUID.randomUUID;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderOpenedEventListenerTest {

    @Mock
    CustomerRepository customers;

    @Mock
    OrderRepository orders;

    OrderOpenedEventListener listener;

    @Before
    public void setUp() throws Exception {
        listener = new OrderOpenedEventListener(customers, orders);
    }

    @Test
    public void addsNewOrderWithExistingCustomer() throws Exception {
        String customerId = randomUUID().toString();
        String orderId = randomUUID().toString();

        Customer customer = new Customer(customerId);
        when(customers.findOne(customerId)).thenReturn(customer);

        listener.on(new OrderOpenedEvent(orderId, customerId));
        verify(orders).save(refEq(new Order(orderId, customer)));
    }
}