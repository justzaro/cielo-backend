package com.example.cielobackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.json.JSONPropertyName;

import java.util.List;

@Data
public class ListingDetailDtoResponse {
    private AttributeDtoResponse attribute;
    private String value;
    @JsonIgnore
    private List<ListingDetailValueDto> detailValues;
    private List<Long> selectedValues;
}
