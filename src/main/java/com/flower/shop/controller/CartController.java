package com.flower.shop.controller;

import com.flower.shop.entity.Flower;
import com.flower.shop.service.FlowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FlowerController {

    private final FlowerService flowerService;

    @GetMapping("/flowers")
    public ResponseEntity<List<Flower>> getAllFlowers() {
        return ResponseEntity.ok(flowerService.getAllFlowers());
    }

    @GetMapping("/flowers/{id}")
    public ResponseEntity<Flower> getFlowerById(@PathVariable Long id) {
        return ResponseEntity.ok(flowerService.getFlowerById(id));
    }

    @GetMapping("/flowers/search")
    public ResponseEntity<List<Flower>> searchFlowers(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String occasion,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {
        return ResponseEntity.ok(flowerService.searchFlowers(keyword, category, occasion, minPrice, maxPrice));
    }
}
