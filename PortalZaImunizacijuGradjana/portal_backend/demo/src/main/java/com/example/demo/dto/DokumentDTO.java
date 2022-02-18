package com.example.demo.dto;

import com.example.demo.model.digitalni_zeleni_sertifikat.DigitalniZeleniSertifikat;
import com.example.demo.model.interesovanje.Interesovanje;
import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost;
import com.example.demo.model.potvrda_o_vakcinaciji.PotvrdaOVakcinaciji;
import org.xmldb.api.modules.XMLResource;

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
        String id = potvrdaOVakcinaciji.getAbout().substring(potvrdaOVakcinaciji.getAbout().lastIndexOf('/') + 1);
        this.id = id;
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        Date date = potvrdaOVakcinaciji.getDatumIzdavanja().getValue().toGregorianCalendar().getTime();
        this.datumKreiranja = ft.format(date);
    }

    public DokumentDTO(DigitalniZeleniSertifikat s){
        this.id = s.getPodaciOSertifikatu().getBrojSertifikata().getValue();
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        Date date = s.getPodaciOSertifikatu().getDatumIVremeIzdavanja().getValue().toGregorianCalendar().getTime();
        this.datumKreiranja = ft.format(date);
    }



    public DokumentDTO(String id, String datumKreiranja) {
        this.id = id;
        this.datumKreiranja = datumKreiranja;

    }

    public DokumentDTO(String id, Interesovanje s) {
        this.id = "interesovanje_"+id+".xml";
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        Date date = s.getDatumPodnosenjaInteresovanja().getValue().toGregorianCalendar().getTime();
        this.datumKreiranja = ft.format(date);
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
