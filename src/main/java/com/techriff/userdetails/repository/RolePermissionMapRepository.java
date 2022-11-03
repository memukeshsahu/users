package com.techriff.userdetails.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techriff.userdetails.entity.RolePermissionMapPK;
import com.techriff.userdetails.entity.RolesPermissionMap;

@Repository
public interface RolePermissionMapRepository extends JpaRepository<RolesPermissionMap,RolePermissionMapPK> ,JpaSpecificationExecutor<RolesPermissionMap>{

    @Query("Select n from RolesPermissionMap n where n.id.role_id=:role_id")
    List<RolesPermissionMap> findByRoleId(int role_id);
    
}