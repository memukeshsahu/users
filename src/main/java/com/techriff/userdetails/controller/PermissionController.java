package com.techriff.userdetails.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.techriff.userdetails.entity.Permission;
import com.techriff.userdetails.service.PermissionService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "Bearer Authentication")

public class PermissionController {
    @Autowired
    private PermissionService permissionService;
    @PostMapping("/permissions")
    public ResponseEntity<Permission>addPermissions(@RequestBody Permission permissions)
    {
        Permission addPermissions=permissionService.addListOfPermission(permissions);
        return new ResponseEntity<>(addPermissions,new HttpHeaders(),HttpStatus.CREATED);
    }
    @GetMapping("/permissions")
    public ResponseEntity<List<Permission>> getPermission()
    {
        List<Permission> permissions = permissionService.getAllPermissions();
        return new ResponseEntity<>(permissions,new HttpHeaders(),HttpStatus.OK);

    }

    
}
