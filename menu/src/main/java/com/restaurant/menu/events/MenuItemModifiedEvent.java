package com.restaurant.menu.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.axonframework.serialization.Revision;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@Revision("1")
public class MenuItemModifiedEvent {
    String id;
    String description;
    BigDecimal price;
}
