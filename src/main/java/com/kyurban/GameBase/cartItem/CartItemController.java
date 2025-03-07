package com.kyurban.GameBase.cartItem;

import com.kyurban.GameBase.product.Product;
import com.kyurban.GameBase.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/cart")
public class CartItemController {

    private final CartItemService cartItemService;
    private final ProductService productService;

    @Autowired
    public CartItemController(CartItemService cartItemService, ProductService productService) {
        this.cartItemService = cartItemService;
        this.productService = productService;
    }

    @GetMapping
    public String viewCart(Model model) {
        List<CartItem> cartItems = cartItemService.getCart();

        double total = cartItemService.getTotal();

        model.addAttribute("cartItems", cartItemService.getCart());
        model.addAttribute("total", total);

        return "cart";
    }

    @PostMapping
    @Transactional
    public String addCartItem(@ModelAttribute CartItem cartItem) {
        Product product = productService.getProdByName(cartItem.getName()).orElse(null);

        if (product == null) {
            return "redirect:/products";
        }

        if (product.getStock() == 0 || product.getStock() < cartItem.getQuantity()) {
            return "redirect:/products";
        }

        List<CartItem> existingCartItems = cartItemService.getCartItemByName(cartItem.getName());

        if (!existingCartItems.isEmpty()) {
            CartItem existingCartItem = existingCartItems.get(0);
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItem.getQuantity());
            existingCartItem.setPrice(existingCartItem.getQuantity() * product.getPrice());
            cartItemService.updateCartItem(existingCartItem);
        } else {
            cartItem.setPrice(cartItem.getQuantity() * product.getPrice());
            cartItemService.addCartItem(cartItem);
        }

        productService.decrementStock(product.getId(), cartItem.getQuantity());
        return "redirect:/products";
    }

    @PostMapping("/remove/{productName}")
    @Transactional
    public String removeCartItem(@PathVariable String productName) {
        List<CartItem> cartItems = cartItemService.getCartItemByName(productName);

        if (cartItems.isEmpty()) {
            return "redirect:/cart";
        }

        CartItem cartItem = cartItems.get(0);
        Product product = productService.getProdByName(productName).orElse(null);

        if (product == null) {
            return "redirect:/cart";
        }

        if (cartItem.getQuantity() > 1) {
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            cartItem.setPrice(cartItem.getQuantity() * product.getPrice());
            cartItemService.updateCartItem(cartItem);
        } else {
            cartItemService.deleteCartItem(cartItem.getName());
        }

        productService.incrementStock(product.getId(), 1);
        return "redirect:/cart";
    }
}
