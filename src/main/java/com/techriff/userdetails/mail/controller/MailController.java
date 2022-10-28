package com.techriff.userdetails.mail.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.techriff.userdetails.dto.MailRequest;
import com.techriff.userdetails.dto.MailResponse;
import com.techriff.userdetails.mail.service.EmailService;

@RestController
public class MailController 
{
	@Autowired
	private EmailService service;

	@PostMapping("/sendingEmail")
	public MailResponse sendEmail(@RequestBody MailRequest request) {
		Map<String, Object> model = new HashMap<>();
		model.put("Name", request.getName());
		model.put("location", "Odisha,India");
		return service.sendEmail(request, model);

	}

}
