package com.example.cielobackend.service.implementation;

import com.example.cielobackend.dto.ProvinceDtoResponse;
import com.example.cielobackend.model.Province;
import com.example.cielobackend.repository.ProvinceRepository;
import com.example.cielobackend.service.ProvinceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ProvinceServiceImpl implements ProvinceService {

    private final ProvinceRepository provinceRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ProvinceDtoResponse> getAllProvinces() {
        return provinceRepository
                .findAll()
                .stream()
                .map((province) -> modelMapper.map(province, ProvinceDtoResponse.class))
                .toList();
    }
}
