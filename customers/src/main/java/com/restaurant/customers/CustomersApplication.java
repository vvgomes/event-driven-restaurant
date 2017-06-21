package com.restaurant.customers;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static lombok.AccessLevel.PRIVATE;

@SpringBootApplication
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CustomersApplication {
    AmqpAdmin admin;

    public static void main(String[] args) {
        SpringApplication.run(CustomersApplication.class, args);
    }

    @Bean
    public Exchange exchange(@Value("${axon.amqp.exchange}") String name) {
        Exchange exchange = ExchangeBuilder.fanoutExchange(name).build();
        admin.declareExchange(exchange);
        return exchange;
    }

    @Bean
    public Queue queue() {
        Queue queue = QueueBuilder.durable("CustomerEvents").build();
        admin.declareQueue(queue);
        return queue;
    }

    @Bean
    public Binding binding(Exchange exchange, Queue queue) {
        Binding binding = BindingBuilder.bind(queue).to(exchange).with("*").noargs();
        admin.declareBinding(binding);
        return binding;
    }
}
