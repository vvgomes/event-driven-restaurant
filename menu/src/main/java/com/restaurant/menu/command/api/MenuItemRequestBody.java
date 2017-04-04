package com.restaurant.menu.command.api;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class MenuItemRequestBody {
    String description;
    BigDecimal price;
}
