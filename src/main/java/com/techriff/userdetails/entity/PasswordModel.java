package com.techriff.userdetails.entity;

import lombok.Data;

@Data
public class PasswordModel {
    private String email;
    
    private String newPassword;
    
    private String confirmPassword;
}
