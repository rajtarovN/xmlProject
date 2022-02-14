package com.example.sluzbenik_back.controller;

import com.example.sluzbenik_back.dto.DokumentDTO;
import com.example.sluzbenik_back.service.SaglasnostService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@Controller
@RequestMapping(value = "/saglasnost")
public class SaglasnostController {

    @Autowired
    private SaglasnostService saglasnostService;

    @PreAuthorize("hasRole('S')")
    @GetMapping(path = "/getAllXmlByEmail/{email}", produces = "application/xml")
    public ResponseEntity<?> getAllXmlByEmail(@PathVariable("email") String email){
        try{
            List<DokumentDTO> retval = saglasnostService.getAllXmlIdsByEmail(email);
            if(retval.isEmpty()){
                return new ResponseEntity<>("Nema izdatih saglasnosti za prisutnog gradjana.", HttpStatus.OK);
            }else
                return new ResponseEntity<>(retval, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error pri dobavljanju saglasnosti.", HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('S')")
    @GetMapping(path = "/generatePDF/{documentId}")
    public ResponseEntity<byte[]> generatePDF(@PathVariable("documentId") String documentId) {

        String file_path = this.saglasnostService.generatePDF(documentId);

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