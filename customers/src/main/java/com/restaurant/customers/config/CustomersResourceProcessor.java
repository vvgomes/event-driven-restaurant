package com.restaurant.customers.config;

import com.restaurant.customers.command.api.CustomersCommandsController;
import com.restaurant.customers.query.Customer;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class CustomersResourceProcessor implements ResourceProcessor<Resources<Customer>> {

    @Override
    public Resources<Customer> process(Resources<Customer> resources) {
        resources.add(linkTo(methodOn(CustomersCommandsController.class).getCommands()).withRel("commands"));
        return resources;
    }
}

