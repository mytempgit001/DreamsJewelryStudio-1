package com.dreamsjewelrystudio.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "item")
public class Item {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itemID")
	private long itemID;
	
	@Column(name = "productID")
	private long productID;
	
	@Column(name = "price_id")
	private long price_id;
	
	@Column(name = "sessID")
	private long sessID;
	
	@Column(name = "quantity")
	private Integer quantity;
	
	@Column(name = "size")
	private String size;
	
	@Column(name = "price")
	private float price;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sessID", insertable = false, updatable = false)
	private Session session;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productID", referencedColumnName = "product_id", insertable = false, updatable = false)
	private Product product;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "price_id", referencedColumnName = "price_id", insertable = false, updatable = false)
	private ProductPriceSize prs;
	
	public long getItemID() {
		return itemID;
	}
	public void setItemID(long itemID) {
		this.itemID = itemID;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
	}
	public long getProductID() {
		return productID;
	}
	public void setProductID(long productID) {
		this.productID = productID;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public long getSessID() {
		return sessID;
	}
	public void setSessID(long sessID) {
		this.sessID = sessID;
	}
	public long getPrice_id() {
		return price_id;
	}
	public void setPrice_id(long price_id) {
		this.price_id = price_id;
	}
	public ProductPriceSize getPrs() {
		return prs;
	}
	public void setPrs(ProductPriceSize prs) {
		this.prs = prs;
	}
}
