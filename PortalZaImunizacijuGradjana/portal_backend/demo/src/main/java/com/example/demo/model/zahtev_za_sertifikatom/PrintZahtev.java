package com.example.demo.model.zahtev_za_sertifikatom;

public class PrintZahtev {
	public static void printZahtev(ZahtevZaZeleniSertifikat zahtev ) {
		System.out.println("-----------------------------");
		System.out.println("Zahtev: ");
		System.out.println("Podnosilac zahteva: "+ zahtev.getPodnosilacZahteva().getIme()
				+ " " + zahtev.getPodnosilacZahteva().getPrezime());
		System.out.println("Broj pasosa: "+ zahtev.getPodnosilacZahteva().getBrojPasosa());
		System.out.println("JMBG: "+ zahtev.getPodnosilacZahteva().getJmbg());
		System.out.println("Datum rodjenja: "+ zahtev.getPodnosilacZahteva().getDatumRodjenja());
		System.out.println("Pol: "+ zahtev.getPodnosilacZahteva().getPol());
		System.out.println("Razlog: " + zahtev.getPodnosilacZahteva().getRazlogPodnosenjaZahteva());
		
		
		System.out.println("Datum podnosenja zahteva: "+ zahtev.getZaglavlje().getDanPodnosenjaZahteva());
		System.out.println("Mesto podnosenja zahteva: "+ zahtev.getZaglavlje().getMestoPodnosenjaZahteva());

	}

}
