package com.techriff.userdetails.jwtAutontication.security.configuration.userDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.techriff.userdetails.entity.TemporaryPassword;
import com.techriff.userdetails.entity.Users;
import com.techriff.userdetails.repository.TemporaryPasswordRepository;
import com.techriff.userdetails.repository.UsersRepository;
@Service
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	private UsersRepository userRepo;
	@Autowired
	private TemporaryPasswordRepository temporaryPasswordRepository;
	

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Users existingUsers=userRepo.findByEmailAdress(email);
		String emailAdress=existingUsers.getEmailAdress();
		//Collection<? extends GrantedAuthority> role =new ArrayList<String>();
		//role_permission.add("");
		//role.add("admin");
		String password=null;
		if((existingUsers.getPasswordType()).toLowerCase().equals("primary"))
		{
			password=existingUsers.getPassword();
	   
		return new User(emailAdress,password,new ArrayList<>());
		}
		else
		{
			Optional<TemporaryPassword> tempPassword=temporaryPasswordRepository.findTempPassword(existingUsers.getId());
			password= tempPassword.get().getTempPassword();
			return new User(emailAdress,password,new ArrayList<>());
		}
	}

}
