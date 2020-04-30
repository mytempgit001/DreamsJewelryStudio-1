package com.dreamsjewelrystudio.util;

import java.util.Arrays;

import javax.servlet.http.Cookie;

public final class Util {
	
	public static final String ADMIN = "ID";
	public static final String SESSID = "sessID";
	public static Cookie getSessionID(Cookie[] cookies) {
		if(cookies == null || cookies.length==0) return null;
		return Arrays.stream(cookies)
				  .filter(cookie -> cookie.getName().equals(SESSID))
				  .findFirst()
				  .orElse(null);
	}
}
