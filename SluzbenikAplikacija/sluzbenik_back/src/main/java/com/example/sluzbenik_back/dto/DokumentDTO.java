package com.example.sluzbenik_back.dto;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.example.sluzbenik_back.model.digitalni_zeleni_sertifikat.DigitalniZeleniSertifikat;
import com.example.sluzbenik_back.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost;
import com.example.sluzbenik_back.model.potvrda_o_vakcinaciji.PotvrdaOVakcinaciji;

@XmlRootElement
public class DokumentDTO {
	public String id;
	public String datumKreiranja;

	public DokumentDTO() {
	}

	public DokumentDTO(Saglasnost saglasnost) {
		this.id = saglasnost.getBrojSaglasnosti();
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
		Date date = saglasnost.getPacijent().getDatum().getValue().toGregorianCalendar().getTime();
		this.datumKreiranja = ft.format(date);
	}

	public DokumentDTO(PotvrdaOVakcinaciji potvrdaOVakcinaciji) {
		String idZahteva = potvrdaOVakcinaciji.getAbout().substring(potvrdaOVakcinaciji.getAbout().lastIndexOf('/') + 1);
		this.id = idZahteva;
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
		Date date = potvrdaOVakcinaciji.getDatumIzdavanja().getValue().toGregorianCalendar().getTime();
		this.datumKreiranja = ft.format(date);
	}

	public DokumentDTO(DigitalniZeleniSertifikat s) {
		this.id = s.getPodaciOSertifikatu().getBrojSertifikata().getValue();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime date = s.getPodaciOSertifikatu().getDatumIVremeIzdavanja().getValue().toGregorianCalendar()
				.toZonedDateTime().toLocalDateTime();
		this.datumKreiranja = date.format(formatter);
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
