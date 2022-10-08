package com.techriff.userdetails.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techriff.userdetails.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Integer>{

	Users findByEmailAdress(String reqestEmail);

}
