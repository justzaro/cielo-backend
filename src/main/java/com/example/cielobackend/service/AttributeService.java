package com.example.cielobackend.service;

import com.example.cielobackend.dto.AttributeDtoResponse;

import java.util.List;

public interface AttributeService {
    AttributeDtoResponse getAttributeById(long id);
    List<AttributeDtoResponse> getAllAttributes();
    AttributeDtoResponse renameAttribute(long id, String name);
    void deleteAttribute(long id);
}
