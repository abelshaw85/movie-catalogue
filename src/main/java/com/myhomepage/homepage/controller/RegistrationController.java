package com.myhomepage.homepage.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myhomepage.homepage.dto.UserDTO;
import com.myhomepage.homepage.entity.User;
import com.myhomepage.homepage.service.UserService;

@Controller
@RequestMapping("/register")
public class RegistrationController {
	
	@Autowired
	private UserService userService;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}	
	
	@GetMapping("")
	public String showRegistrationForm(Model theModel) {
		theModel.addAttribute("user", new UserDTO());
		return "authorisation/registration";
	}
	
	@PostMapping("/registerUser")
	public String registerUser(Model theModel, 
			@Valid @ModelAttribute("user") UserDTO userDto,
			BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			return "authorisation/registration";
		}
		
		User user = userService.findByUserName(userDto.getUserName());
		if (user != null){
        	theModel.addAttribute("user", new UserDTO());
			theModel.addAttribute("registrationError", "User name already exists, please try another.");

        	return "authorisation/registration";
        }		

		userService.save(userDto);
		
		
		return "authorisation/success";
	}
}
