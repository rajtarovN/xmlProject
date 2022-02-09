package com.example.demo.dto;

import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EvidentiraneVakcineDTO {
    public String nazivVakcine;
    public String datumDavanja;
    public String nacinDavanja;
    public String ekstremitet;
    public String serijaVakcine;
    public String proizvodjac;
    public String nezeljenaReakcija;

    public EvidentiraneVakcineDTO() {
    }

    public EvidentiraneVakcineDTO(Saglasnost.EvidencijaOVakcinaciji.Vakcine.Vakcina vakcina) {
        this.nazivVakcine = vakcina.getNaziv();
        this.datumDavanja = vakcina.getDatumDavanja().toString();
        this.nacinDavanja = vakcina.getNacinDavanja();
        this.ekstremitet = vakcina.getEkstremiter();
        this.serijaVakcine = vakcina.getSerija();
        this.proizvodjac = vakcina.getProizvodjac();
        this.nezeljenaReakcija = vakcina.getNezeljenaReakcija();

    }

    public String getNazivVakcine() {
        return nazivVakcine;
    }

    public void setNazivVakcine(String nazivVakcine) {
        this.nazivVakcine = nazivVakcine;
    }

    public String getDatumDavanja() {
        return datumDavanja;
    }

    public void setDatumDavanja(String datumDavanja) {
        this.datumDavanja = datumDavanja;
    }

    public String getNacinDavanja() {
        return nacinDavanja;
    }

    public void setNacinDavanja(String nacinDavanja) {
        this.nacinDavanja = nacinDavanja;
    }

    public String getEkstremitet() {
        return ekstremitet;
    }

    public void setEkstremitet(String ekstremitet) {
        this.ekstremitet = ekstremitet;
    }

    public String getSerijaVakcine() {
        return serijaVakcine;
    }

    public void setSerijaVakcine(String serijaVakcine) {
        this.serijaVakcine = serijaVakcine;
    }

    public String getProizvodjac() {
        return proizvodjac;
    }

    public void setProizvodjac(String proizvodjac) {
        this.proizvodjac = proizvodjac;
    }

    public String getNezeljenaReakcija() {
        return nezeljenaReakcija;
    }

    public void setNezeljenaReakcija(String nezeljenaReakcija) {
        this.nezeljenaReakcija = nezeljenaReakcija;
    }
}
