package com.example.demo.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PotvrdaNaprednaDTO {

	private String ime;
	private String prezime;
	private String datum;
	private String id;
	private boolean and;

	public PotvrdaNaprednaDTO() {
		super();
	}

	public PotvrdaNaprednaDTO(String ime, String prezime, String datum, String id, boolean and) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.datum = datum;
		this.id = id;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isAnd() {
		return and;
	}

	public void setAnd(boolean and) {
		this.and = and;
	}
}
