package com.techriff.userdetails.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techriff.userdetails.entity.UsersRole;

@Repository
public interface UsersRoleRepository extends JpaRepository<UsersRole, Integer>{

}
