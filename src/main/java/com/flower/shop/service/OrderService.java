package com.flower.shop.service;

import com.flower.shop.dto.AddToCartRequest;
import com.flower.shop.dto.UpdateCartItemRequest;
import com.flower.shop.entity.CartItem;
import com.flower.shop.entity.Flower;
import com.flower.shop.entity.User;
import com.flower.shop.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final FlowerService flowerService;
    private final AuthService authService;

    public List<CartItem> getCartItems() {
        User user = authService.getCurrentUser();
        return cartItemRepository.findByUser(user);
    }

    public CartItem addFlowerToCart(AddToCartRequest request) {
        User user = authService.getCurrentUser();
        Flower flower = flowerService.getFlowerById(request.getFlowerId());

        if (request.getQuantity() > flower.getStock()) {
            throw new IllegalArgumentException("Not enough stock available");
        }

        Optional<CartItem> existing = cartItemRepository.findByUserAndFlower(user, flower);
        if (existing.isPresent()) {
            CartItem item = existing.get();
            int newQuantity = item.getQuantity() + request.getQuantity();
            if (newQuantity > flower.getStock()) {
                throw new IllegalArgumentException("Stock limit exceeded");
            }
            item.setQuantity(newQuantity);
            return cartItemRepository.save(item);
        }

        CartItem cartItem = new CartItem();
        cartItem.setUser(user);
        cartItem.setFlower(flower);
        cartItem.setQuantity(request.getQuantity());
        return cartItemRepository.save(cartItem);
    }

    public CartItem updateCartItem(Long flowerId, UpdateCartItemRequest request) {
        User user = authService.getCurrentUser();
        Flower flower = flowerService.getFlowerById(flowerId);

        CartItem cartItem = cartItemRepository.findByUserAndFlower(user, flower)
                .orElseThrow(() -> new IllegalArgumentException("Item not found in cart"));

        if (request.getQuantity() > flower.getStock()) {
            throw new IllegalArgumentException("Not enough stock available");
        }

        cartItem.setQuantity(request.getQuantity());
        return cartItemRepository.save(cartItem);
    }

    public void removeCartItem(Long flowerId) {
        User user = authService.getCurrentUser();
        Flower flower = flowerService.getFlowerById(flowerId);
        CartItem cartItem = cartItemRepository.findByUserAndFlower(user, flower)
                .orElseThrow(() -> new IllegalArgumentException("Item not found in cart"));
        cartItemRepository.delete(cartItem);
    }
}
