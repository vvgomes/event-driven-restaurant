package com.restaurant.customers.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.axonframework.serialization.Revision;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@Revision("1")
public class CustomerSignedUpEvent {
    String id;
    String email;
}
