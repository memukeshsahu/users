package com.techriff.userdetails.dto;

import java.util.List;

import lombok.Data;
@Data
public class RolesDto {
    private int roleId;
    private String role;
    private List<RolesPermissionMapDto> permission;
}
