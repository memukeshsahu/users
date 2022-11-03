package com.techriff.userdetails.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techriff.userdetails.entity.Permission;

public interface PermissionRepository  extends JpaRepository<Permission,Integer>{
    
}
