package com.restaurant.orders.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.Wither;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

import static java.util.Collections.emptyList;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Data
@Wither
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class Customer {
    @Id
    String id;

    @RestResource(exported = false)
    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    List<Order> orders;

    @RestResource(exported = false)
    @OneToMany(mappedBy = "customer")
    List<Address> addresses;

    public Customer(String id) {
        this(id, emptyList(), emptyList());
    }

    private Customer() {
        this(null, emptyList(), emptyList());
    }
}
