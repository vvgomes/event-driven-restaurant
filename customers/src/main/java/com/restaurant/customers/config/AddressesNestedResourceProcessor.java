package com.restaurant.customers.config;

import com.restaurant.customers.command.api.AddressesCommandsController;
import com.restaurant.customers.query.Address;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class AddressesNestedResourceProcessor implements ResourceProcessor<Resources<Resource<Address>>> {

    @Override
    public Resources<Resource<Address>> process(Resources<Resource<Address>> resources) {
        String[] linkParts = resources.getLink("self").getHref().split("/");
        String customerId = linkParts[linkParts.length - 2];

        try {
            UUID.fromString(customerId);
            resources.add(linkTo(methodOn(AddressesCommandsController.class).getCommands(customerId)).withRel("commands"));
        } catch (Exception e) {}

        return resources;
    }
}

