package com.example.demo.controller;

import com.example.demo.dto.KorisnikDTO;
import com.example.demo.dto.KorisnikPrijavaDTO;
import com.example.demo.dto.KorisnikRegistracijaDTO;
import com.example.demo.dto.UserTokenStateDTO;
import com.example.demo.model.korisnik.Korisnik;
import com.example.demo.security.TokenUtils;
import com.example.demo.service.KorisnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@RestController
@RequestMapping(value = "/korisnik")
public class KorisnikController {

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(path = "/registracija", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<HttpStatus> registracija(@RequestBody KorisnikRegistracijaDTO korisnikDTO) {
        KorisnikDTO korisnik = new KorisnikDTO();
        korisnik.setEmail(korisnikDTO.getEmail());
        korisnik.setIme_i_prezime(korisnikDTO.getIme_i_prezime());
        korisnik.setUloga("K");

        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        korisnik.setLozinka(bc.encode(korisnikDTO.getLozinka()));

        String userXML = null;

        try {

            XmlMapper mapper = new XmlMapper();
            userXML = mapper.writeValueAsString(korisnik);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        boolean ok = this.korisnikService.registruj(korisnik.getEmail(), userXML);

        if (ok)
            return new ResponseEntity<>(HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

    }


    @PostMapping(path = "/prijava", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> prijava(@RequestBody KorisnikPrijavaDTO authRequest) throws Exception{
        try {
            final Authentication authentication;
            try {
                authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(), authRequest.getLozinka()));
            } catch (BadCredentialsException e) {
                return  new ResponseEntity<>("Uneti kredencijali su losi, pokusajte ponovo.", HttpStatus.BAD_REQUEST);
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
            /*Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getEmail(), authRequest.getLozinka()));

            // Ubaci korisnika u trenutni security kontekst
            SecurityContextHolder.getContext().setAuthentication(authentication);*/


            // Kreiraj token za tog korisnika
            Korisnik user = (Korisnik) authentication.getPrincipal();

            String email = user.getEmail();
            String jwt = tokenUtils.generateToken(user.getEmail()); // prijavljujemo se na sistem sa email adresom
            int expiresIn = tokenUtils.getExpiredIn();

            // Vrati token kao odgovor na uspesnu autentifikaciju
            return ResponseEntity.ok(new UserTokenStateDTO(jwt, expiresIn, email, user.getUloga(), user.getImeIPrezime()));
        } catch (Exception e) {
            return new ResponseEntity<>("Uneti kredencijali su losi, pokusajte ponovo.",HttpStatus.NOT_FOUND);
        }
    }
}
