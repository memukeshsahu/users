package com.techriff.userdetails.pages;

import org.springframework.data.domain.Sort;

import lombok.Data;

@Data
public class RolesPage {
	int pageNumber=0;
	int pageSize=5;
	private Sort.Direction sortDirection=Sort.Direction.ASC;
	private String sortBy="id";


}
