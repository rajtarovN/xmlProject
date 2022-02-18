package com.example.sluzbenik_back.dto;

public class IzvestajDTO {
    private String pocetakPerioda;
    private String krajPerioda;
    private int brInteresovanja;
    private int brPrimljenihZahteva;
    private int brIzdatihZahteva;
    private int brPrveDoze;
    private int brDrugeDoze;
    private int brTreceDoze;
    private int brPfizerVakcina;
    private int brSinopharmVakcina;
    private int brSputnikVakcina;
    private int brAstraZenecaVakcina;
    private String datumIzdavanja;

    public IzvestajDTO() {
    }

    public IzvestajDTO(String pocetakPerioda, String krajPerioda, int brInteresovanja, int brPrimljenihZahteva, int brIzdatihZahteva, int brPrveDoze, int brDrugeDoze, int brTreceDoze, int brPfizerVakcina, int brSinopharmVakcina, int brSputnikVakcina, int brAstraZenecaVakcina, String datumIzdavanja) {
        this.pocetakPerioda = pocetakPerioda;
        this.krajPerioda = krajPerioda;
        this.brInteresovanja = brInteresovanja;
        this.brPrimljenihZahteva = brPrimljenihZahteva;
        this.brIzdatihZahteva = brIzdatihZahteva;
        this.brPrveDoze = brPrveDoze;
        this.brDrugeDoze = brDrugeDoze;
        this.brTreceDoze = brTreceDoze;
        this.brPfizerVakcina = brPfizerVakcina;
        this.brSinopharmVakcina = brSinopharmVakcina;
        this.brSputnikVakcina = brSputnikVakcina;
        this.brAstraZenecaVakcina = brAstraZenecaVakcina;
        this.datumIzdavanja = datumIzdavanja;
    }

    public String getPocetakPerioda() {
        return pocetakPerioda;
    }

    public void setPocetakPerioda(String pocetakPerioda) {
        this.pocetakPerioda = pocetakPerioda;
    }

    public String getKrajPerioda() {
        return krajPerioda;
    }

    public void setKrajPerioda(String krajPerioda) {
        this.krajPerioda = krajPerioda;
    }

    public int getBrInteresovanja() {
        return brInteresovanja;
    }

    public void setBrInteresovanja(int brInteresovanja) {
        this.brInteresovanja = brInteresovanja;
    }

    public int getBrPrimljenihZahteva() {
        return brPrimljenihZahteva;
    }

    public void setBrPrimljenihZahteva(int brPrimljenihZahteva) {
        this.brPrimljenihZahteva = brPrimljenihZahteva;
    }

    public int getBrIzdatihZahteva() {
        return brIzdatihZahteva;
    }

    public void setBrIzdatihZahteva(int brIzdatihZahteva) {
        this.brIzdatihZahteva = brIzdatihZahteva;
    }

    public int getBrPrveDoze() {
        return brPrveDoze;
    }

    public void setBrPrveDoze(int brPrveDoze) {
        this.brPrveDoze = brPrveDoze;
    }

    public int getBrDrugeDoze() {
        return brDrugeDoze;
    }

    public void setBrDrugeDoze(int brDrugeDoze) {
        this.brDrugeDoze = brDrugeDoze;
    }

    public int getBrTreceDoze() {
        return brTreceDoze;
    }

    public void setBrTreceDoze(int brTreceDoze) {
        this.brTreceDoze = brTreceDoze;
    }

    public int getBrPfizerVakcina() {
        return brPfizerVakcina;
    }

    public void setBrPfizerVakcina(int brPfizerVakcina) {
        this.brPfizerVakcina = brPfizerVakcina;
    }

    public int getBrSinopharmVakcina() {
        return brSinopharmVakcina;
    }

    public void setBrSinopharmVakcina(int brSinopharmVakcina) {
        this.brSinopharmVakcina = brSinopharmVakcina;
    }

    public int getBrSputnikVakcina() {
        return brSputnikVakcina;
    }

    public void setBrSputnikVakcina(int brSputnikVakcina) {
        this.brSputnikVakcina = brSputnikVakcina;
    }

    public int getBrAstraZenecaVakcina() {
        return brAstraZenecaVakcina;
    }

    public void setBrAstraZenecaVakcina(int brAstraZenecaVakcina) {
        this.brAstraZenecaVakcina = brAstraZenecaVakcina;
    }

    public String getDatumIzdavanja() {
        return datumIzdavanja;
    }

    public void setDatumIzdavanja(String datumIzdavanja) {
        this.datumIzdavanja = datumIzdavanja;
    }
}
