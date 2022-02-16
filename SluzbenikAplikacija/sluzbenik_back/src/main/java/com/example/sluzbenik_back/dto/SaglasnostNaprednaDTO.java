package com.example.sluzbenik_back.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SaglasnostNaprednaDTO {

	private String ime;
	private String prezime;
	private String datum;
	private String email;
	private String jmbg;
	private boolean and;

	public SaglasnostNaprednaDTO() {
		super();
	}

	public SaglasnostNaprednaDTO(String ime, String prezime, String datum, String email, String jmbg, boolean and) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.datum = datum;
		this.email = email;
		this.jmbg = jmbg;
		this.and = and;
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

	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}

	public boolean isAnd() {
		return and;
	}

	public void setAnd(boolean and) {
		this.and = and;
	}

}