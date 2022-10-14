package com.techriff.userdetails.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ManyToAny;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.techriff.userdetails.validationAnnotation.ValidateAdressType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

	
	@Entity
	@Table(name = "tblUserDetails")
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public class Users {
		@Id
		@Column(name = "userId")
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int id;
		@NotNull
		@Column(nullable = false,length = 255)
		@NotEmpty(message = "first name can't be empty")
		private String firstName;
		private String middleName; 
		@NotNull
		@NotEmpty(message = "last name can't be empty")
		private String lastName;
		@NotNull
		@NotEmpty(message = "email can't be empty")
		@Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$",message = "Invalid Email adress")
		@Column(unique = true)
		private String emailAdress;
		@Size(min = 6,message = "password length should be greater than 6")
		@NotEmpty(message = "Password can't be empty")
		@Column(nullable = false)
		private String password;

		@OneToMany(targetEntity = Address.class,cascade = CascadeType.ALL)
		@JoinColumn(name = "user_address_fk",referencedColumnName = "userId")
		private  List<Address> address;

		
//		@JsonFormat(pattern="yyyy/MM/dd")
		private Date dob;
		//private String profilePicture;

//		@ManyToMany(fetch = FetchType.LAZY)
//		@JoinTable(name = "tbl_user_user_role",
//		joinColumns = {
//				@JoinColumn(name="user_id",referencedColumnName = "userId")
//		},
//		inverseJoinColumns = {
//				@JoinColumn(name="user_role_id",referencedColumnName = "role_id")
//		}
//		
//		)
	//	@JsonManagedReference
		@Transient
		private List<Integer> usersRoleId;

}
