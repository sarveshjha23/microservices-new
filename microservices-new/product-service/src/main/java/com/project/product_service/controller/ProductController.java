package com.project.product_service.controller;

import com.project.product_service.dto.ProductRequest;
import com.project.product_service.dto.ProductResponse;
import com.project.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j

@RequestMapping("/api/product")
public class ProductController {
    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest){
     productService.createProduct(productRequest);

     return;

    }

   @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){
       System.out.println("Started get");
        return productService.getAllproducts();

   }
}
