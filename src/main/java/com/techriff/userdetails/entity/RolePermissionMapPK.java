package com.techriff.userdetails.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class RolePermissionMapPK implements Serializable {

    private int roleId_FK;
    private int permissionId_FK;
    
    public RolePermissionMapPK(int role_id, int permissionId) {
        this.roleId_FK = role_id;
        this.permissionId_FK = permissionId;
    }

}
