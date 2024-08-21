package com.example.ims.repo;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.ims.model.Product;

public interface ProductRepo extends MongoRepository<Product, String> {
    List<Product> findByUserName(String userName);
}
