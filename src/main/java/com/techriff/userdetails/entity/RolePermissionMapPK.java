package com.techriff.userdetails.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class RolePermissionMapPK implements Serializable {
    //@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPk;
    private int roleId_FK;
    private int permissionId_FK;
    
    public RolePermissionMapPK(int role_id, int permissionId) {
        this.roleId_FK = role_id;
        this.permissionId_FK = permissionId;
    }

}
