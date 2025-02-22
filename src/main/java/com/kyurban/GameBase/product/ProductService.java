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

    public Optional<Product> getProdByID(int id) {
        return productRepository.findById(id);
    }

    public List<Product> getProdByName(String name) {
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

    public Product updateProduct(int id, Product product) {
        Optional<Product> existingProductOptional = productRepository.findById(id);
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
            throw new RuntimeException("Product not found with id: " + id);
        }
    }

    public void deleteProduct(int id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        }
    }
}
