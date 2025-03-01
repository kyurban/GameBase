package com.kyurban.GameBase.cartItem;

import com.kyurban.GameBase.product.Product;
import com.kyurban.GameBase.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/cart")
public class CartItemController {

    private final CartItemService cartItemService;

    @Autowired
    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping
    public List<CartItem> getCartItems(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) Integer quantity){
        if (name != null) {
            return cartItemService.getCartItemByName(name);
        } else if (price != null) {
            return cartItemService.getCartItemByPrice(price);
        } else if (quantity != null) {
            return cartItemService.getCartItemByQuantity(quantity);
        } else {
            return cartItemService.getCart();
        }
    }

    @PostMapping
    public ResponseEntity<CartItem> addCartItem(@RequestBody CartItem cartItem) {
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
