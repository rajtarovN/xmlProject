package com.ftn.email.emailService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ftn.email.emailService.service.EmailService;

@Controller
@RequestMapping(value = "/email")
@CrossOrigin(origins = "*")
public class EmailController {

	@Autowired
	private EmailService emailService;

	
}
