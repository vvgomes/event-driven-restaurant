package com.restaurant.menu.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.Wither;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Data
@Wither
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class Item {
    @Id
    String id;
    String description;
    BigDecimal price;
    Integer popularity;

    private Item() {
        this(null, null, null, 0);
    }

    public Item(String id, String description, BigDecimal price) {
        this(id, description, price, 0);
    }

    public Item increasePopularity() {
        return this.withPopularity(popularity + 1);
    }
}
