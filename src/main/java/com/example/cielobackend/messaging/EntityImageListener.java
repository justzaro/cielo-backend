package com.example.cielobackend.messaging;

import com.example.cielobackend.exception.ResourceDoesNotExistException;
import com.example.cielobackend.service.CategoryService;
import com.example.cielobackend.service.ListingImageService;
import com.example.cielobackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.example.EntityImageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntityImageListener {
    @Value("${rabbitmq.images-exchange.name}")
    private String imagesExchange;
    @Value("${rabbitmq.image-user-mapping-dlq.routing-key}")
    private String imageUserMappingDlqRoutingKey;
    @Value("${rabbitmq.image-listing-mapping-q.routing-key}")
    private String imageListingMappingDlqRoutingKey;
    @Value("${rabbitmq.image-category-mapping-q.routing-key}")
    private String imageCategoryMappingDlqRoutingKey;

    private final UserService userService;
    private final CategoryService categoryService;
    private final ListingImageService listingImageService;
    private final RabbitTemplate rabbitTemplate;
    private final Logger logger = LoggerFactory.getLogger(EntityImageListener.class);

    @RabbitListener(queues = "${rabbitmq.image-listing-mapping-q.name}")
    public void receiveListingImageDto(EntityImageDto entityImageDto) {
        try {
            listingImageService.addListingImage(entityImageDto.getId(), entityImageDto.getName());
        } catch (ResourceDoesNotExistException e) {
            logger.error(e.getMessage());
            rabbitTemplate.convertAndSend(imagesExchange,
                                          imageListingMappingDlqRoutingKey,
                                          entityImageDto);
        }
    }

    @RabbitListener(queues = "${rabbitmq.image-category-mapping-q.name}")
    public void receiveCategoryImageDto(EntityImageDto entityImageDto) {
        try {
            categoryService.setCategoryImageName(entityImageDto.getId(), entityImageDto.getName());
        } catch (ResourceDoesNotExistException e) {
            logger.error(e.getMessage());
            rabbitTemplate.convertAndSend(imagesExchange,
                                          imageCategoryMappingDlqRoutingKey,
                                          entityImageDto);
        }
    }

    @RabbitListener(queues = "${rabbitmq.image-user-mapping-q.name}")
    public void receiveUserImageDto(EntityImageDto entityImageDto) {
        try {
            userService.setProfilePictureName(entityImageDto.getId(), entityImageDto.getName());
        } catch (ResourceDoesNotExistException e) {
            logger.error(e.getMessage());
            rabbitTemplate.convertAndSend(imagesExchange,
                                          imageUserMappingDlqRoutingKey,
                                          entityImageDto);
        }
    }
}
