package com.restaurant.customers.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.Wither;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Data
@ToString(exclude = "customer")
@Wither
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class Address {
    @Id
    String id;
    String nickName;
    String location;

    @ManyToOne
    Customer customer;

    private Address() {
        this(null, null, null, null);
    }
}
