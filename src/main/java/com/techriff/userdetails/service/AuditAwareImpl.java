package com.techriff.userdetails.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.techriff.userdetails.entity.Users;
import com.techriff.userdetails.repository.UsersRepository;

public class AuditAwareImpl  implements AuditorAware<String>{
@Autowired
private UsersRepository usersRepository;
   

    @Override
    public Optional<String> getCurrentAuditor() {

        String currentUserName="";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserEmail = authentication.getName();
            Users currentUsers =usersRepository.findByEmailAdress(currentUserEmail);
            currentUserName=currentUserName+currentUsers.getFirstName();
            
        }
        return Optional.of(currentUserName);
    }
  
}
