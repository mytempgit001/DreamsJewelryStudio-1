package com.dreamsjewelrystudio.projections;

import org.springframework.beans.factory.annotation.Value;

public interface RelatedProductsProjection {
	@Value("#{target.product_id}")
	Long getProduct_id();
	@Value("#{target.main_img}")
	String getMain_Img();
}
