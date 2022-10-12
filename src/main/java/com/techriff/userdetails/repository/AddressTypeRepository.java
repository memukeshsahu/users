package com.techriff.userdetails.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techriff.userdetails.entity.Address;
import com.techriff.userdetails.entity.AddressType;

@Repository
public interface AddressTypeRepository extends JpaRepository<AddressType, Integer> {

    @Query("SELECT n FROM AddressType n WHERE n.id =:addressTypeId")
    Optional<AddressType> findByAddressTypeId(int addressTypeId);
    @Query("SELECT n FROM AddressType n WHERE n.id =1")
    AddressType findPrimary();
   
}
