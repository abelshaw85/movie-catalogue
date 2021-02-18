package com.movie.catalogue.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	@GetMapping("/login")
	public String login() {
		return "authorisation/login";
	}
	
	@GetMapping("/access-denied")
	public String showAccessDenied() {
		return "authorisation/access-denied";
	}

}
