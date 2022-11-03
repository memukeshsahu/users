package com.techriff.userdetails.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techriff.userdetails.entity.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    
}
