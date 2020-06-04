package com.dreamsjewelrystudio.interceptors;

import java.util.Objects;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.dreamsjewelrystudio.service.AdminsService;
import com.dreamsjewelrystudio.util.Util;

public class AdminSessionInterceptor implements HandlerInterceptor{
	@Autowired private AdminsService adminSrvc;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
		Cookie coockie = Util.getSessionID(request.getCookies(), Util.ADMIN);
		if(Objects.isNull(coockie) || !Util.isStringNotEmpty(coockie.getValue()) || Objects.isNull(adminSrvc.findAdminBySession(coockie.getValue()))) {
			response.sendRedirect(request.getContextPath()+"/admin/alogin");
			return false;
		}
		return true;
	   }
}
