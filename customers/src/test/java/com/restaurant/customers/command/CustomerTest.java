package com.restaurant.customers.command;

import com.restaurant.customers.events.CustomerSignedUpEvent;
import com.restaurant.customers.events.AddressAddedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import static java.util.UUID.randomUUID;

public class CustomerTest {
    FixtureConfiguration<Customer> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = new AggregateTestFixture<>(Customer.class);
    }

    @Test
    public void signUpCustomer() throws Exception {
        String id = randomUUID().toString();

        fixture
            .givenNoPriorActivity()
            .when(new SignUpCustomerCommand(id, "jdoe@gmail.com"))
            .expectEvents(new CustomerSignedUpEvent(id, "jdoe@gmail.com"));
    }

    @Test
    public void signUpCustomer_withInvalidEmail() throws Exception {
        String id = randomUUID().toString();

        fixture
            .givenNoPriorActivity()
            .when(new SignUpCustomerCommand(id, "jdoegmail.com"))
            .expectException(IllegalArgumentException.class);
    }

    @Test
    public void addDeliveryAddress() throws Exception {
        String customerId = randomUUID().toString();
        String addressId = randomUUID().toString();

        fixture
            .given(new CustomerSignedUpEvent(customerId, "jdoe@gmail.com"))
            .when(new AddAddressCommand(customerId, addressId, "home", "555 Main St"))
            .expectEvents(new AddressAddedEvent(customerId, addressId, "home", "555 Main St"));
    }

    @Test
    public void addDeliveryAddress_withDuplicatedNickName() throws Exception {
        String customerId = randomUUID().toString();
        String addressId = randomUUID().toString();

        fixture
            .given(
                new CustomerSignedUpEvent(customerId, "jdoe@gmail.com"),
                new AddressAddedEvent(customerId, addressId, "home", "555 Main St"))
            .when(new AddAddressCommand(customerId, addressId, "home", "555 Main St"))
            .expectException(IllegalStateException.class);
    }
}