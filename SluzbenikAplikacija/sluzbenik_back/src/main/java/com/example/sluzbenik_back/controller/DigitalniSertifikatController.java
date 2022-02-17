package com.example.sluzbenik_back.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.sluzbenik_back.dto.DokumentDTO;
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

	@PreAuthorize("hasRole('S')")
	@GetMapping(path = "/getAllXmlByEmail/{email}", produces = "application/xml")
	public ResponseEntity<?> getAllXmlByEmail(@PathVariable("email") String email) {
		try {
			List<DokumentDTO> retval = digitalniSertifikatService.getAllXmlIdsByEmail(email);
			if (retval.isEmpty()) {
				return new ResponseEntity<>("Nema izdatih sertifikata za prisutnog gradjana.", HttpStatus.OK);
			} else
				return new ResponseEntity<>(retval, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Error pri dobavljanju sertifikata.", HttpStatus.NOT_FOUND);
		}
	}

	@PreAuthorize("hasRole('S')")
	@GetMapping(path = "/generatePDF/{documentId}")
	public ResponseEntity<byte[]> generatePDF(@PathVariable("documentId") String documentId) {

		String file_path = this.digitalniSertifikatService.generatePDF(documentId);

		try {
			File file = new File(file_path);
			FileInputStream fileInputStream = new FileInputStream(file);
			return new ResponseEntity<byte[]>(IOUtils.toByteArray(fileInputStream), HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('S')")
	@GetMapping(value = "/obicnaPretraga/{searchTerm}", produces = "application/xml")
	public ResponseEntity<?> obicnaPretraga(@PathVariable("searchTerm") String searchTerm){
		try {
			return new ResponseEntity<>(this.digitalniSertifikatService.obicnaPretraga(searchTerm), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
