package com.example.demo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import com.example.demo.dto.DokumentDTO;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xmldb.api.modules.XMLResource;

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

	@GetMapping(value = "/{email}", produces = "application/xml")
	public ResponseEntity<?> getXMLByEmail(@PathVariable String email) {
		try {
			XMLResource interesovanje = interesovanjeService.pronadjiInteresovanjePoEmailu(email);
			//PrintInteresovanje.printInteresovanje(interesovanje);

			if(interesovanje == null)
				return new ResponseEntity<>( HttpStatus.NOT_FOUND);
			return new ResponseEntity<>(interesovanje.getContent(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/updatePending", produces = "application/xml")
	public ResponseEntity<?> updatePendingInteresovanja() {
		try {
			interesovanjeService.updatePendingInteresovanja();
			return new ResponseEntity<>(HttpStatus.OK);
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
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteInteresovanje(@PathVariable("id") String id) throws Exception {
		interesovanjeService.deleteXML(id);
		return new ResponseEntity<>( HttpStatus.OK);
	}

	@GetMapping("/generatePDF/{id}")
	public ResponseEntity<byte[]> generatePDF(@PathVariable("id") String id) {

		String file_path = this.interesovanjeService.generatePDF(id);
		System.out.println(file_path+" ovde je");
		try {
			File file = new File(file_path);
			FileInputStream fileInputStream = new FileInputStream(file);
			return new ResponseEntity<byte[]>(IOUtils.toByteArray(fileInputStream), HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}
	@GetMapping("/generateHTML/{id}")
	public ResponseEntity<byte[]> generateHTML(@PathVariable("id") String id) {

		try {
			String file_path = this.interesovanjeService.generateHTML(id);
			File file = new File(file_path);
			FileInputStream fileInputStream = new FileInputStream(file);
			return new ResponseEntity<byte[]>(IOUtils.toByteArray(fileInputStream), HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "/generateJson/{documentId}")
	public ResponseEntity<byte[]> generateJson(@PathVariable("documentId") String documentId){
		try {
			return new ResponseEntity<>(interesovanjeService.generateJson(documentId), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "/generateRdf/{documentId}")
	public ResponseEntity<byte[]> generateRdf(@PathVariable("documentId") String documentId){
		try {
			return new ResponseEntity<>(interesovanjeService.generateRdf(documentId), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value="/getAllInDateRange/{fromDate}/{toDate}", produces = "application/xml")
	public ResponseEntity<String> getAllInDateRange(@PathVariable("fromDate") String fromDate, @PathVariable("toDate") String toDate){
		try {
			String ret = interesovanjeService.pronadjiPoVremenskomPeriodu(fromDate, toDate);
			System.out.println(ret);
			return new ResponseEntity<>(ret, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		}
	}

	@GetMapping(path ="/getAllI/{email}")
	public ResponseEntity<?> getAllI(@PathVariable("email") String email) {
		System.out.println("USLOOOOOOO");
		try {
			List<DokumentDTO> retval = interesovanjeService.getInteresovanjeAllByEmail(email);
			if (retval == null || retval.isEmpty()) {
				return new ResponseEntity<>("Nema interesovanja za prisutnog gradjana.", HttpStatus.OK);
			} else
				return new ResponseEntity<>(retval, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Error pri dobavljanju potvrda.", HttpStatus.NOT_FOUND);
		}
	}

}
