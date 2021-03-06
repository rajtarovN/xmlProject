package com.example.sluzbenik_back.service;

import com.example.sluzbenik_back.model.korisnik.Korisnik;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private KorisnikService korisnikService;

    // Funkcija koja na osnovu username-a iz baze vraca objekat User-a
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // ako se ne radi nasledjivanje, paziti gde sve treba da se proveri email
        Korisnik user = korisnikService.pronadjiPoEmailu(email);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", email));
        } else {
            return user;
        }
    }

}
