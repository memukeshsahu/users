package com.techriff.userdetails.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@Entity
@Table(name = "RolePermissionMap")
public class RolesPermissionMap  {
    @EmbeddedId
	private RolePermissionMapPK id;
}
