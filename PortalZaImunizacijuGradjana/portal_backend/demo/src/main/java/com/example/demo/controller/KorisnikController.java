package com.example.demo.controller;

import com.example.demo.dto.KorisnikDTO;
import com.example.demo.dto.KorisnikPrijavaDTO;
import com.example.demo.dto.KorisnikRegistracijaDTO;
import com.example.demo.dto.UserTokenStateDTO;
import com.example.demo.model.korisnik.Korisnik;
import com.example.demo.security.TokenUtils;
import com.example.demo.service.KorisnikService;
import com.example.demo.util.ExistManager;
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

import java.util.UUID;

@RestController
@RequestMapping(value = "/korisnik")
public class KorisnikController {

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private ExistManager existManager;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(path = "/registracija", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> registracija(@RequestBody KorisnikRegistracijaDTO korisnikDTO) {
        try {
            //existManager.saveToDb("korisnik", "lista_korisnika");
            Korisnik korisnik = new Korisnik();

            korisnik.setEmail(korisnikDTO.getEmail());
            korisnik.setImeIPrezime(korisnikDTO.getIme_i_prezime());
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
            /*String documentId = UUID.randomUUID().toString();
            korisnikService.saveXML(documentId, userXML);*/
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
            UserTokenStateDTO dto = new UserTokenStateDTO(jwt, expiresIn, email, user.getUloga(), user.getImeIPrezime());
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Uneti kredencijali su losi, pokusajte ponovo.",HttpStatus.NOT_FOUND);
        }
    }
}
