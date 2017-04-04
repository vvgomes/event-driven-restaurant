package com.restaurant.orders.query.listeners;

import com.restaurant.menu.events.MenuItemRemovedEvent;
import com.restaurant.orders.query.MenuItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MenuItemRemovedEventListenerTest {

    @Mock
    MenuItemRepository menu;
    MenuItemRemovedEventListener listener;

    @Before
    public void setUp() throws Exception {
        listener = new MenuItemRemovedEventListener(menu);
    }

    @Test
    public void removesMenuItem() throws Exception {
        String id = randomUUID().toString();
        listener.on(new MenuItemRemovedEvent(id));
        verify(menu).delete(eq(id));
    }
}