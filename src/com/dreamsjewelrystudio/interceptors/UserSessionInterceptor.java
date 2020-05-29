package com.dreamsjewelrystudio.interceptors;

import java.util.Objects;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.dreamsjewelrystudio.service.SessionService;
import com.dreamsjewelrystudio.util.Util;

public class UserSessionInterceptor implements HandlerInterceptor{
	
	@Autowired private SessionService sessSrvc;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
		Cookie coockie = Util.getSessionID(request.getCookies(), Util.SESSID);
		if(Objects.isNull(coockie) || !Util.isStringNotEmpty(coockie.getValue()) || Objects.isNull(sessSrvc.findSessionByToken(coockie.getValue()))) { 
			response.sendRedirect(request.getContextPath()+"/404");
			return false;
		}
		return true;
	}
}
