package com.udemy.budgettingapp.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.udemy.budgettingapp.domain.Authority;
import com.udemy.budgettingapp.domain.User;
import com.udemy.budgettingapp.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
   public User saveUser(User user) {
   	Authority authority = new Authority();
   	authority.setAuthority("ROLE_USER");
   	authority.setUser(user);
   	
   	Set<Authority> authorities = new HashSet<>();
   	authorities.add(authority);
   	
   	final String encryptedPassword = passwordEncoder.encode(user.getPassword());
   	user.setPassword(encryptedPassword);   	
   	user.setAuthorities(authorities);
   	user = userRepo.save(user); 
   	
   	return user;
   }
}
