package com.restaurant.orders.query.listeners;

import com.restaurant.menu.events.MenuItemAddedEvent;
import com.restaurant.orders.query.MenuItem;
import com.restaurant.orders.query.MenuItemRepository;
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
    MenuItemRepository menu;
    MenuItemAddedEventListener listener;

    @Before
    public void setUp() throws Exception {
        listener = new MenuItemAddedEventListener(menu);
    }

    @Test
    public void addsNewMenuItem() throws Exception {
        String id = randomUUID().toString();

        listener.on(new MenuItemAddedEvent(id, "Pepperoni", new BigDecimal(10)));
        verify(menu).save(refEq(new MenuItem(id, new BigDecimal(10))));
    }
}