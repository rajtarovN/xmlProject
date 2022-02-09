package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.service.InteresovanjeService;

@Controller
@RequestMapping(value = "/api/interesovanje")
public class InteresovanjeController {

	private InteresovanjeService interesovanjeService;
	
	private static final Logger LOG = LoggerFactory.getLogger(InteresovanjeController.class);

	
	@Autowired
	public InteresovanjeController(InteresovanjeService interesovanjeService) {
		this.interesovanjeService = interesovanjeService;
	}

	@PostMapping(path = "/{jmbg}/{datum}", consumes = "application/xml")
	public ResponseEntity<?> saveXML(@PathVariable String jmbg, @PathVariable String datum,
			@RequestBody String content) {
		String documentId = jmbg + '_' + datum;

		try {
			interesovanjeService.saveXML(documentId, content);
			//interesovanjeService.saveRDF(content, documentId);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
