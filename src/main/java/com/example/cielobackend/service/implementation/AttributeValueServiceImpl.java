package com.example.cielobackend.service.implementation;

import com.example.cielobackend.dto.AttributeDtoResponse;
import com.example.cielobackend.dto.AttributeValueDtoResponse;
import com.example.cielobackend.exception.ResourceAlreadyExistsException;
import com.example.cielobackend.exception.ResourceDoesNotExistException;
import com.example.cielobackend.model.Attribute;
import com.example.cielobackend.model.AttributeValue;
import com.example.cielobackend.repository.AttributeRepository;
import com.example.cielobackend.repository.AttributeValueRepository;
import com.example.cielobackend.service.AttributeValueService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static com.example.cielobackend.common.ExceptionMessages.ATTRIBUTE_DOES_NOT_EXIST;
import static com.example.cielobackend.common.ExceptionMessages.DUPLICATE_ATTRIBUTE_VALUE;

@Service
@RequiredArgsConstructor
public class AttributeValueServiceImpl implements AttributeValueService {

    private final AttributeValueRepository attributeValueRepository;
    private final AttributeRepository attributeRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public AttributeValueDtoResponse renameAttributeValue(long attributeId, String value) {
        AttributeValue attributeValue = attributeValueRepository.findById(attributeId)
                .orElseThrow(() -> new ResourceDoesNotExistException(ATTRIBUTE_DOES_NOT_EXIST));
        attributeValue.setValue(value);
        attributeValue = attributeValueRepository.save(attributeValue);
        return modelMapper.map(attributeValue, AttributeValueDtoResponse.class);
    }

    @Override
    public AttributeValueDtoResponse addAttributeValue(long attributeId, String value) {
        if (attributeValueRepository.findByValue(value).isPresent()) {
            throw new ResourceAlreadyExistsException(DUPLICATE_ATTRIBUTE_VALUE);
        }
        Attribute attribute = attributeRepository.findById(attributeId)
                .orElseThrow(() -> new ResourceDoesNotExistException(ATTRIBUTE_DOES_NOT_EXIST));
        AttributeValue attributeValue = new AttributeValue();
        attributeValue.setValue(value);
        attributeValue.setAttribute(attribute);
        attributeValue = attributeValueRepository.save(attributeValue);
        return modelMapper.map(attributeValue, AttributeValueDtoResponse.class);
    }

    @Override
    public void deleteAttributeValue(long attributeId) {
        AttributeValue attributeValue = attributeValueRepository.findById(attributeId)
                .orElseThrow(() -> new ResourceDoesNotExistException(ATTRIBUTE_DOES_NOT_EXIST));
        attributeValueRepository.delete(attributeValue);
    }
}
