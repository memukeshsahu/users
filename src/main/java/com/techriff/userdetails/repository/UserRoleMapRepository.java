package com.techriff.userdetails.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techriff.userdetails.entity.UserRoleMap;
import com.techriff.userdetails.entity.UserRoleMapPK;

@Repository
public interface UserRoleMapRepository extends JpaRepository<UserRoleMap, UserRoleMapPK>,JpaSpecificationExecutor<UserRoleMap>{

	@Query("SELECT n FROM UserRoleMap n WHERE n.id.userId =:userId")
	List<UserRoleMap> findByUserId(int userId);

	
	@Query("SELECT n FROM UserRoleMap n WHERE n.id.userRoleId =:existingRoleId")

	List<UserRoleMap> findByRoleId(int existingRoleId);
	
	
	
	
}
