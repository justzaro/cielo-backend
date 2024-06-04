package com.example.cielobackend.messaging;

import com.example.cielobackend.exception.ResourceDoesNotExistException;
import com.example.cielobackend.service.CategoryService;
import com.example.cielobackend.service.ListingImageService;
import lombok.RequiredArgsConstructor;
import org.example.EntityImageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntityImageListener {
    private final CategoryService categoryService;
    private final ListingImageService listingImageService;
    private final AmqpTemplate amqpTemplate;
    private final Logger logger = LoggerFactory.getLogger(EntityImageListener.class);

    @RabbitListener(queues = "image-to-listing-mapping-queue")
    public void receiveListingImageDto(EntityImageDto entityImageDto) {
        try {
            listingImageService.addListingImage(entityImageDto.getId(), entityImageDto.getName());
        } catch (ResourceDoesNotExistException e) {
            logger.error(e.getMessage());
            amqpTemplate.convertAndSend("image-to-listing-mapping-dlq", entityImageDto);
        }
    }

    @RabbitListener(queues = "image-to-category-mapping-queue")
    public void receiveCategoryImageDto(EntityImageDto entityImageDto) {
        try {
            categoryService.setCategoryImageName(entityImageDto.getId(), entityImageDto.getName());
        } catch (ResourceDoesNotExistException e) {
            logger.error(e.getMessage());
            amqpTemplate.convertAndSend("image-to-category-mapping-dlq", entityImageDto);
        }
    }
}
