package com.restaurant.orders.command.api;

public class Exceptions {
    public static class MenuItemNotFoundException extends RuntimeException {
        public MenuItemNotFoundException() {
            super("Menu item not found.");
        }
    }

    public static class AddressNotFoundException extends RuntimeException {
        public AddressNotFoundException() {
            super("Address not found.");
        }
    }

    public static class CustomerNotFoundException extends RuntimeException {
        public CustomerNotFoundException() {
            super("Customer not found.");
        }
    }
}
