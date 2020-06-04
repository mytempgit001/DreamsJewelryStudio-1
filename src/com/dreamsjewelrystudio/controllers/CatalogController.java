package com.dreamsjewelrystudio.controllers;

import java.util.List;
import java.util.Objects;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

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
import com.dreamsjewelrystudio.util.CatalogPagination;
import com.dreamsjewelrystudio.util.Util;
import com.dreamsjewelrystudio.util.comparators.DateComparator;
import com.dreamsjewelrystudio.util.comparators.PriceComparator;

@Controller
public class CatalogController {
	
	@Autowired private ProductService prdSrvc;
	@Autowired private ProductPriceSizeService prsSrvc;
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
			if(filterBy.equals("Epoxy Resin Jewelry")) 
				products = prdSrvc.findProductsWithPriceByTypeOrCategoryLimit("product_type", "Resin", range[0], range[1]);
			else if(filterBy.equals("Gemstone Jewelry")) 
				products = prdSrvc.findProductsWithPriceByTypeOrCategoryLimit("product_type", "Gemstone", range[0], range[1]);
			else 
				products = prdSrvc.findProductsWithPriceByTypeOrCategoryLimit("category", filterBy, range[0], range[1]);
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
	public String cart(HttpServletRequest request, Model model,
			@RequestParam(name="id", required = true) Long productID,
			@RequestParam(name="quantity", required = true) Integer quantity,
			@RequestParam(name="size", required = true) String size) throws Exception {
		Session currentSession = (Session)request.getAttribute("param1");
		Item item = currentSession
				.getItems().stream()
				.filter(i -> i.getPrs().getProduct_id() == productID && i.getSize().equals(size))
				.findFirst().orElse(null);
		
		if(!Objects.isNull(item)) { 
			item.setPrice_id(item.getPrs().getPrice_id());
			item.setSize(size);
			item.setQuantity(item.getPrs().getQuantity() >= quantity ? quantity : item.getPrs().getQuantity());
			item.setPrice(item.getPrs().getDiscountPrice() > 0 ? (item.getQuantity() * item.getPrs().getDiscountPrice()) : (item.getQuantity() * item.getPrs().getPrice()));
		}else { 
			item = getTransientItem(productID, size, quantity, currentSession.getSessID());
		}
		itmSrvc.insert(item);
		return "SUCCESS";
	}
	
	@PostMapping("/addToCartWithNoSession")
	@ResponseBody
	public String test(HttpServletRequest request,
			@RequestParam(name="id", required = true) Long productID,
			@RequestParam(name="quantity", required = true) Integer quantity,
			@RequestParam(name="size", required = true) String size) {
		Item item = getTransientItem(productID, size, quantity, (Long)request.getAttribute("param1"));
		itmSrvc.insert(item);
		return "SUCCESS";
	}
	
	private Item getTransientItem(Long productID, String size, Integer quantity, long sessID) {
		ProductPriceSize prs = prsSrvc.getPrsBySizeAndProductId(size, productID);
		
		Item item = new Item();
		item.setSessID(sessID);
		item.setProductID(productID);
		item.setPrice_id(prs.getPrice_id());
		item.setSize(size);
		item.setQuantity(prs.getQuantity() >= quantity ? quantity : prs.getQuantity());
		item.setPrice(prs.getDiscountPrice() > 0 ? (item.getQuantity() * prs.getDiscountPrice()) : (item.getQuantity() * prs.getPrice()));
		return item;
	}
	
	@ExceptionHandler(Exception.class)
	public String handleException(Exception e, Model model) {
		e.printStackTrace();
		if(Util.isStringNotEmpty(e.getMessage())) model.addAttribute("exceptionMsg", e.getMessage());
		return "404";
	}
}
