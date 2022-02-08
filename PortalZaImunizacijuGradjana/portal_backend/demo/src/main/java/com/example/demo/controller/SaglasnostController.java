package com.example.demo.controller;

import com.example.demo.dto.SaglasnostDTO;
import com.example.demo.service.SaglasnostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping(value = "/saglasnost")
public class SaglasnostController {

    @Autowired
    private SaglasnostService saglasnostService;

    @PreAuthorize("hasRole('Z')")
    @GetMapping(path = "/pretragaTermina/{imePrezime}/{datumTermina}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<SaglasnostDTO>> pretragaTermina(@PathVariable("imePrezime") String imePrezime,
                                                             @PathVariable("datumTermina") Date datumTermina) {
        ArrayList<SaglasnostDTO> lista = new ArrayList<>();

        try {
            lista = this.saglasnostService.pretragaTermina(imePrezime, datumTermina);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(!lista.isEmpty())
            return new ResponseEntity<>(lista, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
