package com.example.sluzbenik_back.dto;

import com.example.sluzbenik_back.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost;
import com.example.sluzbenik_back.model.potvrda_o_vakcinaciji.PotvrdaOVakcinaciji;

import javax.xml.bind.annotation.XmlRootElement;
import java.text.SimpleDateFormat;
import java.util.Date;

@XmlRootElement
public class DokumentDTO {
    public String id;
    public String datumKreiranja;

    public DokumentDTO() {
    }

    public DokumentDTO(Saglasnost saglasnost){
        this.id = saglasnost.getBrojSaglasnosti();
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        Date date = saglasnost.getPacijent().getDatum().getValue().toGregorianCalendar().getTime();
        this.datumKreiranja = ft.format(date);
    }

    public DokumentDTO(PotvrdaOVakcinaciji potvrdaOVakcinaciji){
        String num = potvrdaOVakcinaciji.getAbout().split("/")[-1];
        this.id = num;
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        Date date = potvrdaOVakcinaciji.getDatumIzdavanja().getValue().toGregorianCalendar().getTime();
        this.datumKreiranja = ft.format(date);
    }

    public DokumentDTO(String id, String datumKreiranja) {
        this.id = id;
        this.datumKreiranja = datumKreiranja;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDatum_termina() {
        return datumKreiranja;
    }

    public void setDatumKreiranja(String datum_termina) {
        this.datumKreiranja = datum_termina;
    }

}
