package com.dreamsjewelrystudio.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

import com.dreamsjewelrystudio.util.Util;

@Controller
public class AboutUsController {
	
	@GetMapping("/about")
	public String getAboutPage() {
		return "about";
	}
	
	@ExceptionHandler(Exception.class)
	public String handleException(Exception e, Model model) {
		e.printStackTrace();
		if(Util.isStringNotEmpty(e.getMessage())) model.addAttribute("exceptionMsg", e.getMessage());
		return "404";
	}
}
