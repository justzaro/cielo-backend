package com.example.cielobackend.service.implementation;

import com.example.cielobackend.dto.AttributeDtoResponse;
import com.example.cielobackend.exception.ResourceDoesNotExistException;
import com.example.cielobackend.model.Attribute;
import com.example.cielobackend.model.AttributeValue;
import com.example.cielobackend.repository.AttributeRepository;
import com.example.cielobackend.repository.AttributeValueRepository;
import com.example.cielobackend.service.AttributeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.w3c.dom.Attr;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.cielobackend.common.ExceptionMessages.*;

@Service
@RequiredArgsConstructor
public class AttributeServiceImpl implements AttributeService {
    private final AttributeRepository attributeRepository;
    private final AttributeValueRepository attributeValueRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public AttributeDtoResponse getAttributeById(long id) {
        Attribute attribute = attributeRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException(ATTRIBUTE_DOES_NOT_EXIST));
        return modelMapper.map(attribute, AttributeDtoResponse.class);
    }

    @Override
    public List<AttributeDtoResponse> getAllAttributes() {
        return attributeRepository
               .findAll()
               .stream()
               .map(attribute -> modelMapper.map(attribute, AttributeDtoResponse.class))
               .collect(Collectors.toList());
    }

    @Override
    public AttributeDtoResponse renameAttribute(long id, String name) {
        Attribute attribute = attributeRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException(ATTRIBUTE_DOES_NOT_EXIST));
        attribute.setName(name);
        attributeRepository.save(attribute);
        return modelMapper.map(attribute, AttributeDtoResponse.class);
    }

    @Override
    public void deleteAttribute(long id) {
        Attribute attribute = attributeRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException(ATTRIBUTE_DOES_NOT_EXIST));
        attributeRepository.delete(attribute);
    }
}
