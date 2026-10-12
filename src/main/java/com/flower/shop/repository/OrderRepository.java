package com.flower.shop.repository;

import com.flower.shop.entity.CartItem;
import com.flower.shop.entity.Flower;
import com.flower.shop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);
    Optional<CartItem> findByUserAndFlower(User user, Flower flower);
    void deleteByUser(User user);
}
