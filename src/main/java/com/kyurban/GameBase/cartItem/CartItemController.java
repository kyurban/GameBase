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
    public ResponseEntity<String> addCartItem(@ModelAttribute CartItem cartItem) {
        Product product = productService.getProdByName(cartItem.getName()).orElse(null);

        if (product == null) {
            return new ResponseEntity<>("Product not found.", HttpStatus.NOT_FOUND);
        }

        if (product.getStock() == 0 || product.getStock() < cartItem.getQuantity()) {
            return new ResponseEntity<>("Product is out of stock.", HttpStatus.BAD_REQUEST);
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
        return new ResponseEntity<>("Item added to cart.", HttpStatus.CREATED);
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
        return new ResponseEntity<>("Cart item deleted successfully", HttpStatus.OK);
    }
}
