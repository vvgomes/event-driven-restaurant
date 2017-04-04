package com.restaurant.orders.config;

import com.restaurant.orders.command.api.OrdersCommandsController;
import com.restaurant.orders.query.Order;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class OrdersPagedResourceProcessor implements ResourceProcessor<PagedResources<Resource<Order>>> {

    @Override
    public PagedResources<Resource<Order>> process(PagedResources<Resource<Order>> resources) {
        resources.add(linkTo(methodOn(OrdersCommandsController.class).getCommands()).withRel("commands"));
        return resources;
    }
}

