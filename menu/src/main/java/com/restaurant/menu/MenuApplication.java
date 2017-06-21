package com.restaurant.menu;

import com.rabbitmq.client.Channel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.axonframework.amqp.eventhandling.DefaultAMQPMessageConverter;
import org.axonframework.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.axonframework.serialization.Serializer;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static lombok.AccessLevel.PRIVATE;

@SpringBootApplication
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class MenuApplication {
    AmqpAdmin admin;

    public static void main(String[] args) {
        SpringApplication.run(MenuApplication.class, args);
    }

    @Bean
    public Exchange exchange(@Value("${axon.amqp.exchange}") String name) {
        Exchange exchange = ExchangeBuilder.fanoutExchange(name).build();
        admin.declareExchange(exchange);
        return exchange;
    }

    @Bean
    public Queue queue() {
        Queue queue = QueueBuilder.durable("MenuEvents").build();
        admin.declareQueue(queue);
        return queue;
    }

    @Bean
    public Binding binding(Exchange exchange, Queue queue) {
        Binding binding = BindingBuilder.bind(queue).to(exchange).with("*").noargs();
        admin.declareBinding(binding);
        return binding;
    }

    @Bean
    public SpringAMQPMessageSource menuMessageSource(Serializer serializer) {
        return new SpringAMQPMessageSource(new DefaultAMQPMessageConverter(serializer)) {
            @Override
            @RabbitListener(queues = "OrderEvents")
            public void onMessage(Message message, Channel channel) throws Exception {
                super.onMessage(message, channel);
            }
        };
    }
}
