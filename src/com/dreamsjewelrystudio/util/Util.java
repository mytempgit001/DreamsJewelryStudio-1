package com.dreamsjewelrystudio.util;

import java.util.Arrays;

import javax.servlet.http.Cookie;

public final class Util {
	
	public static Integer PRODUCTS_AMOUNT;
	public static final String ADMIN = "ID";
	public static final String SESSID = "sessID";
	public static Cookie getSessionID(Cookie[] cookies) {
		if(cookies == null || cookies.length==0) return null;
		return Arrays.stream(cookies)
				  .filter(cookie -> cookie.getName().equals(SESSID))
				  .findFirst()
				  .orElse(null);
	}
	
	public static boolean isStringNotEmpty(String str) {
		return str!=null && str.length()!=0;
	}
}
