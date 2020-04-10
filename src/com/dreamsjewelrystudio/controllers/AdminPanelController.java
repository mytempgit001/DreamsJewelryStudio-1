package com.dreamsjewelrystudio.controllers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dreamsjewelrystudio.models.Admins;
import com.dreamsjewelrystudio.models.Item;
import com.dreamsjewelrystudio.models.Messages;
import com.dreamsjewelrystudio.models.Product;
import com.dreamsjewelrystudio.models.ProductImages;
import com.dreamsjewelrystudio.models.ProductPriceSize;
import com.dreamsjewelrystudio.models.Session;
import com.dreamsjewelrystudio.service.AdminsService;
import com.dreamsjewelrystudio.service.ItemServiceImpl;
import com.dreamsjewelrystudio.service.MessagesServiceImpl;
import com.dreamsjewelrystudio.service.ProductImagesService;
import com.dreamsjewelrystudio.service.ProductPriceSizeService;
import com.dreamsjewelrystudio.service.ProductServiceImpl;
import com.dreamsjewelrystudio.service.SessionServiceImpl;
import com.dreamsjewelrystudio.utils.Util;

@Controller
@RequestMapping("/admin")
public class AdminPanelController {

	@Autowired private PasswordEncoder passwordEncoder;
	@Autowired private AdminsService adminSrvc;
	@Autowired private ProductServiceImpl productService;
	@Autowired private ProductPriceSizeService productPriceSizeService;
	@Autowired private ProductImagesService productImagesService;
	@Autowired private MessagesServiceImpl msgSrvc;
	@Autowired private ItemServiceImpl itemService;
	@Autowired private SessionServiceImpl sessSrvc;
	
	@GetMapping
	public String admin(@CookieValue(name=Util.ADMIN, defaultValue="") String session) {
		if(!isAdmin(session)) return "forward:/admin/alogin";
		return "adminpanel";
	}
	
	@GetMapping("/alogin")
	public String login() {
		return "alogin";
	}
	
	@GetMapping("/modifying")
	public String modifyEntry(@CookieValue(name=Util.ADMIN, defaultValue="") String session,
			@RequestParam(name="table", required = false) String table,
			@RequestParam(name="operation", required = false) String operation,
			Model model){
		if(!isAdmin(session)) return "forward:/admin/alogin";
		
		model.addAttribute("operation", operation);
		model.addAttribute("table", table);
		return "entry";
	}
	
	@PostMapping("/modify")
	@ResponseBody
	public String insert(@RequestBody Product product) {
		return "SUCCESS";
	}
	
	public boolean isAdmin(String session) {
		if(session.length()>0 && adminSrvc.findAdminBySession(session)!=null)
			return true;
		
		return false;
	}
	
	private List<Field> getFieldNames(List<String> str, Field[] fields) {
		List<Field> newFilteredFields = new ArrayList<>();
		Arrays.stream(fields).forEach(field -> {
			String fieldType = field.getType().toString().toLowerCase();
			if(fieldType.contains("interface"))
				fieldType = fieldType.split(" ")[1];
				
			if(fieldType.contains("int") ||
			fieldType.contains("string") ||
			fieldType.contains("long") 	 ||
			fieldType.contains("float")  ||
			fieldType.contains("double") ||
			fieldType.contains("boolean")) {
				str.add(field.getName());
				newFilteredFields.add(field);
			}
		});
		return newFilteredFields;
	}
	
	@PostMapping("/alogin")
	@ResponseBody
	public String login(
			@RequestParam(name="name") String name,
			@RequestParam(name="pass") String pass,
			@RequestParam(name="notOwnPC") Boolean notOwnPC, 
			HttpServletResponse response) {
		
		Admins admin = adminSrvc.findAdminByName(name);
		
		if(Objects.nonNull(admin) && passwordEncoder.matches(pass, admin.getPass())) { 
			String uuid = UUID.randomUUID().toString();
			Cookie newCookie = new Cookie(Util.ADMIN, uuid);
	        
	        if(!notOwnPC) newCookie.setMaxAge(60 * 60 * 2);
			else newCookie.setMaxAge(60 * 60 * 24 * 365 * 10);
	        
	        response.addCookie(newCookie);
	        admin.setToken(uuid);
	        adminSrvc.updateToken(admin);
	        
			return "SUCCESS";
		}
		
		return "FAILURE";
	}
	
	private void getFieldValues(List<List<String>> items, List<?> entries, List<Field> classFields) {
		for(Object entry : entries) {
			ArrayList<String> temp = new ArrayList<>();
			for(Field field : classFields) {
				field.setAccessible(true);
				try {
					String value = field.get(entry).toString();
					temp.add(value);
				} catch (Exception e) {
					temp.add("");
					e.printStackTrace();
				} 
			}
			items.add(temp);
		}
	}
	
	@GetMapping("/atables")
	public String tables(@CookieValue(name=Util.ADMIN, defaultValue="") String session,
			@RequestParam(name="table", required=false, defaultValue="Tables") String table,
			Model model) {
		if(!isAdmin(session)) return "forward:/admin/alogin";
		
		if(table.length()!=0) {
			List<List<String>> items = new ArrayList<>();
			List<String> fields = new ArrayList<>();
			
			model.addAttribute("items", items);
			model.addAttribute("fields", fields);
			model.addAttribute("tableName", table);
			
			switch(table) {
			case "admins":
				getFieldValues(items, adminSrvc.findAll(), getFieldNames(fields, Admins.class.getDeclaredFields()));
				break;
			case "item":
				getFieldValues(items, itemService.findAll(), getFieldNames(fields, Item.class.getDeclaredFields()));
				break;
			case "messages":
				getFieldValues(items, msgSrvc.findAll(), getFieldNames(fields, Messages.class.getDeclaredFields()));
				break;
			case "product_images":
				getFieldValues(items, productImagesService.findAll(), getFieldNames(fields, ProductImages.class.getDeclaredFields()));
				break;
			case "product_price_size":
				getFieldValues(items, productPriceSizeService.findAll(), getFieldNames(fields, ProductPriceSize.class.getDeclaredFields()));
				break;
			case "product":
				getFieldValues(items, productService.findAllWithChildren(), getFieldNames(fields, Product.class.getDeclaredFields()));
				break;
			case "session":
				getFieldValues(items, sessSrvc.findAll(), getFieldNames(fields, Session.class.getDeclaredFields()));
				break;
			}
		}
		
		return "atables";
	}
}
