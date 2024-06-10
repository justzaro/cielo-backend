package com.example.cielobackend.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitMQConfig {
    @Value("${rabbitmq.images-exchange.name}")
    private String imagesExchange;

    @Value("${rabbitmq.image-user-mapping-q.name}")
    private String imageUserMappingQueue;
    @Value("${rabbitmq.image-listing-mapping-q.name}")
    private String imageListingMappingQueue;
    @Value("${rabbitmq.image-category-mapping-q.name}")
    private String imageCategoryMappingQueue;
    @Value("${rabbitmq.image-listing-mapping-dlq.name}")
    private String imageListingMappingDlq;
    @Value("${rabbitmq.image-category-mapping-dlq.name}")
    private String imageCategoryMappingDlq;
    @Value("${rabbitmq.image-user-mapping-dlq.name}")
    private String imageUserMappingDlq;


    @Value("${rabbitmq.image-user-mapping-q.routing-key}")
    private String imageUserMappingQueueRoutingKey;
    @Value("${rabbitmq.image-listing-mapping-q.routing-key}")
    private String imageListingMappingQueueRoutingKey;
    @Value("${rabbitmq.image-category-mapping-q.routing-key}")
    private String imageCategoryMappingQueueRoutingKey;
    @Value("${rabbitmq.image-listing-mapping-dlq.routing-key}")
    private String imageListingMappingDlqRoutingKey;
    @Value("${rabbitmq.image-category-mapping-dlq.routing-key}")
    private String imageCategoryMappingDlqRoutingKey;
    @Value("${rabbitmq.image-user-mapping-dlq.routing-key}")
    private String imageUserMappingDlqRoutingKey;

    @Bean
    public SimpleMessageConverter converter() {
        SimpleMessageConverter converter = new SimpleMessageConverter();
        converter.setAllowedListPatterns(List.of(
                "org.example.EntityImageDto"
        ));
        return converter;
    }

    @Bean
    public Queue imageUserMappingQueue() {
        return new Queue(imageUserMappingQueue, false);
    }
    @Bean
    public Queue imageListingMappingQueue() {
        return new Queue(imageListingMappingQueue, false);
    }
    @Bean
    public Queue imageCategoryMappingQueue() {
        return new Queue(imageCategoryMappingQueue, false);
    }

    @Bean
    public Queue listingDeadLetterQueue() {
        return new Queue(imageListingMappingDlq, true);
    }

    @Bean
    public Queue categoryDeadLetterQueue() {
        return new Queue(imageCategoryMappingDlq, true);
    }

    @Bean
    public Queue userDeadLetterQueue() {
        return new Queue(imageUserMappingDlq, true);
    }


    @Bean
    public TopicExchange imagesExchange() {
        return new TopicExchange(imagesExchange);
    }


    @Bean
    public Binding bindingImageUserMappingQueue(Queue imageUserMappingQueue, TopicExchange imagesExchange) {
        return BindingBuilder
               .bind(imageUserMappingQueue)
               .to(imagesExchange)
               .with(imageUserMappingQueueRoutingKey);
    }

    @Bean
    public Binding bindingImageListingMappingQueue(Queue imageListingMappingQueue, TopicExchange imagesExchange) {
        return BindingBuilder
               .bind(imageListingMappingQueue)
               .to(imagesExchange)
               .with(imageListingMappingQueueRoutingKey);
    }

    @Bean
    public Binding bindingImageCategoryMappingQueue(Queue imageCategoryMappingQueue, TopicExchange imagesExchange) {
        return BindingBuilder
               .bind(imageCategoryMappingQueue)
               .to(imagesExchange)
               .with(imageCategoryMappingQueueRoutingKey);
    }

    @Bean
    public Binding bindingListingDeadLetterQueue(Queue listingDeadLetterQueue, TopicExchange imagesExchange) {
        return BindingBuilder
                .bind(listingDeadLetterQueue)
                .to(imagesExchange)
                .with(imageListingMappingDlqRoutingKey);
    }

    @Bean
    public Binding bindingCategoryDeadLetterQueue(Queue categoryDeadLetterQueue, TopicExchange imagesExchange) {
        return BindingBuilder
                .bind(categoryDeadLetterQueue)
                .to(imagesExchange)
                .with(imageCategoryMappingDlqRoutingKey);
    }

    @Bean
    public Binding bindingUserDeadLetterQueue(Queue userDeadLetterQueue, TopicExchange imagesExchange) {
        return BindingBuilder
                .bind(userDeadLetterQueue)
                .to(imagesExchange)
                .with(imageUserMappingDlqRoutingKey);
    }
}
