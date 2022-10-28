package com.techriff.userdetails.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techriff.userdetails.entity.TemporaryPassword;
@Repository
public interface TemporaryPasswordRepository extends JpaRepository<TemporaryPassword, Integer>{

    String query="select * from temporary_password n where n.fk_user_temp_password=:id order by  n.id desc limit 1";
    @Query(value = query,nativeQuery = true)
    Optional<TemporaryPassword> findTempPassword(int id);
    @Query("Select n from TemporaryPassword n where n.tempPassword=:password ")
    TemporaryPassword findbyTempPassword(String password);

}
