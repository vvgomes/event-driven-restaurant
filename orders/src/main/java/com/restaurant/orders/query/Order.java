package com.restaurant.orders.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ZERO;
import static java.util.Collections.emptyList;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "foodOrder")
@Data
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class Order {
    @Id
    String id;

    @ManyToOne
    Customer customer;

    @ManyToOne
    Address address;

    @OneToMany(mappedBy = "order")
    List<OrderItem> orderItems;

    Boolean placed;

    public Order(String id, Customer customer) {
        this(id, customer, null, emptyList(), false);
    }

    private Order() {
        this(null, null, null, null, false);
    }

    public Order withAddress(Address address) {
        return new Order(id, customer, address, orderItems, placed);
    }

    public Order place() {
        return new Order(id, customer, address, orderItems, true);
    }

    @JsonProperty(value = "total")
    public BigDecimal total() {
        return orderItems
            .stream()
            .map(OrderItem::getSubTotal)
            .reduce(ZERO, BigDecimal::add);
    }
}
