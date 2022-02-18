package com.example.sluzbenik_back.controller;

import com.example.sluzbenik_back.dto.KorisnikPrijavaDTO;
import com.example.sluzbenik_back.dto.UserTokenStateDTO;
import com.example.sluzbenik_back.exceptions.BadRequestException;
import com.example.sluzbenik_back.model.korisnik.Korisnik;
import com.example.sluzbenik_back.security.TokenUtils;
import com.example.sluzbenik_back.service.KorisnikService;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/korisnik")
public class KorisnikController {

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;


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

    @GetMapping(value = "/listaKorisnika/{searchTerm}", produces = MediaType.APPLICATION_XML_VALUE )
    public ResponseEntity<?> getListaKorisnika(@PathVariable("searchTerm") String searchTerm) {
        if(searchTerm.equals("all") || searchTerm.equals("")){
            searchTerm = null;
        }
        try{
            return new ResponseEntity<>(korisnikService.getKorisnike(searchTerm), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error pri dobavljanju gradjana.", HttpStatus.NOT_FOUND);
        }
    }

}
