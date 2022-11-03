package com.techriff.userdetails.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

@Embeddable
public class UserRoleMapPK implements Serializable{
	
	


	public UserRoleMapPK(int userId, int userRoleId) {
		super();
		this.userId = userId;
		this.userRoleId = userRoleId;
	}

	
	@Column(name="user_id")
	private int userId;
	
	@Column(name="user_role_id")
	private int userRoleId;
	
	

}
