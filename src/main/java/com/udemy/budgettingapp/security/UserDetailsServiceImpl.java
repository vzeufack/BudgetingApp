package com.udemy.budgettingapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.udemy.budgettingapp.domain.User;
import com.udemy.budgettingapp.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
   
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		
		if (user == null)
			throw new UsernameNotFoundException("User does not exist!");
		
		return new SecurityUser(user);
	}

}
