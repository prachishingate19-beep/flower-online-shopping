package com.flower.shop.service;

import com.flower.shop.dto.FlowerRequest;
import com.flower.shop.entity.Flower;
import com.flower.shop.repository.FlowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlowerService {

    private final FlowerRepository flowerRepository;

    public List<Flower> getAllFlowers() {
        return flowerRepository.findAll();
    }

    public Flower getFlowerById(Long id) {
        return flowerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Flower not found with id: " + id));
    }

    public List<Flower> searchFlowers(String keyword, String category, String occasion, Double minPrice, Double maxPrice) {
        List<Flower> flowers = flowerRepository.findAll();
        List<Flower> result = new ArrayList<>();

        for (Flower flower : flowers) {
            boolean match = true;

            if (keyword != null && !keyword.isBlank()) {
                match = flower.getName().toLowerCase().contains(keyword.toLowerCase())
                        || flower.getDescription() != null && flower.getDescription().toLowerCase().contains(keyword.toLowerCase());
            }

            if (match && category != null && !category.isBlank()) {
                match = flower.getCategory().equalsIgnoreCase(category);
            }

            if (match && occasion != null && !occasion.isBlank()) {
                match = flower.getOccasion().equalsIgnoreCase(occasion);
            }

            if (match && minPrice != null) {
                match = flower.getPrice().doubleValue() >= minPrice;
            }

            if (match && maxPrice != null) {
                match = flower.getPrice().doubleValue() <= maxPrice;
            }

            if (match) {
                result.add(flower);
            }
        }

        return result;
    }

    public Flower createFlower(FlowerRequest request) {
        Flower flower = new Flower();
        flower.setName(request.getName());
        flower.setDescription(request.getDescription());
        flower.setCategory(request.getCategory());
        flower.setOccasion(request.getOccasion());
        flower.setPrice(request.getPrice());
        flower.setStock(request.getStock());
        flower.setImageUrl(request.getImageUrl());
        flower.setFeatured(request.isFeatured());
        return flowerRepository.save(flower);
    }

    public Flower updateFlower(Long id, FlowerRequest request) {
        Flower flower = getFlowerById(id);
        flower.setName(request.getName());
        flower.setDescription(request.getDescription());
        flower.setCategory(request.getCategory());
        flower.setOccasion(request.getOccasion());
        flower.setPrice(request.getPrice());
        flower.setStock(request.getStock());
        flower.setImageUrl(request.getImageUrl());
        flower.setFeatured(request.isFeatured());
        return flowerRepository.save(flower);
    }

    public void deleteFlower(Long id) {
        Flower flower = getFlowerById(id);
        flowerRepository.delete(flower);
    }
}
