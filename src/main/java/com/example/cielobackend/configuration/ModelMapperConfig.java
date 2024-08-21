package com.example.cielobackend.configuration;

import com.example.cielobackend.dto.ListingDtoResponse;
import com.example.cielobackend.dto.ListingDtoUpdate;
import com.example.cielobackend.model.Listing;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<ListingDtoUpdate, Listing>() {
            @Override
            protected void configure() {
                skip(destination.getId());
                //skip(destination.getDetails());
                skip(destination.getCategory());
            }
        });
        return modelMapper;
    }
}
