package com.example.sluzbenik_back.model.potvrda_o_vakcinaciji;

public class PrintPotvrda {
	
	public static void printPotvrda (PotvrdaOVakcinaciji potvrda) { 
		
		System.out.println("-----------------------------");
		System.out.println("Licni podaci:");
		System.out.println("Ime:  " + potvrda.getLicniPodaci().getIme());
		System.out.println("Prezime:  "+potvrda.getLicniPodaci().getPrezime());
		System.out.println("Datum rodjenja:  "+potvrda.getLicniPodaci().getDatumRodjenja());
		System.out.println("JMBG:  "+potvrda.getLicniPodaci().getJmbg());
		System.out.println("Pol:  " + potvrda.getLicniPodaci().getPol());
		
		System.out.println("Vakcine:");
		System.out.println("Doze:");
		for(int i = 0; i<potvrda.getVakcinacija().getDoze().getDoza().size(); i++) {
			System.out.println("    Doza: " + potvrda.getVakcinacija().getDoze().getDoza().get(i).getBroj());
			System.out.println("      Datum davanja: "+potvrda.getVakcinacija().getDoze().getDoza().get(i).getDatumDavanja());
			System.out.println("      Broj serije: "+potvrda.getVakcinacija().getDoze().getDoza().get(i).getBrojSerije());
		}
		
		System.out.println("Zdravstvena ustanova: "+potvrda.getVakcinacija().getZdravstvenaUstanova());
		System.out.println("Naziv vakcine: "+potvrda.getVakcinacija().getNazivVakcine());
		
		System.out.println("Datum izdavanja potvrde: " + potvrda.getDatumIzdavanja());
		System.out.println("Sifra potvrde vakcine:  " + potvrda.getSifraPotvrdeVakcine());
	}

}
