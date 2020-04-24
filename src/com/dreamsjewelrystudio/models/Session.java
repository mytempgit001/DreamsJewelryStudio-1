package com.dreamsjewelrystudio.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "session")
@NamedEntityGraph(name = "session-items", attributeNodes = @NamedAttributeNode(value = "items"))
public class Session {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sessID")
	private long sessID;
	
	@Column(name = "token")
	private String token;
	
	@Column(name = "lastUsed")
	private String lastUsed;
	
	@OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.LAZY)
	private List<Item> items;
	
	public long getSessID() {
		return sessID;
	}
	public void setSessID(long sessID) {
		this.sessID = sessID;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
//	public long getUserID() {
//		return userID;
//	}
//	public void setUserID(long userID) {
//		this.userID = userID;
//	}
	public String getLastUsed() {
		return lastUsed;
	}
	public void setLastUsed(String lastUsed) {
		this.lastUsed = lastUsed;
	}
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
}
