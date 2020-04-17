package com.dreamsjewelrystudio.additional.comparators;

import java.util.Comparator;

import com.dreamsjewelrystudio.models.Product;
import com.dreamsjewelrystudio.models.ProductPriceSize;

public class PriceComparator implements Comparator<Product>{
	
	private boolean isReversed;
	private ProductPriceComparator priceComparator;
	
	public PriceComparator(boolean isReversed) {
		this.isReversed = isReversed;
		priceComparator = new ProductPriceComparator();
	}
	
	@Override
	public int compare(Product o1, Product o2) {
		
		o1.getPrice().sort(priceComparator);
		o2.getPrice().sort(priceComparator);
		
		Comparator<ProductPriceSize> comparator = isReversed ? priceComparator.reversed() : priceComparator;
		return comparator.compare(o1.getPrice().get(0), o2.getPrice().get(0));
	}
	
	private class ProductPriceComparator implements Comparator<ProductPriceSize>{

		@Override
		public int compare(ProductPriceSize o1, ProductPriceSize o2) {
			boolean o1DiscountPrice = o1.getDiscountPrice()!=null && o1.getDiscountPrice()>0;
			boolean o2DiscountPrice = o2.getDiscountPrice()!=null && o2.getDiscountPrice()>0;
			
			if(o1DiscountPrice && !o2DiscountPrice) 
				return o1.getDiscountPrice().compareTo(o2.getPrice());
			else if(!o1DiscountPrice && o2DiscountPrice) 
				return o1.getPrice().compareTo(o2.getDiscountPrice());
			else if(!o1DiscountPrice && !o2DiscountPrice)
				return o1.getPrice().compareTo(o2.getPrice());
			else return o1.getDiscountPrice().compareTo(o2.getDiscountPrice());
		}
	}
}
