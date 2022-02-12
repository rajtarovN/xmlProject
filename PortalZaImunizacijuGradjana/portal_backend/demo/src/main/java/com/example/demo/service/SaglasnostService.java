package com.example.demo.service;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.example.demo.dto.EvidencijaVakcinacijeDTO;
import com.example.demo.dto.EvidentiraneVakcineDTO;
import com.example.demo.dto.ListaEvidentiranihVakcina;
import com.example.demo.dto.SaglasnostDTO;
import com.example.demo.exceptions.ForbiddenException;
import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost;
import com.example.demo.repository.SaglasnostRepository;
import com.example.demo.util.DBManager;

@Service
public class SaglasnostService extends AbstractService {

	protected String collectionId;

	protected String fusekiCollectionId;

	@Autowired
	private SaglasnostRepository saglasnostRepository;

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
						Saglasnost saglasnost = this.pronadjiPoId(Long.parseLong(i));
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
			Saglasnost z = this.pronadjiPoId(Long.parseLong(i));
			lista.add(new SaglasnostDTO(z));
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
		for (String i : ids) {
			Saglasnost z = this.pronadjiPoId(Long.parseLong(i));
			if (!z.getEvidencijaOVakcinaciji().getVakcine().getVakcina().isEmpty()) {
				for (Saglasnost.EvidencijaOVakcinaciji.Vakcine.Vakcina vakcina : z.getEvidencijaOVakcinaciji()
						.getVakcine().getVakcina()) {
					lista.add(new EvidentiraneVakcineDTO(vakcina));
				}
			}
		}
		return lista;

	}

	public Saglasnost pronadjiPoId(long id) throws IllegalAccessException, InstantiationException, JAXBException,
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

	public Saglasnost pronadjiSaglasnostPoEmailu(String email) throws Exception {
		String id = this.saglasnostRepository.pronadjiPoEmailu(email).get(0);
		try {
			if (id != null) {

				return this.pronadjiPoId(Long.parseLong(id));

			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	public String dodajEvidenciju(EvidencijaVakcinacijeDTO evidencijaDTO, String brojSaglasnosti) {
		try {
			Saglasnost saglasnost = pronadjiPoId(Long.parseLong(brojSaglasnosti));

			Saglasnost.EvidencijaOVakcinaciji evidencija = new Saglasnost.EvidencijaOVakcinaciji();
			evidencija.setVakcinacijskiPunkt(evidencijaDTO.getVakcinacijskiPunkkt());
			evidencija.setZdravstvenaUstanova(evidencijaDTO.getZdravstvenaUstanova());
			
			Saglasnost.EvidencijaOVakcinaciji.Lekar lekar = new Saglasnost.EvidencijaOVakcinaciji.Lekar();
			lekar.setIme(evidencijaDTO.getImeLekara());
			lekar.setPrezime(evidencijaDTO.getPrezimeLekara());
			lekar.setTelefon(evidencijaDTO.getTelefonLekara());
			evidencija.setLekar(lekar);

			Saglasnost.EvidencijaOVakcinaciji.Vakcine vakcine = new Saglasnost.EvidencijaOVakcinaciji.Vakcine();
			vakcine.setOdlukaKomisijeZaTrajneKontraindikacije(evidencijaDTO.getOdlukaKomisije());
			Saglasnost.EvidencijaOVakcinaciji.Vakcine.PrivremeneKontraindikacije privremeneKontraindikacije = new Saglasnost.EvidencijaOVakcinaciji.Vakcine.PrivremeneKontraindikacije();
			privremeneKontraindikacije.setDijagnoza(evidencijaDTO.getDijagnoza());
			String FORMATER = "yyyy-MM-dd";
			DateFormat format = new SimpleDateFormat(FORMATER);
			Date date = format.parse(evidencijaDTO.getDatumUtvrdjivanja());
			XMLGregorianCalendar gDateFormatted = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(format.format(date));
			privremeneKontraindikacije.setDatumUtvrdjivanja(gDateFormatted);
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

	public String dodajEvidentiraneVakcine(ListaEvidentiranihVakcina evidentiraneVakcineDTO, String brojSaglasnosti){
        try {
            Saglasnost saglasnost = pronadjiPoId(Long.parseLong(brojSaglasnosti));
            Saglasnost.EvidencijaOVakcinaciji evidencija = saglasnost.getEvidencijaOVakcinaciji();
            Saglasnost.EvidencijaOVakcinaciji.Vakcine vakcine = saglasnost.getEvidencijaOVakcinaciji().getVakcine();
            List<Saglasnost.EvidencijaOVakcinaciji.Vakcine.Vakcina> listaVakcina = new ArrayList<>();
            int i = 0;

            for (EvidentiraneVakcineDTO vakcinaDto: evidentiraneVakcineDTO.getEvidentiraneVakcineDTO()) {
                i += 1;
                Saglasnost.EvidencijaOVakcinaciji.Vakcine.Vakcina vakcina =
                        new Saglasnost.EvidencijaOVakcinaciji.Vakcine.Vakcina();
                vakcina.setNaziv(vakcinaDto.getNazivVakcine());
                XMLGregorianCalendar result1 = DatatypeFactory.newInstance()
                        .newXMLGregorianCalendar(vakcinaDto.getDatumDavanja());
                vakcina.setDatumDavanja(result1);

                vakcina.setNacinDavanja("IM");
                if (vakcinaDto.getEkstremitet().equals("LR") ||
                        vakcinaDto.getEkstremitet().equals("DR"))
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

            JAXBContext context = JAXBContext
                    .newInstance("com.example.demo.model.obrazac_saglasnosti_za_imunizaciju");
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

}
