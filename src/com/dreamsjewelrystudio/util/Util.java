package com.dreamsjewelrystudio.util;

import java.util.Arrays;

import javax.servlet.http.Cookie;

public final class Util {
	
	public static Integer PRODUCTS_AMOUNT;
	public static final String ADMIN = "ID";
	public static final String SESSID = "sessID";
	public static Cookie getSessionID(Cookie[] cookies, String coockie) {
		if(cookies == null || cookies.length==0) return null;
		return Arrays.stream(cookies)
				  .filter(str -> str.getName().equals(coockie))
				  .findFirst()
				  .orElse(null);
	}
	
	public static boolean isStringNotEmpty(String str) {
		return str!=null && str.length()!=0;
	}
}
