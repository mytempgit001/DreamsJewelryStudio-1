package com.dreamsjewelrystudio.interceptors;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.dreamsjewelrystudio.models.Session;
import com.dreamsjewelrystudio.service.SessionService;
import com.dreamsjewelrystudio.util.Util;

public class UserSessionInterceptor implements HandlerInterceptor{
	
	@Autowired private SessionService sessSrvc;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
		Cookie coockie = Util.getSessionID(request.getCookies(), Util.SESSID);
		final String URI = request.getRequestURI();
		
		if(URI.equals("/addToCart")) return checkAddToCartSession(request, response, coockie);
		
		
		if(Objects.isNull(coockie) || !Util.isStringNotEmpty(coockie.getValue()) || Objects.isNull(sessSrvc.findSessionByToken(coockie.getValue()))) { 
			response.sendRedirect(request.getContextPath()+"/404");
			return false;
		}
		return true;
	}
	
	public boolean checkAddToCartSession(HttpServletRequest request, HttpServletResponse response, Cookie sessionCookie) throws ServletException, IOException {
		if(Objects.isNull(sessionCookie)) {
			request.setAttribute("param1", assignSession(response).getSessID());
	        request.getRequestDispatcher("/addToCartWithNoSession").include(request, response);
			return false;
		}
		
		Session currentSession = sessSrvc.findSessionItemPrsByToken(sessionCookie.getValue());
		
		if(Objects.isNull(currentSession)) {
			request.setAttribute("param1", assignSession(response).getSessID());
	        request.getRequestDispatcher("/addToCartWithNoSession").include(request, response);
			return false;
		}
		
		request.setAttribute("param1", currentSession);
		return true;
	}
	
	public Session assignSession(HttpServletResponse response) {
		String uuid = UUID.randomUUID().toString();
		Cookie newCookie = new Cookie(Util.SESSID, uuid);
        newCookie.setMaxAge(172800);
        response.addCookie(newCookie);
        
        Session currentSession = new Session();
        currentSession.setToken(uuid);
        currentSession.setLastUsed(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		return sessSrvc.createNewSession(currentSession);
	}
}
