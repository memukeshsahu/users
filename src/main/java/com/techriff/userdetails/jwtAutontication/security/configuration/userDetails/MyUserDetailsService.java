package com.techriff.userdetails.jwtAutontication.security.configuration.userDetails;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.techriff.userdetails.entity.Users;
import com.techriff.userdetails.repository.UsersRepository;
@Service
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	
	private UsersRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Users existingUsers=userRepo.findByEmailAdress(email);
		String emailAdress=existingUsers.getEmailAdress();
		String Password=existingUsers.getPassword();
		return new User(emailAdress,Password,new ArrayList<>());
	}

}
