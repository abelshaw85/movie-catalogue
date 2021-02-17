package com.myhomepage.homepage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String showHome(Model model) {
		model.addAttribute("theDate", new java.util.Date());
		return "home";
	}
}
