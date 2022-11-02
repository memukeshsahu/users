package com.techriff.userdetails.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techriff.userdetails.entity.PasswordResetToken;
@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
@Query("SELECT n from PasswordResetToken n where n.token=:token")
	PasswordResetToken findbyToken(String token);

}
