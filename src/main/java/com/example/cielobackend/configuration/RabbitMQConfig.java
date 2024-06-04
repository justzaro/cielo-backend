package com.example.cielobackend.configuration;

import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitMQConfig {
    @Bean
    public SimpleMessageConverter converter() {
        SimpleMessageConverter converter = new SimpleMessageConverter();
        converter.setAllowedListPatterns(List.of(
                "org.example.EntityImageDto"
        ));
        return converter;
    }

    @Bean
    public Queue listingDeadLetterQueue() {
        return new Queue("image-to-listing-mapping-dlq", true);
    }

    @Bean
    public Queue categoryDeadLetterQueue() {
        return new Queue("image-to-category-mapping-dlq", true);
    }

    @Bean
    public Queue imagesToBeDeletedQueue() {
        return new Queue("images-to-be-deleted", false);
    }
}
