package com.dreamsjewelrystudio.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dreamsjewelrystudio.additional.PayPalIntegrator;

@RestController
@RequestMapping(value = "/paypal")
public class PayPalController {
		
	@Autowired
    private PayPalIntegrator paypal;
    
    @PostMapping(value = "/make/payment")
    public Map<String, Object> makePayment(@RequestParam("sum") String sum){
        return paypal.createPayment(sum);
    }
    
    @GetMapping(value = "/complete/payment")
    public Map<String, Object> completePayment(HttpServletRequest request){
        return paypal.completePayment(request);
    }
}
