package com.kyurban.GameBase.product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getProducts(Model model) {
        model.addAttribute("products", productService.getProducts());
        return "products";
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product createdProduct = productService.addProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        Product resultProduct = productService.updateProduct(product);
        if (resultProduct != null) {
            return new ResponseEntity<>(resultProduct, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{productName}")
    public ResponseEntity<String> deleteProduct(@PathVariable String productName) {
        productService.deleteProduct(productName);
        return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
    }
}
