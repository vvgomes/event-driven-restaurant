package com.restaurant.menu.query.listeners;

import com.restaurant.menu.query.Item;
import com.restaurant.menu.query.ItemRepository;
import com.restaurant.orders.events.ItemAddedToOrderEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static java.util.UUID.randomUUID;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ItemAddedToOrderEventListenerTest {

    @Mock
    ItemRepository items;
    ItemAddedToOrderEventListener listener;

    @Before
    public void setUp() throws Exception {
        listener = new ItemAddedToOrderEventListener(items);
    }

    @Test
    public void addsNewItem() throws Exception {
        String orderId = randomUUID().toString();
        String orderItemId = randomUUID().toString();
        String menuItemId = randomUUID().toString();
        BigDecimal price = new BigDecimal(8.2);

        Item item = new Item(menuItemId, "Pepperoni", price, 5);
        when(items.findOne(menuItemId)).thenReturn(item);

        listener.on(new ItemAddedToOrderEvent(orderId, orderItemId, menuItemId, price, 2));
        verify(items).save(refEq(new Item(menuItemId, "Pepperoni", price, 6)));
    }
}