package com.example.demo.dto;

import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "evidentirana_vakcina")
@XmlAccessorType(XmlAccessType.FIELD)
public class EvidentiraneVakcineDTO implements Serializable {
    private final static long serialVersionUID = 1L;

    @XmlElement(name= "nazivVakcine", required = true)
    public String nazivVakcine;

    @XmlElement(name= "datumDavanja", required = true)
    public String datumDavanja;

    @XmlElement(name= "nacinDavanja", required = true)
    public String nacinDavanja;

    @XmlElement(name= "ekstremitet", required = true)
    public String ekstremitet;

    @XmlElement(name= "serijaVakcine", required = true)
    public String serijaVakcine;

    @XmlElement(name= "proizvodjac", required = true)
    public String proizvodjac;

    @XmlElement(name= "nezeljenaReakcija")
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
