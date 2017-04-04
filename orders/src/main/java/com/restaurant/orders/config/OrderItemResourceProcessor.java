package com.restaurant.orders.config;

import com.restaurant.orders.command.api.OrderItemCommandsController;
import com.restaurant.orders.query.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class OrderItemResourceProcessor implements ResourceProcessor<Resource<OrderItem>> {

    @Autowired
    private RepositoryRestMvcConfiguration configuration;

    @Override
    public Resource<OrderItem> process(Resource<OrderItem> resource) {
        OrderItem orderItem = resource.getContent();
        String orderItemId = orderItem.getId();
        String orderId = orderItem.getOrder().getId();

        resource.add(linkTo(methodOn(OrderItemCommandsController.class).getCommands(orderId, orderItemId))
            .withRel("commands").expand(orderId, orderItemId));

        return resource;
    }
}

