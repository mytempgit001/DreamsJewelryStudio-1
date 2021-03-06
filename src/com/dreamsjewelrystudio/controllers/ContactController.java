package com.dreamsjewelrystudio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dreamsjewelrystudio.models.Messages;
import com.dreamsjewelrystudio.service.MessagesServiceImpl;

@Controller
public class ContactController {
	
	@Autowired
	private MessagesServiceImpl msgSrvc;
	
	@GetMapping("/contact")
	public String getContactPage() {
		return "contact";
	}
	
	@PostMapping("/sendMessage")
	@ResponseBody
	public String sendMessage(
			@RequestParam(name="msgType") String msgType,
			@RequestParam(name="name") String name,
			@RequestParam(name="email") String email,
			@RequestParam(name="msg") String msg) {
		
		Messages message = new Messages();
		message.setMsgType(msgType);
		message.setName(name);
		message.setEmail(email);
		message.setMsg(msg);
		
		msgSrvc.updateMessage(message);
		return "SUCCESS";
	}
}
