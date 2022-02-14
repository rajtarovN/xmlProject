package com.example.sluzbenik_back.controller;

import com.example.sluzbenik_back.dto.DokumentDTO;
import com.example.sluzbenik_back.model.potvrda_o_vakcinaciji.PotvrdaOVakcinaciji;
import com.example.sluzbenik_back.service.PotvrdaVakcinacijeService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
