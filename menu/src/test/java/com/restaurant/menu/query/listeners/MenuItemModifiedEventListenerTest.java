package com.restaurant.menu.query.listeners;

import com.restaurant.menu.events.MenuItemModifiedEvent;
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
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MenuItemModifiedEventListenerTest {

    @Mock
    ItemRepository items;
    MenuItemModifiedEventListener listener;

    @Before
    public void setUp() throws Exception {
        listener = new MenuItemModifiedEventListener(items);
    }

    @Test
    public void updatesAnExistingItem() throws Exception {
        String id = randomUUID().toString();
        when(items.findOne(id)).thenReturn(new Item(id, "Pepperoni", new BigDecimal(10)));

        listener.on(new MenuItemModifiedEvent(id, "Ultimate Pepperoni", new BigDecimal(12)));
        verify(items).save(refEq(new Item(id, "Ultimate Pepperoni", new BigDecimal(12))));
    }

    @Test
    public void doesNothingWhenItemIsNotFound() throws Exception {
        String id = randomUUID().toString();
        when(items.findOne(id)).thenReturn(null);

        listener.on(new MenuItemModifiedEvent(id, "Ultimate Pepperoni", new BigDecimal(12)));
        verify(items, never()).save(any(Item.class));
    }
}