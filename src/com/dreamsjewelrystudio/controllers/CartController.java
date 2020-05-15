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
import com.dreamsjewelrystudio.service.ItemService;
import com.dreamsjewelrystudio.service.SessionService;
import com.dreamsjewelrystudio.util.Util;

@Controller
public class CartController {
	
	@Autowired private SessionService sessSrvc;
	@Autowired private ItemService itmSrvc;

	@GetMapping("/cart")
	public String getPersonalCart(@CookieValue(name=Util.SESSID, defaultValue="") String sessionCookie, Model model) {
		if(sessionCookie.length()>0) {
			Session currentSession = sessSrvc.findSessionItemPrsPrdByToken(sessionCookie);
			if(currentSession!=null) {
				List<Item> items = currentSession.getItems();
				Float totalPrice = items.stream().map(i->i.getPrice()).reduce(Float::sum).orElse(0f);
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
		sessSrvc.deleteItemBySession(sessionCookie, itemID);
		return "SUCCESS";
	}
	
	@PostMapping("/changeAmount")
	@ResponseBody
	public String changeAmount(
			@RequestParam(name="itemID", required = true) Long itemID,
			@RequestParam(name="qty", required = true) Integer qty) throws Exception {
		Item item = itmSrvc.findItemWithPrsById(itemID);
		if(item.getPrs().getDiscountPrice()!=null && item.getPrs().getDiscountPrice() > 0)
			itmSrvc.updateItemQuantity(qty, item.getPrs().getDiscountPrice()*qty, itemID);
		else
			itmSrvc.updateItemQuantity(qty, item.getPrs().getPrice()*qty, itemID);
		return "SUCCESS";
	}
	
	@ExceptionHandler(Exception.class)
	public String handleException(Exception e, Model model) {
		e.printStackTrace();
		if(Util.isStringNotEmpty(e.getMessage())) model.addAttribute("exceptionMsg", e.getMessage());
		return "404";
	}
}
