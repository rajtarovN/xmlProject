package com.example.demo.service;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import static com.example.demo.util.PathConstants.INTERESOVANJE_XSL;
import static com.example.demo.util.PathConstants.INTERESOVANJE_XSL_FO;
import static com.example.demo.util.PathConstants.SAVE_HTML;
import static com.example.demo.util.PathConstants.SAVE_PDF;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.dto.DokumentDTO;
import com.example.demo.model.digitalni_zeleni_sertifikat.DigitalniZeleniSertifikat;
import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.ListaSaglasnosti;
import com.example.demo.util.MetadataExtractor;
import com.example.demo.util.XSLFORTransformer;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.ReaderInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.example.demo.client.DostupneVakcineClient;
import com.example.demo.client.EmailClient;
import com.example.demo.dto.DokumentDTO;
import com.example.demo.model.dostupne_vakcine.Zalihe;
import com.example.demo.model.dostupne_vakcine.Zalihe.Vakcina;
import com.example.demo.model.interesovanje.Interesovanje;
import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost;
import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost.Pacijent.Datum;
import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost.Pacijent.LicniPodaci;
import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost.Pacijent.LicniPodaci.Ime;
import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost.Pacijent.LicniPodaci.KontaktInformacije;
import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost.Pacijent.LicniPodaci.KontaktInformacije.Email;
import com.example.demo.repository.InteresovanjeRepository;
import com.example.demo.util.XSLFORTransformer;

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

		Zalihe zalihe = this.dostupneVakcineClient.getDostupneVakcine();

		List<String> proizvodjaci = interesovanje.getProizvodjaci().getProizvodjac();

		// Check for available doses
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
		
		String poruka1 = "Po??tovani,\n Obave??tavamo Vas da je izvr??ena prijava za vakcinaciju protiv COVID-19 " 
				+ "uspe??no obavjelna. U narednih sedam dana ??ete dobiti elektronsku poruku sa datumom i vrmemenom termina. "
				+ "Unete informacije su slede??e \n Ime - " + interesovanje.getLicneInformacije().getIme().getValue()
				+ ", Prezime - " + interesovanje.getLicneInformacije().getPrezime().getValue()
				+ " , Jmbg - " + interesovanje.getLicneInformacije().getJmbg().getValue()
				+ " , Email - " + interesovanje.getLicneInformacije().getKontakt().getEmail().getValue()
				+ ", Broj mobilnog telefona - " + interesovanje.getLicneInformacije().getKontakt().getBrojMobilnog()
				+ ", Broj fiksnog telefona - " + interesovanje.getLicneInformacije().getKontakt().getBrojFiksnog()
				+ ", Lokacija primanja vakcine - " + interesovanje.getLokacijaPrimanjaVakcine().getValue()
				+ ", Odabrane vakcine - " + odabir
				+ ". \n S po??tovanjem, Institut za javno zdravlje Srbije.";
			com.example.demo.model.email.Email firstEmail = new com.example.demo.model.email.Email();
			firstEmail.setTo(interesovanje.getLicneInformacije().getKontakt().getEmail().getValue());
			firstEmail.setContent(poruka1);
			firstEmail.setSubject("Informacije o prijvai za vakcinacije protiv COVID-19");
			emailClient.sendMail(firstEmail);
			
		if(dostupno) {
			LocalDateTime termin = this.saglasnostService.pronadjiSlobodanTermin(1);
			DateTimeFormatter ft = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm");

			message = "Po??tovani, \n Obave??tavamo vas da je va?? termin za vakcinaciju protiv Covid-19 u "
					+ termin.format(ft) + ". \n   Dostupne vakcine su: " + message
					+ ".\n Molimo vas da popunite obrazac saglasnosti za vakcinaciju pre pocetka vaseg termina. Obrazac za saglasnost se nalazi na poralu. \\n S po??tovanjem, Institut za javno zdravlje Srbije.";

			com.example.demo.model.email.Email emailModel = new com.example.demo.model.email.Email();
			emailModel.setTo(interesovanje.getLicneInformacije().getKontakt().getEmail().getValue());
			emailModel.setContent(message);
			emailModel.setSubject("Informacije o terminu vakcinacije protiv COVID-19");
			emailClient.sendMail(emailModel);
			System.out.println(message);

			// Napravi saglasnost
			String ref = generateAndSaveGradjaninSaglasnost(interesovanje, odabir, termin);

			// Set seeAlso
			interesovanje.setSaglasnost(new Interesovanje.Saglasnost());
			interesovanje.getSaglasnost().setValue(ref);
			interesovanje.getSaglasnost().setProperty("pred:seeAlso");
			
			// Azuriraj zalihe
			this.dostupneVakcineClient.updateVakcine(zalihe);
		}

		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		ByteArrayOutputStream stream = new ByteArrayOutputStream();

		marshaller.marshal(interesovanje, stream);

		String finalString = new String(stream.toByteArray());
		System.out.println(finalString);

		content = finalString;

		repository.saveXML("interesovanje_" + documentId, collectionId, content);
		repository.saveRDF(content, "/lista_interesovanja"); 
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

		if (results.size() <= 0)
			return;
		JAXBContext context = JAXBContext.newInstance(Interesovanje.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();

		Zalihe zalihe = this.dostupneVakcineClient.getDostupneVakcine();

		results.forEach(result -> {
			try {

				XMLResource resource = pronadjiPoId(result);
				Interesovanje interesovanje = (Interesovanje) unmarshaller.unmarshal(resource.getContentAsDOM());

				if (!interesovanje.isDodeljenTermin() && interesovanje != null) {
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

					if (dostupno) {
						LocalDateTime termin = this.saglasnostService.pronadjiSlobodanTermin(1);
						DateTimeFormatter ft = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm");

						message = "Po??tovani, \n Obave??tavamo vas da je va?? termin za vakcinaciju protiv Covid-19 u "
								+ termin.format(ft) + ". \n   Dostupne vakcine su: " + message
								+ "\n Molimo vas da popunite obrazac saglasnosti za vakcinaciju pre pocetka vaseg termina. Obrazac za saglasnost se nalazi na poralu. \\n S po??tovanjem, Institut za javno zdravlje Srbije.";

						com.example.demo.model.email.Email emailModel = new com.example.demo.model.email.Email();
						emailModel.setTo(interesovanje.getLicneInformacije().getKontakt().getEmail().getValue());
						emailModel.setContent(message);
						emailModel.setSubject("Informacije o terminu vakcinacije protiv COVID-19");
						emailClient.sendMail(emailModel);
						System.out.println(message);

						// Napravi saglasnost
						String ref = generateAndSaveGradjaninSaglasnost(interesovanje, odabir, termin);
						
						// Set seeAlso
						interesovanje.setSaglasnost(new Interesovanje.Saglasnost());
						interesovanje.getSaglasnost().setValue(ref);
						interesovanje.getSaglasnost().setProperty("pred:seeAlso");
						
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
						repository.saveRDF(finalString, "/lista_interesovanja"); 
					}
				}
			} catch (Exception e) {

			}
		});
	}

	private String generateAndSaveGradjaninSaglasnost(Interesovanje interesovanje, String odabir, LocalDateTime termin)
			throws Exception {
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

		return "http://www.ftn.uns.ac.rs/xml_i_veb_servisi/obrazac_saglasnosti_za_imunizaciju/" + id;
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

	public List<DokumentDTO> getInteresovanjeAllByEmail(String email){
		try {
			String id = ((InteresovanjeRepository) repository).pronadjiPoEmailu(email);
			XMLResource res = pronadjiInteresovanjePoEmailu(email);
			System.out.println("natasa"+id);
			List<DokumentDTO> ret = new ArrayList<>();
			if (id != null) {

				JAXBContext context = JAXBContext
						.newInstance("com.example.demo.model.interesovanje");

				Unmarshaller unmarshaller = context.createUnmarshaller();

				Interesovanje s = (Interesovanje) unmarshaller.unmarshal((res).getContentAsDOM());

				ret.add(new DokumentDTO(id,s));
				return ret;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ArrayList<>();
	}

	public List<String> getAllInteresovanja() throws IOException {
		return this.repository.readAllDocumentIds(fusekiCollectionId);
	}

	public String pronadjiPoVremenskomPeriodu(String odDatum, String doDatum) throws DatatypeConfigurationException, IOException, ParseException, Exception {
		System.out.println("uslo u service");
		try{
			List<String> sviId = this.getAllInteresovanja();
			System.out.println("dobio sva interesovanja");
			SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
			XMLGregorianCalendar pocetak = DatatypeFactory.newInstance().newXMLGregorianCalendar(ft.format(ft.parse(odDatum)));
			XMLGregorianCalendar kraj = DatatypeFactory.newInstance().newXMLGregorianCalendar(ft.format(ft.parse(doDatum)));
			System.out.println("parsirao datume");
			int brojac = 0;
			for(String id : sviId){
				System.out.println("uslo u id");
				XMLResource res = this.pronadjiPoId(id);
				try {
					if (res != null) {

						JAXBContext context = JAXBContext.newInstance("com.example.demo.model.interesovanje");

						Unmarshaller unmarshaller = context.createUnmarshaller();

						Interesovanje interesovanje = (Interesovanje) unmarshaller
								.unmarshal((res).getContentAsDOM());

						if (interesovanje.getDatumPodnosenjaInteresovanja().getValue().compare(kraj) ==  DatatypeConstants.LESSER &&
								interesovanje.getDatumPodnosenjaInteresovanja().getValue().compare(pocetak) == DatatypeConstants.GREATER){
							brojac+=1;
						}
					}
				} catch (Exception e) {
					return "0";
				}
			}
			return String.valueOf(brojac);
		}
		catch (Exception e){
			return "0";
		}

	}

	public byte[] generateJson(String documentId) throws Exception {
		String about = "http://www.ftn.uns.ac.rs/xml_i_veb_servisi/interesovanje/" + documentId;
		String graphUri = "/lista_interesovanja";
		String documentNameId = "interesovanje_" + documentId;
		String filePath = "src/main/resources/static/json/" + documentNameId + ".json";
		((InteresovanjeRepository)repository).generateJson(documentNameId, graphUri, about);
		File file = new File(filePath);
		FileInputStream fileInputStream = new FileInputStream(file);

		return IOUtils.toByteArray(fileInputStream);
	}

	public byte[] generateRdf(String id) throws SAXException, IOException {
		String rdfFilePath = "src/main/resources/static/rdf/interesovanje_" + id + ".rdf";
		String xmlFilePath = "src/main/resources/static/xml/interesovanje_" + id + ".xml";
		MetadataExtractor metadataExtractor = new MetadataExtractor();
		String rs;
		FileWriter fw;
		try {
			XMLResource res = ((InteresovanjeRepository)repository).pronadjiPoId(id);
			rs = (String) res.getContent();
			fw = new FileWriter(xmlFilePath);
			fw.write(rs);
			fw.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			metadataExtractor.extractMetadata(
					new FileInputStream(new File(xmlFilePath)),
					new FileOutputStream(new File(rdfFilePath)));

			File file = new File(rdfFilePath);
			FileInputStream fileInputStream = new FileInputStream(file);

			return IOUtils.toByteArray(fileInputStream);

		} catch (Exception e) {
			e.printStackTrace();
			throw new BadRequestException("Error pri generisanju rdf interesovanja.");
		}
  }
  
	public String getInteresovanjeRefFromSeeAlso(String seeAlso) throws Exception {
		return ((InteresovanjeRepository) this.repository).pronadjiPoSaglasnostRef(seeAlso);
	}
}
