package com.example.demo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.ws.rs.GET;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.xmldb.api.modules.XMLResource;

import com.example.demo.dto.DokumentDTO;
import com.example.demo.dto.IdentificationDTO;
import com.example.demo.dto.SertifikatNaprednaDTO;
import com.example.demo.service.DigitalniSertifikatService;
import com.example.demo.service.ZahtevService;

@Controller
@RequestMapping(value = "/sertifikat")
public class DigitalniSertifikatController {

	private DigitalniSertifikatService digitalniSertifikatService;
	
	@Autowired
	private ZahtevService zahtevService;
	
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

	@PreAuthorize("hasRole('G')")
	@GetMapping(path ="/getAllS/{email}")
	public ResponseEntity<?> getAllS(@PathVariable("email") String email) {
		System.out.println("USLOOOOOOO");
		try {
			List<DokumentDTO> retval = digitalniSertifikatService.getSertifikatiAllByEmail(email);
			if (retval.isEmpty()) {
				return new ResponseEntity<>("Nema izdatih sertifikata za prisutnog gradjana.", HttpStatus.OK);
			} else
				return new ResponseEntity<>(retval, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Error pri dobavljanju sertifikata.", HttpStatus.NOT_FOUND);
		}
	}
	@GetMapping(path = "/obicnaPretraga/{searchTerm}",  produces = "application/xml")
	public ResponseEntity<IdentificationDTO> obicnaPretraga(@PathVariable("searchTerm") String searchTerm){
		IdentificationDTO dto = new IdentificationDTO();
		try {
			dto.setIds(digitalniSertifikatService.obicnaPretraga(searchTerm));
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "/generateJson/{documentId}")
	public ResponseEntity<byte[]> generateJson(@PathVariable("documentId") String documentId){
		try {
			return new ResponseEntity<>(digitalniSertifikatService.generateJson(documentId), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "/generateRdf/{documentId}")
	public ResponseEntity<byte[]> generateRdf(@PathVariable("documentId") String documentId){
		try {
			return new ResponseEntity<>(digitalniSertifikatService.generateRdf(documentId), HttpStatus.OK);
    } catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
  }

	@GET
	@GetMapping(path = "/referenciraniDoc/{id}",  produces = "application/xml")
	public ResponseEntity<IdentificationDTO> getDocumentIdReferences(@PathVariable("id") String id) {
		
		IdentificationDTO dto = new IdentificationDTO();
		try {
			dto.setIds(zahtevService.getZahtevRefFromSeeAlso("http://www.ftn.uns.ac.rs/xml_i_veb_servisi/digitalni_zeleni_sertifikat/" + id));
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
