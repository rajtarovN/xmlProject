package com.example.sluzbenik_back.controller;

import com.example.sluzbenik_back.service.ZahtevService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/zahtev")
public class ZahtevController {

    @Autowired
    private ZahtevService zahtevService;

    @PreAuthorize("hasRole('S')")
    @GetMapping(value = "/listaZahteva/{searchTerm}", produces = MediaType.APPLICATION_XML_VALUE )
    public ResponseEntity<?> getListaZahteva(@PathVariable("searchTerm") String searchTerm) {
        if(searchTerm.equals("all") || searchTerm.equals("")){
            searchTerm = null;
        }
        try{
            return new ResponseEntity<>(zahtevService.getZahteve(searchTerm), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error pri dobavljanju zahteva.", HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('S')")
    @GetMapping(value = "/odbijZahtev/{idZahteva}/{content}", produces = MediaType.APPLICATION_XML_VALUE )
    public ResponseEntity<?> odbijZahtev(@PathVariable("idZahteva") String idZahteva,
                                         @PathVariable("content") String content) {
        try{
            return new ResponseEntity<>(zahtevService.odbijZahtev(idZahteva, content), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error pri odbijanju zahteva.", HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('S')")
    @GetMapping(value = "/odobriZahtev/{idZahteva}", produces = MediaType.APPLICATION_XML_VALUE )
    public ResponseEntity<?> odobriZahtev(@PathVariable("idZahteva") String idZahteva) {
        try{
            return new ResponseEntity<>(zahtevService.odobriZahtev(idZahteva), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error pri odobravanju zahteva.", HttpStatus.NOT_FOUND);
        }
    }
}
