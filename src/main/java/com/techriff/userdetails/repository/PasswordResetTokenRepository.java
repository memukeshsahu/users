package com.techriff.userdetails.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techriff.userdetails.entity.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

}
