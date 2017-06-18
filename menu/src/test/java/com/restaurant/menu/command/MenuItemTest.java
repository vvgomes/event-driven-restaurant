package com.restaurant.menu.command;

import com.restaurant.menu.events.MenuItemAddedEvent;
import com.restaurant.menu.events.MenuItemModifiedEvent;
import com.restaurant.menu.events.MenuItemRemovedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static java.util.UUID.randomUUID;

public class MenuItemTest {
    FixtureConfiguration<MenuItem> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = new AggregateTestFixture<>(MenuItem.class);
    }

    @Test
    public void addMenuItem() throws Exception {
        String id = randomUUID().toString();

        fixture
            .givenNoPriorActivity()
            .when(new AddMenuItemCommand(id, "Pepperoni", new BigDecimal(10)))
            .expectEvents(new MenuItemAddedEvent(id, "Pepperoni", new BigDecimal(10)));
    }

    @Test
    public void addMenuItem_withEmptyDescription() throws Exception {
        String id = randomUUID().toString();

        fixture
            .givenNoPriorActivity()
            .when(new AddMenuItemCommand(id, "", new BigDecimal(12)))
            .expectException(IllegalArgumentException.class);
    }

    @Test
    public void modifyMenuItem() throws Exception {
        String id = randomUUID().toString();

        fixture
            .given(new MenuItemAddedEvent(id, "Pepperoni", new BigDecimal(10)))
            .when(new ModifyMenuItemCommand(id, "Ultimate Pepperoni", new BigDecimal(12)))
            .expectEvents(new MenuItemModifiedEvent(id, "Ultimate Pepperoni", new BigDecimal(12)));
    }

    @Test
    public void modifyMenuItem_withEmptyDescription() throws Exception {
        String id = randomUUID().toString();

        fixture
            .given(new MenuItemAddedEvent(id, "Pepperoni", new BigDecimal(10)))
            .when(new ModifyMenuItemCommand(id, "", new BigDecimal(12)))
            .expectException(IllegalArgumentException.class);
    }

    @Test
    public void removeMenuItem() throws Exception {
        String id = randomUUID().toString();

        fixture
            .given(new MenuItemAddedEvent(id, "Pepperoni", new BigDecimal(10)))
            .when(new RemoveMenuItemCommand(id))
            .expectEvents(new MenuItemRemovedEvent(id));
    }
}