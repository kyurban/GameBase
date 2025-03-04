package com.kyurban.GameBase.cartItem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByName(String name);
    List<CartItem> findByPrice(double price);
    List<CartItem> findByQuantity(int quantity);
    void deleteByName(String CartItemName);
}
