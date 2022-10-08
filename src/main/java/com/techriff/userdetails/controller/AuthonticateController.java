package com.techriff.userdetails.controller;

import java.util.Date;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.techriff.userdetails.Exception.InCorrectException;
import com.techriff.userdetails.entity.Users;
import com.techriff.userdetails.jwtAutontication.models.AuthenticationRequest;
import com.techriff.userdetails.jwtAutontication.security.configuration.jwt.util.JwtUtill;
import com.techriff.userdetails.jwtAutontication.security.configuration.userDetails.MyUserDetailsService;
import com.techriff.userdetails.repository.UsersRepository;

import io.swagger.v3.oas.annotations.Operation;
import net.minidev.json.JSONObject;

@SuppressWarnings("unused")
@RestController
public class AuthonticateController {
	private static Logger log= Logger.getLogger(AuthonticateController.class);

	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private MyUserDetailsService  myUserDetailsService;
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private JwtUtill jwtTokenUtil;
	
	@Operation(summary = "Gets an bearer token", description = "Gets an bearer token")
	@PostMapping("/authonticate")
	public ResponseEntity<JSONObject> createAuthonticationToken(@RequestBody AuthenticationRequest authonticationRequest) throws Exception
	{
		JSONObject result=new JSONObject();
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authonticationRequest.getUsername(), authonticationRequest.getPassword()));
			
		}
		catch (Exception e) {
		throw new InCorrectException("Incorrect credential",e);
		}
		final UserDetails userDetails =myUserDetailsService.loadUserByUsername(authonticationRequest.getUsername());
		final String jwt=jwtTokenUtil.generateToken(userDetails);
//		Users user=new Users();
//		user=usersRepository.findByEmailAdress(authonticationRequest.getUsername());
		
		Users  getUserDetails = usersRepository.findByEmailAdress(authonticationRequest.getUsername());
		
		JwtUtill jwtUtill= new JwtUtill();
		Date date= jwtUtill.extractExpiration(jwt);
		result.put("Token Expire Time", date);
		result.put("jwtToken", jwt);
		result.put("firstName", getUserDetails.getFirstName());
		result.put("lastName", getUserDetails.getLastName());
		result.put("userId", getUserDetails.getId());
		result.put("emailId", getUserDetails.getEmailAdress());
		
		return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.OK);
		
	}

}
