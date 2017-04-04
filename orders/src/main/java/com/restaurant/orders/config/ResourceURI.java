package com.restaurant.orders.config;

import lombok.experimental.FieldDefaults;

import java.net.URI;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ResourceURI {
    private final String[] parts;

    public ResourceURI(String raw) {
        parts = URI.create(raw).getPath().split("/");
    }

    public String getId() {
        return parts[parts.length - 1];
    }

    public String getParentId() {
        return parts[parts.length - 2];
    }
}
