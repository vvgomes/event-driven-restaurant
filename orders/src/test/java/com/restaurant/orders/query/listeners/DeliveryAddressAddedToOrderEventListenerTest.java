package com.restaurant.orders.query.listeners;

import com.restaurant.orders.events.DeliveryAddressAddedToOrderEvent;
import com.restaurant.orders.query.Address;
import com.restaurant.orders.query.AddressRepository;
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
public class DeliveryAddressAddedToOrderEventListenerTest {

    @Mock
    OrderRepository orders;

    @Mock
    AddressRepository addresses;

    DeliveryAddressAddedToOrderEventListener listener;

    @Before
    public void setUp() throws Exception {
        listener = new DeliveryAddressAddedToOrderEventListener(orders, addresses);
    }

    @Test
    public void addsDeliveryAddressToExistingOrder() throws Exception {
        String orderId = randomUUID().toString();
        String addressId = randomUUID().toString();

        Order order = new Order(orderId, null);
        when(orders.findOne(orderId)).thenReturn(order);

        Address address = new Address(addressId, "99 Madison NYC", null);
        when(addresses.findOne(addressId)).thenReturn(address);

        listener.on(new DeliveryAddressAddedToOrderEvent(orderId, addressId));
        verify(orders).save(refEq(new Order(orderId, null, address, emptyList(), false)));
    }
}