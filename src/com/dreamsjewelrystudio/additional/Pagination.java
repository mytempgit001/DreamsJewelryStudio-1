package com.dreamsjewelrystudio.additional;

import org.springframework.beans.factory.annotation.Autowired;

import com.dreamsjewelrystudio.service.ProductService;

public class Pagination {
	
	private int productsAmount = -1;
	private int productsAmountToBeRenderedOnPage;
	private int pagesAmount = -1;
	
	private int[] productsRange;
	
	@Autowired
	private ProductService productJpa;
	
	public Pagination() {}
	
	public Pagination(int productsToRender) {
		productsAmountToBeRenderedOnPage = productsToRender;
	}
	
	public int[] calculateRange(int numPage) {
		if(numPage>pagesAmount) return null;
		
		productsRange = new int[2];
		
		int from = 0;
		int step = productsAmountToBeRenderedOnPage;
		
		for(int i = 1; i < numPage; i++) from += step;
		
		productsRange[0] = from;
		productsRange[1] = productsAmountToBeRenderedOnPage;
		
		return productsRange;
	}

	public int getProductsAmountToBeRenderedOnPage() {
		return productsAmountToBeRenderedOnPage;
	}

	public void setProductsAmountToBeRenderedOnPage(int productsAmountToBeRenderedOnPage) {
		this.productsAmountToBeRenderedOnPage = productsAmountToBeRenderedOnPage;
	}

	public int getPagesAmount() {
		if(productsAmount == -1)
			productsAmount = (int) productJpa.selectCountProduct();
		
		int temp = productsAmount/productsAmountToBeRenderedOnPage;
		if(productsAmount%productsAmountToBeRenderedOnPage > 0) temp++;
		pagesAmount = temp;
		return pagesAmount;
	}
	
	public void setPagesAmount(int pagesAmount) {
		this.pagesAmount = pagesAmount;
	}
}