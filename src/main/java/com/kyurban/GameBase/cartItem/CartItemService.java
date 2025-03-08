package com.kyurban.GameBase.cartItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    @Autowired
    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public List<CartItem> getCart() {
        return cartItemRepository.findAll();
    }

    public List<CartItem> getCartItemByName(String name) {
        return cartItemRepository.findByName(name);
    }

    public CartItem addCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    public CartItem updateCartItem(CartItem cartItem) {
        Optional<CartItem> existingCartItemOptional = cartItemRepository.findById(cartItem.getId());
        if (existingCartItemOptional.isPresent()) {
            CartItem existingCartItem = existingCartItemOptional.get();
            if (cartItem.getName() != null) {
                existingCartItem.setName(cartItem.getName());
            }
            if (cartItem.getPrice() > 0.0) {
                existingCartItem.setPrice(cartItem.getPrice());
            }
            if (cartItem.getQuantity() >= 0) {
                existingCartItem.setQuantity(cartItem.getQuantity());
            }
            return cartItemRepository.save(existingCartItem);
        } else {
            return null;
        }
    }

    public double getTotal() {
        List<CartItem> cartItems = cartItemRepository.findAll(); // Fetch all cart items
        double total = 0.0;
        for (CartItem item : cartItems) {
            total += item.getPrice() * item.getQuantity(); // Calculate total price for each item
        }
        return total;
    }

    public void deleteCartItem(String cartItemName) {
        cartItemRepository.deleteByName(cartItemName);
    }
}
