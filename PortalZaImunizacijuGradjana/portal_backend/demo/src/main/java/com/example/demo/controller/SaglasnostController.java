package com.example.demo.controller;

import com.example.demo.dto.EvidencijaVakcinacijeDTO;
import com.example.demo.dto.EvidentiraneVakcineDTO;
import com.example.demo.dto.KorisnikRegistracijaDTO;
import com.example.demo.dto.SaglasnostDTO;
import com.example.demo.model.korisnik.Korisnik;
import com.example.demo.service.SaglasnostService;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping(value = "/saglasnost")
public class SaglasnostController {

    @Autowired
    private SaglasnostService saglasnostService;

    @PreAuthorize("hasRole('Z')")
    @GetMapping(path = "/pretragaTermina/{imePrezime}/{datumTermina}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> pretragaTermina(@PathVariable("imePrezime") String imePrezime,
                                                             @PathVariable("datumTermina") Date datumTermina) {
        ArrayList<SaglasnostDTO> lista = new ArrayList<>();
        if(imePrezime.equals("all")){
            imePrezime = null;
        }

        try {
            lista = this.saglasnostService.pretragaTermina(imePrezime, datumTermina);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(!lista.isEmpty())
            return new ResponseEntity<>(lista, HttpStatus.OK);
        else
            return new ResponseEntity<>("Nema termina po trazenim parametrima.", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('Z')")
    @GetMapping(path = "/pronadjiPoEmailu/{email}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> pronadjiPoEmailu(@PathVariable("email") String email) {
        ArrayList<EvidentiraneVakcineDTO> lista = new ArrayList<>();

        try {
            lista = this.saglasnostService.pronadjiPoEmailu(email);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(!lista.isEmpty())
            return new ResponseEntity<>(lista, HttpStatus.OK);
        else
            return new ResponseEntity<>("Nema prethodno evidentiranih vakcina.", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('Z')")
    @PostMapping(path = "/sacuvajEvidenciju/{brojSaglasnosti}", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> sacuvajEvidenciju(@PathVariable("brojSaglasnosti") String brojSaglasnosti,
                                              @RequestBody EvidencijaVakcinacijeDTO evidencijaVakcinacijeDTO) {
        try {
            return new ResponseEntity<>(this.saglasnostService.dodajEvidenciju(evidencijaVakcinacijeDTO, brojSaglasnosti),
                    HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('Z')")
    @PostMapping(path = "/sacuvajEvidentiraneVakcine/{brojSaglasnosti}", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> sacuvajEvidentiraneVakcine(@PathVariable("brojSaglasnosti") String brojSaglasnosti,
                                               @RequestBody EvidentiraneVakcineDTO evidentiraneVakcineDTO) {
        try {
            return new ResponseEntity<>(this.saglasnostService.dodajEvidentiraneVakcine(evidentiraneVakcineDTO, brojSaglasnosti),
                    HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}