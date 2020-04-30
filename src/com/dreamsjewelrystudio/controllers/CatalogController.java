package com.dreamsjewelrystudio.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import com.dreamsjewelrystudio.service.ItemService;
import com.dreamsjewelrystudio.service.ProductPriceSizeService;
import com.dreamsjewelrystudio.service.ProductService;
import com.dreamsjewelrystudio.service.SessionService;
import com.dreamsjewelrystudio.util.Pagination;
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
	private Pagination pagination;
	
	private String processCatalogPage(Model model, Integer numPage, String filterBy, String sortBy) {
		
		model.addAttribute("pagesAmount", pagination.getPagesAmount());
		int[] range = pagination.calculateRange(numPage);
		if(range == null) {
			model.addAttribute("message", numPage + " catalog page does not exist");
			return "404";
		}
		model.addAttribute("activePage", numPage);
		
		List<Product> products;
		if(filterBy!=null) 
			products = prdSrvc.findProdcutsWithPriceByTypeOrCategoryLimit(filterBy, range[0], range[1]);
		else
			products = prdSrvc.findProductsWithPriceLimit(range[0], range[1]);
		
		if(sortBy!=null) { 
			switch(sortBy) {
				case "By date: Old to new": products.sort(new DateComparator(false)); break;
				case "By date: New to old": products.sort(new DateComparator(true)); break;
				case "By price: Low to high": products.sort(new PriceComparator(false)); break;
				case "By price: High to low": products.sort(new PriceComparator(true)); break;
			}
		}
		
		model.addAttribute("sorts", getSorts(sortBy));
		model.addAttribute("filters", getFilters(filterBy));
		model.addAttribute("products", products);
		return "catalog";
	}
	
	private List<String> getSorts(String sort){
		List<String> names = new ArrayList<>();
		
		names.add("<option value=\"All\">All</option>");
		names.add("<option value=\"By price: Low to high\">By price: Low to High</option>");
		names.add("<option value=\"By price: High to low\">By price: High to low</option>");
		names.add("<option value=\"By date: Old to new\">By date: Old to new</option>");
		names.add("<option value=\"By date: New to old\">By date: New to old</option>");
		
		if(sort == null) {
			names.set(0, insertIntoString(names.get(0)));
		}else if(sort.equals("By price: Low to high")) {
			names.set(1, insertIntoString(names.get(1)));
		}else if(sort.equals("By price: High to low")) {
			names.set(2, insertIntoString(names.get(2)));
		}else if(sort.equals("By date: Old to new")) {
			names.set(3, insertIntoString(names.get(3)));
		}else if(sort.equals("By date: New to old")) {
			names.set(4, insertIntoString(names.get(4)));
		}
		
		return names;
	}
	
	private List<String> getFilters(String filter) {
		List<String> names = new ArrayList<>();
		
		names.add("<option value=\"All pieces\">All pieces</option>");
		names.add("<option value=\"Gemstone Jewelry\">Gemstone Jewelry</option>");
		names.add("<option value=\"Epoxy Resin Jewelry\">Epoxy Resin Jewelry</option>");
		names.add("<option value=\"Earrings\">Earrings</option>");
		names.add("<option value=\"Bracelets\">Bracelets</option>");
		names.add("<option value=\"Rings\">Rings</option>");
		names.add("<option value=\"Necklaces\">Necklaces</option>");
		
		if(filter == null) {
			names.set(0, insertIntoString(names.get(0)));
		}else if(filter.equals("Gemstone")) {
			names.set(1, insertIntoString(names.get(1)));
		}else if(filter.equals("Resin")) {
			names.set(2, insertIntoString(names.get(2)));
		}else if(filter.equals("Earrings")) {
			names.set(3, insertIntoString(names.get(3)));
		}else if(filter.equals("Bracelets")) {
			names.set(4, insertIntoString(names.get(4)));
		}else if(filter.equals("Rings")) {
			names.set(5, insertIntoString(names.get(5)));
		}else if(filter.equals("Necklaces")) {
			names.set(6, insertIntoString(names.get(6)));
		}
		
		return names;
	}
	
	private String insertIntoString(String originalStr) {
		int index = 7;
		String temp1 = originalStr.substring(0, index);
		String temp2 = originalStr.substring(index, originalStr.length());
		
		return temp1 + " selected " + temp2; 
	}
	
	@GetMapping("/catalog")
	public String catalog(@RequestParam(name="page", required=false, defaultValue ="1") Integer numPage,
			@RequestParam(name="filter", required=false) String filterBy,
			@RequestParam(name="sort", required=false) String sortBy,
			Model model) {
		return processCatalogPage(model, numPage, filterBy, sortBy);
	}
	
	
	@GetMapping("/listing")
	public String listing(@RequestParam(name="product_id", required = false) Long productID, Model model) {
		if(productID!=null) {
			Product product = prdSrvc.findProductWithChildrenByID(productID);
			if(product!=null) {
				model.addAttribute("product", product);
				return "listing";
			}
		}
		model.addAttribute("message", "The product wasn't found.");
		return "404";
	}
	
	@PostMapping("/addToCart")
	@ResponseBody
	public String cart(
			@RequestParam(name="id", required = false) Long productID,
			@RequestParam(name="quantity", required = false, defaultValue = "1") Integer quantity,
			@RequestParam(name="size", required = false, defaultValue = "noSize") String size,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		try {
			Cookie sessionCookie = Util.getSessionID(request.getCookies());
			Session currentSession;
			Item item;
			if (Objects.nonNull(sessionCookie)) { // если есть сессия
				String sessIDValue = sessionCookie.getValue();
				currentSession = sessSrvc.findSessionItemPrsByToken(sessIDValue);
				if(Objects.nonNull(currentSession)) {
					List<Item> itemsList = currentSession.getItems();
					item = itemsList.stream()
							.filter(i -> i.getPrs().getProduct_id() == productID && i.getSize().equals(size))
							.findFirst()
							.orElse(null);
					if(!Objects.isNull(item)) { // если есть тот же продукт в корзине
						processProductPriceSize(item, size, quantity, item.getPrs());
					}else { // если есть сессия, но в корзине нет этого продукта
						item = createNewItem(productID, size, quantity, currentSession.getSessID());
					}
				}else {
					return "DENIAL OF SERVICE (no session was found)";
				}
			}else { // если сессии нет
				currentSession = sessSrvc.createNewSessionWithItems(assignSession(response));
		        item = createNewItem(productID, size, quantity, currentSession.getSessID());
			}
			
			itmSrvc.persistItem(item);
			
			return "SUCCESS";
		}catch(Exception e) {
			e.printStackTrace();
			return "ERROR";
		}
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
	public String handleException(Exception e) {
		e.printStackTrace();
		return "404";
	}
}
