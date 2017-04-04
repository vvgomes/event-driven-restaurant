package com.restaurant.menu.query.listeners;

import com.restaurant.menu.events.MenuItemAddedEvent;
import com.restaurant.menu.query.Item;
import com.restaurant.menu.query.ItemRepository;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;

@Component
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class MenuItemAddedEventListener {

    @Autowired
    ItemRepository items;

    @EventHandler
    public void on(MenuItemAddedEvent event) {
        items.save(new Item(event.getId(), event.getDescription(), event.getPrice()));
    }
}
