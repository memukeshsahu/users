package com.techriff.userdetails.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techriff.userdetails.entity.AddressType;

@Repository
public interface AddressTypeRepository extends JpaRepository<AddressType, Integer> {

}
