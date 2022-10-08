package com.techriff.userdetails.pages;

import org.springframework.data.domain.Sort;

import lombok.Data;

@Data
public class UsersPage {
	private int pageNumber=0;
	private int pageSize=10;
	private Sort.Direction sortDirection=Sort.Direction.ASC;
	private String sortBy="id";

}
