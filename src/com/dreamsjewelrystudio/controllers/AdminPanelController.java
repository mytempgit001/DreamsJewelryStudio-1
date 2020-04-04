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
			@RequestParam(name="table") String table,
			@RequestParam(name="operation") String operation){
		if(!isAdmin(session)) return "forward:/admin/alogin";
		System.out.println(operation + " " + table);
		switch(operation) {
		case "INSERT":
			
			break;
		case "UPDATE":
			
			break;
			
		case "DELETE":
			
			break;
		}
		
		return "entry";
	}
	
	public boolean isAdmin(String session) {
		if(session.length()>0 && adminSrvc.findAdminBySession(session)!=null)
			return true;
		
		return false;
	}
	
	public void getFieldNames(List<String> str, Field[] fields) {
		Arrays.stream(fields).forEach(field -> {
			String fieldType = field.getType().toString().toLowerCase();
			if(fieldType.contains("interface"))
				fieldType = fieldType.split(" ")[1];
				
			if(fieldType.contains("int") ||
			fieldType.contains("string") ||
			fieldType.contains("long") 	 ||
			fieldType.contains("float")  ||
			fieldType.contains("double") ||
			fieldType.contains("boolean"))
				str.add(field.getName());
		});
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
				List<Admins> adminsList = adminSrvc.findAll();
				getFieldNames(fields, adminsList.get(0).getClass().getDeclaredFields());
				adminsList.stream().forEach(a -> {
					List<String> temp = new ArrayList<>();
					temp.add(String.valueOf(a.getId()));
					temp.add(a.getName());
					temp.add(a.getPass());
					temp.add(a.getToken());
					items.add(temp);
				});
				break;
			case "item":
				List<Item> itemsList = itemService.findAll();
				getFieldNames(fields, itemsList.get(0).getClass().getDeclaredFields());
				itemsList.stream().forEach(i -> {
					List<String> temp = new ArrayList<>();
					temp.add(String.valueOf(i.getItemID()));
					temp.add(String.valueOf(i.getProductID()));
					temp.add(String.valueOf(i.getSessID()));
					temp.add(String.valueOf(i.getQuantity()));
					temp.add(i.getSize());
					temp.add(String.valueOf(i.getPrice()));
					temp.add(String.valueOf(i.getPricePerOne()));
					items.add(temp);
				});
				break;
			case "messages":
				List<Messages> msgs = msgSrvc.findAll();
				getFieldNames(fields, msgs.get(0).getClass().getDeclaredFields());
				msgs.stream().forEach(msg -> {
					List<String> temp = new ArrayList<>();
					temp.add(String.valueOf(msg.getId()));
					temp.add(msg.getMsgType());
					temp.add(msg.getName());
					temp.add(msg.getEmail());
					temp.add(msg.getMsg());
					items.add(temp);
				});
				break;
			case "product_images":
				List<ProductImages> prodImages = productImagesService.findAll();
				getFieldNames(fields, prodImages.get(0).getClass().getDeclaredFields());
				prodImages.stream().forEach(img -> {
					List<String> temp = new ArrayList<>();
					temp.add(String.valueOf(img.getId()));
					temp.add(String.valueOf(img.getProduct_id()));
					temp.add(img.getUrl());
					items.add(temp);
				});
				break;
			
			case "product_price_size":
				List<ProductPriceSize> productsPriceSize = productPriceSizeService.findAll();
				getFieldNames(fields, productsPriceSize.get(0).getClass().getDeclaredFields());
				productsPriceSize.stream().forEach(p -> {
					List<String> temp = new ArrayList<>();
					temp.add(String.valueOf(p.getPriceId()));
					temp.add(String.valueOf(p.getProduct_id()));
					temp.add(String.valueOf(p.getPrice()));
					temp.add(p.getSize());
					temp.add(String.valueOf(p.getDiscountPrice()));
					temp.add(String.valueOf(p.getDiscountAvailability()));
					temp.add(String.valueOf(p.getQuantity()));
					items.add(temp);
				});
				break;
			
			case "product":
				List<Product> products = productService.getAllWithChildren();
				getFieldNames(fields, products.get(0).getClass().getDeclaredFields());
				products.stream().forEach(prod -> {
					List<String> temp = new ArrayList<>();
					temp.add(String.valueOf(prod.getProduct_id()));
					temp.add(prod.getDescription());
					temp.add(prod.getMain_img());
					temp.add(prod.getName());
					temp.add(prod.getProduct_type());
					temp.add(prod.getMaterial());
					temp.add(prod.getDate());
					temp.add(prod.getTime());
					items.add(temp);
				});
				break;
				
			case "session":
				List<Session> sessList = sessSrvc.findAll();
				getFieldNames(fields, sessList.get(0).getClass().getDeclaredFields());
				sessList.stream().forEach(sess -> {
					List<String> temp = new ArrayList<>();
					temp.add(String.valueOf(sess.getSessID()));
					temp.add(sess.getToken());
					temp.add(sess.getLastUsed());
					items.add(temp);
				});
				break;
			}
		}
		
		return "atables";
	}
}
