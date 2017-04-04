package com.restaurant.menu.config;

import com.restaurant.menu.command.api.MenuItemsCommandsController;
import com.restaurant.menu.query.Item;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class ItemsResourceProcessor implements ResourceProcessor<PagedResources<Resource<Item>>> {

    @Override
    public PagedResources<Resource<Item>> process(PagedResources<Resource<Item>> resources) {
        resources.add(linkTo(methodOn(MenuItemsCommandsController.class).getCommands()).withRel("commands"));
        return resources;
    }
}

