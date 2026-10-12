package com.flower.shop.repository;

import com.flower.shop.entity.Flower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlowerRepository extends JpaRepository<Flower, Long> {
    List<Flower> findByNameContainingIgnoreCase(String name);
    List<Flower> findByCategoryIgnoreCase(String category);
    List<Flower> findByOccasionIgnoreCase(String occasion);
}
