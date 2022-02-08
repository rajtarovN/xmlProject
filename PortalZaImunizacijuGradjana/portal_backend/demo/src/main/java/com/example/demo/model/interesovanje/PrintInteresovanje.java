package com.example.demo.model.interesovanje;

import com.example.demo.model.digitalni_zeleni_sertifikat.DigitalniZeleniSertifikat.PodaciOVakcinaciji.Vakcinacija.Proizvodjac;

import main.java.com.example.demo.model.interesovanje.Interesovanje.Proizvodjaci;

public class PrintInteresovanje {

    public static void printInteresovanje(Interesovanje interesovanje){
        System.out.println("-----------------------------");
        System.out.println("Interesovanje: ");
        System.out.println("Licne informacije: ");
        System.out.println("----->Drzavljanstvo: " + interesovanje.getLicneInformacije().getDrzavljanstvo());
        System.out.println("----->Ime: " + interesovanje.getLicneInformacije().getIme().getValue());
        System.out.println("----->Prezime: " + interesovanje.getLicneInformacije().getPrezime().getValue());
        System.out.println("----->Jmbg: " + interesovanje.getLicneInformacije().getJmbg().getValue());
        System.out.println("----->Davalac krvi: " + interesovanje.getLicneInformacije().getDavalacKrvi().isDavalac());
        System.out.println("----->Kontakt: ");
        System.out.println("------------->Email: "+ interesovanje.getLicneInformacije().getKontakt().getEmail().getValue());
        System.out.println("------------->Broj mobilnog: "+ interesovanje.getLicneInformacije().getKontakt().getBrojMobilnog());
        System.out.println("------------->Broj fiksnog: "+ interesovanje.getLicneInformacije().getKontakt().getBrojFiksnog());
        System.out.println("Lokacija primanja vakcine: " + interesovanje.getLokacijaPrimanjaVakcine().getValue());
        for (Proizvodjac p : Proizvodjaci) {
        	System.out.println("Proizvodjac: " + p.getProizvodjac());
		}
        System.out.println("Datum podnosenja interesovanja: " + interesovanje.getDatumPodnosenjaInteresovanja().getValue());
    }
}
