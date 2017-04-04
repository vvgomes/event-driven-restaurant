package com.restaurant.menu.command;

import com.restaurant.menu.events.MenuItemAddedEvent;
import com.restaurant.menu.events.MenuItemModifiedEvent;
import com.restaurant.menu.events.MenuItemRemovedEvent;
import lombok.experimental.FieldDefaults;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.common.Assert;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import static lombok.AccessLevel.PRIVATE;
import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
@FieldDefaults(level = PRIVATE)
public class MenuItem {
    @AggregateIdentifier
    String id;
    Boolean active;

    private MenuItem() {}

    @CommandHandler
    public MenuItem(AddMenuItemCommand command) {
        Assert.isFalse(command.getDescription().isEmpty(), () -> "Description must be present.");
        apply(new MenuItemAddedEvent(command.getId(), command.getDescription(), command.getPrice()));
    }

    @CommandHandler
    public void handle(ModifyMenuItemCommand command) {
        Assert.state(active, () -> "Item is inactive.");
        Assert.isFalse(command.getDescription().isEmpty(), () -> "Description must be present.");
        apply(new MenuItemModifiedEvent(id, command.getDescription(), command.getPrice()));
    }

    @CommandHandler
    public void handle(RemoveMenuItemCommand command) {
        apply(new MenuItemRemovedEvent(id));
    }

    @EventSourcingHandler
    public void on(MenuItemAddedEvent event) {
        id = event.getId();
        active = true;
    }

    @EventSourcingHandler
    public void on(MenuItemRemovedEvent event) {
        active = false;
    }
}
