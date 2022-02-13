package com.ftn.email.emailService.controller;

import javax.ws.rs.POST;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ftn.email.emailService.service.EmailService;

@Controller
@RequestMapping(value = "/email")
@CrossOrigin(origins = "*")
public class EmailController {

	@Autowired
	private EmailService emailService;

	private static final Logger LOG = LoggerFactory.getLogger(EmailController.class);

	@POST
	@PostMapping(consumes = "application/xml")
	public ResponseEntity<?> sendEmail(@RequestBody String email) {
		LOG.info("Sending email...");

		try {
			emailService.sendMail(email);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
