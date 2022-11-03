package com.techriff.userdetails.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class RolePermissionMapPK implements Serializable {

    private int role_id;
    private int permissionId;
    
    public RolePermissionMapPK(int role_id, int permissionId) {
        this.role_id = role_id;
        this.permissionId = permissionId;
    }

}
