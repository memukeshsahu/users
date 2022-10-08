package com.techriff.userdetails.jwtAutontication.models;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable{
	
	 private final String jwt;

	    public AuthenticationResponse(String jwt) {
	        this.jwt = jwt;
	    }

	    public String getJwt() {
	        return jwt;
	    }

}
