package com.example.demo.controller;

import com.example.demo.model.zahtev_za_sertifikatom.ListaZahteva;
import com.example.demo.service.InteresovanjeService;
import com.example.demo.service.ZahtevService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.xmldb.api.modules.XMLResource;

import javax.ws.rs.GET;
import java.io.File;
import java.io.FileInputStream;

@Controller
@RequestMapping(value = "/zahtev")
public class ZahtevController {

    private ZahtevService zahtevService;

    private static final Logger LOG = LoggerFactory.getLogger(ZahtevController.class);


    @Autowired
    public ZahtevController(ZahtevService zahtevService) {
        this.zahtevService = zahtevService;
    }

    @PostMapping(path = "/{jmbg}/{datum}", consumes = "application/xml")
    public ResponseEntity<?> saveXML(@PathVariable String jmbg, @PathVariable String datum,
                                     @RequestBody String content) {
        String documentId = jmbg + '_' + datum;

        try {
            zahtevService.saveXML(documentId, content);
            zahtevService.saveRDF(content, documentId);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/generatePDF/{id}")
    public ResponseEntity<byte[]> generatePDF(@PathVariable("id") String id) {

        String file_path = this.zahtevService.generatePDF(id);
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
            String file_path = this.zahtevService.generateHTML(id);
            File file = new File(file_path);
            FileInputStream fileInputStream = new FileInputStream(file);
            return new ResponseEntity<byte[]>(IOUtils.toByteArray(fileInputStream), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
    @GET
    @GetMapping(path ="/findByStatus")
    public ResponseEntity<String> findByStatus(){
        try {
            String ret = zahtevService.getListuZahtevaPoStatusu("na cekanju");
            return new ResponseEntity<>(ret, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GET
    @GetMapping(path ="/findByStatusAndPeriod/{fromDate}/{toDate}")
    public ResponseEntity<String> findByStatusAndPeriod(@PathVariable("fromDate") String fromDate, @PathVariable("toDate") String toDate){
        try {
            String ret = zahtevService.getListuZahtevaPoStatusuIPeriodu("na cekanju", fromDate, toDate);
            return new ResponseEntity<>(ret, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/odbijZahtev/{idZahteva}/{content}")
    public ResponseEntity<?> odbijZahtev(@PathVariable("idZahteva") String idZahteva,
                                         @PathVariable("content") String content) {
        try{
            return new ResponseEntity<>(zahtevService.odbijZahtev(idZahteva, content), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/odobriZahtev/{idZahteva}")
    public ResponseEntity<?> odobriZahtev(@PathVariable("idZahteva") String idZahteva) {
        try{
            zahtevService.odobriZahtev(idZahteva);
            return new ResponseEntity<>( HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GET
    @GetMapping(path ="/getZahtev/{jmbg}")
    public ResponseEntity<String> getZahtev(@PathVariable("jmbg") String jmbg){
        try {
            String ret = zahtevService.getListuZahtevaPoStatusuJMBG(jmbg);
            return new ResponseEntity<>(ret, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/generateJson/{documentId}")
    public ResponseEntity<byte[]> generateJson(@PathVariable("documentId") String documentId){
        try {
            return new ResponseEntity<>(zahtevService.generateJson(documentId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/generateRdf/{documentId}")
    public ResponseEntity<byte[]> generateRdf(@PathVariable("documentId") String documentId){
        try {
            return new ResponseEntity<>(zahtevService.generateRdf(documentId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
