package com.techriff.userdetails.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.techriff.userdetails.entity.Permission;
import com.techriff.userdetails.repository.PermissionRepository;
@Service
public class PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    public List<Permission> addListOfPermission(List<Permission> permissions) {

        // for (Permission permission : permissions) {

        //     permission.setPermission(permission.getPermission());
        // }
        List<Permission> saveData= permissionRepository.saveAll(permissions);
        return saveData;
    }

    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    

}
