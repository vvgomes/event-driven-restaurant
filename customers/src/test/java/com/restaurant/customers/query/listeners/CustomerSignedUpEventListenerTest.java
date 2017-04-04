package com.restaurant.customers.query.listeners;

import com.restaurant.customers.events.CustomerSignedUpEvent;
import com.restaurant.customers.query.Customer;
import com.restaurant.customers.query.CustomerRepository;
import com.restaurant.customers.query.listeners.CustomerSignedUpEventListener;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.UUID.randomUUID;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CustomerSignedUpEventListenerTest {

    @Mock
    CustomerRepository customers;
    CustomerSignedUpEventListener listener;

    @Before
    public void setUp() throws Exception {
        listener = new CustomerSignedUpEventListener(customers);
    }

    @Test
    public void addsANewCustomer() throws Exception {
        String id = randomUUID().toString();

        listener.on(new CustomerSignedUpEvent(id, "jdoe@gmail.com"));
        verify(customers).save(refEq(new Customer(id, "jdoe@gmail.com")));
    }
}