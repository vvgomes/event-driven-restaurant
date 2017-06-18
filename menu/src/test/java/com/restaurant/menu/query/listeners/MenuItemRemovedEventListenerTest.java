package com.restaurant.menu.query.listeners;

import com.restaurant.menu.events.MenuItemRemovedEvent;
import com.restaurant.menu.query.Item;
import com.restaurant.menu.query.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static java.util.UUID.randomUUID;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MenuItemRemovedEventListenerTest {

    @Mock
    ItemRepository items;
    MenuItemRemovedEventListener listener;

    @Before
    public void setUp() throws Exception {
        listener = new MenuItemRemovedEventListener(items);
    }

    @Test
    public void deletesAnExistingItem() throws Exception {
        String id = randomUUID().toString();
        when(items.findOne(id)).thenReturn(new Item(id, "Pepperoni", new BigDecimal(10)));

        listener.on(new MenuItemRemovedEvent(id));
        verify(items).delete(refEq(new Item(id, "Pepperoni", new BigDecimal(10))));
    }

    @Test
    public void doesNothingWhenItemIsNotFound() throws Exception {
        String id = randomUUID().toString();
        when(items.findOne(id)).thenReturn(null);

        listener.on(new MenuItemRemovedEvent(id));
        verify(items, never()).delete(any(Item.class));
    }
}