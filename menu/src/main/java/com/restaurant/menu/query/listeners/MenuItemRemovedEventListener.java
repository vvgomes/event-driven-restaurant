package com.restaurant.menu.query.listeners;

import com.restaurant.menu.events.MenuItemRemovedEvent;
import com.restaurant.menu.query.ItemRepository;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Component
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class MenuItemRemovedEventListener {

    @Autowired
    ItemRepository items;

    @EventHandler
    public void on(MenuItemRemovedEvent event) {
        Optional
            .ofNullable(items.findOne(event.getId()))
            .ifPresent(items::delete);
    }
}
