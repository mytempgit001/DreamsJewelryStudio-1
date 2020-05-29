package com.dreamsjewelrystudio.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dreamsjewelrystudio.models.Item;
import com.dreamsjewelrystudio.models.Product;
import com.dreamsjewelrystudio.models.ProductPriceSize;
import com.dreamsjewelrystudio.models.Session;
import com.dreamsjewelrystudio.projections.RelatedProductsProjection;
import com.dreamsjewelrystudio.service.ItemService;
import com.dreamsjewelrystudio.service.ProductPriceSizeService;
import com.dreamsjewelrystudio.service.ProductService;
import com.dreamsjewelrystudio.service.SessionService;
import com.dreamsjewelrystudio.util.CatalogPagination;
import com.dreamsjewelrystudio.util.Util;
import com.dreamsjewelrystudio.util.comparators.DateComparator;
import com.dreamsjewelrystudio.util.comparators.PriceComparator;

@Controller
public class CatalogController {
	
	@Autowired private ProductService prdSrvc;
	@Autowired private ProductPriceSizeService prsSrvc;
	@Autowired private SessionService sessSrvc;
	@Autowired private ItemService itmSrvc;
	
	@Autowired
	@Qualifier("pagination")
	private CatalogPagination pagination;
	
	@GetMapping("/catalog")
	public String catalog(@RequestParam(name="page", required=false, defaultValue ="1") Integer numPage,
			@RequestParam(name="filter", defaultValue="", required=false) String filterBy,
			@RequestParam(name="sort", defaultValue="", required=false) String sortBy,
			Model model) {
		model.addAttribute("pagesAmount", pagination.getPagesAmount());
		int[] range = pagination.calculateRange(numPage);
		if(range == null) {
			model.addAttribute("exceptionMsg", numPage + " catalog page does not exist");
			return "404";
		}
		model.addAttribute("activePage", numPage);
		List<Product> products;
		if(Util.isStringNotEmpty(filterBy)) 
			products = prdSrvc.findProductsWithPriceByTypeOrCategoryLimit(filterBy, range[0], range[1]);
		else
			products = prdSrvc.findProductsWithPriceLimit(range[0], range[1]);
		
		if(Util.isStringNotEmpty(sortBy)) { 
			switch(sortBy) {
				case "By date: Old to new": products.sort(new DateComparator(false)); break;
				case "By date: New to old": products.sort(new DateComparator(true)); break;
				case "By price: Low to high": products.sort(new PriceComparator(false)); break;
				case "By price: High to low": products.sort(new PriceComparator(true)); break;
			}
		}
		
		model.addAttribute("sorting", sortBy);
		model.addAttribute("filtering", filterBy);
		model.addAttribute("products", products);
		return "catalog";
	}
	
	@GetMapping("/listing")
	public String listing(@RequestParam(name="product_id", required = false) Long productID, Model model) {
		if(productID!=null) {
			Product product = prdSrvc.findProductWithChildrenByID(productID);
			List<RelatedProductsProjection> related = prdSrvc.findRelated(product.getCategory(), 
					product.getProduct_id(),
					PageRequest.of(new Random().nextInt(pagination.getPagesAmount()), 4));
			if(product!=null) {
				model.addAttribute("product", product);
				model.addAttribute("related", related);
				return "listing";
			}
		}
		model.addAttribute("message", "The product wasn't found.");
		return "404";
	}
	
	@PostMapping("/addToCart")
	@ResponseBody
	public String cart(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name="id", required = true) Long productID,
			@RequestParam(name="quantity", required = true) Integer quantity,
			@RequestParam(name="size", required = true) String size,
			Model model) {
		
		Cookie sessionCookie = Util.getSessionID(request.getCookies(), Util.SESSID);
		Item item;
		
		if (Objects.nonNull(sessionCookie)) { // если есть сессия
			String sessIDValue = sessionCookie.getValue();
			Session currentSession = sessSrvc.findSessionItemPrsByToken(sessIDValue);
			
			if(Objects.nonNull(currentSession)) {
				item = currentSession.getItems().stream()
						.filter(i -> i.getPrs().getProduct_id() == productID && i.getSize().equals(size))
						.findFirst()
						.orElse(null);
				if(!Objects.isNull(item)) { // если есть тот же продукт в корзине
					processProductPriceSize(item, size, quantity, item.getPrs());
				}else { // если есть сессия, но в корзине нет этого продукта
					item = createNewItem(productID, size, quantity, currentSession.getSessID());
				}
				itmSrvc.insert(item);
				return "SUCCESS";
			}
			
		}
		
        item = createNewItem(productID, size, quantity, 
        		sessSrvc.createNewSessionWithItems(assignSession(response)).getSessID());
		itmSrvc.insert(item);
		return "SUCCESS";
	}
	
	private Session assignSession(HttpServletResponse response) {
		String uuid = UUID.randomUUID().toString();
		int firstSessIDCookieTime = 129600;
		
		Cookie newCookie = new Cookie(Util.SESSID, uuid);
        newCookie.setMaxAge(firstSessIDCookieTime);
        response.addCookie(newCookie);
        
        Session currentSession = new Session();
        currentSession.setToken(uuid);
        currentSession.setLastUsed(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return currentSession;
	}
	
	private Item createNewItem(Long productID, String size, Integer quantity, long sessID) {
		Item item = new Item();
		item.setSessID(sessID);
		item.setProductID(productID);
		ProductPriceSize prs = prsSrvc.getPrsBySizeAndProductId(size, productID);
		processProductPriceSize(item, size, quantity, prs);
		return item;
	}
	
	private void processProductPriceSize(Item item, String size, Integer quantity, ProductPriceSize prs) {
		if(prs!=null) {
			item.setPrice_id(prs.getPrice_id());
			item.setSize(size);
			if(prs.getQuantity() >= quantity) item.setQuantity(quantity);
			else item.setQuantity(prs.getQuantity());
			float f1 = item.getQuantity(); 
			float f2 = 0f;
			if(prs.getDiscountPrice() != null
			&& prs.getDiscountPrice()>0) f2 = prs.getDiscountPrice();
			else f2 = prs.getPrice();
			item.setPrice(f1 * f2);
		}
	}
	
	@ExceptionHandler(Exception.class)
	public String handleException(Exception e, Model model) {
		e.printStackTrace();
		if(Util.isStringNotEmpty(e.getMessage())) model.addAttribute("exceptionMsg", e.getMessage());
		return "404";
	}
}
