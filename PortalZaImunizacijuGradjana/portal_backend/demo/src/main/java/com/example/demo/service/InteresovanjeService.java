package com.example.demo.service;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.ListaSaglasnosti;
import com.example.demo.util.XSLFORTransformer;
import org.apache.commons.io.input.ReaderInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.example.demo.client.DostupneVakcineClient;
import com.example.demo.client.EmailClient;
import com.example.demo.model.dostupne_vakcine.Zalihe;
import com.example.demo.model.dostupne_vakcine.Zalihe.Vakcina;
import com.example.demo.model.interesovanje.Interesovanje;
import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost;
import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost.Pacijent.Datum;
import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost.Pacijent.LicniPodaci;
import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost.Pacijent.LicniPodaci.Ime;
import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost.Pacijent.LicniPodaci.KontaktInformacije;
import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost.Pacijent.LicniPodaci.KontaktInformacije.Email;
import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost.Pacijent.LicniPodaci.Prezime;
import com.example.demo.repository.InteresovanjeRepository;

import static com.example.demo.util.PathConstants.*;
import static com.example.demo.util.PathConstants.ZAHTEV_ZA_SERTIFIKAT_XSL;

@Service
public class InteresovanjeService extends AbstractService {

	private DostupneVakcineClient dostupneVakcineClient;

	private SaglasnostService saglasnostService;

	private EmailClient emailClient;

	@Autowired
	public InteresovanjeService(InteresovanjeRepository interesovanjeRepository,
			DostupneVakcineClient dostupneVakcineClient, SaglasnostService saglasnostService, EmailClient emailClient) {

		super(interesovanjeRepository, "/db/portal/lista_interesovanja", "/lista_interesovanja");

		this.dostupneVakcineClient = dostupneVakcineClient;
		this.saglasnostService = saglasnostService;
		this.emailClient = emailClient;
	}

