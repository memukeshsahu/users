package com.techriff.userdetails.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    private int id;
    private String address;
    private String addressType;
    private int zipCode;
    private String city;
	private String state;

}
