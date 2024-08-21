package com.example.ims.service;

import java.util.List;
import java.util.Optional;

import com.example.ims.model.Product;

public interface ProductService {

    // Method to get all products
    List<Product> getAllProducts();

    // Method to save a product with associated username
    void saveProduct(Product product, String userName);

    // Method to add a new product
    void addProduct(Product product);

    // Method to get a product by ID
    Optional<Product> getProductById(String id);

    // Method to delete a product by ID
    boolean deleteProductById(String id);

    // Method to get products for a specific user
    List<Product> getProductsForUser(String userName);
}
