package com.example.sluzbenik_back.model.izvestaj_o_imunizaciji;

public class PrintIzvestajOImunizaciji {

	public static void printIzvestaj(IzvestajOImunizaciji izvestaj) {
		System.out.println("-----------------------------");
		System.out.println("Izvestaj: ");
		System.out.println("Pocetak perioda: "+ izvestaj.getPeriodIzvestaja().getPocetakPerioda());
		System.out.println("Kraj perioda: "+ izvestaj.getPeriodIzvestaja().getKrajPerioda());
		System.out.println("Ukupan broj dokumenata zahteva: "+ izvestaj.getPodaciOZahtevima().getBrojDokumenataOInteresovanju());
		System.out.println("Broj primljenih zahteva: "+ izvestaj.getPodaciOZahtevima().getBrojZahteva().getPrimljeno());
		System.out.println("Broj primljenih zahteva: "+ izvestaj.getPodaciOZahtevima().getBrojZahteva().getIzdato());
		System.out.println("Lista vakcina: \n-----------------------------");
		printVakcine(izvestaj.getListaVakcina());
		System.out.println("Raspodela po proizvodjacima: \n-----------------------------");
		printProizvodjaci(izvestaj.getRaspodelaPoProizvodjacima());
		System.out.println("Datum izdavanja: "+izvestaj.getDatumIzdavanja()+ "\n");

	}

	private static void printProizvodjaci(RaspodelaPoProizvodjacima raspodelaPoProizvodjacima) {
		for(Proizvodjac p : raspodelaPoProizvodjacima.getProizvodjac()) {
			System.out.println("Proizvodjac: "+p.getImeProizvodjaca());
			System.out.println("Broj datih doza: "+p.getBrojDoza());
		}
		
	}

	private static void printVakcine(ListaVakcina listaVakcina) {
		for(Vakcina v: listaVakcina.getVakcina()) {
			System.out.println("Redni broj doze: "+v.getRedniBrojDoze());
			System.out.println("Broj doza: "+v.getBrojDatihDoza());
		}
		
	}
	
}
