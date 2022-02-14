package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.KorisnikPrijavaDTO;
import com.example.demo.dto.KorisnikRegistracijaDTO;
import com.example.demo.dto.UserTokenStateDTO;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.model.korisnik.Korisnik;
import com.example.demo.security.TokenUtils;
import com.example.demo.service.KorisnikService;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.xmldb.api.modules.XMLResource;

import javax.ws.rs.GET;

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
    public ResponseEntity<?> registracija(@RequestBody KorisnikRegistracijaDTO korisnikDTO) {
        try {
            Korisnik korisnik = new Korisnik();
            korisnik.setEmail(korisnikDTO.getEmail());
            korisnik.setIme(korisnikDTO.getIme());
            korisnik.setPrezime(korisnikDTO.getPrezime());
            korisnik.setPol(korisnikDTO.getPol());
            korisnik.setRodjendan(korisnikDTO.getRodjendan());
            korisnik.setUloga("G");
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
            return  new ResponseEntity<>("Uspesno kreiran nalog!", HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Pokusajte ponovo.",HttpStatus.NOT_FOUND);
        }

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

            Korisnik user = (Korisnik) authentication.getPrincipal();
            String email = user.getEmail();
            String jwt = tokenUtils.generateToken(user.getEmail());
            int expiresIn = tokenUtils.getExpiredIn();

            UserTokenStateDTO dto = new UserTokenStateDTO(jwt, expiresIn, email, user.getUloga(), user.getIme(), user.getPrezime(), user.getPol(), user.getRodjendan());
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Uneti kredencijali su losi, pokusajte ponovo.",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/logout", produces = MediaType.APPLICATION_XML_VALUE )
    public ResponseEntity logout() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)){
            SecurityContextHolder.clearContext();

            return new ResponseEntity<>("Uspesan logout!", HttpStatus.OK);
        } else {
            throw new BadRequestException("Korisnik nije autentifikovan!");
        }

    }

    @GET
    @GetMapping
    public ResponseEntity<String> getXML() {
        try {
            XMLResource korisnici = korisnikService.readXML();
            String ret = korisnici.getContent().toString();
            return new ResponseEntity<>(ret, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
