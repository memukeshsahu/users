package com.techriff.userdetails.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.techriff.userdetails.Exception.ResourceNotFoundException;
import com.techriff.userdetails.entity.Product;
import com.techriff.userdetails.service.ProductService;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "Bearer Authentication")


public class ProductController {
    @Autowired
    private ProductService productService;

    // @PostMapping("/products")
    // public ResponseEntity<List<Product>> addProducts(@RequestBody List<Product> products)
    // {
    //     List<Product> addProducts=productService.addListOfProducts(products);
    //     return new ResponseEntity<>(addProducts,new HttpHeaders(),HttpStatus.CREATED);
    // }
    @PostMapping("/product")
    public ResponseEntity<Product> addProduct(@RequestBody Product product)
    {
        Product addProduct=productService.addProduct(product);
        return new ResponseEntity<>(addProduct,new HttpHeaders(),HttpStatus.CREATED);
    }
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts()
    {
        List<Product> getProducts=productService.getListOfProducts();
        return new ResponseEntity<>(getProducts,new HttpHeaders(),HttpStatus.CREATED);
    }
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById( @PathVariable int id) throws ResourceNotFoundException
    {
        Product getProduct=productService.getProductById(id);
        return new ResponseEntity<>(getProduct,new HttpHeaders(),HttpStatus.CREATED);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) throws ResourceNotFoundException
    {
        String deleteProduct=productService.deleteProduct(id);
        return new ResponseEntity<>(deleteProduct,new HttpHeaders(),HttpStatus.CREATED);
    }
    
    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable int id) throws ResourceNotFoundException
    {
        Product updateProduct=productService.updateProduct(product,id);
        return new ResponseEntity<>(updateProduct,new HttpHeaders(),HttpStatus.OK);
    }

    
    
}
