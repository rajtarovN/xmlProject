package com.example.sluzbenik_back.dto;

public class UserTokenStateDTO {

	private String authenticationToken;
	private int expiresAt;
	private String email;
	private String uloga;
	private String ime;
	private String prezime;
	private String pol;
	private String rodjendan;

	public UserTokenStateDTO() {
	}

	public UserTokenStateDTO(String authenticationToken, int expiresAt) {
		super();
		this.authenticationToken = authenticationToken;
		this.expiresAt = expiresAt;
	}

	public UserTokenStateDTO(String authenticationToken, int expiresAt, String email) {
		super();
		this.authenticationToken = authenticationToken;
		this.expiresAt = expiresAt;
		this.email = email;

	}

	public UserTokenStateDTO(String authenticationToken, int expiresAt, String email, String uloga) {
		super();
		this.authenticationToken = authenticationToken;
		this.expiresAt = expiresAt;
		this.email = email;
		this.uloga = uloga;
	}

	public UserTokenStateDTO(String authenticationToken, int expiresAt, String email, String uloga, String ime,
                             String prezime, String pol, String rodjendan) {
		super();
		this.authenticationToken = authenticationToken;
		this.expiresAt = expiresAt;
		this.email = email;
		this.uloga = uloga;
		this.ime = ime;
		this.prezime = prezime;
		this.pol = pol;
		this.rodjendan = rodjendan;
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

	public String getUloga() {
		return uloga;
	}

	public void setUloga(String uloga) {
		this.uloga = uloga;
	}

	public String getAuthenticationToken() {
		return authenticationToken;
	}

	public void setAuthenticationToken(String authenticationToken) {
		this.authenticationToken = authenticationToken;
	}

	public int getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(int expiresAt) {
		this.expiresAt = expiresAt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
