package com.example.demo.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PotvrdaVakcinacijeDTO {
    public String brojSaglasnosti;
    public String ime;
    public String prezime;
    public String datumRodjenja;
    public String pol;
    public String zUstanova;
    public String datumIzdavanja;
    public String drz;
    public String jmbg;//opciono
    public String ebs;//opciono

    public PotvrdaVakcinacijeDTO() {
    }

    public PotvrdaVakcinacijeDTO(String brojSaglasnosti, String ime, String prezime, String datumRodjenja, String pol,
                                  String zUstanova, String datumIzdavanja) {
        this.brojSaglasnosti = brojSaglasnosti;
        this.ime = ime;
        this.prezime = prezime;
        this.datumRodjenja = datumRodjenja;
        this.pol = pol;
        this.zUstanova = zUstanova;
        this.datumIzdavanja = datumIzdavanja;
    }

    public String getDrz() {
        return drz;
    }

    public void setDrz(String drz) {
        this.drz = drz;
    }

    public String getBrojSaglasnosti() {
        return brojSaglasnosti;
    }

    public void setBrojSaglasnosti(String brojSaglasnosti) {
        this.brojSaglasnosti = brojSaglasnosti;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(String datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public String getPol() {
        return pol;
    }

    public void setPol(String pol) {
        this.pol = pol;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getEbs() {
        return ebs;
    }

    public void setEbs(String ebs) {
        this.ebs = ebs;
    }

    public String getzUstanova() {
        return zUstanova;
    }

    public void setzUstanova(String zUstanova) {
        this.zUstanova = zUstanova;
    }

    public String getDatumIzdavanja() {
        return datumIzdavanja;
    }

    public void setDatumIzdavanja(String datumIzdavanja) {
        this.datumIzdavanja = datumIzdavanja;
    }
}
