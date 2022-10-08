package com.techriff.userdetails.entity;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tbl_userRole")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor

public class UsersRole {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private int id;
	private String role;
	//@ManyToMany(fetch = FetchType.LAZY,mappedBy = "usersRole")
	//@JsonBackReference
	//private List<Users> user;

}
