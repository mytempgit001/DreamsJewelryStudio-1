package com.dreamsjewelrystudio.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author stepanovam
 *
 */
/**
 * @author stepanovam
 *
 */
@Entity
@Table(name = "product_price_size")
public class ProductPriceSize implements Comparable<ProductPriceSize>{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="price_id")
	private Long price_id;
	
	@Column(name = "product_id")
	private Long product_id;
	
	@Column(name="price")
	private Float price;
	
	@Column(name="size")
	private String size;
	
	@Column(name="discount_price")
	private Float discountPrice;
	
	@Column(name="discount_availability")
	private Boolean discountAvailability;
	
	@Column(name="quantity")
	private Integer quantity;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable=false, updatable=false)
	private Product product;

	public Long getPrice_id() {
		return price_id;
	}
	public void setPrice_id(Long price_id) {
		this.price_id = price_id;
	}
	public Long getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public Float getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(Float discountPrice) {
		this.discountPrice = discountPrice;
	}
	public Boolean getDiscountAvailability() {
		return discountAvailability;
	}
	public void setDiscountAvailability(Boolean discountAvailability) {
		this.discountAvailability = discountAvailability;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	@Override
	public int compareTo(ProductPriceSize o) {
		if(this.getDiscountAvailability() && !o.getDiscountAvailability()) 
			return this.getDiscountPrice().compareTo(o.getPrice());
		else if(!this.getDiscountAvailability() && o.getDiscountAvailability()) 
			return this.getPrice().compareTo(o.getDiscountPrice());
		else if(!this.getDiscountAvailability() && !o.getDiscountAvailability())
			return this.getPrice().compareTo(o.getPrice());
		else return this.getDiscountPrice().compareTo(o.getDiscountPrice());
	}
}
