package com.techriff.userdetails.dto;

import java.util.List;

import lombok.Data;

@Data
public class UsersRolePermissionDto {

    private int userId;
    private String userName;
   // private String UserEmailAddress;
    private List<RolesJwtDto> roles;
    
}
