package com.project.product_service.repository;


import com.project.product_service.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component

public interface ProductRepository extends MongoRepository<Product,String> {
}
