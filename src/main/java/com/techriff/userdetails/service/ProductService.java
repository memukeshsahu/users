package com.techriff.userdetails.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techriff.userdetails.Exception.ResourceNotFoundException;
import com.techriff.userdetails.entity.Product;
import com.techriff.userdetails.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> addListOfProducts(List<Product> products) {
        List<Product> list=productRepository.saveAll(products);
        return list;
        
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getListOfProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(int id) throws ResourceNotFoundException {
        Product product =productRepository.findById(id).orElseThrow(
            ()-> new ResourceNotFoundException("Product Not Found wit id : "+id));
        return product;
    }

    public String deleteProduct(int id) throws ResourceNotFoundException {
        Product product =productRepository.findById(id).orElseThrow(
            ()-> new ResourceNotFoundException("Product Not Found wit id : "+id));
            productRepository.deleteById(id);
        return "Product deleted wit id : "+id;
    }

    public Product updateProduct(Product product, int id) throws ResourceNotFoundException {
        Product existinProduct =productRepository.findById(id).orElseThrow(
            ()-> new ResourceNotFoundException("Product Not Found wit id : "+id));
            existinProduct.setName(product.getName());
            existinProduct.setDescription(product.getDescription());
            existinProduct.setPrice(product.getPrice());
            existinProduct.setProductType(product.getProductType());
            
            Product SaveProduct=productRepository.save(existinProduct);
        return  SaveProduct;
    }
    
    
}
