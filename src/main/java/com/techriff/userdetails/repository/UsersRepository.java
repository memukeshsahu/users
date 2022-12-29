package com.techriff.userdetails.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import com.techriff.userdetails.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Integer>{

	Users findByEmailAdress(String reqestEmail);

	//@Query(value = "call sp_find_user_by_name_and_email(:email,:name)",nativeQuery = true)
	@Procedure(name = "firstStoredProcedure")
    Map<String, ?> getUserDeatailsByNameAndEmail(String email, String name);

}
