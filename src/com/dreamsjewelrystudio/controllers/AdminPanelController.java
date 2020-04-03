package com.dreamsjewelrystudio.controllers;

import java.util.Objects;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dreamsjewelrystudio.models.Admins;
import com.dreamsjewelrystudio.service.AdminsService;
import com.dreamsjewelrystudio.utils.Util;

@Controller
@RequestMapping("/admin")
public class AdminPanelController {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AdminsService adminService;
	
	@GetMapping
	public String admin(@CookieValue(name=Util.ADMIN, defaultValue="") String session) {
		if(!isAdmin(session)) return "forward:/admin/alogin";
			
		return "adminpanel";
	}
	
	@GetMapping("/alogin")
	public String login() {
		return "alogin";
	}
	
	@GetMapping("/atables")
	public String tables(@CookieValue(name=Util.ADMIN, defaultValue="") String session,
			@RequestParam(name="table", required=false) String table) {
		if(!isAdmin(session)) return "forward:/admin/alogin";
		
		
		return "atables";
	}
	
	@PostMapping("/alogin")
	@ResponseBody
	public String login(
			@RequestParam(name="name") String name,
			@RequestParam(name="pass") String pass,
			@RequestParam(name="notOwnPC") Boolean notOwnPC, 
			HttpServletResponse response) {
		
		Admins admin = adminService.findAdminByName(name);
		
		if(Objects.nonNull(admin) && passwordEncoder.matches(pass, admin.getPass())) { 
			String uuid = UUID.randomUUID().toString();
			Cookie newCookie = new Cookie(Util.ADMIN, uuid);
	        
	        if(!notOwnPC) newCookie.setMaxAge(60 * 60 * 2);
			else newCookie.setMaxAge(60 * 60 * 24 * 365 * 10);
	        
	        response.addCookie(newCookie);
	        admin.setToken(uuid);
	        adminService.updateToken(admin);
	        
			return "SUCCESS";
		}
		
		return "FAILURE";
	}
	
	public boolean isAdmin(String session) {
		if(session.length()>0 && adminService.findAdminBySession(session)!=null)
			return true;
		
		return false;
	}
}
