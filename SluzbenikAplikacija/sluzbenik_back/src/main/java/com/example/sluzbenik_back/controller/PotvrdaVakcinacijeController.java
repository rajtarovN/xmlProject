package com.example.sluzbenik_back.controller;

import com.example.sluzbenik_back.dto.DokumentDTO;
import com.example.sluzbenik_back.dto.PotvrdaNaprednaDTO;
import com.example.sluzbenik_back.service.PotvrdaVakcinacijeService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xmldb.api.base.XMLDBException;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@RestController
@RequestMapping(value = "/potvrda")
public class PotvrdaVakcinacijeController {

    @Autowired
    private PotvrdaVakcinacijeService potvrdaVakcinacijeService;

    @PreAuthorize("hasRole('S')")
    @GetMapping(path = "/getAllXmlByEmail/{email}", produces = "application/xml")
    public ResponseEntity<?> getAllXmlByEmail(@PathVariable("email") String email){
        try{
            List<DokumentDTO> retval = potvrdaVakcinacijeService.getAllXmlIdsByEmail(email);
            if(retval.isEmpty()){
                return new ResponseEntity<>("Nema izdatih potvrda za prisutnog gradjana.", HttpStatus.OK);
            }else
                return new ResponseEntity<>(retval, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error pri dobavljanju potvrda.", HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('S')")
    @GetMapping(path = "/generatePDF/{documentId}")
    public ResponseEntity<byte[]> generatePDF(@PathVariable("documentId") String documentId) {

        String file_path = this.potvrdaVakcinacijeService.generatePDF(documentId);

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
            return new ResponseEntity<>(this.potvrdaVakcinacijeService.obicnaPretraga(searchTerm), HttpStatus.OK);
        } catch (Exception e) {
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

    @GetMapping(value = "/getAll", produces="text/xml")
    public ResponseEntity<?> getAllPotvrde() {

        try {
            System.out.println(potvrdaVakcinacijeService.getAllPotvrde());
            return new ResponseEntity<>(potvrdaVakcinacijeService.getAllPotvrde(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('S')")
    @GetMapping("/generisiJSON/{idPotvrde}")
    public ResponseEntity<byte[]> generisiJSON(@PathVariable("idPotvrde") String idPotvrde) throws XMLDBException {
        try {
            return new ResponseEntity<byte[]>(this.potvrdaVakcinacijeService.generisiJSON(idPotvrde), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PreAuthorize("hasRole('S')")
    @GetMapping("/generisiRDF/{idPotvrde}")
    public ResponseEntity<byte[]> generisiRDF(@PathVariable("idPotvrde") String idPotvrde) throws XMLDBException {
        try {
            return new ResponseEntity<byte[]>(this.potvrdaVakcinacijeService.generisiRDF(idPotvrde), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
    
    @PostMapping(value = "/naprednaPretraga", consumes="application/xml", produces = "application/xml")
	public ResponseEntity<?> naprednaPretraga(@RequestBody PotvrdaNaprednaDTO dto){

		try {
			return new ResponseEntity<>(this.potvrdaVakcinacijeService.naprednaPretraga(dto), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
    
    @GetMapping(value = "/referenciraniDoc/{id}", produces = "text/xml")
	public ResponseEntity<?> getAllReferences(@PathVariable("id") String id) {

		try {	
			String refs = this.potvrdaVakcinacijeService.getAllIdReferences(id);
			if(refs != null)	return new ResponseEntity<>(refs, HttpStatus.OK);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
