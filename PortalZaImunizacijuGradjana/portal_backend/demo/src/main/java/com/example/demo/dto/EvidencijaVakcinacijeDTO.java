package com.example.demo.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "evidencijaVakcinacijeDTO")
@XmlAccessorType(XmlAccessType.FIELD)
public class EvidencijaVakcinacijeDTO {

    @XmlElement(name = "zdravstvenaUstanova")
    public String zdravstvenaUstanova;

    @XmlElement(name = "vakcinacijskiPunkkt")
    public String vakcinacijskiPunkkt;

    @XmlElement(name = "imeLekara")
    public String imeLekara;

    @XmlElement(name = "prezimeLekara")
    public String prezimeLekara;

    @XmlElement(name = "telefonLekara")
    public String telefonLekara;

    @XmlElement(name = "odlukaKomisije")
    public String odlukaKomisije;

    @XmlElement(name = "datumUtvrdjivanja")
    public String datumUtvrdjivanja;

    @XmlElement(name = "dijagnoza")
    public String dijagnoza;


    public String getOdlukaKomisije() {
        return odlukaKomisije;
    }

    public void setOdlukaKomisije(String odlukaKomisije) {
        this.odlukaKomisije = odlukaKomisije;
    }

    public String getDatumUtvrdjivanja() {
        return datumUtvrdjivanja;
    }

    public void setDatumUtvrdjivanja(String datumUtvrdjivanja) {
        this.datumUtvrdjivanja = datumUtvrdjivanja;
    }

    public String getDijagnoza() {
        return dijagnoza;
    }

    public void setDijagnoza(String dijagnoza) {
        this.dijagnoza = dijagnoza;
    }

    public EvidencijaVakcinacijeDTO() {
    }

    public String getZdravstvenaUstanova() {
        return zdravstvenaUstanova;
    }

    public void setZdravstvenaUstanova(String zdravstvenaUstanova) {
        this.zdravstvenaUstanova = zdravstvenaUstanova;
    }

    public String getVakcinacijskiPunkkt() {
        return vakcinacijskiPunkkt;
    }

    public void setVakcinacijskiPunkkt(String vakcinacijskiPunkkt) {
        this.vakcinacijskiPunkkt = vakcinacijskiPunkkt;
    }

    public String getImeLekara() {
        return imeLekara;
    }

    public void setImeLekara(String imeLekara) {
        this.imeLekara = imeLekara;
    }

    public String getPrezimeLekara() {
        return prezimeLekara;
    }

    public void setPrezimeLekara(String prezimeLekara) {
        this.prezimeLekara = prezimeLekara;
    }

    public String getTelefonLekara() {
        return telefonLekara;
    }

    public void setTelefonLekara(String telefonLekara) {
        this.telefonLekara = telefonLekara;
    }
}
