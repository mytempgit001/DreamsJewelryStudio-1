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

@Entity
@Table(name = "product_price_size")
public class ProductPriceSize implements Comparable<ProductPriceSize>{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="price_id")
	private long price_id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable=false, updatable=false)
	private Product product;
	
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

	public long getPriceId() {
		return price_id;
	}

	public void setPriceId(long price_id) {
		this.price_id = price_id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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

	public void setDiscountPrice(Float discount_price) {
		this.discountPrice = discount_price;
	}

	public Boolean getDiscountAvailability() {
		return discountAvailability;
	}

	public void setDiscountAvailability(Boolean discount_availability) {
		this.discountAvailability = discount_availability;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
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
