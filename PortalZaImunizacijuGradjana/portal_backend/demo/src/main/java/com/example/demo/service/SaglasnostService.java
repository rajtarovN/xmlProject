package com.example.demo.service;

import com.example.demo.client.DostupneVakcineClient;
import com.example.demo.dto.EvidencijaVakcinacijeDTO;
import com.example.demo.dto.EvidentiraneVakcineDTO;
import com.example.demo.dto.ListaEvidentiranihVakcina;
import com.example.demo.dto.SaglasnostDTO;
import com.example.demo.exceptions.ForbiddenException;
import com.example.demo.model.dostupne_vakcine.Zalihe;
import com.example.demo.model.dostupne_vakcine.Zalihe.Vakcina;
import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.ListaSaglasnosti;
import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost;
import com.example.demo.repository.SaglasnostRepository;
import com.example.demo.util.DBManager;
import com.example.demo.util.XSLFORTransformer;
import org.apache.commons.io.input.ReaderInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.*;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static com.example.demo.util.PathConstants.SAGLASNOST_XSL_FO;
import static com.example.demo.util.PathConstants.SAVE_PDF;

@Service
public class SaglasnostService extends AbstractService {

	protected String collectionId;

	protected String fusekiCollectionId;

	@Autowired
	private SaglasnostRepository saglasnostRepository;

	@Autowired
	private PotvrdaVakcinacijeService potvrdaVakcinacijeService;

	@Autowired
	private DostupneVakcineClient dostupneVakcineClient;

	@Autowired
	private DBManager dbManager;

	@Autowired
	public SaglasnostService(SaglasnostRepository saglasnostRepository) {
		super(saglasnostRepository, "/db/portal/lista_saglasnosti", "/lista_saglasnosti");
	}

