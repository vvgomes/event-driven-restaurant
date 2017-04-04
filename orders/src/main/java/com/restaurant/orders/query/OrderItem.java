package com.restaurant.orders.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.Wither;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Data
@Wither
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class OrderItem {
    @Id
    String id;

    @ManyToOne
    Order order;

    @ManyToOne
    MenuItem menuItem;
    Integer quantity;

    private OrderItem() {
        this(null, null, null, null);
    }

    @JsonProperty(value = "subTotal")
    public BigDecimal getSubTotal() {
       return BigDecimal.valueOf(quantity.longValue()).multiply(menuItem.getPrice());
    }
}
