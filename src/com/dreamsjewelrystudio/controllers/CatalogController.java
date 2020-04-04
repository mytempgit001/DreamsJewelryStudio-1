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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dreamsjewelrystudio.additional.Pagination;
import com.dreamsjewelrystudio.additional.comparators.DateComparator;
import com.dreamsjewelrystudio.additional.comparators.PriceComparator;
import com.dreamsjewelrystudio.models.Item;
import com.dreamsjewelrystudio.models.Product;
import com.dreamsjewelrystudio.models.ProductPriceSize;
import com.dreamsjewelrystudio.models.Session;
import com.dreamsjewelrystudio.service.ItemServiceImpl;
import com.dreamsjewelrystudio.service.ProductServiceImpl;
import com.dreamsjewelrystudio.service.SessionServiceImpl;
import com.dreamsjewelrystudio.utils.Util;

@Controller
public class CatalogController {
	
	@Autowired
	private ProductServiceImpl productService;
	
	@Autowired
	private SessionServiceImpl sessionService;
	
	@Autowired
	private ItemServiceImpl itemService;
	
	@Autowired
	@Qualifier("pagination")
	private Pagination pagination;
	
	private String abstractMethod(Model model, Integer numPage, String filterBy, String sortBy) {
		
		model.addAttribute("pagesAmount", pagination.getPagesAmount());
		int[] range = pagination.calculateRange(numPage);
		if(range == null) {
			model.addAttribute("message", numPage + " catalog page does not exist");
			return "404";
		}
		model.addAttribute("activePage", numPage);
		
		List<Product> products;
		if(filterBy!=null) 
			products = productService.getAllProdcutsByType(filterBy, range[0], range[1]);
		else
			products = productService.getAllProductsWithChildrenLimit(range[0], range[1]);
		 
		
		if(sortBy!=null) 
			switch(sortBy) {
			case "By date: Old to new": products.sort(new DateComparator(false)); break;
			case "By date: New to old": products.sort(new DateComparator(true)); break;
			case "By price: Low to high": products.sort(new PriceComparator(false)); break;
			case "By price: High to low": products.sort(new PriceComparator(true)); break;
			}
		
		model.addAttribute("sorts", getSorts(sortBy));
		model.addAttribute("filters", getFilters(filterBy));
		model.addAttribute("products", products);
		return "catalog";
	}
	
	public List<String> getSorts(String sort){
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
	
	public List<String> getFilters(String filter) {
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
	
	public String insertIntoString(String originalStr) {
		int index = 7;
		String temp1 = originalStr.substring(0, index);
		String temp2 = originalStr.substring(index, originalStr.length());
		
		return temp1 + " selected " + temp2; 
	}
	
	@GetMapping("/catalog")
	public String getCatalogPage(@RequestParam(name="page", required=false, defaultValue ="1") Integer numPage,
			@RequestParam(name="filter", required=false) String filterBy,
			@RequestParam(name="sort", required=false) String sortBy,
			Model model) {
		return abstractMethod(model, numPage, filterBy, sortBy);
	}
	
	
	@GetMapping("/listing")
	public String getListingPage(@RequestParam(name="product_id", required = false) Long productID, Model model) {
		try {
			if(productID!=null) {
				Product product = productService.getProductByID(productID, true, true);
				if(product!=null) {
					model.addAttribute("product", product);
					return "listing";
				}
			}
			model.addAttribute("message", "The product wasn't found.");
			return "404";
		}catch(Exception e) {
			e.printStackTrace();
			return "404";
		}
	}
	
	@PostMapping("/addToCart")
	@ResponseBody
	public String getProductPortfolio(
			@RequestParam(name="id", required = false) Long productID,
			@RequestParam(name="quantity", required = false, defaultValue = "1") Integer quantity,
			@RequestParam(name="size", required = false, defaultValue = "noSize") String size,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		try {
			Product product = productService.getProductByID(productID, false, true);
			if(Objects.isNull(product))
				return "DENIAL OF SERVICE (no product was found)";
			
			Cookie sessionCookie = Util.getSessionID(request.getCookies());
			Session currentSession;
			Item item;
			if (!Objects.isNull(sessionCookie)) { // если есть сессия
				String sessIDValue = sessionCookie.getValue();
				currentSession = sessionService.findSessionByToken(sessIDValue);
				if(Objects.isNull(currentSession))
					return "DENIAL OF SERVICE (no session was found)";
				
				List<Item> itemsList = itemService.getItemWithSession(currentSession);
				item = itemsList.stream()
						.filter(i -> i.getProduct().equals(product) && i.getSize().equals(size))
						.findFirst()
						.orElse(null);
				if(!Objects.isNull(item)) { // если есть тот же продукт в корзине
					quantity = item.getQuantity() + quantity; // увеличиваем количество и стоимость
					processProductPriceSize(item, product, size, quantity, product.getPrice());
				}else { // если есть сессия, но в корзине нет этого продукта
					item = createNewItem(product, size, quantity, currentSession.getSessID());
				}
			}else { // если сессии нет
				String uuid = UUID.randomUUID().toString();
				int firstSessIDCookieTime = 129600;
				
				Cookie newCookie = new Cookie(Util.SESSID, uuid);
		        newCookie.setMaxAge(firstSessIDCookieTime);
		        response.addCookie(newCookie);
		        
		        currentSession = new Session();
		        currentSession.setToken(uuid);
		        currentSession.setLastUsed(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		        currentSession = sessionService.createNewSession(currentSession);
		        item = createNewItem(product, size, quantity, currentSession.getSessID());
		        if(Objects.isNull(item.getSize()))
		        	return "DENIAL OF SERVICE";
			}
			
			itemService.persistItem(item);
			
			return "SUCCESS";
		}catch(Exception e) {
			e.printStackTrace();
			return "ERROR";
		}
	}
	
	public Item createNewItem(Product product, String size, Integer quantity, long sessID) {
		Item item = new Item();
		item.setSessID(sessID);
		item.setProductID(product.getProduct_id());
        List<ProductPriceSize> priceSize = product.getPrice();
        processProductPriceSize(item, product, size, quantity, priceSize);
        return item;
	}
	
	public void processProductPriceSize(Item item, Product product, String size, Integer quantity, List<ProductPriceSize> priceSize) {
		for(ProductPriceSize p : priceSize) {
        	if(p.getSize().equals(size)) {
        		item.setSize(size);
        		if(p.getQuantity() >= quantity)
        			item.setQuantity(quantity);
        		else
        			item.setQuantity(p.getQuantity());
        		
        		float f1 = item.getQuantity(); 
        		float f2 = 0f;
        		if(p.getDiscountPrice() != null & p.getDiscountPrice()>0) 
        			f2 = p.getDiscountPrice();
        		else 
        			f2 = p.getPrice();
        		
        		item.setPricePerOne(f2);
        		item.setPrice(f1 * f2);
        		break;
        	}
        }
	}
}
