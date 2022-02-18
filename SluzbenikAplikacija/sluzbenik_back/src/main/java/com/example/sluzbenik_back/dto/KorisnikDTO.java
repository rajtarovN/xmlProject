package com.example.sluzbenik_back.dto;

import com.fasterxml.jackson.annotation.JsonRootName;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "korisnik")
@JsonRootName(value = "korisnik")
public class KorisnikDTO {
    private String email;
    private String ime;
    private String prezime;

    public KorisnikDTO() {
        super();
    }

    public KorisnikDTO(String email, String ime, String prezime) {
        super();
        this.email = email;
        this.ime = ime;
        this.prezime = prezime;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

}
