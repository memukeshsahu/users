package com.techriff.userdetails.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@Entity
@Table(name = "RolePermissionMapTbl")
public class RolesPermissionMap  {
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPk;
    @EmbeddedId
	private RolePermissionMapPK id;
    
    
}
