package com.flower.shop.controller;

import com.flower.shop.dto.AddToCartRequest;
import com.flower.shop.dto.UpdateCartItemRequest;
import com.flower.shop.entity.CartItem;
import com.flower.shop.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/cart")
    public ResponseEntity<List<CartItem>> getCart() {
        return ResponseEntity.ok(cartService.getCartItems());
    }

    @PostMapping("/cart/add")
    public ResponseEntity<CartItem> addToCart(@Valid @RequestBody AddToCartRequest request) {
        return ResponseEntity.ok(cartService.addFlowerToCart(request));
    }

    @PutMapping("/cart/{flowerId}")
    public ResponseEntity<CartItem> updateCartItem(@PathVariable Long flowerId,
                                                 @Valid @RequestBody UpdateCartItemRequest request) {
        return ResponseEntity.ok(cartService.updateCartItem(flowerId, request));
    }

    @DeleteMapping("/cart/{flowerId}")
    public ResponseEntity<String> removeCartItem(@PathVariable Long flowerId) {
        cartService.removeCartItem(flowerId);
        return ResponseEntity.ok("Item removed from cart successfully");
    }
}
