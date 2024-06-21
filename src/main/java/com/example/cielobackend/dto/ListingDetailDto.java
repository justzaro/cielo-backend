package com.example.cielobackend.dto;

import com.example.cielobackend.model.Attribute;
import lombok.Data;

import java.util.List;

@Data
public class ListingDetailDto {
    private String value;
    private AttributeDtoResponse attribute;
    private List<ListingDetailValueDto> detailValues;
}
