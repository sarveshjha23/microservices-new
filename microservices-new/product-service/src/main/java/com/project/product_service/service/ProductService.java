package com.project.product_service.service;


import com.project.product_service.dto.ProductRequest;
import com.project.product_service.dto.ProductResponse;
import com.project.product_service.model.Product;

import com.project.product_service.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

@Slf4j
public class ProductService {
    ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }




    public void createProduct(ProductRequest productRequest){
        Product product =Product.builder().name(productRequest.getName()).description(productRequest.getDescription()).price(productRequest.getPrice()).build();
        productRepository.save(product);

        System.out.println(product.getId());
    }

    public List<ProductResponse> getAllproducts(){
      List<Product>products=productRepository.findAll();
     return products.stream().map(product -> mapToProductResponse(product)).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        ProductResponse productResponse = ProductResponse.builder().id(product.getId()).name(product.getName()).price(product.getPrice()).description(product.getDescription()).build();
        return productResponse;
    }
}
