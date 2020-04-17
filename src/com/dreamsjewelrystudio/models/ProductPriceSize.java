package com.dreamsjewelrystudio.models;

import java.io.Serializable;

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
public class ProductPriceSize implements Comparable<ProductPriceSize>, Serializable{
	private static final long serialVersionUID = 1147350579884562465L;

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
		boolean thisDiscountPrice = this.getDiscountPrice()!=null && this.getDiscountPrice()>0;
		boolean oDiscountPrice = o.getDiscountPrice()!=null && o.getDiscountPrice()>0;
		
		if(thisDiscountPrice && !oDiscountPrice) 
			return this.getDiscountPrice().compareTo(o.getPrice());
		else if(!thisDiscountPrice && oDiscountPrice) 
			return this.getPrice().compareTo(o.getDiscountPrice());
		else if(!thisDiscountPrice && !oDiscountPrice)
			return this.getPrice().compareTo(o.getPrice());
		else return this.getDiscountPrice().compareTo(o.getDiscountPrice());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((discountPrice == null) ? 0 : discountPrice.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((price_id == null) ? 0 : price_id.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + ((product_id == null) ? 0 : product_id.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result + ((size == null) ? 0 : size.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductPriceSize other = (ProductPriceSize) obj;
		if (discountPrice == null) {
			if (other.discountPrice != null)
				return false;
		} else if (!discountPrice.equals(other.discountPrice))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (price_id == null) {
			if (other.price_id != null)
				return false;
		} else if (!price_id.equals(other.price_id))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (product_id == null) {
			if (other.product_id != null)
				return false;
		} else if (!product_id.equals(other.product_id))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		if (size == null) {
			if (other.size != null)
				return false;
		} else if (!size.equals(other.size))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ProductPriceSize [price_id=" + price_id + ", product_id=" + product_id + ", price=" + price + ", size="
				+ size + ", discountPrice=" + discountPrice + ", quantity=" + quantity + ", product=" + product + "]";
	}
}
