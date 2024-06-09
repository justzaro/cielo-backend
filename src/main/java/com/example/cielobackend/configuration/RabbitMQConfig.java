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

    @Value("${rabbitmq.image-listing-mapping-q.name}")
    private String imageListingMappingQueue;
    @Value("${rabbitmq.image-category-mapping-q.name}")
    private String imageCategoryMappingQueue;
    @Value("${rabbitmq.image-listing-mapping-dlq.name}")
    private String imageListingMappingDlq;
    @Value("${rabbitmq.image-category-mapping-dlq.name}")
    private String imageCategoryMappingDlq;

    @Value("${rabbitmq.image-listing-mapping-q.routing-key}")
    private String imageListingMappingQueueRoutingKey;
    @Value("${rabbitmq.image-category-mapping-q.routing-key}")
    private String imageCategoryMappingQueueRoutingKey;
    @Value("${rabbitmq.image-listing-mapping-dlq.routing-key}")
    private String imageListingMappingDlqRoutingKey;
    @Value("${rabbitmq.image-category-mapping-dlq.routing-key}")
    private String imageCategoryMappingDlqRoutingKey;

    @Bean
    public SimpleMessageConverter converter() {
        SimpleMessageConverter converter = new SimpleMessageConverter();
        converter.setAllowedListPatterns(List.of(
                "org.example.EntityImageDto"
        ));
        return converter;
    }

    @Bean
    public Queue imageToListingMappingQueue() {
        return new Queue(imageListingMappingQueue, false);
    }

    @Bean
    public Queue imageToCategoryMappingQueue() {
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
    public TopicExchange imagesExchange() {
        return new TopicExchange(imagesExchange);
    }

    @Bean
    public Binding bindingImageToListingMappingQueue(Queue imageToListingMappingQueue, TopicExchange imagesExchange) {
        return BindingBuilder
               .bind(imageToListingMappingQueue)
               .to(imagesExchange)
               .with(imageListingMappingQueueRoutingKey);
    }

    @Bean
    public Binding bindingImageToCategoryMappingQueue(Queue imageToCategoryMappingQueue, TopicExchange imagesExchange) {
        return BindingBuilder
                .bind(imageToCategoryMappingQueue)
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
}
