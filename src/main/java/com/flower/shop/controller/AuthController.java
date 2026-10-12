package com.flower.shop.service;

import com.flower.shop.dto.OrderRequest;
import com.flower.shop.entity.CartItem;
import com.flower.shop.entity.Flower;
import com.flower.shop.entity.Order;
import com.flower.shop.entity.OrderItem;
import com.flower.shop.entity.User;
import com.flower.shop.repository.CartItemRepository;
import com.flower.shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final AuthService authService;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public Order placeOrder(OrderRequest request) {
        User user = authService.getCurrentUser();
        List<CartItem> cartItems = cartItemRepository.findByUser(user);

        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

        BigDecimal total = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            Flower flower = cartItem.getFlower();
            if (flower.getStock() < cartItem.getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock for flower: " + flower.getName());
            }

            BigDecimal itemTotal = flower.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            total = total.add(itemTotal);

            OrderItem orderItem = new OrderItem();
            orderItem.setFlower(flower);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(flower.getPrice());
            orderItem.setOrder(null);
            orderItems.add(orderItem);

            flower.setStock(flower.getStock() - cartItem.getQuantity());
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setTotalAmount(total);
        order.setPaymentMethod(request.getPaymentMethod());
        order.setPaymentStatus("PENDING");
        order.setOrderStatus("PLACED");
        order.setShippingAddress(request.getShippingAddress());
        order.setCreatedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);

        for (OrderItem item : orderItems) {
            item.setOrder(savedOrder);
        }

        savedOrder.setOrderItems(orderItems);
        orderRepository.save(savedOrder);
        cartItemRepository.deleteAll(cartItems);

        return savedOrder;
    }

    public List<Order> getOrderHistory() {
        User user = authService.getCurrentUser();
        return orderRepository.findByUserOrderByCreatedAtDesc(user);
    }

    public Order getOrderById(Long id) {
        User user = authService.getCurrentUser();
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("You do not have access to this order");
        }

        return order;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
