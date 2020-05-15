package com.dreamsjewelrystudio.util;

public class CatalogPagination {
	
	private int productsAmountToBeRenderedOnPage;
	private int pagesAmount = -1;
	private int[] productsRange;
	
	public CatalogPagination(int productsToRender) {
		productsAmountToBeRenderedOnPage = productsToRender;
		productsRange = new int[2];
		if(Util.PRODUCTS_AMOUNT%productsAmountToBeRenderedOnPage > 0) 
			pagesAmount = (Util.PRODUCTS_AMOUNT/productsAmountToBeRenderedOnPage)+1;
		else pagesAmount = Util.PRODUCTS_AMOUNT/productsAmountToBeRenderedOnPage;
	}
	
	public int[] calculateRange(int numPage) {
		if(numPage>pagesAmount) return null;
		int from = 0;
		for(int i = 1; i < numPage; i++) from += productsAmountToBeRenderedOnPage;
		productsRange[0] = from;
		productsRange[1] = productsAmountToBeRenderedOnPage;
		return productsRange;
	}

	public int getPagesAmount() {
		return pagesAmount;
	}
}