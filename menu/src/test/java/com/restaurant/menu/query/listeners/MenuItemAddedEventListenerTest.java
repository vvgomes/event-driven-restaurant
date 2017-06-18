package com.restaurant.menu.query.listeners;

import com.restaurant.menu.events.MenuItemAddedEvent;
import com.restaurant.menu.query.Item;
import com.restaurant.menu.query.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static java.util.UUID.randomUUID;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MenuItemAddedEventListenerTest {

    @Mock
    ItemRepository items;
    MenuItemAddedEventListener listener;

    @Before
    public void setUp() throws Exception {
        listener = new MenuItemAddedEventListener(items);
    }

    @Test
    public void addsNewItem() throws Exception {
        String id = randomUUID().toString();

        listener.on(new MenuItemAddedEvent(id, "Pepperoni", new BigDecimal(10)));
        verify(items).save(refEq(new Item(id, "Pepperoni", new BigDecimal(10))));
    }
}