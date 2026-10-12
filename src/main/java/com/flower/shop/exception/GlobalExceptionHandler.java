package com.flower.shop.controller;

import com.flower.shop.dto.FlowerRequest;
import com.flower.shop.entity.Flower;
import com.flower.shop.entity.Order;
import com.flower.shop.service.FlowerService;
import com.flower.shop.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final FlowerService flowerService;
    private final OrderService orderService;

    @PostMapping("/flowers")
    public ResponseEntity<Flower> createFlower(@Valid @RequestBody FlowerRequest request) {
        return ResponseEntity.ok(flowerService.createFlower(request));
    }

    @PutMapping("/flowers/{id}")
    public ResponseEntity<Flower> updateFlower(@PathVariable Long id, @Valid @RequestBody FlowerRequest request) {
        return ResponseEntity.ok(flowerService.updateFlower(id, request));
    }

    @DeleteMapping("/flowers/{id}")
    public ResponseEntity<String> deleteFlower(@PathVariable Long id) {
        flowerService.deleteFlower(id);
        return ResponseEntity.ok("Flower removed successfully");
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
}
