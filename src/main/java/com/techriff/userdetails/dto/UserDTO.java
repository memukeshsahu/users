package com.techriff.userdetails.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class UserDTO {
	
	private int id;
	private String emailAddress;
	private String firstName;
	private String middleName;
	private String lastName;
//	private String password;
	
	private Date dob;
	private String profilePicture;
	
	List<UserRoleMapDTO> roles ;
	List<AddressDto> addressDtos;
	

}
