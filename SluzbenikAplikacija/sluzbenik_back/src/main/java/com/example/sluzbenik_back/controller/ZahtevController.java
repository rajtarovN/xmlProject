package com.example.sluzbenik_back.controller;

import com.example.sluzbenik_back.service.ZahtevService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/zahtev")
public class ZahtevController {

    @Autowired
    private ZahtevService zahtevService;

    @GetMapping(value = "/listaZahteva/{searchTerm}", produces = MediaType.APPLICATION_XML_VALUE )
    public ResponseEntity<?> getListaZahteva(@PathVariable("searchTerm") String searchTerm) {
        if(searchTerm.equals("all")){
            searchTerm = null;
        }
        try{
            return new ResponseEntity<>(zahtevService.getZahteve(searchTerm), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error pri dobavljanju zahteva.", HttpStatus.NOT_FOUND);
        }
    }
}
