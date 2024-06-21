package com.example.cielobackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class ListingSearchParameters {
    private double minPrice = 0.0;
    private double maxPrice = 100000000;
    private List<String> eurostandard;
    private List<String> doors;
    private List<String> engineCount;
    private List<String> seats;
    private List<String> condition;
    private List<String> engine;
    private List<String> coupe;
    private List<String> model;
    private List<String> colour;
    private List<String> gearbox;
    private List<String> termsOfSale;
    private List<String> importedFrom;
    private List<String> comfort;
    private List<String> safety;
    private List<String> others;
    private List<String> heating;
    private List<String> type;
}
