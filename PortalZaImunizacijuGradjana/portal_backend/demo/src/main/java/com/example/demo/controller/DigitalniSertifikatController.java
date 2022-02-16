package com.example.demo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xmldb.api.modules.XMLResource;

import com.example.demo.dto.IdentificationDTO;
import com.example.demo.dto.SertifikatNaprednaDTO;
import com.example.demo.service.DigitalniSertifikatService;

@Controller
@RequestMapping(value = "/sertifikat")
public class DigitalniSertifikatController {

	private DigitalniSertifikatService digitalniSertifikatService;

	private static final Logger LOG = LoggerFactory.getLogger(InteresovanjeController.class);

	@Autowired
	public DigitalniSertifikatController(DigitalniSertifikatService digitalniSertifikatService) {
		this.digitalniSertifikatService = digitalniSertifikatService;
	}

	@GetMapping("/generatePDF/{id}")
	public ResponseEntity<byte[]> generatePDF(@PathVariable("id") String id) {

		String file_path = this.digitalniSertifikatService.generatePDF(id);
		System.out.println(file_path + " ovde je");
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
			String file_path = this.digitalniSertifikatService.generateHTML(id);
			File file = new File(file_path);
			FileInputStream fileInputStream = new FileInputStream(file);
			return new ResponseEntity<byte[]>(IOUtils.toByteArray(fileInputStream), HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}
	
	@GetMapping(value = "/getAll", produces = "application/xml")
	public ResponseEntity<?> getAllSertifikati() {
		IdentificationDTO dto = new IdentificationDTO();
		try {
			dto.setIds(digitalniSertifikatService.getAllSertifikati());
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}


	@PostMapping(value = "/naprednaPretraga", produces = "application/xml")
	public ResponseEntity<IdentificationDTO> naprednaPretraga(@RequestBody SertifikatNaprednaDTO dto) throws Exception {
		String brojPasosa = "\"" + dto.getBroj_pasosa() + "\"";
		String brojSertifikata = "\"" + dto.getBroj_sertifikata() + "\"";
		String datum = "\"" + dto.getDatum_izdavaja() + "\"";
		String ime = "\"" + dto.getIme() + "\"";
		String jmbg = "\"" + dto.getJmbg() + "\"";
		String prezime = "\"" + dto.getPrezime() + "\"";

		List<String> lista = this.digitalniSertifikatService.naprednaPretraga(brojPasosa, brojSertifikata, datum, ime,
				jmbg, prezime, dto.isAnd());

		if (!lista.isEmpty())

			return new ResponseEntity<>(new IdentificationDTO(lista), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

       
    

    @GetMapping(path = "/allXmlByEmail/{userEmail}")
    public ResponseEntity<String> allXmlByEmail(@PathVariable("userEmail") String userEmail){
        try{
            return new ResponseEntity<>(digitalniSertifikatService.allXmlByEmail(userEmail), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/xml/{id}", produces = "application/xml")
    public ResponseEntity<String> getXML(@PathVariable("id") String id) {

        try {
            XMLResource xml = digitalniSertifikatService.getXML(id);
            return new ResponseEntity<>(xml.getContent().toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
