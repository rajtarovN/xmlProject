package com.example.sluzbenik_back.controller;


import com.example.sluzbenik_back.dto.IzvestajDTO;
import com.example.sluzbenik_back.service.IzvestajService;
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
@RequestMapping(value = "/izvestaj")
public class IzvestajController {

    @Autowired
    private IzvestajService izvestajService;

    //@PreAuthorize("hasRole('S')")
    @GetMapping(path = "/getIzvestaji/{fromDate}/{toDate}", produces = "application/xml")
    public ResponseEntity<?> getIzvestaji(@PathVariable("fromDate") String fromDate, @PathVariable("toDate") String toDate){
        try{
            IzvestajDTO izvestaj = izvestajService.createIzvestaj(fromDate, toDate);
            return new ResponseEntity<>(izvestaj, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error pri dobavljanju izvestaja.", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/generateHTML/{fromDate}/{toDate}")
    public ResponseEntity<byte[]> generateHTML(@PathVariable("fromDate") String fromDate, @PathVariable("toDate") String toDate) {
        try {
            String file_path = this.izvestajService.generateHTML(fromDate, toDate);
            File file = new File(file_path);
            FileInputStream fileInputStream = new FileInputStream(file);
            return new ResponseEntity<byte[]>(IOUtils.toByteArray(fileInputStream), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/generatePDF/{fromDate}/{toDate}")
    public ResponseEntity<byte[]> generatePDF(@PathVariable("fromDate") String fromDate, @PathVariable("toDate") String toDate) {

        String file_path = this.izvestajService.generatePDF(fromDate, toDate);

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
