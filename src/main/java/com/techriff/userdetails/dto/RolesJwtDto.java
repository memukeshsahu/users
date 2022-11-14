package com.techriff.userdetails.dto;

import java.util.List;

import lombok.Data;
@Data
public class RolesJwtDto {
    private String role;
    private List<PermissionJwtDto> permission;

}
