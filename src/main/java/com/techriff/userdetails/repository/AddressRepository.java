package com.techriff.userdetails.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techriff.userdetails.entity.Address;
@Repository
public interface AddressRepository extends JpaRepository<Address,Integer>{
    @Query("SELECT n FROM Address n WHERE n.aid =:addressTypeId")
    List<Address> findByAddressType(int addressTypeId);

//    String query="select * from address n where n.user_address_fk=:userId and n.addressType='primary'";
//    @Query(name = query, nativeQuery = true)
//    List<Address> findByUserId(int userId);

}
