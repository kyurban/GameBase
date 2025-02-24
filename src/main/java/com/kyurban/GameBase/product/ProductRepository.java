package com.kyurban.GameBase.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByName(String name);
    List<Product> findByPrice(double price);
    List<Product> findByStock(int stock);
    void deleteByName(String productName);
}
