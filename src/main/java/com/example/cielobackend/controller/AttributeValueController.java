package com.example.cielobackend.controller;

import com.example.cielobackend.dto.AttributeDtoResponse;
import com.example.cielobackend.dto.AttributeValueDtoResponse;
import com.example.cielobackend.model.AttributeValue;
import com.example.cielobackend.service.AttributeValueService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.basePath}/attribute-values")
@RequiredArgsConstructor
public class AttributeValueController {
    private final AttributeValueService attributeValueService;

    @PostMapping("/{id}")
    public AttributeValueDtoResponse addAttributeValue(@PathVariable long id,
                                                       @RequestBody @NotBlank String value) {
        return attributeValueService.addAttributeValue(id, value);
    }

    @PatchMapping("/{id}")
    public AttributeValueDtoResponse renameAttributeValue(@PathVariable long id,
                                                          @RequestBody @NotBlank String value) {
        return attributeValueService.renameAttributeValue(id, value);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttributeValue(@PathVariable long id) {
        attributeValueService.deleteAttributeValue(id);
        return ResponseEntity
               .noContent()
               .build();
    }
}
