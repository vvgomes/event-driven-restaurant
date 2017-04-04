package com.restaurant.orders.config;

import com.restaurant.orders.command.api.OrdersCommandsController;
import com.restaurant.orders.query.Order;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class OrdersResourceProcessor implements ResourceProcessor<Resources<Order>> {

    @Override
    public Resources<Order> process(Resources<Order> resources) {
        resources.add(linkTo(methodOn(OrdersCommandsController.class).getCommands()).withRel("commands"));
        return resources;
    }
}
