package com.techriff.userdetails.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techriff.userdetails.Exception.ResourceNotFoundException;
import com.techriff.userdetails.entity.Users;
import com.techriff.userdetails.service.PasswordService;

@RestController
public class ForgotPasswordController {
    @Autowired
    private PasswordService passwordService;
    @PutMapping("/users/forgotPassword/{emailAddress}")
    public ResponseEntity<String>forgotPassword(@PathVariable String emailAddress) throws ResourceNotFoundException{
        
       
        String forgotPassword=passwordService.sentNewPassword(emailAddress);
        return new ResponseEntity<>(forgotPassword, new HttpHeaders(),HttpStatus.OK);
    }
    

}
