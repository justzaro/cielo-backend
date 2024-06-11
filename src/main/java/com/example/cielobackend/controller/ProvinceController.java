package com.example.cielobackend.controller;

import com.example.cielobackend.dto.ProvinceDtoResponse;
import com.example.cielobackend.service.ProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.basePath}/provinces")
@RequiredArgsConstructor
public class ProvinceController {
    private final ProvinceService provinceService;

    @GetMapping
    public List<ProvinceDtoResponse> getAllProvinces() {
        return provinceService.getAllProvinces();
    }
}
