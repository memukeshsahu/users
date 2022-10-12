package com.techriff.userdetails.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techriff.userdetails.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

    @Query(value = "SELECT * FROM Address n WHERE n.address_type_id =1 and n.user_address_fk=:userid", nativeQuery = true)
    Optional<Address> findPrimary(int userid);

    String query = "SELECT * FROM Address n WHERE n.user_address_fk=:userId";

    @Query(value = query, nativeQuery = true)
    List<Address> findAddress(int userId);

}
