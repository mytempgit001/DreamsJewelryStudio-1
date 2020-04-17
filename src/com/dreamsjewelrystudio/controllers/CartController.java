package com.dreamsjewelrystudio.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dreamsjewelrystudio.models.Item;
import com.dreamsjewelrystudio.models.Session;
import com.dreamsjewelrystudio.service.ItemServiceImpl;
import com.dreamsjewelrystudio.service.SessionServiceImpl;
import com.dreamsjewelrystudio.utils.Util;

@Controller
public class CartController {
	
	@Autowired
	private SessionServiceImpl sessionService;
	
	@Autowired
	private ItemServiceImpl itemService;

	@GetMapping("/cart")
	public String getPersonalCart(@CookieValue(name=Util.SESSID, defaultValue="") String sessionCookie, Model model) {
		if(sessionCookie.length()>0) {
			Session currentSession = sessionService.findSessionByToken(sessionCookie);
			if(currentSession!=null) {
				List<Item> items = itemService.getItemsAndProductWtihChildrenWithSessionId(currentSession.getSessID());
				float totalPrice = 0f;
				for(int i = 0; i < items.size(); i++) { 
					System.out.println(items.get(i).getPricePerOne());
					totalPrice+=items.get(i).getPrice();
				}
				
				model.addAttribute("items", items);
				model.addAttribute("totalPrice", totalPrice);
			}
		}
		return "cart";
	}
	
	@PostMapping("/deleteItemFromCart")
	@ResponseBody
	public String deleteItem(@CookieValue(name=Util.SESSID, defaultValue="") String sessionCookie,
			@RequestParam(name="itemID", defaultValue="") Long itemID) throws Exception {
		if(sessionCookie.length()>0) {
			itemService.removeItem(itemID);
			return "SUCCESS";
		}
		throw new Exception();
	}
	
	@PostMapping("/changeAmount")
	@ResponseBody
	public String changeAmount(
			@RequestParam(name="itemID", required = true) Long itemID,
			@RequestParam(name="qty", required = true) Integer qty) throws Exception {
		
		Item item = itemService.findItemById(itemID);
		item.setQuantity(qty);
		item.setPrice(item.getPricePerOne() * qty);
		itemService.updateItem(item);
		
		return "SUCCESS";
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public String handleException(Exception e) {
		e.printStackTrace();
		return "ERROR";
	}
}
