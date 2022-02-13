package com.example.sluzbenik_back.dto;

import com.fasterxml.jackson.annotation.JsonRootName;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "zahtev")
@JsonRootName(value = "zahtev")
public class ZahtevDTO {
    private String brojZahteva;
    private String ime;
    private String prezime;
    private String jmbgEbs;
    private String datumPodnosenja;
    private String status;
    private String razlog;

    public ZahtevDTO() {
    }

    public ZahtevDTO(String brojZahteva, String ime, String prezime, String jmbgEbs, String datumPodnosenja, String status, String razlog) {
        this.brojZahteva = brojZahteva;
        this.ime = ime;
        this.prezime = prezime;
        this.jmbgEbs = jmbgEbs;
        this.datumPodnosenja = datumPodnosenja;
        this.status = status;
        this.razlog = razlog;
    }

    public String getBrojZahteva() {
        return brojZahteva;
    }

    public void setBrojZahteva(String brojZahteva) {
        this.brojZahteva = brojZahteva;
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

    public String getJmbgEbs() {
        return jmbgEbs;
    }

    public void setJmbgEbs(String jmbgEbs) {
        this.jmbgEbs = jmbgEbs;
    }

    public String getDatumPodnosenja() {
        return datumPodnosenja;
    }

    public void setDatumPodnosenja(String datumPodnosenja) {
        this.datumPodnosenja = datumPodnosenja;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRazlog() {
        return razlog;
    }

    public void setRazlog(String razlog) {
        this.razlog = razlog;
    }
}
