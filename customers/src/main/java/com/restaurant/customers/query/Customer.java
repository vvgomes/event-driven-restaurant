package com.restaurant.customers.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.Wither;

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
    String email;

    @OneToMany(mappedBy = "customer")
    List<Address> addresses;

    public Customer(String id, String email) {
        this.id = id;
        this.email = email;
        this.addresses = emptyList();
    }

    private Customer() {
        this(null, null, null);
    }
}
