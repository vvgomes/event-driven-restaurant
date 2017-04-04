package com.restaurant.orders.config;

import com.restaurant.orders.command.api.OrderCommandsController;
import com.restaurant.orders.query.Order;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class OrderResourceProcessor implements ResourceProcessor<Resource<Order>> {

    @Override
    public Resource<Order> process(Resource<Order> resource) {
        String id = resource.getContent().getId();
        resource.add(linkTo(methodOn(OrderCommandsController.class).getCommands(id)).withRel("commands"));
        return resource;
    }
}

