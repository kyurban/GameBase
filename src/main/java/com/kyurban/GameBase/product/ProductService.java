package com.kyurban.GameBase.product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.List;

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

    public List<Product> getProdByID() {
        return productRepository.findAll();
    }

    public List<Product> getProdByName() {
        return productRepository.findAll();
    }

    public List<Product> getProdByPrice(double price) {
        return productRepository.findAll();
    }

    public List<Product> getProdByStock(int stock) {
        return productRepository.findAll();
    }

    public List<Product> addProduct() {
        return productRepository.findAll();
    }

    public List<Product> updateProduct(Product product) {
        return productRepository.findAll();
    }

    public List<Product> deleteProduct() {
        return productRepository.findAll();
    }
}
