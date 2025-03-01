package com.kyurban.GameBase.cartItem;

import com.kyurban.GameBase.product.Product;
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

    public Optional<CartItem> getCartItemByID(int id) {
        return cartItemRepository.findById(id);
    }

    public List<CartItem> getCartItemByName(String name) {
        return cartItemRepository.findByName(name);
    }

    public List<CartItem> getCartItemByPrice(double price) {
        return cartItemRepository.findByPrice(price);
    }

    public List<CartItem> getCartItemByQuantity(int quantity) {
        return cartItemRepository.findByQuantity(quantity);
    }

    public CartItem addCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    public CartItem updateCartItem(CartItem cartItem) {
        Optional<CartItem> existingCartItemOptional = cartItemRepository.findById(cartItem.getId());
        if (existingCartItemOptional.isPresent()) {
            CartItem existingProduct = existingCartItemOptional.get();
            if (cartItem.getName() != null) {
                existingProduct.setName(cartItem.getName());
            }
            if (cartItem.getPrice() > 0.0) {
                existingProduct.setPrice(cartItem.getPrice());
            }
            if (cartItem.getQuantity() >= 0) {
                existingProduct.setQuantity(cartItem.getQuantity());
            }
            return cartItemRepository.save(existingProduct);
        } else {
            return null;
        }
    }

    public void deleteCartItem(String cartItemName) {
        cartItemRepository.deleteByName(cartItemName);
    }
}