	@Override
	public void saveXML(String documentId, String content) throws Exception {

		InputStream inputStream = new ReaderInputStream(new StringReader(content));

		JAXBContext context = JAXBContext.newInstance(Interesovanje.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();

		Interesovanje interesovanje = (Interesovanje) unmarshaller.unmarshal(inputStream);

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
		String odabir = "";
		for (Vakcina zaliha : zalihe.getVakcina()) {

			if ((proizvodjaci.contains(zaliha.getNaziv()) || proizvodjaci.contains("Bilo koja"))
					&& zaliha.getDostupno() - zaliha.getRezervisano() > 0) {
				dostupno = true;
				message += zaliha.getNaziv() + '\n';
				odabir += zaliha.getNaziv() + ',';
				zaliha.setRezervisano(zaliha.getRezervisano() + 1);
			}
		}
		if (!dostupno) {
			message = "Postovani, \n trenutno nema odabanih vakcina na stanju, u najkracem mogucem roku cemo vas obavestiti o terminu.";
		} else {
			LocalDateTime termin = this.saglasnostService.pronadjiSlobodanTermin(1);
			DateTimeFormatter ft = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm");

			message = "Poštovani, \n Obaveštavamo vas da je izvršena prijava za vakcinaciju. Vaš termin je: "
					+ termin.format(ft) + "   Dostupne vakcine su: " + message
					+ ".\n Molimo vas da popunite obrazac saglasnosti za vakcinaciju pre pocetka vaseg termina. Obrazac za saglasnost se nalazi na poralu. Poy";

			// TODO send mail

			com.example.demo.model.email.Email emailModel = new com.example.demo.model.email.Email();
			emailModel.setTo("rajtea6@gmail.com");
			emailModel.setContent(message);
			emailModel.setSubject("Pozdrav");
			emailClient.sendMail(emailModel);
			System.out.println(message);

			// Napravi saglasnost
			generateAndSaveGradjaninSaglasnost(interesovanje, odabir, termin);

			// Azuriraj zalihe
			this.dostupneVakcineClient.updateVakcine(zalihe);
		}

		repository.saveXML("interesovanje_" + documentId, collectionId, content);
	}

	@Override
	public void deleteXML(String documentId) throws Exception {

		// Delete interesovanje RDF
		repository.deleteRDF(documentId, "/lista_interesovanja",
				"http://www.ftn.uns.ac.rs/xml_i_veb_servisi/interesovanje/");

		// Delete XML
		documentId = "interesovanje_" + documentId + ".xml";

		XMLResource res = repository.readXML(documentId, collectionId);

		if (res != null) {

			JAXBContext context = JAXBContext.newInstance(Interesovanje.class);

			Unmarshaller unmarshaller = context.createUnmarshaller();

			Interesovanje i = (Interesovanje) unmarshaller.unmarshal((res).getContentAsDOM());
			String email = i.getLicneInformacije().getKontakt().getEmail().getValue();

			// Find saglasnost by email
			Saglasnost saglasnost = saglasnostService.pronadjiSaglasnostPoEmailu(email);

			// If saglasnost exists delete it
			if (saglasnost != null) {

				// Azuriraj zalihe
				Zalihe zalihe = this.dostupneVakcineClient.getDostupneVakcine();

				String[] temp = saglasnost.getOdabraneVakcine().split(",");
				List<String> proizvodjaci = new ArrayList<>();
				for (String t : temp) {
					proizvodjaci.add(t);
				}

				for (Vakcina zaliha : zalihe.getVakcina()) {

					if (proizvodjaci.contains(zaliha.getNaziv())) {

						zaliha.setRezervisano(zaliha.getRezervisano() - 1);
					}
				}

				this.dostupneVakcineClient.updateVakcine(zalihe);

				// Delete saglasnost RDF and XML
				this.saglasnostService.deleteRDF(saglasnost.getBrojSaglasnosti());
				saglasnostService.deleteXML("saglasnost_" + saglasnost.getBrojSaglasnosti() + ".xml");
			}
			// Delete interesovanje
			repository.deleteXML(documentId, collectionId);

		}

	}

	public XMLResource pronadjiPoId(String documentId) throws Exception {
		XMLResource res = repository.readXML("interesovanje_" + documentId + ".xml", collectionId);
		return res;

	}

	public XMLResource pronadjiInteresovanjePoEmailu(String email) throws Exception {
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

	// Kreira saglasnost i dodijeli termine za interesovanja koja cekaju za termin
	public void updatePendingInteresovanja() throws Exception {
		List<String> results = ((InteresovanjeRepository) repository).getInteresovanjeOrderByAsc();

		if(results.size() <= 0) return;		
		JAXBContext context = JAXBContext.newInstance(Interesovanje.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();

		Zalihe zalihe = this.dostupneVakcineClient.getDostupneVakcine();
		
		results.forEach(result -> {
			try {

				XMLResource resource = pronadjiPoId(result);
				Interesovanje interesovanje = (Interesovanje) unmarshaller.unmarshal(resource.getContentAsDOM());
				
				if (!interesovanje.isDodeljenTermin() && interesovanje != null)
				{
					List<String> proizvodjaci = interesovanje.getProizvodjaci().getProizvodjac();

					String message = "";
					Boolean dostupno = false;
					String odabir = "";
					for (Vakcina zaliha : zalihe.getVakcina()) {

						if ((proizvodjaci.contains(zaliha.getNaziv()) || proizvodjaci.contains("Bilo koja"))
								&& zaliha.getDostupno() - zaliha.getRezervisano() > 0) {
							dostupno = true;
							message += zaliha.getNaziv() + '\n';
							odabir += zaliha.getNaziv() + ',';
							zaliha.setRezervisano(zaliha.getRezervisano() + 1);
						}
					}
					
					if(dostupno){
						LocalDateTime termin = this.saglasnostService.pronadjiSlobodanTermin(1);
						DateTimeFormatter ft = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm");

						message = "Poštovani, \n Obaveštavamo vas da je izvršena prijava za vakcinaciju. Vaš termin je: "
								+ termin.format(ft) + "   Dostupne vakcine su: " + message
								+ ".\n Molimo vas da popunite obrazac saglasnosti za vakcinaciju pre pocetka vaseg termina. Obrazac za saglasnost se nalazi na poralu. Poy";

						// TODO send mail
						com.example.demo.model.email.Email emailModel = new com.example.demo.model.email.Email();
						emailModel.setTo("rajtea6@gmail.com");
						emailModel.setContent(message);
						emailModel.setSubject("Pozdrav");
						//emailClient.sendMail(emailModel);
						System.out.println(message);

						// Napravi saglasnost
						generateAndSaveGradjaninSaglasnost(interesovanje, odabir, termin);

						// Azuriraj zalihe
						this.dostupneVakcineClient.updateVakcine(zalihe);
						interesovanje.setDodeljenTermin(true);
						
						Marshaller marshaller = context.createMarshaller();
						marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

						ByteArrayOutputStream stream = new ByteArrayOutputStream();

						marshaller.marshal(interesovanje, stream);

						String finalString = new String(stream.toByteArray());
						System.out.println(finalString);

						
						repository.saveXML("interesovanje_" + result, collectionId, finalString);
					}
				}
			} catch (Exception e) {

			}
		});	
	}
	
	private void generateAndSaveGradjaninSaglasnost(Interesovanje interesovanje, String odabir,LocalDateTime termin) throws Exception {
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

		odabir = odabir.substring(0, odabir.length() - 1);
		saglasnost.setOdabraneVakcine(odabir);

		saglasnost.getPacijent().setDatum(new Datum());
		saglasnost.getPacijent().getDatum().setProperty("pred:datum_termina");
		saglasnost.getPacijent().getDatum().setValue(dateFormatted);

		saglasnost.getPacijent().setLicniPodaci(new LicniPodaci());
		saglasnost.getPacijent().getLicniPodaci().setKontaktInformacije(new KontaktInformacije());

		// Email
		String email = interesovanje.getLicneInformacije().getKontakt().getEmail().getValue();
		saglasnost.getPacijent().getLicniPodaci().getKontaktInformacije().setEmail(new Email());
		saglasnost.getPacijent().getLicniPodaci().getKontaktInformacije().getEmail().setValue(email);
		saglasnost.getPacijent().getLicniPodaci().getKontaktInformacije().getEmail().setProperty("pred:email");

		// Ime
		saglasnost.getPacijent().getLicniPodaci().setIme(new Ime());
		saglasnost.getPacijent().getLicniPodaci().getIme().setProperty("pred:ime");
		saglasnost.getPacijent().getLicniPodaci().getIme()
				.setValue(interesovanje.getLicneInformacije().getIme().getValue());

	

		saglasnost.getPacijent().getLicniPodaci().setZanimanjeZaposlenog("");

		JAXBContext contextSaglasnost = JAXBContext.newInstance(Saglasnost.class);
		OutputStream os = new ByteArrayOutputStream();

		Marshaller marshallerSaglasnost = contextSaglasnost.createMarshaller();
		marshallerSaglasnost.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		marshallerSaglasnost.marshal(saglasnost, os);
		saglasnostService.saveXML("saglasnost_" + id, os.toString());
		System.out.println(os.toString());
		saglasnostService.saveRDF(os.toString(), "/lista_saglasnosti");
	}
	
	public String generatePDF(String id) {
		XSLFORTransformer transformer = null;

		try {
			transformer = new XSLFORTransformer();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		XMLResource xmlRes = this.readXML(id);
		String doc_str = "";
		try {
			doc_str = xmlRes.getContent().toString();
			System.out.println(doc_str);
		} catch (XMLDBException e1) {
			e1.printStackTrace();
		}

		boolean ok = false;
		String pdf_path = SAVE_PDF + "interesovanje_" + id.split(".xml")[0] + ".pdf";

		try {
			ok = transformer.generatePDF(doc_str, pdf_path, INTERESOVANJE_XSL_FO);
			if (ok)
				return pdf_path;
			else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String generateHTML(String id) throws XMLDBException {
		XSLFORTransformer transformer = null;

		try {
			transformer = new XSLFORTransformer();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		XMLResource xmlRes = this.readXML(id);
		String doc_str = xmlRes.getContent().toString();
		boolean ok = false;
		String html_path = SAVE_HTML + "interesovanje_" + id + ".html";
		System.out.println(doc_str);

		try {
			ok = transformer.generateHTML(doc_str, html_path, INTERESOVANJE_XSL);
			if (ok)
				return html_path;
			else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<com.example.demo.dto.DokumentDTO> getSaglasnostiAllByEmail(String email){
//		try {
//			System.out.println("OVDEEEEEE");
//			String all = this.allXmlByEmail(email);
//
//			JAXBContext context = JAXBContext.newInstance(ListaSaglasnosti.class);
//			Unmarshaller unmarshaller = context.createUnmarshaller();
//			StringReader reader = new StringReader(all);
//			ListaSaglasnosti saglasnosti = (ListaSaglasnosti) unmarshaller.unmarshal(reader);
//			List<com.example.sluzbenik_back.dto.DokumentDTO> ret = new ArrayList<>();
//			for (Saglasnost s: saglasnosti.getSaglasnosti()) {
//				ret.add(new com.example.sluzbenik_back.dto.DokumentDTO(s));
//			}System.out.println("OVDEEEEEE");
//			return ret;
//
//		} catch (Exception e){
//			e.printStackTrace();
//		}
		return new ArrayList<>();
	}
}
