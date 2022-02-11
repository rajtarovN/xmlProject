package com.example.demo.controller;

import com.example.demo.dto.ListaEvidentiranihVakcina;
import com.example.demo.dto.PotvrdaVakcinacijeDTO;
import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost;
import com.example.demo.service.PotvrdaVakcinacijeService;
import com.example.demo.service.SaglasnostService;
import org.apache.commons.io.input.ReaderInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.io.StringReader;

@RestController
@RequestMapping(value = "/potvrda")
public class PotvrdaVakcinacijeController {

    @Autowired
    private PotvrdaVakcinacijeService potvrdaVakcinacijeService;

    @Autowired
    private SaglasnostService saglasnostService;

    @PreAuthorize("hasRole('Z')")
    @GetMapping(path = "/kreirajPotvrdu/{brojSaglasnosti}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> kreirajPotvrdu(@PathVariable("brojSaglasnosti") String brojSaglasnosti) {

        try {
            Saglasnost saglasnost = this.saglasnostService.pronadjiPoId(Long.parseLong(brojSaglasnosti));
            PotvrdaVakcinacijeDTO potvrdaOVakcinaciji = potvrdaVakcinacijeService.kreirajPotvrdu(saglasnost);
            return new ResponseEntity<>(potvrdaOVakcinaciji, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('Z')")
    @PostMapping(path = "/savePotvrdu", consumes = "application/xml")
    public ResponseEntity<?> savePotvrdu(@RequestBody PotvrdaVakcinacijeDTO content) {
        String documentId = "";//TODO
        if(content.getDrz().equals("srb")){
            documentId = content.getJmbg() +"_"+ content.getDatumIzdavanja();
        }else{
            documentId = content.getEbs() +"_"+ content.getDatumIzdavanja();
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
    @PostMapping(path = "/saveDoze/{docId}", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> saveDoze(@PathVariable("docId") String docId,
                                                        @RequestBody String evidentiraneVakcineDTO) {
        try {
            JAXBContext context = JAXBContext.newInstance(ListaEvidentiranihVakcina.class);
            InputStream inputStream = new ReaderInputStream(new StringReader(evidentiraneVakcineDTO));

            Unmarshaller unmarshaller = context.createUnmarshaller();

            ListaEvidentiranihVakcina evidentiraneVakcine = (ListaEvidentiranihVakcina) unmarshaller.unmarshal(inputStream);
            return new ResponseEntity<>(potvrdaVakcinacijeService.saveDoze(docId, evidentiraneVakcine),
                    HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
