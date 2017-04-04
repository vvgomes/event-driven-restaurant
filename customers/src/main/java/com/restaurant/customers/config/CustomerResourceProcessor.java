package com.restaurant.customers.config;

import com.restaurant.customers.command.api.CustomerCommandsController;
import com.restaurant.customers.query.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class CustomerResourceProcessor implements ResourceProcessor<Resource<Customer>> {

    @Autowired
    private RepositoryRestMvcConfiguration configuration;

    @Override
    public Resource<Customer> process(Resource<Customer> resource) {
        String id = resource.getContent().getId();
        resource.add(linkTo(methodOn(CustomerCommandsController.class).getCommands(id)).withRel("commands"));
        return resource;
    }
}

