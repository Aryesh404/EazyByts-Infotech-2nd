package com.example.ims.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.ims.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
}
