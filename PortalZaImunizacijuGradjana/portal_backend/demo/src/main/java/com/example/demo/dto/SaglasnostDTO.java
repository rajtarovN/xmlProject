package com.example.demo.dto;

import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost;

import javax.xml.bind.annotation.XmlRootElement;
import java.text.SimpleDateFormat;

@XmlRootElement
public class SaglasnostDTO {
    public String brojSaglasnosti;
    public String ime;
    public String prezime;
    public String datum_termina;
    public String email;

    public SaglasnostDTO() {
    }

    public SaglasnostDTO(Saglasnost saglasnost){
        this.brojSaglasnosti = saglasnost.getBrojSaglasnosti();
        this.ime = saglasnost.getPacijent().getLicniPodaci().getIme().getValue();
        this.prezime = saglasnost.getPacijent().getLicniPodaci().getPrezime().getValue();
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        this.datum_termina = saglasnost.getPacijent().getDatum().getValue().toString();
        this.email = saglasnost.getPacijent().getLicniPodaci().getKontaktInformacije().getEmail().getValue();
    }

    public SaglasnostDTO(String id, String ime, String prezime, String datum_termina, String email) {
        this.brojSaglasnosti = id;
        this.ime = ime;
        this.prezime = prezime;
        this.datum_termina = datum_termina;
        this.email = email;
    }

    public String getId() {
        return brojSaglasnosti;
    }

    public void setId(String id) {
        this.brojSaglasnosti = id;
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

    public String getDatum_termina() {
        return datum_termina;
    }

    public void setDatum_termina(String datum_termina) {
        this.datum_termina = datum_termina;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
