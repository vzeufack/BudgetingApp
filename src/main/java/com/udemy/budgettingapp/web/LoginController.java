package com.udemy.budgettingapp.web;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.udemy.budgettingapp.domain.User;
import com.udemy.budgettingapp.service.UserService;

@Controller
public class LoginController {
	@Autowired
	private UserService userService;
	private static final Logger LOGGER = Logger.getLogger( LoginController.class.getName() );
	
   @RequestMapping(value = "/login", method = RequestMethod.GET)
   public String getLogin(ModelMap model) {
   	User user = new User();
   	model.put("user", user);
   	
   	return "login";
   }
   
   @RequestMapping(value = "/register", method = RequestMethod.GET)
   public String getRegister (ModelMap model) {
   	User user = new User();
   	model.put("user", user);
   	
   	return "register";
   } 
   
   @RequestMapping(value = "/register", method = RequestMethod.POST)
   public String postRegister (@ModelAttribute User user, ModelMap model) {
   	
   	LOGGER.log( Level.FINE, "Reached here");
   	
   	if (!user.getPassword().isEmpty() && !user.getConfirmPassword().isEmpty()) {
   		if(!user.getPassword().equals(user.getConfirmPassword())) {
   			model.put("error", "Passwords don't match");	
   			return "register";
   		}
   	}
   	
   	if (user.getPassword().isEmpty() || user.getConfirmPassword().isEmpty()) {
   		model.put("error", "You must choose a password");
   		return "register";
   	}
   	
   	System.out.println("test loggin");
   	user = userService.saveUser(user);
   	
   	Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
   	SecurityContextHolder.getContext().setAuthentication(auth);
   	
   	return "redirect:/budgets";
   } 
}
