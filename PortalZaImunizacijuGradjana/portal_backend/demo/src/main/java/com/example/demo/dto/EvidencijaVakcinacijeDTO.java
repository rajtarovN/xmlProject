package com.example.demo.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class EvidencijaVakcinacijeDTO {
    public String zdravstvenaUstanova;
    public String vakcinacijskiPunkkt;
    public String imeLekara;
    public String prezimeLekara;
    public String telefonLekara;
    public List<EvidentiraneVakcineDTO> vakcineDTOS;
    public String odlukaKomisije;
    public String datumUtvrdjivanja;
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

    public List<EvidentiraneVakcineDTO> getVakcineDTOS() {
        return vakcineDTOS;
    }

    public void setVakcineDTOS(List<EvidentiraneVakcineDTO> vakcineDTOS) {
        this.vakcineDTOS = vakcineDTOS;
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
