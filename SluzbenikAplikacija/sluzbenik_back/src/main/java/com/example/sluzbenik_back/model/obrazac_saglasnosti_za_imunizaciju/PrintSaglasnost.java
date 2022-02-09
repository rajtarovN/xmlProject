package com.example.sluzbenik_back.model.obrazac_saglasnosti_za_imunizaciju;

public class PrintSaglasnost {
	public static void printSaglasnost(Saglasnost saglasnost) {
		System.out.println("-----------------------------");
		System.out.println("Saglasnost: ");
		System.out.println("Pacijent: ");
		if (saglasnost.getPacijent().getDrzavljaninSrbije() != null) {
			System.out.println("----->Jmbg: " + saglasnost.getPacijent().getDrzavljaninSrbije().getJmbg().getValue());

		} else {
			System.out.println(
					"----->Drzavljanstvo: " + saglasnost.getPacijent().getStraniDrzavljanin().getDrzavljanstvo());
			System.out.println("----->Identifikacija: "
					+ saglasnost.getPacijent().getStraniDrzavljanin().getIdentifikacija().getValue());
		}
		System.out.println("----->Prezime: " + saglasnost.getPacijent().getLicniPodaci().getPrezime().getValue());
		System.out.println("----->Ime: " + saglasnost.getPacijent().getLicniPodaci().getIme().getValue());
		System.out.println("----->Ime Roditelja: " + saglasnost.getPacijent().getLicniPodaci().getImeRoditelja());
		System.out.println("----->Pol: " + saglasnost.getPacijent().getLicniPodaci().getPol());
		System.out.println("----->Datum rodjenja: " + saglasnost.getPacijent().getLicniPodaci().getDatumRodjenja());
		System.out.println("----->Mesto rodjenja: " + saglasnost.getPacijent().getLicniPodaci().getMestoRodjenja());
		System.out.println("----->Adresa: ");
		System.out.println("------------->Ulica: " + saglasnost.getPacijent().getLicniPodaci().getAdresa().getUlica());
		System.out.println("------------->Broj: " + saglasnost.getPacijent().getLicniPodaci().getAdresa().getBroj());
		System.out.println("------------->Mesto: " + saglasnost.getPacijent().getLicniPodaci().getAdresa().getMesto());
		System.out.println("------------->Grad: " + saglasnost.getPacijent().getLicniPodaci().getAdresa().getGrad());
		System.out.println("----->Fiksni telefon: "
				+ saglasnost.getPacijent().getLicniPodaci().getKontaktInformacije().getFiksniTelefon());
		System.out.println("----->Mobilni telefon: "
				+ saglasnost.getPacijent().getLicniPodaci().getKontaktInformacije().getMobilniTelefon());
		System.out.println(
				"----->Email: " + saglasnost.getPacijent().getLicniPodaci().getKontaktInformacije().getEmail());
		System.out.println("----->Radni status: " + saglasnost.getPacijent().getLicniPodaci().getRadniStatus());
		System.out.println(
				"----->Zanimanje zaposlenog: " + saglasnost.getPacijent().getLicniPodaci().getZanimanjeZaposlenog());
		if (saglasnost.getPacijent().getLicniPodaci().getSocijalnaZastita().isKorisnik()) {
			System.out.println("----->Socijalna zastita: ");
			System.out.println("------------->Naziv sedista: "
					+ saglasnost.getPacijent().getLicniPodaci().getSocijalnaZastita().getNazivSedista());
			System.out.println("------------->Opstina sedista: "
					+ saglasnost.getPacijent().getLicniPodaci().getSocijalnaZastita().getOpstinaSedista());
		}

		if (saglasnost.getPacijent().getSaglasnostPacijenta().isSaglasan()) {
			System.out.println("----->Saglasnost pacijenta: saglasan");
			System.out.println("------------->Naziv imunoloskog lekara: "
					+ saglasnost.getPacijent().getSaglasnostPacijenta().getNazivImunoloskogLekara());
		} else {
			System.out.println("----->Saglasnost pacijenta: nije saglasan");
		}
		System.out.println("----->Datum: " + saglasnost.getPacijent().getDatum());

		System.out.println("Evidencija o vakcini: ");
		System.out.println(
				"----->Zdravstvena ustanova: " + saglasnost.getEvidencijaOVakcinaciji().getZdravstvenaUstanova());
		System.out.println(
				"----->Vakcinacijski punkt: " + saglasnost.getEvidencijaOVakcinaciji().getVakcinacijskiPunkt());
		System.out.println("----->Lekar: ");
		System.out.println("------------->Ime: " + saglasnost.getEvidencijaOVakcinaciji().getLekar().getIme());
		System.out.println("------------->Prezime: " + saglasnost.getEvidencijaOVakcinaciji().getLekar().getPrezime());
		System.out.println("------------->Telefon: " + saglasnost.getEvidencijaOVakcinaciji().getLekar().getTelefon());

		System.out.println("Vakcine: ");
		/*for (main.java.xml_i_veb_servisi.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost.EvidencijaOVakcinaciji.Vakcine.Vakcina vakcina : saglasnost
				.getEvidencijaOVakcinaciji().getVakcine().getVakcina()) {
			System.out.println("----->Vakcina : " + vakcina.getDoza());
			System.out.println("------------->Naziv: " + vakcina.getNaziv());
			System.out.println("------------->Datum davanja: " + vakcina.getDatumDavanja());
			System.out.println("------------->Nacin davanja: " + vakcina.getNacinDavanja());
			System.out.println("------------->Eksremitet: " + vakcina.getEkstremiter());
			System.out.println("------------->Serija: " + vakcina.getSerija());
			System.out.println("------------->Proizvodjac: " + vakcina.getProizvodjac());
			System.out.println("------------->Nezeljena reakcija: " + vakcina.getNezeljenaReakcija());
		}*/
		System.out.println("----->Privremene kontradikcije: ");
		System.out.println("------------->Datum utvrdjivanja: " + saglasnost.getEvidencijaOVakcinaciji().getVakcine()
				.getPrivremeneKontraindikacije().getDatumUtvrdjivanja());
		System.out.println("------------->Diagnoza: "
				+ saglasnost.getEvidencijaOVakcinaciji().getVakcine().getPrivremeneKontraindikacije().getDijagnoza());
		System.out.println("----->Odluka komisije za trajne kontradikcije: "
				+ saglasnost.getEvidencijaOVakcinaciji().getVakcine().getOdlukaKomisijeZaTrajneKontraindikacije());
	}
}
