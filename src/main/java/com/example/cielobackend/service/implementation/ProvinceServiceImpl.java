package com.example.cielobackend.service.implementation;

import com.example.cielobackend.dto.ProvinceCitiesDtoResponse;
import com.example.cielobackend.repository.ProvinceRepository;
import com.example.cielobackend.service.ProvinceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProvinceServiceImpl implements ProvinceService {
    private final ProvinceRepository provinceRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ProvinceCitiesDtoResponse> getAllProvinces() {
        return provinceRepository
                .findAll()
                .stream()
                .map((province) -> modelMapper.map(province, ProvinceCitiesDtoResponse.class))
                .toList();
    }
}
