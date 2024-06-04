package com.example.cielobackend.controller;

import com.example.cielobackend.dto.AttributeDtoResponse;
import com.example.cielobackend.service.AttributeService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cielo/attributes")
@RequiredArgsConstructor
public class AttributeController {
    private final AttributeService attributeService;

    @GetMapping("/{id}")
    public AttributeDtoResponse getAttributeById(@PathVariable long id) {
        return attributeService.getAttributeById(id);
    }

    @GetMapping
    public List<AttributeDtoResponse> getAllAttributes() {
        return attributeService.getAllAttributes();
    }

    @PatchMapping("/{id}")
    public AttributeDtoResponse renameAttribute(@PathVariable long id,
                                                @RequestBody @NotBlank String name) {
        return attributeService.renameAttribute(id, name);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttribute(@PathVariable long id) {
        attributeService.deleteAttribute(id);
        return ResponseEntity
               .noContent()
               .build();
    }
}
