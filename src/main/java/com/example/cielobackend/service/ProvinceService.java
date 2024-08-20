package com.example.cielobackend.service;

import com.example.cielobackend.dto.ProvinceCitiesDtoResponse;
import com.example.cielobackend.dto.ProvinceDtoResponse;

import java.util.List;

public interface ProvinceService {
    List<ProvinceCitiesDtoResponse> getAllProvinces();
}
