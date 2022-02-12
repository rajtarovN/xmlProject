package com.example.sluzbenik_back.controller;

import javax.ws.rs.GET;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xmldb.api.modules.XMLResource;

import com.example.sluzbenik_back.service.DostupneVakcineService;

@Controller
@RequestMapping(value = "/zalihe")
public class DostupneVakcineController {

	private DostupneVakcineService dostupneVakcineService;

	private static final Logger LOG = LoggerFactory.getLogger(DostupneVakcineController.class);

	@Autowired
	public DostupneVakcineController(DostupneVakcineService dostupneVakcineService) {
		this.dostupneVakcineService = dostupneVakcineService;
	}

	@GET
	@GetMapping
	public ResponseEntity<String> getXML() {
		try {
			XMLResource zalihe = dostupneVakcineService.readXML("zalihe.xml");
			return new ResponseEntity<>(zalihe.getContent().toString(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping(consumes = "application/xml")
	//@PreAuthorize("hasAnyRole('S')")
	public ResponseEntity<?> saveXML(@RequestBody String content) {
		String documentId = "zalihe";

		try {
			dostupneVakcineService.saveXML(documentId, content);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
