package com.example.cielobackend.service;

import com.example.cielobackend.dto.AttributeDtoResponse;
import com.example.cielobackend.dto.AttributeValueDtoResponse;

public interface AttributeValueService {
    AttributeValueDtoResponse addAttributeValue(long attributeId, String value);
    AttributeValueDtoResponse renameAttributeValue(long attributeId, String value);
    void deleteAttributeValue(long attributeId);
}
