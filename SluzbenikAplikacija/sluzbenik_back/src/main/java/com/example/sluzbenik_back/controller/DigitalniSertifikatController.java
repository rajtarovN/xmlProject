package com.example.sluzbenik_back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.sluzbenik_back.dto.SertifikatNaprednaDTO;
import com.example.sluzbenik_back.service.DigitalniSertifikatService;

@Controller
@RequestMapping(value = "/sertifikat")
public class DigitalniSertifikatController {

	@Autowired
	private DigitalniSertifikatService digitalniSertifikatService;

	@GetMapping(value = "/getAll", produces = "text/xml")
	public ResponseEntity<?> getAllSertifikati() {

		try {
			System.out.println(digitalniSertifikatService.getAllSertifikati());
			return new ResponseEntity<>(digitalniSertifikatService.getAllSertifikati(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/naprednaPretraga", consumes = "application/xml", produces = "application/xml")
	public ResponseEntity<?> naprednaPretraga(@RequestBody SertifikatNaprednaDTO dto) {

		try {
			return new ResponseEntity<>(this.digitalniSertifikatService.naprednaPretraga(dto), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
