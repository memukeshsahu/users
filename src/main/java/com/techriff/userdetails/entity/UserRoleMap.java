package com.techriff.userdetails.entity;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Entity
@Table(name = "tbl_user_user_role")
public class UserRoleMap {
	
	@EmbeddedId
	private UserRoleMapPK id;
	
	
	

}
