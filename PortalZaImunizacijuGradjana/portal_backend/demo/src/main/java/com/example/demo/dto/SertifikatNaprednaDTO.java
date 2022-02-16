package com.example.demo.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SertifikatNaprednaDTO {

	private String broj_pasosa;
	private String broj_sertifikata;
	private String datum_izdavaja;
	private String ime;
	private String jmbg;
	private String prezime;
	private boolean and;

	public SertifikatNaprednaDTO() {
		super();
	}

	public SertifikatNaprednaDTO(String broj_pasosa, String broj_sertifikata, String datum_izdavaja, String ime,
			String jmbg, String prezime, boolean and) {
		super();
		this.broj_pasosa = broj_pasosa;
		this.broj_sertifikata = broj_sertifikata;
		this.datum_izdavaja = datum_izdavaja;
		this.ime = ime;
		this.jmbg = jmbg;
		this.prezime = prezime;
		this.and = and;
	}

	public String getBroj_pasosa() {
		return broj_pasosa;
	}

	public void setBroj_pasosa(String broj_pasosa) {
		this.broj_pasosa = broj_pasosa;
	}

	public String getBroj_sertifikata() {
		return broj_sertifikata;
	}

	public void setBroj_sertifikata(String broj_sertifikata) {
		this.broj_sertifikata = broj_sertifikata;
	}

	public String getDatum_izdavaja() {
		return datum_izdavaja;
	}

	public void setDatum_izdavaja(String datum_izdavaja) {
		this.datum_izdavaja = datum_izdavaja;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public boolean isAnd() {
		return and;
	}

	public void setAnd(boolean and) {
		this.and = and;
	}

}
