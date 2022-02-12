package com.example.demo.controller;

import java.util.UUID;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xmldb.api.modules.XMLResource;

import com.example.demo.model.interesovanje.Interesovanje;
import com.example.demo.model.interesovanje.PrintInteresovanje;
import com.example.demo.service.InteresovanjeService;

@Controller
@RequestMapping(value = "/interesovanje")
public class InteresovanjeController {

	private InteresovanjeService interesovanjeService;

	private static final Logger LOG = LoggerFactory.getLogger(InteresovanjeController.class);

	@Autowired
	public InteresovanjeController(InteresovanjeService interesovanjeService) {
		this.interesovanjeService = interesovanjeService;
	}

	@GetMapping(value = "/{email}", consumes = "application/xml")
	public ResponseEntity<?> getXMLByEmail(@PathVariable String email) {
		try {
			XMLResource interesovanje = interesovanjeService.pronadjiInteresovanjePoEmailu(email);
			//PrintInteresovanje.printInteresovanje(interesovanje);
			//TODO send string
			return new ResponseEntity<>(interesovanje.getContent(), HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/{documentId}", consumes = "application/xml")
	public ResponseEntity<?> saveXML(@RequestBody String content, @PathVariable String documentId) {
		try {
			System.out.println(content);
			interesovanjeService.saveXML(documentId, content);
			interesovanjeService.saveRDF(content, "/interesovanje");
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> deleteNotice(@PathVariable("id") String id) throws Exception {
		interesovanjeService.deleteXML("interesovanje_" + id + ".xml");
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}
}
