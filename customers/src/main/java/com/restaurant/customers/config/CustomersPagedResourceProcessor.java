package com.restaurant.customers.config;

import com.restaurant.customers.command.api.CustomersCommandsController;
import com.restaurant.customers.query.Customer;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class CustomersPagedResourceProcessor implements ResourceProcessor<PagedResources<Resource<Customer>>> {

    @Override
    public PagedResources<Resource<Customer>> process(PagedResources<Resource<Customer>> resources) {
        resources.add(linkTo(methodOn(CustomersCommandsController.class).getCommands()).withRel("commands"));
        return resources;
    }
}

