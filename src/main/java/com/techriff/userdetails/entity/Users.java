package com.techriff.userdetails.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import javax.persistence.ParameterMode;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.techriff.userdetails.dto.SpUsersDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

	
	@Entity
	@SqlResultSetMapping(
		name = "SpUsersDto", classes = {
			@ConstructorResult(targetClass = SpUsersDto.class,
			columns = {
				@ColumnResult(name = "user_id"),
				@ColumnResult(name = "first_name"),
				@ColumnResult(name = "last_name"),
				@ColumnResult(name = "email_adress"),
				@ColumnResult(name="dob"),
				@ColumnResult(name = "address_id"),
				@ColumnResult(name = "address"),
				@ColumnResult(name = "city"),
				@ColumnResult(name = "state"),
				@ColumnResult(name = "zip_code")


			}
			)
		})
	@Table(name = "tblUserDetails")
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@NamedStoredProcedureQueries(
		{ 
			@NamedStoredProcedureQuery
			(
				name="firstStoredProcedure",procedureName = "sp_find_user_by_name_and_email",resultSetMappings = {"SpUsersDto"},
				parameters = {
					@StoredProcedureParameter(mode = ParameterMode.IN,name = "email",type = String.class),
					@StoredProcedureParameter(mode = ParameterMode.IN,name = "name",type = String.class)
					
				}
			)
		}
	)
	public class Users  {
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
		
		
		private String passwordType="Primary";

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
