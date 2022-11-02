package com.techriff.userdetails.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techriff.userdetails.Exception.ResourceNotFoundException;
import com.techriff.userdetails.dto.EmailModel;
import com.techriff.userdetails.entity.PasswordModel;
import com.techriff.userdetails.entity.Users;
import com.techriff.userdetails.service.PasswordService;

@RestController
public class ForgotPasswordController {
    @Autowired
    private PasswordService passwordService;
    @PutMapping("/users/forgotPassword")
    public ResponseEntity<String>forgotPassword(@RequestBody EmailModel emailAddress,HttpServletRequest request) throws Exception{


        String forgotPassword=passwordService.sendVarificationMailForResetPassword(emailAddress.getEmailAddress().trim(),request);
        return new ResponseEntity<>(forgotPassword, new HttpHeaders(),HttpStatus.OK);
    }
//    @PostMapping("/users/resetPassword")
//    public ResponseEntity<String> resetPassword(@RequestBody EmailModel emailAddress, HttpServletRequest request) throws ResourceNotFoundException {
//        String forgotPassword=passwordService.sentResetPasswordToken(emailAddress.getEmailAddress().trim(),request);
//        //String mailStatus= "A varification mail is sent to your mail please check it";
//        return  new ResponseEntity<>(forgotPassword, new HttpHeaders(),HttpStatus.OK);
//        
//    }
    
    @GetMapping("/users/varifyMailForNewPassword")
    public ResponseEntity<String> varifyMailForNewPassword(@RequestParam String token) throws Exception
    {
    	
    	 String newTemporaryPassword=passwordService.resetPassword( token);
		return  new ResponseEntity<>(newTemporaryPassword, new HttpHeaders(),HttpStatus.OK);
    	
    }
  
}



