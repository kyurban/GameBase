package com.kyurban.GameBase.product;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    public List<Product> getProducts() {
        return List.of(new Product(1, "headphones", 30.00, 10));
    }

}
