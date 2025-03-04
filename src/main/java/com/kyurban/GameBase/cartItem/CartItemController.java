package com.kyurban.GameBase.cartItem;

import com.kyurban.GameBase.product.Product;
import com.kyurban.GameBase.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
        model.addAttribute("cartItems", cartItemService.getCart());
        return "cart";
    }

    @PostMapping
    public ResponseEntity<CartItem> addCartItem(@ModelAttribute CartItem cartItem) {
        System.out.println("Entering addCartItem method");

        Product product = productService.getProdByName(cartItem.getName()).orElse(null);

        if (product != null) {
            System.out.println("Product found: " + product.getName());

            product = productService.decrementStock(product.getId(), cartItem.getQuantity());
            if (product != null) {
                System.out.println("Product updated successfully.");
            } else {
                System.out.println("Failed to update product stock.");
            }
        } else {
            System.out.println("Product not found with name: " + cartItem.getName());
        }


        CartItem createdCartItem = cartItemService.addCartItem(cartItem);

        return new ResponseEntity<>(createdCartItem, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CartItem> updateCartItem(@RequestBody CartItem cartItem) {
        CartItem resultCartItem = cartItemService.updateCartItem(cartItem);
        if (resultCartItem != null) {
            return new ResponseEntity<>(resultCartItem, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{cartItemName}")
    public ResponseEntity<String> deleteCartItem(@PathVariable String cartItemName) {
        cartItemService.deleteCartItem(cartItemName);
        return new ResponseEntity<>("CartItem deleted successfully", HttpStatus.OK);
    }
}
