package com.example.demo.controller;

import com.example.demo.dto.ListaEvidentiranihVakcina;
import com.example.demo.dto.PotvrdaVakcinacijeDTO;
import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost;
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
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import javax.xml.datatype.DatatypeConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;

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
            Saglasnost saglasnost = this.saglasnostService.pronadjiPoId(brojSaglasnosti);
            PotvrdaVakcinacijeDTO potvrdaOVakcinaciji = potvrdaVakcinacijeService.kreirajPotvrdu(saglasnost);
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
        String documentId = "";//TODO
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

<<<<<<< HEAD
    @GetMapping("/generatePDF/{id}")
    public ResponseEntity<byte[]> generatePDF(@PathVariable("id") String id) {

        String file_path = this.potvrdaVakcinacijeService.generatePDF(id);
        System.out.println(file_path+" ovde je");
        try {
            File file = new File(file_path);
            FileInputStream fileInputStream = new FileInputStream(file);
            return new ResponseEntity<byte[]>(IOUtils.toByteArray(fileInputStream), HttpStatus.OK);

=======
    @GetMapping(path = "/allXmlByEmail/{userEmail}")
    public ResponseEntity<String> allXmlByEmail(@PathVariable("userEmail") String userEmail){
        try{
            return new ResponseEntity<>(potvrdaVakcinacijeService.allXmlByEmail(userEmail), HttpStatus.OK);
>>>>>>> develop
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
<<<<<<< HEAD

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

=======
    }

    @GetMapping(path = "/xml/{id}", produces = "application/xml")
    public ResponseEntity<String> getXML(@PathVariable("id") String id) {

        try {
            XMLResource xml = potvrdaVakcinacijeService.getXML(id);
            return new ResponseEntity<>(xml.getContent().toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
>>>>>>> develop
    }
}
