package com.dreamsjewelrystudio.controllers;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dreamsjewelrystudio.models.Admins;
import com.dreamsjewelrystudio.models.Product;
import com.dreamsjewelrystudio.service.AdminsService;
import com.dreamsjewelrystudio.util.Util;
import com.dreamsjewelrystudio.util.administration.AdminDataManager;

@Controller
@RequestMapping("/admin")
public class AdminPanelController {

	@Autowired private PasswordEncoder pswdencdr;
	@Autowired private AdminsService adminSrvc;
	@Autowired private AdminDataManager dataManager;
	
	@GetMapping
	public String admin() {
		return "adminpanel";
	}
	
	@GetMapping("/alogin")
	public String login() {
		return "alogin";
	}
	
	@PostMapping("/alogin")
	@ResponseBody
	public String login(HttpServletResponse response,
			@RequestParam(name="name") String name,
			@RequestParam(name="pass") String pass,
			@RequestParam(name="notOwnPC") Boolean notOwnPC) {
		
		Admins admin = adminSrvc.findAdminByName(name);
		
		if(Objects.nonNull(admin) && pswdencdr.matches(pass, admin.getPass())) { 
			String uuid = UUID.randomUUID().toString();
			Cookie newCookie = new Cookie(Util.ADMIN, uuid);
	        
	        if(notOwnPC) newCookie.setMaxAge(60 * 60 * 2);
			else newCookie.setMaxAge(60 * 60 * 24 * 365 * 10);
	        
	        response.addCookie(newCookie);
	        admin.setToken(uuid);
	        adminSrvc.updateToken(admin);
	        
			return "SUCCESS";
		}
		
		return "FAILURE";
	}
	
	@GetMapping("/atables")
	public String tables(String table, String service, Model model) {
		Map.Entry<List<String>, List<List<String>>> entry = dataManager.revealClass(service).entrySet().iterator().next();
		model.addAttribute("fields", entry.getKey());
		model.addAttribute("items", entry.getValue());
		model.addAttribute("tableName", table);
		
		return "atables";
	}
	
	@GetMapping("/modifying")
	public String modifyEntry(Model model,
			@RequestParam String table,
			@RequestParam String operation){
		model.addAttribute("operation", operation);
		model.addAttribute("table", table);
		if(!operation.equals("INSERT") || !table.equals("product"))
			return "redirect:/admin";
		return "entry";
	}
	
	@PostMapping("/product")
	@ResponseBody
	public String productProcess(@RequestBody Product product) {
		if(dataManager.insertProduct(product))
			return "SUCCESS";
		else
			return "ERROR";
	}
	
	@ExceptionHandler(Exception.class)
	public String handleException(Exception e, Model model) {
		e.printStackTrace();
		if(e.getMessage()!=null && e.getMessage().length()>0) model.addAttribute("exceptionMsg", e.getMessage());
		return "404";
	}
}
