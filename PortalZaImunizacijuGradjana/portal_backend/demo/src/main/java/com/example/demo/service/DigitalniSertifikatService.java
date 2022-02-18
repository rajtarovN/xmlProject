package com.example.demo.service;

import static com.example.demo.util.PathConstants.DIGITALNISERTIFIKAT_XSL;
import static com.example.demo.util.PathConstants.DIGITALNISERTIFIKAT_XSL_FO;
import static com.example.demo.util.PathConstants.SAVE_HTML;
import static com.example.demo.util.PathConstants.SAVE_PDF;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.ListaSaglasnosti;
import com.example.demo.util.QRCodeService;
import org.exist.xmldb.EXistResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.example.demo.model.digitalni_zeleni_sertifikat.DigitalniZeleniSertifikat;
import com.example.demo.model.digitalni_zeleni_sertifikat.ListaSertifikata;
import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost;
import com.example.demo.model.zahtev_za_sertifikatom.ZahtevZaZeleniSertifikat;
import com.example.demo.repository.DigitalniSertifikatRepository;
import com.example.demo.util.XSLFORTransformer;

@Service
public class DigitalniSertifikatService extends AbstractService {

	private SaglasnostService saglasnostService;

	@Autowired
	public DigitalniSertifikatService(DigitalniSertifikatRepository digitalniSertifikatRepository,
			SaglasnostService saglasnostService) {

		super(digitalniSertifikatRepository, "/db/portal/lista_sertifikata", "/lista_sertifikata");

		this.saglasnostService = saglasnostService;
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
		String pdf_path = SAVE_PDF + "sertifikat_" + id.split(".xml")[0] + ".pdf";

		try {
			ok = transformer.generatePDF(doc_str, pdf_path, DIGITALNISERTIFIKAT_XSL_FO);
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
		String html_path = SAVE_HTML + "sertifikat_" + id + ".html";
		System.out.println(doc_str);

		try {
			ok = transformer.generateHTML(doc_str, html_path, DIGITALNISERTIFIKAT_XSL);
			if (ok)
				return html_path;
			else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String saveSertifikat(ZahtevZaZeleniSertifikat zahtev) throws Exception {
		String id = UUID.randomUUID().toString();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

		LocalDateTime now = LocalDateTime.now();
		XMLGregorianCalendar dateFormatted = DatatypeFactory.newInstance()
				.newXMLGregorianCalendar(now.format(formatter));

		DigitalniZeleniSertifikat sertifikat = new DigitalniZeleniSertifikat();
		sertifikat.setAbout("http://www.ftn.uns.ac.rs/xml_i_veb_servisi/digitalni_zeleni_sertifikat/" + id);
		sertifikat.setIdSertifikata(id);
		sertifikat.setDatum(dateFormatted);

		DigitalniZeleniSertifikat.PodaciOSertifikatu ps = new DigitalniZeleniSertifikat.PodaciOSertifikatu();
		DigitalniZeleniSertifikat.PodaciOSertifikatu.BrojSertifikata bs = new DigitalniZeleniSertifikat.PodaciOSertifikatu.BrojSertifikata();
		DigitalniZeleniSertifikat.PodaciOSertifikatu.DatumIVremeIzdavanja di = new DigitalniZeleniSertifikat.PodaciOSertifikatu.DatumIVremeIzdavanja();
		bs.setValue(id);
		bs.setProperty("pred:broj_sertifikata");
		di.setValue(dateFormatted);
		di.setProperty("pred:datum_izdavaja");
		ps.setDatumIVremeIzdavanja(di);
		ps.setBrojSertifikata(bs);
		sertifikat.setPodaciOSertifikatu(ps);

		DigitalniZeleniSertifikat.PodaciOOsobi osoba = new DigitalniZeleniSertifikat.PodaciOOsobi();
		DigitalniZeleniSertifikat.PodaciOOsobi.Ime ime = new DigitalniZeleniSertifikat.PodaciOOsobi.Ime();
		ime.setValue(zahtev.getPodnosilacZahteva().getIme().getValue());
		ime.setProperty("pred:ime");
		osoba.setIme(ime);

		DigitalniZeleniSertifikat.PodaciOOsobi.Prezime prezime = new DigitalniZeleniSertifikat.PodaciOOsobi.Prezime();
		prezime.setValue(zahtev.getPodnosilacZahteva().getPrezime().getValue());
		prezime.setProperty("pred:prezime");
		osoba.setPrezime(prezime);

		DigitalniZeleniSertifikat.PodaciOOsobi.Jmbg jmbg = new DigitalniZeleniSertifikat.PodaciOOsobi.Jmbg();
		jmbg.setValue(zahtev.getPodnosilacZahteva().getJmbg().getValue());
		jmbg.setProperty("pred:jmbg");
		osoba.setJmbg(jmbg);

		osoba.setPol(zahtev.getPodnosilacZahteva().getPol());
		osoba.setDatumRodjenja(zahtev.getPodnosilacZahteva().getDatumRodjenja());

		DigitalniZeleniSertifikat.PodaciOOsobi.BrojPasosa brojPasosa = new DigitalniZeleniSertifikat.PodaciOOsobi.BrojPasosa();
		brojPasosa.setValue(zahtev.getPodnosilacZahteva().getBrojPasosa().getValue());
		brojPasosa.setProperty("pred:broj_pasosa");
		osoba.setBrojPasosa(brojPasosa);

		sertifikat.setPodaciOOsobi(osoba);

		DigitalniZeleniSertifikat.PodaciOVakcinaciji podaciOVakcinaciji = new DigitalniZeleniSertifikat.PodaciOVakcinaciji();
		List<DigitalniZeleniSertifikat.PodaciOVakcinaciji.Vakcinacija> vakcinacije = new ArrayList<>();
		List<String> saglasnosti = saglasnostService.pronadjiSveSaglasnostiPoEmailu(zahtev.getEmail());
		for (String sid : saglasnosti) {
			Saglasnost saglasnost = saglasnostService.pronadjiPoId(sid);
			if (saglasnost.getEvidencijaOVakcinaciji() != null
					&& saglasnost.getEvidencijaOVakcinaciji().getVakcine() != null) {
				int size = saglasnost.getEvidencijaOVakcinaciji().getVakcine().getVakcina().size();
				Saglasnost.EvidencijaOVakcinaciji.Vakcine.Vakcina vakcina = saglasnost.getEvidencijaOVakcinaciji()
						.getVakcine().getVakcina().get(size - 1);
				DigitalniZeleniSertifikat.PodaciOVakcinaciji.Vakcinacija v = new DigitalniZeleniSertifikat.PodaciOVakcinaciji.Vakcinacija();
				v.setSerija(vakcina.getSerija());
				v.setProizvodjac(vakcina.getProizvodjac());
				v.setZdravstvenaUstanova(saglasnost.getEvidencijaOVakcinaciji().getZdravstvenaUstanova());
				v.setDatumDavanja(vakcina.getDatumDavanja());
				v.setBrDoze(vakcina.getDoza());
				v.setTip(vakcina.getNaziv());
				vakcinacije.add(v);
			}
		}
		podaciOVakcinaciji.setVakcinacija(vakcinacije);
		sertifikat.setPodaciOVakcinaciji(podaciOVakcinaciji);

		sertifikat.setQrKod(QRCodeService.getQRCode("http://localhost:4001/digitalni_zeleni_sertifikat/"+id));

		JAXBContext contextSaglasnost = JAXBContext.newInstance(DigitalniZeleniSertifikat.class);
		OutputStream os = new ByteArrayOutputStream();

		Marshaller marshaller = contextSaglasnost.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		marshaller.marshal(sertifikat, os);
		repository.saveXML("sertifikat_" + id, collectionId, os.toString());
		System.out.println(os.toString());
		repository.saveRDF(os.toString(), "/lista_sertifikata");

		return id;
	}

	public List<String> getAllSertifikati() throws IOException {
		return this.repository.readAllDocumentIds(fusekiCollectionId);
	}

	public List<String> naprednaPretraga(String brojPasosa, String brojSertifikata, String datum, String ime,
			String jmbg, String prezime, boolean and) throws Exception {

		return ((DigitalniSertifikatRepository) this.repository).naprednaPretraga(brojPasosa, brojSertifikata, datum,
				ime, jmbg, prezime, and);

	}

	public DigitalniZeleniSertifikat pronadjiPoId(String id) throws IllegalAccessException, InstantiationException,
			JAXBException, ClassNotFoundException, XMLDBException, IOException {
		XMLResource res = ((DigitalniSertifikatRepository) this.repository).pronadjiPoId(id);
		try {
			if (res != null) {

				JAXBContext context = JAXBContext.newInstance("com.example.demo.model.digitalni_zeleni_sertifikat");

				Unmarshaller unmarshaller = context.createUnmarshaller();

				DigitalniZeleniSertifikat s = (DigitalniZeleniSertifikat) unmarshaller
						.unmarshal((res).getContentAsDOM());

				return s;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	public String allXmlByEmail(String email) throws Exception {
		Saglasnost saglasnost = saglasnostService.pronadjiSaglasnostPoEmailu(email);
		List<String> ids = new ArrayList<>();
		List<DigitalniZeleniSertifikat> ret = new ArrayList<>();
		if (saglasnost != null) {
			if (saglasnost.getPacijent().getStraniDrzavljanin() != null
					&& saglasnost.getPacijent().getStraniDrzavljanin().getIdentifikacija() != null
					&& saglasnost.getPacijent().getStraniDrzavljanin().getIdentifikacija().getValue() != null) {
				ids = ((DigitalniSertifikatRepository) this.repository)
						.pronadjiPoJmbg(saglasnost.getPacijent().getStraniDrzavljanin().getIdentifikacija().getValue());
			} else {
				ids = ((DigitalniSertifikatRepository) this.repository)
						.pronadjiPoJmbg(saglasnost.getPacijent().getDrzavljaninSrbije().getJmbg().getValue());
			}

			if (!ids.isEmpty()) {
				for (String id : ids) {
					DigitalniZeleniSertifikat sertifikat = pronadjiPoId(id);
					if (sertifikat != null) {
						ret.add(sertifikat);
					}
				}
			}
		}
		ListaSertifikata lista = new ListaSertifikata();
		lista.setSertifikate(ret);
		JAXBContext context = JAXBContext.newInstance(ListaSertifikata.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		ByteArrayOutputStream stream = new ByteArrayOutputStream();

		marshaller.marshal(lista, stream);

		return stream.toString();
	}

	public XMLResource getXML(String documentId) throws IllegalAccessException, InstantiationException, JAXBException,
			ClassNotFoundException, XMLDBException, IOException {
		return ((DigitalniSertifikatRepository) this.repository).pronadjiPoId(documentId);
	}

	public List<com.example.sluzbenik_back.dto.DokumentDTO> getSertifikatiAllByEmail(String email) {
		try {
			System.out.println("OVDEEEEEE");
			String all = allXmlByEmail(email);

			JAXBContext context = JAXBContext.newInstance(ListaSaglasnosti.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			StringReader reader = new StringReader(all);
			ListaSertifikata saglasnosti = (ListaSertifikata) unmarshaller.unmarshal(reader);
			List<com.example.sluzbenik_back.dto.DokumentDTO> ret = new ArrayList<>();
			for (DigitalniZeleniSertifikat s : saglasnosti.getSertifikate()) {
				ret.add(new com.example.sluzbenik_back.dto.DokumentDTO(s));
			}
			System.out.println("OVDEEEEEE");
			return ret;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
	public List<String> obicnaPretraga(String searchTerm) throws Exception{
		List<String> filteredIds = new ArrayList<>();
		ResourceSet result = ((DigitalniSertifikatRepository) this.repository).obicnaPretraga(searchTerm);
		ResourceIterator i = result.getIterator();
		Resource res = null;
		JAXBContext context = JAXBContext.newInstance(DigitalniZeleniSertifikat.class);

		while (i.hasMoreResources()) {
			try {
				Unmarshaller unmarshaller = context.createUnmarshaller();
				res = i.nextResource();
				DigitalniZeleniSertifikat r = (DigitalniZeleniSertifikat) unmarshaller.unmarshal(((XMLResource) res).getContentAsDOM());

				String about = r.getAbout();

				filteredIds.add(about);
			} finally {
				try {
					((EXistResource) res).freeResources();
				} catch (XMLDBException xe) {
					xe.printStackTrace();
				}
			}
		}
		return filteredIds;
	}
}
