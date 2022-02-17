package com.example.demo.controller;

import com.example.demo.dto.EvidencijaVakcinacijeDTO;
import com.example.demo.dto.EvidentiraneVakcineDTO;
import com.example.demo.dto.IdentificationDTO;
import com.example.demo.dto.ListaEvidentiranihVakcina;
import com.example.demo.dto.SaglasnostDTO;
import com.example.demo.dto.SaglasnostNaprednaDTO;
import com.example.demo.service.SaglasnostService;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.ReaderInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/saglasnost")
public class SaglasnostController {

	@Autowired
	private SaglasnostService saglasnostService;

	@PreAuthorize("hasRole('Z')")
	@GetMapping(path = "/pretragaTermina/{imePrezime}/{datumTermina}", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<?> pretragaTermina(@PathVariable("imePrezime") String imePrezime,
			@PathVariable("datumTermina") Date datumTermina) {
		ArrayList<SaglasnostDTO> lista = new ArrayList<>();
		if (imePrezime.equals("all")) {
			imePrezime = null;
		}

		try {
			lista = this.saglasnostService.pretragaTermina(imePrezime, datumTermina);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		if (!lista.isEmpty())
			return new ResponseEntity<>(lista, HttpStatus.OK);
		else
			return new ResponseEntity<>("Nema termina po trazenim parametrima.", HttpStatus.OK);
	}

	@PreAuthorize("hasRole('Z')")
	@GetMapping(path = "/pronadjiPoEmailu/{email}", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<?> pronadjiPoEmailu(@PathVariable("email") String email) {
		ArrayList<EvidentiraneVakcineDTO> lista = new ArrayList<>();

		try {
			lista = this.saglasnostService.pronadjiPoEmailu(email);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		if (!lista.isEmpty())
			return new ResponseEntity<>(lista, HttpStatus.OK);
		else
			return new ResponseEntity<>("Nema prethodno evidentiranih vakcina.", HttpStatus.OK);
	}

	@PreAuthorize("hasRole('Z')")
	@PostMapping(path = "/sacuvajEvidenciju/{brojSaglasnosti}", consumes = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<?> sacuvajEvidenciju(@PathVariable("brojSaglasnosti") String brojSaglasnosti,
			@RequestBody EvidencijaVakcinacijeDTO evidencijaVakcinacijeDTO) {
		try {
			return new ResponseEntity<>(
					this.saglasnostService.dodajEvidenciju(evidencijaVakcinacijeDTO, brojSaglasnosti), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('Z')")
	@PostMapping(path = "/sacuvajEvidentiraneVakcine/{brojSaglasnosti}", consumes = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<?> sacuvajEvidentiraneVakcine(@PathVariable("brojSaglasnosti") String brojSaglasnosti,
			@RequestBody String evidentiraneVakcineDTO) {
		try {
			JAXBContext context = JAXBContext.newInstance(ListaEvidentiranihVakcina.class);
			InputStream inputStream = new ReaderInputStream(new StringReader(evidentiraneVakcineDTO));

			Unmarshaller unmarshaller = context.createUnmarshaller();

			ListaEvidentiranihVakcina evidentiraneVakcine = (ListaEvidentiranihVakcina) unmarshaller
					.unmarshal(inputStream);
			return new ResponseEntity<>(
					this.saglasnostService.dodajEvidentiraneVakcine(evidentiraneVakcine, brojSaglasnosti),
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/generatePDF/{id}")
	public ResponseEntity<byte[]> generatePDF(@PathVariable("id") String id) {

		String file_path = this.saglasnostService.generatePDF(id);

		try {
			File file = new File(file_path);
			FileInputStream fileInputStream = new FileInputStream(file);
			return new ResponseEntity<byte[]>(IOUtils.toByteArray(fileInputStream), HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('G')")
	@GetMapping(path = "/zadnjaSaglasnost/{email}", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<?> getXMLByEmail(@PathVariable String email) {
		try {
			String saglasnost = saglasnostService.pronadjiZadnjuSaglasnost(email);
			if (saglasnost == null)
				return new ResponseEntity<>(saglasnost, HttpStatus.BAD_REQUEST);
			return new ResponseEntity<>(saglasnost, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/saveSaglasnost/{documentId}/{vakcine}", consumes = "application/xml")
	public ResponseEntity<?> saveXML(@RequestBody String content, @PathVariable String documentId,
			@PathVariable String vakcine) {
		try {
			System.out.println(content);
			saglasnostService.saveXML(documentId, content, vakcine);
			saglasnostService.saveRDF(content, "/interesovanje");
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/generateHTML/{id}")
	public ResponseEntity<byte[]> generateHTML(@PathVariable("id") String id) {

		try {
			String file_path = this.saglasnostService.generateHTML(id);
			File file = new File(file_path);
			FileInputStream fileInputStream = new FileInputStream(file);
			return new ResponseEntity<byte[]>(IOUtils.toByteArray(fileInputStream), HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping(path = "/xml/{id}", produces = "application/xml")
	public ResponseEntity<String> getXML(@PathVariable("id") String id) {

		try {
			XMLResource xml = saglasnostService.getXML(id);
			return new ResponseEntity<>(xml.getContent().toString(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "/allXmlByEmail/{userEmail}")
	public ResponseEntity<String> allXmlByEmail(@PathVariable("userEmail") String userEmail) {
		try {
			return new ResponseEntity<>(saglasnostService.allXmlByEmail(userEmail), HttpStatus.OK);
		} catch (IllegalAccessException | InstantiationException | ClassNotFoundException
				| DatatypeConfigurationException | XMLDBException | JAXBException | IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}


	@PreAuthorize("hasRole('Z')")
	@GetMapping(path = "/getAvailableVaccines", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<?> getAvailableVaccines(){
		try {
			return new ResponseEntity<>(saglasnostService.getAvailableVaccines(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = "/getAll", produces = "application/xml")
	public ResponseEntity<?> getAllSaglasnosti() {
		IdentificationDTO dto = new IdentificationDTO();
		try {
			dto.setIds(saglasnostService.getAllSaglasnosti());
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/naprednaPretraga", produces = "application/xml")
	public ResponseEntity<IdentificationDTO> naprednaPretraga(@RequestBody SaglasnostNaprednaDTO dto) throws Exception {
		String ime = "\"" + dto.getIme() + "\"";
		String prezime = "\"" + dto.getPrezime() + "\"";
		String jmbg = "\"" + dto.getJmbg() + "\"";
		String datum = "\"" + dto.getDatum() + "\"";
		String email = "\"" + dto.getEmail() + "\"";

		List<String> lista = this.saglasnostService.naprednaPretraga(ime, prezime, jmbg, datum, email, dto.isAnd());

		if (!lista.isEmpty())

			return new ResponseEntity<>(new IdentificationDTO(lista), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping(path = "/obicnaPretraga/{searchTerm}",  produces = "application/xml")
	public ResponseEntity<IdentificationDTO> obicnaPretraga(@PathVariable("searchTerm") String searchTerm){
		IdentificationDTO dto = new IdentificationDTO();
		try {
			dto.setIds(saglasnostService.obicnaPretraga(searchTerm));
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
