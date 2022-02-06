package com.example.sluzbenik_back.model.digitalni_zeleni_sertifikat;

import java.util.List;

public class PrintDigitalniZeleniSertifikat {

    public static void printSertifikat(DigitalniZeleniSertifikat digitalniZeleniSertifikat){
        System.out.println("-----------------------------");
        System.out.println("Digitalni zeleni sertifikat: ");
        System.out.println("Podaci o sertifikatu: ");
        System.out.println("----->Broj sertifikata: " + digitalniZeleniSertifikat.getPodaciOSertifikatu().getBrojSertifikata().getValue());
        System.out.println("----->Datum i vreme izdavanja: " + digitalniZeleniSertifikat.getPodaciOSertifikatu().getDatumIVremeIzdavanja().getValue());
        System.out.println("Podaci o osobi: ");
        System.out.println("----->Ime: " + digitalniZeleniSertifikat.getPodaciOOsobi().getIme().getValue());
        System.out.println("----->Prezime: " + digitalniZeleniSertifikat.getPodaciOOsobi().getPrezime().getValue());
        System.out.println("----->Pol: " + digitalniZeleniSertifikat.getPodaciOOsobi().getPol());
        System.out.println("----->Datum rodjenja: " + digitalniZeleniSertifikat.getPodaciOOsobi().getDatumRodjenja());
        System.out.println("----->Jmbg: " + digitalniZeleniSertifikat.getPodaciOOsobi().getJmbg().getValue());
        System.out.println("----->Broj pasosa: " + digitalniZeleniSertifikat.getPodaciOOsobi().getBrojPasosa().getValue());
        System.out.println("Podaci o vakcinaciji: ");
        printPodaciOVakcinaciji(digitalniZeleniSertifikat.getPodaciOVakcinaciji().getVakcinacija());
        System.out.println("Testovi: ");
        System.out.println("----->Naziv testa: " + digitalniZeleniSertifikat.getTestovi().getNazivTesta());
        System.out.println("----->Vrsta uzorka: " + digitalniZeleniSertifikat.getTestovi().getVrstaUzorka());
        System.out.println("----->Datum i vreme uzorkovanja: " + digitalniZeleniSertifikat.getTestovi().getDatumIVremeIzdavanjaRezultata());
        System.out.println("----->Datum i vreme izdavanja rezultata: " + digitalniZeleniSertifikat.getTestovi().getDatumIVremeIzdavanjaRezultata());
        System.out.println("Datum: " + digitalniZeleniSertifikat.getDatum());
    }

    public static void printPodaciOVakcinaciji(List<DigitalniZeleniSertifikat.PodaciOVakcinaciji.Vakcinacija> vakcinacijaList){
        for(DigitalniZeleniSertifikat.PodaciOVakcinaciji.Vakcinacija vakcinacija : vakcinacijaList){
            System.out.println("----->Tip: " + vakcinacija.getTip());
            System.out.println("----->Proizvodjac: " + vakcinacija.getProizvodjac().getValue());
            System.out.println("----->Serija: " + vakcinacija.getSerija());
            System.out.println("----->Datum: " + vakcinacija.getDatum().getValue());
            System.out.println("----->Zdravstvena_ustanova: " + vakcinacija.getZdravstvenaUstanova().getValue());
            System.out.println("-----------------------------");
        }
    }
}
