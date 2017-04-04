package com.restaurant.orders.query.listeners;

import com.restaurant.customers.events.AddressAddedEvent;
import com.restaurant.orders.query.Address;
import com.restaurant.orders.query.AddressRepository;
import com.restaurant.orders.query.Customer;
import com.restaurant.orders.query.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.UUID.randomUUID;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AddressAddedEventListenerTest {

    @Mock
    CustomerRepository customers;
    @Mock
    AddressRepository addresses;

    AddressAddedEventListener listener;

    @Before
    public void setUp() throws Exception {
        listener = new AddressAddedEventListener(customers, addresses);
    }

    @Test
    public void addsANewAddressForExistingCustomer() throws Exception {
        String customerId = randomUUID().toString();
        String addressId = randomUUID().toString();

        Customer customer = new Customer(customerId);
        when(customers.findOne(customerId)).thenReturn(customer);

        listener.on(new AddressAddedEvent(customerId, addressId, "Home", "555 Main St"));
        verify(addresses).save(refEq(new Address(addressId, "555 Main St", customer)));
    }

    @Test
    public void doesNothingWhenCustomerIsNotFound() throws Exception {
        String customerId = randomUUID().toString();
        String addressId = randomUUID().toString();

        when(customers.findOne(customerId)).thenReturn(null);

        listener.on(new AddressAddedEvent(customerId, addressId, "Home", "555 Main St"));
        verify(addresses, never()).save(any(Address.class));
    }
}