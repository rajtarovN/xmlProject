package com.example.demo.controller;

import com.example.demo.service.InteresovanjeService;
import com.example.demo.service.ZahtevService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
}
