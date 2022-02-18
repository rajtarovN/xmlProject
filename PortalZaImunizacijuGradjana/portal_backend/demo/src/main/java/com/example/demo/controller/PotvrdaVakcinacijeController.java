package com.example.demo.controller;

import com.example.demo.dto.DokumentDTO;
import com.example.demo.dto.IdentificationDTO;
import com.example.demo.dto.ListaEvidentiranihVakcina;
import com.example.demo.dto.PotvrdaNaprednaDTO;
import com.example.demo.dto.PotvrdaVakcinacijeDTO;
import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.PrintSaglasnost;
import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost;
import com.example.demo.service.DigitalniSertifikatService;
import com.example.demo.service.PotvrdaVakcinacijeService;
import com.example.demo.service.SaglasnostService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.ReaderInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.xmldb.api.modules.XMLResource;

import javax.ws.rs.GET;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/potvrda")
public class PotvrdaVakcinacijeController {

    @Autowired
    private PotvrdaVakcinacijeService potvrdaVakcinacijeService;

    @Autowired
    private SaglasnostService saglasnostService;
    
    @Autowired
    private DigitalniSertifikatService digitalniSertifikatService;

    @PreAuthorize("hasRole('Z')")
    @GetMapping(path = "/kreirajPotvrdu/{brojSaglasnosti}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> kreirajPotvrdu(@PathVariable("brojSaglasnosti") String brojSaglasnosti) {

        try {
            Saglasnost saglasnost = this.saglasnostService.pronadjiPoId(brojSaglasnosti);
            PrintSaglasnost.printSaglasnost(saglasnost);
            PotvrdaVakcinacijeDTO potvrdaOVakcinaciji = potvrdaVakcinacijeService.kreirajPotvrdu(saglasnost);
            saglasnost.setPotvrda(new Saglasnost.Potvrda());
            saglasnost.getPotvrda().setValue("http://www.ftn.uns.ac.rs/xml_i_veb_servisi/potvrda_o_vakcinaciji/" + potvrdaOVakcinaciji.getBrojSaglasnosti());
            saglasnost.getPotvrda().setProperty("pred:seeAlso");
            this.saglasnostService.saveSaglasnost(saglasnost);
            return new ResponseEntity<>(potvrdaOVakcinaciji, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('Z')")
    @PostMapping(path = "/savePotvrdu", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> savePotvrdu(@RequestBody PotvrdaVakcinacijeDTO content) {
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        String documentId = "";
        if(content.getDrz().equals("srb")){
            documentId = content.getJmbg() +"_"+ ft.format(new Date());
        }else{
            documentId = content.getEbs() +"_"+ ft.format(new Date());
        }

        try {
            potvrdaVakcinacijeService.savePotvrdu(documentId, content);
            return new ResponseEntity<>(documentId,HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('Z')")
    @PostMapping(path = "/saveDoze/{docId}/{email}", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> saveDoze(@PathVariable("docId") String docId, @PathVariable("email") String email,
                                                        @RequestBody String evidentiraneVakcineDTO) {
        try {
            JAXBContext context = JAXBContext.newInstance(ListaEvidentiranihVakcina.class);
            InputStream inputStream = new ReaderInputStream(new StringReader(evidentiraneVakcineDTO));

            Unmarshaller unmarshaller = context.createUnmarshaller();

            ListaEvidentiranihVakcina evidentiraneVakcine = (ListaEvidentiranihVakcina) unmarshaller.unmarshal(inputStream);
            return new ResponseEntity<>(potvrdaVakcinacijeService.saveDoze(docId, evidentiraneVakcine, email),
                    HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/generatePDF/{id}")
    public ResponseEntity<byte[]> generatePDF(@PathVariable("id") String id) {

        String file_path = this.potvrdaVakcinacijeService.generatePDF(id);
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

    @GetMapping(path = "/allXmlByEmail/{userEmail}")
    public ResponseEntity<String> allXmlByEmail(@PathVariable("userEmail") String userEmail){
        try{
            return new ResponseEntity<>(potvrdaVakcinacijeService.allXmlByEmail(userEmail), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/generateHTML/{id}")
    public ResponseEntity<byte[]> generateHTML(@PathVariable("id") String id) {

        try {
            String file_path = this.potvrdaVakcinacijeService.generateHTML(id);
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
            XMLResource xml = potvrdaVakcinacijeService.getXML(id);
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
            List<DokumentDTO> retval = potvrdaVakcinacijeService.getPotvrdaAllByEmail(email);
            if (retval.isEmpty()) {
                return new ResponseEntity<>("Nema izdatih potvrda za prisutnog gradjana.", HttpStatus.OK);
            } else
                return new ResponseEntity<>(retval, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error pri dobavljanju potvrda.", HttpStatus.NOT_FOUND);

        }
    }
    
    @GetMapping(path = "/obicnaPretraga/{searchTerm}",  produces = "application/xml")
    public ResponseEntity<IdentificationDTO> obicnaPretraga(@PathVariable("searchTerm") String searchTerm){
        IdentificationDTO dto = new IdentificationDTO();
        try {
            dto.setIds(potvrdaVakcinacijeService.obicnaPretraga(searchTerm));
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/getAll", produces = "application/xml")
    public ResponseEntity<?> getAllPotvrde() {
        IdentificationDTO dto = new IdentificationDTO();
        try {
            dto.setIds(potvrdaVakcinacijeService.getAllPotvrde());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/generateJson/{documentId}")
    public ResponseEntity<byte[]> generateJson(@PathVariable("documentId") String documentId){
        try {
            return new ResponseEntity<>(potvrdaVakcinacijeService.generateJson(documentId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/generateRdf/{documentId}")
    public ResponseEntity<byte[]> generateRdf(@PathVariable("documentId") String documentId){
        try {
            return new ResponseEntity<>(potvrdaVakcinacijeService.generateRdf(documentId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
	@GET
	@GetMapping(path = "/referenciraniDoc/{id}",  produces = "application/xml")
	public ResponseEntity<IdentificationDTO> getDocumentIdReferences(@PathVariable("id") String id) {

		IdentificationDTO dto = new IdentificationDTO();
		try {
			List<String> ids = digitalniSertifikatService.getSertifikatRefFromSeeAlso("http://www.ftn.uns.ac.rs/xml_i_veb_servisi/zahtev_za_sertifikatom/" + id);
			String id1 = saglasnostService.getSaglasnostRefFromSeeAlso("http://www.ftn.uns.ac.rs/xml_i_veb_servisi/zahtev_za_sertifikatom/" + id);
			ids.add(id1);
			dto.setIds(ids);
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = "/naprednaPretraga", produces = "application/xml")
	public ResponseEntity<IdentificationDTO> naprednaPretraga(@RequestBody PotvrdaNaprednaDTO dto) throws Exception {
		String ime = "\"" + dto.getIme() + "\"";
		String prezime = "\"" + dto.getPrezime() + "\"";
		String id = "\"" + dto.getId() + "\"";
		String datum = "\"" + dto.getDatum() + "\"";

		List<String> lista = this.potvrdaVakcinacijeService.naprednaPretraga(ime, prezime, id, datum, dto.isAnd());

		if (!lista.isEmpty())

			return new ResponseEntity<>(new IdentificationDTO(lista), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
