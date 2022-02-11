package com.example.demo.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.io.input.ReaderInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmldb.api.modules.XMLResource;

import com.example.demo.client.DostupneVakcineClient;
import com.example.demo.model.dostupne_vakcine.Zalihe;
import com.example.demo.model.dostupne_vakcine.Zalihe.Vakcina;
import com.example.demo.model.interesovanje.Interesovanje;
import com.example.demo.model.interesovanje.PrintInteresovanje;
import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost;
import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost.Pacijent.Datum;
import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost.Pacijent.LicniPodaci;
import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost.Pacijent.LicniPodaci.KontaktInformacije;
import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost.Pacijent.LicniPodaci.KontaktInformacije.Email;
import com.example.demo.repository.InteresovanjeRepository;

@Service
public class InteresovanjeService extends AbstractService {

	private DostupneVakcineClient dostupneVakcineClient;

	private SaglasnostService saglasnostService;

	@Autowired
	public InteresovanjeService(InteresovanjeRepository interesovanjeRepository,
			DostupneVakcineClient dostupneVakcineClient, SaglasnostService saglasnostService) {

		super(interesovanjeRepository, "/db/portal/lista_interesovanja", "/lista_interesovanja");

		this.dostupneVakcineClient = dostupneVakcineClient;
		this.saglasnostService = saglasnostService;
	}

	@Override
	public void saveXML(String documentId, String content) throws Exception {

		InputStream inputStream = new ReaderInputStream(new StringReader(content));

		JAXBContext context = JAXBContext.newInstance(Interesovanje.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();

		Interesovanje interesovanje = (Interesovanje) unmarshaller.unmarshal(inputStream);
		
		interesovanje.setAbout("http://www.ftn.uns.ac.rs/xml_i_veb_servisi/interesovanje/" + documentId);
		repository.saveRDF(content, fusekiCollectionId);
		PrintInteresovanje.printInteresovanje(interesovanje);
		
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		ByteArrayOutputStream stream = new ByteArrayOutputStream();

		marshaller.marshal(interesovanje, stream);

		String finalString = new String(stream.toByteArray());
		System.out.println(finalString);

		content = finalString;

		Zalihe zalihe = this.dostupneVakcineClient.getDostupneVakcine();

		List<String> proizvodjaci = interesovanje.getProizvodjaci().getProizvodjac();

		String message = "";
		Boolean dostupno = false;
		for (Vakcina zaliha : zalihe.getVakcina()) {

			if ((proizvodjaci.contains(zaliha.getNaziv()) || proizvodjaci.contains("Bilo koja"))
					&& zaliha.getDostupno() - zaliha.getRezervisano() > 0) {
				dostupno = true;
				message += zaliha.getNaziv() + '\n';
				// TODO zaliha.setRezervisano(zaliha.getRezervisano() + 1);
			}
		}

		if (!dostupno) {
			message = "Postovani, \n trenutno nema odabanih vakcina na stanju, u najkracem mogucem roku cemo vas obavestiti o terminu.";
		} else {
			LocalDateTime termin = this.saglasnostService.pronadjiSlobodanTermin();
			DateTimeFormatter ft = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm");

			message = "Poštovani, \n Obaveštavamo vas da je izvršena prijava za vakcinaciju. Vaš termin je: "
					+ termin.format(ft) + "   Dostupne vakcine su: " + message
					+ ".\n Molimo vas da popunite obrazac saglasnosti za vakcinaciju pre pocetka vaseg termina. Obrazac za saglasnost se nalazi na poralu. Poy";

			// TODO send mail
			System.out.println(message);

			// Napravi saglasnost
			String id = UUID.randomUUID().toString();

			Saglasnost saglasnost = new Saglasnost();
			saglasnost.setAbout("http://www.ftn.uns.ac.rs/xml_i_veb_servisi/obrazac_saglasnosti_za_imunizaciju/" + id);
			saglasnost.setBrojSaglasnosti(id);
			saglasnost.setEvidencijaOVakcinaciji(new Saglasnost.EvidencijaOVakcinaciji());
			saglasnost.setPacijent(new Saglasnost.Pacijent());

			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");
			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

			LocalDate date = termin.toLocalDate();
			LocalTime time = termin.toLocalTime();

			XMLGregorianCalendar dateFormatted = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(dateFormatter.format(date));
			XMLGregorianCalendar timeFormatted = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(timeFormatter.format(time));

			saglasnost.setVremeTermina(timeFormatted);
			saglasnost.getPacijent().setDatum(new Datum());
			saglasnost.getPacijent().getDatum().setValue(dateFormatted);
			saglasnost.getPacijent().setLicniPodaci(new LicniPodaci());
			saglasnost.getPacijent().getLicniPodaci().setKontaktInformacije(new KontaktInformacije());
			
			String email = interesovanje.getLicneInformacije().getKontakt().getEmail().getValue();
			saglasnost.getPacijent().getLicniPodaci().getKontaktInformacije().setEmail(new Email());
			saglasnost.getPacijent().getLicniPodaci().getKontaktInformacije().getEmail()
					.setValue(email);

			JAXBContext contextSaglasnost = JAXBContext
					.newInstance("com.example.demo.model.obrazac_saglasnosti_za_imunizaciju");
			OutputStream os = new ByteArrayOutputStream();

			Marshaller marshallerSaglasnost = contextSaglasnost.createMarshaller();
			marshallerSaglasnost.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			marshallerSaglasnost.marshal(saglasnost, os);
			saglasnostService.saveXML("saglasnost_" + id, os.toString());

			// TODO Save zalihe
		}

		repository.saveXML("interesovanje_" + documentId, collectionId, content);
		//repository.saveRDF(content, fusekiCollectionId);
	}

	@Override
	public void deleteXML(String documentId) throws Exception {
		// Delete interesovanje

		XMLResource res = repository.readXML(documentId, collectionId);

		if (res != null) {

			JAXBContext context = JAXBContext.newInstance(Interesovanje.class);

			Unmarshaller unmarshaller = context.createUnmarshaller();

			Interesovanje i = (Interesovanje) unmarshaller.unmarshal((res).getContentAsDOM());
			String email = i.getLicneInformacije().getKontakt().getEmail().getValue();

			// Find saglasnost by email
			Saglasnost s = saglasnostService.pronadjiSaglasnostPoEmailu(email);

			// If saglasnost exists delete it
			if (s != null)
				saglasnostService.deleteXML(s.getBrojSaglasnosti());

			// Delete interesovanje
			repository.deleteXML(documentId, collectionId);
		}

	}

	public Interesovanje pronadjiPoId(String documentId) throws Exception {
		XMLResource res = repository.readXML("interesovanje_" + documentId + ".xml", collectionId);
		try {
			if (res != null) {

				JAXBContext context = JAXBContext.newInstance(Interesovanje.class);

				Unmarshaller unmarshaller = context.createUnmarshaller();

				return (Interesovanje) unmarshaller.unmarshal((res).getContentAsDOM());

			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	public Interesovanje pronadjiInteresovanjePoEmailu(String email) throws Exception {
		String id = ((InteresovanjeRepository) repository).pronadjiPoEmailu(email);
		try {
			if (id != null) {

				return this.pronadjiPoId(id);

			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
}