	// Funkcija pronalazi prvi slobodan termin za vakcinaciju pocevsi od sutra u 8h
	// do 20h, na svakih 5 minuta
	public LocalDateTime pronadjiSlobodanTermin() throws NumberFormatException, IllegalAccessException,
			InstantiationException, ClassNotFoundException, JAXBException, XMLDBException, IOException {
		LocalDate date = LocalDate.now().plusDays(1);
		List<String> ids = new ArrayList<>();

		DateTimeFormatter ft = DateTimeFormatter.ofPattern("YYYY-MM-dd");
		while (true) {
			try {
				ids = this.saglasnostRepository.pretragaPoTerminu(ft.format(date));

				LocalTime time = LocalTime.of(8, 0);
				Boolean flagTimeTaken = false;
				while (true) {
					for (String i : ids) {
						Saglasnost saglasnost = this.pronadjiPoId(i);
						LocalTime foundTime = LocalTime.parse(saglasnost.getVremeTermina().toString());

						if (foundTime.equals(time)) {
							flagTimeTaken = true;
							break;
						}
					}
					if (flagTimeTaken) {
						flagTimeTaken = false;
						if (time.isAfter(LocalTime.of(19, 55))) {
							time = LocalTime.of(8, 0);
							date = date.plusDays(1);
						} else {
							time = time.plusMinutes(5);
						}

					} else {
						System.out.println(LocalDateTime.of(date, time).toString());
						return LocalDateTime.of(date, time);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Saglasnost pronadjiSaglasnostPoEmailu(String email) throws Exception {
		String id = this.saglasnostRepository.pronadjiPoEmailu(email).get(0);
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

	public String pronadjiZadnjuSaglasnost(String email) throws Exception {
		List<String> ids = this.saglasnostRepository.pronadjiPoEmailu(email);
		System.out.println(ids);
		try {
			if (ids != null && ids.size() >= 1) {
				if (ids.size() > 1) {
					XMLGregorianCalendar maxDate = null;
					Saglasnost saglasnost = null;
					for (String id : ids) {
						Saglasnost tempSaglasnost = pronadjiPoId(id);
						XMLGregorianCalendar currentDate = saglasnost.getPacijent().getDatum().getValue();
						if (maxDate == null || currentDate.compare(maxDate) > 1) {
							maxDate = currentDate;
							saglasnost = tempSaglasnost;
						}
					}
					if (maxDate.toGregorianCalendar().compareTo(new GregorianCalendar()) < 1) {
						return null; // Date has passed
					}
					return marshal(saglasnost);
				} else {
					return marshal(pronadjiPoId(ids.get(0)));

				}

			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	public ArrayList<SaglasnostDTO> pretragaTermina(String imePrezime, Date datumTermina)
			throws IllegalAccessException, InstantiationException, JAXBException, IOException, XMLDBException,
			ClassNotFoundException, ParseException, DatatypeConfigurationException {
		List<String> ids = new ArrayList<>();
		if (datumTermina == null) {
			datumTermina = new Date();
		}
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");

		try {
			ids = this.saglasnostRepository.pretragaTermina(imePrezime, ft.format(datumTermina));
		} catch (Exception e) {
			e.printStackTrace();
		}

		ArrayList<SaglasnostDTO> lista = new ArrayList<>();
		for (String i : ids) {
			Saglasnost z = this.pronadjiPoId(i);
			if (z.getEvidencijaOVakcinaciji() != null && z.getEvidencijaOVakcinaciji().getVakcine() != null
					&& z.getEvidencijaOVakcinaciji().getVakcine().getVakcina().isEmpty()) {
				SaglasnostDTO dto = new SaglasnostDTO(z);
				dto.setPrimioDozu(false);
				dto.setDobioPotvrdu(false);
				lista.add(dto);
			} else {
				boolean vaxxed = false;
				SaglasnostDTO dto = new SaglasnostDTO(z);
				dto.setPrimioDozu(false);
				dto.setDobioPotvrdu(false);
				if (z.getEvidencijaOVakcinaciji() != null && z.getEvidencijaOVakcinaciji().getVakcine() != null) {
					for (Saglasnost.EvidencijaOVakcinaciji.Vakcine.Vakcina vakcina : z.getEvidencijaOVakcinaciji()
							.getVakcine().getVakcina()) {
						if (vakcina.getDatumDavanja().toString()
								.equals(z.getPacijent().getDatum().getValue().toString())) {
							vaxxed = true;
							break;
						}
					}
					if (vaxxed) {
						dto.setPrimioDozu(true);
						List<String> potvrdeIds = new ArrayList<>();
						if (z.getPacijent().getDrzavljaninSrbije() != null) {
							potvrdeIds = potvrdaVakcinacijeService.pronadjiPoJmbgIDatumu(
									z.getPacijent().getDrzavljaninSrbije().getJmbg().getValue(), datumTermina);
						} else if (z.getPacijent().getStraniDrzavljanin() != null) {
							potvrdeIds = potvrdaVakcinacijeService.pronadjiPoEbsIDatumu(
									z.getPacijent().getStraniDrzavljanin().getIdentifikacija().getValue(),
									datumTermina);
						}
						if (!potvrdeIds.isEmpty()) {
							dto.setDobioPotvrdu(true);
						}
					}
				}

				lista.add(dto);
			}
		}
		return lista;
	}

	public ArrayList<EvidentiraneVakcineDTO> pronadjiPoEmailu(String email) throws IllegalAccessException,
			InstantiationException, JAXBException, IOException, XMLDBException, ClassNotFoundException {
		List<String> ids = new ArrayList<>();
		try {
			ids = this.saglasnostRepository.pronadjiPoEmailu(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ArrayList<EvidentiraneVakcineDTO> lista = new ArrayList<>();
		XMLGregorianCalendar date1 = null;
		for (String i : ids) {
			Saglasnost z = this.pronadjiPoId(i);
			XMLGregorianCalendar date2 = z.getPacijent().getDatum().getValue();
			if (z.getEvidencijaOVakcinaciji() != null && z.getEvidencijaOVakcinaciji().getVakcine() != null) {
				if (date1 == null) {
					date1 = date2;
					lista = new ArrayList<>();
					if (!z.getEvidencijaOVakcinaciji().getVakcine().getVakcina().isEmpty()) {
						for (Saglasnost.EvidencijaOVakcinaciji.Vakcine.Vakcina vakcina : z.getEvidencijaOVakcinaciji()
								.getVakcine().getVakcina()) {
							lista.add(new EvidentiraneVakcineDTO(vakcina));
						}
					}
				} else if (date2.toGregorianCalendar().compareTo(date1.toGregorianCalendar()) > 0) {
					lista = new ArrayList<>();
					date1 = date2;
					if (!z.getEvidencijaOVakcinaciji().getVakcine().getVakcina().isEmpty()) {
						for (Saglasnost.EvidencijaOVakcinaciji.Vakcine.Vakcina vakcina : z.getEvidencijaOVakcinaciji()
								.getVakcine().getVakcina()) {
							lista.add(new EvidentiraneVakcineDTO(vakcina));
						}
					}
				}
			}

		}
		return lista;

	}

	public Saglasnost pronadjiPoId(String id) throws IllegalAccessException, InstantiationException, JAXBException,
			ClassNotFoundException, XMLDBException, IOException {
		XMLResource res = this.saglasnostRepository.pronadjiPoId(id);
		try {
			if (res != null) {

				JAXBContext context = JAXBContext
						.newInstance("com.example.demo.model.obrazac_saglasnosti_za_imunizaciju");

				Unmarshaller unmarshaller = context.createUnmarshaller();

				Saglasnost s = (Saglasnost) unmarshaller.unmarshal((res).getContentAsDOM());

				return s;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	public String dodajEvidenciju(EvidencijaVakcinacijeDTO evidencijaDTO, String brojSaglasnosti) {
		try {
			Saglasnost saglasnost = pronadjiPoId(brojSaglasnosti);

			Saglasnost.EvidencijaOVakcinaciji evidencija = new Saglasnost.EvidencijaOVakcinaciji();
			evidencija.setVakcinacijskiPunkt(evidencijaDTO.getVakcinacijskiPunkkt());
			evidencija.setZdravstvenaUstanova(evidencijaDTO.getZdravstvenaUstanova());

			Saglasnost.EvidencijaOVakcinaciji.Lekar lekar = new Saglasnost.EvidencijaOVakcinaciji.Lekar();
			lekar.setIme(evidencijaDTO.getImeLekara());
			lekar.setPrezime(evidencijaDTO.getPrezimeLekara());
			lekar.setTelefon(evidencijaDTO.getTelefonLekara());
			evidencija.setLekar(lekar);

			Saglasnost.EvidencijaOVakcinaciji.Vakcine vakcine = new Saglasnost.EvidencijaOVakcinaciji.Vakcine();
			if (evidencijaDTO.getOdlukaKomisije() == null) {
				vakcine.setOdlukaKomisijeZaTrajneKontraindikacije("");
			} else
				vakcine.setOdlukaKomisijeZaTrajneKontraindikacije(evidencijaDTO.getOdlukaKomisije());
			Saglasnost.EvidencijaOVakcinaciji.Vakcine.PrivremeneKontraindikacije privremeneKontraindikacije = new Saglasnost.EvidencijaOVakcinaciji.Vakcine.PrivremeneKontraindikacije();

			if (evidencijaDTO.getDatumUtvrdjivanja() != null) {
				String FORMATER = "yyyy-MM-dd";
				DateFormat format = new SimpleDateFormat(FORMATER);
				Date date = format.parse(evidencijaDTO.getDatumUtvrdjivanja());
				XMLGregorianCalendar gDateFormatted = DatatypeFactory.newInstance()
						.newXMLGregorianCalendar(format.format(date));
				privremeneKontraindikacije.setDatumUtvrdjivanja(gDateFormatted);
				privremeneKontraindikacije.setDijagnoza(evidencijaDTO.getDijagnoza());
			} else {
				privremeneKontraindikacije.setDijagnoza("");
				privremeneKontraindikacije.setDatumUtvrdjivanja(null);
			}
			vakcine.setPrivremeneKontraindikacije(privremeneKontraindikacije);

			evidencija.setVakcine(vakcine);
			saglasnost.setEvidencijaOVakcinaciji(evidencija);

			String documentId = "saglasnost_" + brojSaglasnosti;
			String collectionId = "/db/portal/lista_saglasnosti";

			JAXBContext context = JAXBContext.newInstance("com.example.demo.model.obrazac_saglasnosti_za_imunizaciju");
			OutputStream os = new ByteArrayOutputStream();

			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			marshaller.marshal(saglasnost, os);
			dbManager.saveFileToDB(documentId, collectionId, os.toString());
			return "Uspesno sacuvana evidencija o vakcinaciji.";
		} catch (Exception e) {
			e.printStackTrace();
			throw new ForbiddenException("Error se desio pri cuvanju evidencije o vakcinaciji.");
		}
	}

	public String dodajEvidentiraneVakcine(ListaEvidentiranihVakcina evidentiraneVakcineDTO, String brojSaglasnosti) {
		try {
			Saglasnost saglasnost = pronadjiPoId(brojSaglasnosti);
			Saglasnost.EvidencijaOVakcinaciji evidencija = saglasnost.getEvidencijaOVakcinaciji();
			Saglasnost.EvidencijaOVakcinaciji.Vakcine vakcine = saglasnost.getEvidencijaOVakcinaciji().getVakcine();
			List<Saglasnost.EvidencijaOVakcinaciji.Vakcine.Vakcina> listaVakcina = new ArrayList<>();
			int i = 0;

			for (EvidentiraneVakcineDTO vakcinaDto : evidentiraneVakcineDTO.getEvidentiraneVakcineDTO()) {
				i += 1;
				Saglasnost.EvidencijaOVakcinaciji.Vakcine.Vakcina vakcina = new Saglasnost.EvidencijaOVakcinaciji.Vakcine.Vakcina();
				vakcina.setNaziv(vakcinaDto.getNazivVakcine());
				XMLGregorianCalendar result1 = DatatypeFactory.newInstance()
						.newXMLGregorianCalendar(vakcinaDto.getDatumDavanja());
				vakcina.setDatumDavanja(result1);

				vakcina.setNacinDavanja("IM");
				if (vakcinaDto.getEkstremitet().equals("LR") || vakcinaDto.getEkstremitet().equals("DR"))
					vakcina.setEkstremiter(vakcinaDto.getEkstremitet().equals("LR") ? "leva ruka" : "desna ruka");
				else
					vakcina.setEkstremiter(vakcinaDto.getEkstremitet());
				vakcina.setSerija(vakcinaDto.getSerijaVakcine());
				vakcina.setProizvodjac(vakcinaDto.getProizvodjac());
				vakcina.setNezeljenaReakcija(vakcinaDto.getNezeljenaReakcija());
				vakcina.setDoza(BigInteger.valueOf(i));
				listaVakcina.add(vakcina);

			}
			vakcine.setVakcina(listaVakcina);
			evidencija.setVakcine(vakcine);
			saglasnost.setEvidencijaOVakcinaciji(evidencija);

			String documentId = "saglasnost_" + brojSaglasnosti;
			String collectionId = "/db/portal/lista_saglasnosti";

			JAXBContext context = JAXBContext.newInstance("com.example.demo.model.obrazac_saglasnosti_za_imunizaciju");
			OutputStream os = new ByteArrayOutputStream();

			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			marshaller.marshal(saglasnost, os);
			dbManager.saveFileToDB(documentId, collectionId, os.toString());

			return "Uspesno sacuvane evidentirane vakcine.";
		} catch (Exception e) {
			e.printStackTrace();
			throw new ForbiddenException("Error se desio pri cuvanju evidentirane vakcine.");
		}
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
		String pdf_path = SAVE_PDF + "saglasnost_" + id.split(".xml")[0] + ".pdf";

		try {
			ok = transformer.generatePDF(doc_str, pdf_path, SAGLASNOST_XSL_FO);
			if (ok)
				return pdf_path;
			else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private String marshal(Saglasnost saglasnost) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Saglasnost.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		ByteArrayOutputStream stream = new ByteArrayOutputStream();

		marshaller.marshal(saglasnost, stream);

		String finalString = new String(stream.toByteArray());
		System.out.println(finalString);

		return finalString;
	}

	public void saveXML(String documentId, String content, String vakcine) throws Exception {

		InputStream inputStream = new ReaderInputStream(new StringReader(content));

		JAXBContext context = JAXBContext.newInstance(Saglasnost.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();

		Saglasnost saglasnost = (Saglasnost) unmarshaller.unmarshal(inputStream);

		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		ByteArrayOutputStream stream = new ByteArrayOutputStream();

		marshaller.marshal(saglasnost, stream);

		String finalString = new String(stream.toByteArray());
		System.out.println(finalString);

		content = finalString;

		Zalihe zalihe = this.dostupneVakcineClient.getDostupneVakcine();

		String[] temp = vakcine.split(",");
		List<String> proizvodjaci = new ArrayList<String>();
		for (String t : temp) {
			proizvodjaci.add(t);
		}
		String odabrana = saglasnost.getOdabraneVakcine();

		Boolean azuriraj = false;
		// Azuriraj zalihe - umanji reervacije
		for (Vakcina zaliha : zalihe.getVakcina()) {

			if (proizvodjaci.contains(zaliha.getNaziv()) && zaliha.getNaziv().compareTo(odabrana) != 0) {
				zaliha.setRezervisano(zaliha.getRezervisano() - 1);
				azuriraj = true;
			}
		}
		if(azuriraj) this.dostupneVakcineClient.updateVakcine(zalihe);

		this.saveXML("saglasnost_" + documentId, content);
		System.out.println(content);
		this.saveRDF(content, "/saglasnost");

	}
	
	public void deleteRDF(String documentId) throws IOException {
		repository.deleteRDF(documentId, "/lista_saglasnosti", "http://www.ftn.uns.ac.rs/xml_i_veb_servisi/obrazac_saglasnosti_za_imunizaciju/");

  }
  
	public String allXmlByEmail(String email) throws IllegalAccessException, InstantiationException, JAXBException, IOException, XMLDBException, ClassNotFoundException, DatatypeConfigurationException {
		List<String> ids = new ArrayList<>();
		try {
			ids = this.saglasnostRepository.pronadjiPoEmailu(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Saglasnost> onlyValid = new ArrayList<>();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		XMLGregorianCalendar currentDate = DatatypeFactory.newInstance()
				.newXMLGregorianCalendar(ft.format(new Date()));

		if(!ids.isEmpty()) {
			for (String i : ids) {
				Saglasnost z = this.pronadjiPoId(i);
				XMLGregorianCalendar date = z.getPacijent().getDatum().getValue();
				if (!((z.getEvidencijaOVakcinaciji() == null || z.getEvidencijaOVakcinaciji().getVakcine() == null)
						&& date.toGregorianCalendar().compareTo(currentDate.toGregorianCalendar()) < 0)) {
					onlyValid.add(z);
				}
			}
		}
		ListaSaglasnosti saglasnosti = new ListaSaglasnosti();
		saglasnosti.setSaglasnosti(onlyValid);
		JAXBContext context = JAXBContext.newInstance(ListaSaglasnosti.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		ByteArrayOutputStream stream = new ByteArrayOutputStream();

		marshaller.marshal(saglasnosti, stream);

		return stream.toString();
	}

	public XMLResource getXML(String documentId) throws IllegalAccessException, InstantiationException, JAXBException, ClassNotFoundException, XMLDBException, IOException {
		return this.saglasnostRepository.pronadjiPoId(documentId);
	}
}