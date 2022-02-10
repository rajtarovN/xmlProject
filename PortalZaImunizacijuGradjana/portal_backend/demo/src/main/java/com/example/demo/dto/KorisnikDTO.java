package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonRootName;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "korisnik")
@JsonRootName(value = "korisnik")
public class KorisnikDTO {
	private String ime;
	private String prezime;
	private String pol;
	private String rodjendan;
	private String email;
	private String lozinka;
	private String uloga;

	public KorisnikDTO() {
		super();
	}

	public KorisnikDTO(String ime, String prezime, String pol, String rodjendan, String email, String lozinka,
			String uloga) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.pol = pol;
		this.rodjendan = rodjendan;
		this.email = email;
		this.lozinka = lozinka;
		this.uloga = uloga;
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

	public String getPol() {
		return pol;
	}

	public void setPol(String pol) {
		this.pol = pol;
	}

	public String getRodjendan() {
		return rodjendan;
	}

	public void setRodjendan(String rodjendan) {
		this.rodjendan = rodjendan;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public String getUloga() {
		return uloga;
	}

	public void setUloga(String uloga) {
		this.uloga = uloga;
	}

}
