package com.dreamsjewelrystudio.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping("/")
	public String getHomePage() {
		
		return "index";
	}
	
	@GetMapping("/aboutUs")
	public String getAboutUsPage() {
		return "new";
	}
}
