package com.kyurban.GameBase.product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProdById(int productId) {
        System.out.println("Fetching product with ID: " + productId);
        return productRepository.findById(productId);
    }

    public Optional<Product> getProdByName(String name) {
        return productRepository.findByName(name);
    }

    public List<Product> getProdByPrice(double price) {
        return productRepository.findByPrice(price);
    }

    public List<Product> getProdByStock(int stock) {
        return productRepository.findByStock(stock);
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Product product) {
        Optional<Product> existingProductOptional = productRepository.findById(product.getId());
        if (existingProductOptional.isPresent()) {
            Product existingProduct = existingProductOptional.get();
            if (product.getName() != null) {
                existingProduct.setName(product.getName());
            }
            if (product.getPrice() > 0.0) {
                existingProduct.setPrice(product.getPrice());
            }
            if (product.getStock() >= 0) {
                existingProduct.setStock(product.getStock());
            }
            return productRepository.save(existingProduct);
        } else {
            return null;
        }
    }

    public Product decrementStock(int productId, int quantityToDecrement) {
        System.out.println("Entering decrementStock method for product ID: " + productId);
        Optional<Product> existingProductOptional = productRepository.findById(productId);

        if (existingProductOptional.isPresent()) {
            Product existingProduct = existingProductOptional.get();
            System.out.println("Current stock before decrement: " + existingProduct.getStock());

            if (existingProduct.getStock() >= quantityToDecrement) {
                existingProduct.setStock(existingProduct.getStock() - quantityToDecrement);
                System.out.println("Updated stock after decrement: " + existingProduct.getStock());
                return productRepository.save(existingProduct);
            } else {
                System.out.println("Insufficient stock for product with ID: " + productId);
            }
        } else {
            System.out.println("Product with ID " + productId + " not found.");
        }

        return null;
    }

    public void deleteProduct(String productName) {
        productRepository.deleteByName(productName);
    }
}
