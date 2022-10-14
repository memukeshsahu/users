package com.techriff.userdetails.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ReportDTO {
	
	private int id;
	private String emailAdress;
	private String firstName;
	private String middleName;
	private String lastName;
//	private String password;
	private String city;
	private String state;
	private Date dob;
	private String profilePicture;
	private int zipCode;
	private String roles ;
	private String address;
	
}
