package com.restaurant.customers.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AddressAddedEvent {
    String customerId;
    String addressId;
    String nickName;
    String location;
}
