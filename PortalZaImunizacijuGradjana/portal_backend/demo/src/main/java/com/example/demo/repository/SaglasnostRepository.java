package com.example.demo.repository;

import com.example.demo.util.DBManager;
import com.example.demo.util.ExistManager;
import com.example.demo.util.FusekiManager;
import com.example.demo.util.MetadataExtractor;
import org.exist.xupdate.XUpdateProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SaglasnostRepository extends RepositoryInterface {

	private String collectionId = "/db/portal/lista_saglasnosti";

	private static final String ID_STRING = "http://www.ftn.uns.ac.rs/xml_i_veb_servisi/obrazac_saglasnosti_za_imunizaciju/";

	private static final String TARGET_NAMESPACE = "http://www.ftn.uns.ac.rs/xml_i_veb_servisi/obrazac_saglasnosti_za_imunizaciju";

	public static final String APPEND = "<xu:modifications version=\"1.0\" xmlns:xu=\"" + XUpdateProcessor.XUPDATE_NS
			+ "\" xmlns=\"" + TARGET_NAMESPACE + "\">" + "<xu:append select=\"%1$s\" child=\"last()\">%2$s</xu:append>"
			+ "</xu:modifications>";

	public static final String UPDATE = "<xu:modifications version=\"1.0\" xmlns:xu=\"" + XUpdateProcessor.XUPDATE_NS
			+ "\" xmlns=\"" + TARGET_NAMESPACE + "\">" + "<xu:update select=\"%1$s\">%2$s</xu:update>"
			+ "</xu:modifications>";

	public static final String SPARQL_FILE = "src/main/resources/static/sparql/saglasnost/";

	@Autowired
	private ExistManager existManager;

	@Autowired
	private FusekiManager fusekiManager;

	@Autowired
	private DBManager dbManager;

	public List<String> pretragaTermina(String imePrezime, String datumTermina) throws Exception {
		List<String> ids = new ArrayList<>();

		if (imePrezime != null && datumTermina != null) {
			// po imePrezime i datumTermina
			ArrayList<String> params = new ArrayList<>();
			params.add("\"" + datumTermina + "\"");
			params.add("\"" + imePrezime + "\"");

			ids = this.fusekiManager.query("/lista_saglasnosti", SPARQL_FILE + "saglasnost_datum_imeprezime.rq",
					params);
			return ids;
		} else {
			if (datumTermina != null) {
				// samo po datumu
				ArrayList<String> params = new ArrayList<>();
				params.add("\"" + datumTermina + "\"");

				ids = this.fusekiManager.query("/lista_saglasnosti", SPARQL_FILE + "saglasnost_datum.rq", params);
				return ids;

			}
		}
		return ids;
	}

	public List<String> pretragaPoTerminu(String danas) throws Exception {
		List<String> ids = new ArrayList<>();

		// Find saglasnost with last date
		if (danas != null) {

			ArrayList<String> params = new ArrayList<>();
			params.add("\"" + danas + "\"");

			ids = this.fusekiManager.query("/lista_saglasnosti", SPARQL_FILE + "saglasnost_datum.rq", params);
			return ids;

		}
		return ids;
	}

	public List<String> pronadjiPoEmailu(String email) throws Exception {
		List<String> ids = new ArrayList<>();
		ArrayList<String> params = new ArrayList<>();
		params.add("\"" + email + "\"");

		ids = this.fusekiManager.query("/lista_saglasnosti", SPARQL_FILE + "saglasnost_email.rq", params);
		return ids;
	}

	public XMLResource pronadjiPoId(String id) throws IllegalAccessException, JAXBException, InstantiationException,
			IOException, XMLDBException, ClassNotFoundException {
		return dbManager.readFileFromDB("saglasnost_" + id + ".xml", collectionId);
	}

	public void saveRDF(String content, String uri) throws Exception {
		InputStream in = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		MetadataExtractor extractor = new MetadataExtractor();
		extractor.extractMetadata(in, out);

		String rdfAsString = new String(out.toByteArray());
		InputStream rdfInputStream = new ByteArrayInputStream(rdfAsString.getBytes());
		fusekiManager.writeFuseki(rdfInputStream, uri);
	}

	public List<String> naprednaPretraga(String ime, String prezime, String jmbg, String datum, String email,
			boolean and) throws Exception {
		List<String> ids = new ArrayList<>();
		ArrayList<String> params = new ArrayList<>();
		params.add(ime);
		params.add(prezime);
		params.add(jmbg);
		params.add(datum);
		params.add(email);
		if (and) {

			ids = this.fusekiManager.queryAbout("/lista_saglasnosti", SPARQL_FILE + "saglasnost_sve_and.rq", params);

		} else {

			ids = this.fusekiManager.queryAbout("/lista_saglasnosti", SPARQL_FILE + "saglasnost_sve_or.rq", params);
		}
		System.out.println(ids);
		return ids;
	}

	public boolean obicnaPretraga(String documentId, String searchTerm){
		String xPath =  "//Saglasnost[contains(Pacijent/Licni_podaci/Kontakt_informacije/Email, '" + searchTerm + "')]" ;
				/*"contains(Ime, '" + searchTerm + "') or " +
				" contains(Prezime, '" + searchTerm + "') or contains(Ime_roditelja, '" + searchTerm + "') or " +
				" contains(Mesto_rodjenja, '" + searchTerm + "') or contains(Adresa/Mesto, '" + searchTerm + "') or " +
				" contains(Adresa/Grad, '" + searchTerm + "') or contains(Kontakt_informacije/Email, '" + searchTerm + "') or ]";*/
		/*
		String xPath = "/ns2:Saglasnost[ns2:Pacijent/ns2:Licni_podaci/ns2:Ime[contains(., '" + searchTerm + "')]"
				+ " or Pacijent/Licni_podaci/Prezime[contains(., '" + searchTerm + "')]"
				+ " or Pacijent/Licni_podaci/Ime_roditelja[contains(., '" + searchTerm + "')]"
				+ " or Pacijent/Licni_podaci/Mesto_rodjenja[contains(., '" + searchTerm + "')]"
				+ " or Pacijent/Licni_podaci/Adresa/Mesto[contains(., '" + searchTerm + "')]"
				+ " or Pacijent/Licni_podaci/Adresa/Grad[contains(., '" + searchTerm + "')]"
				+ " or Pacijent/Licni_podaci/Kontakt_informacije/Email[contains(., '" + searchTerm + "')]"
				+ " or Pacijent/Licni_podaci/Radni_status[contains(., '" + searchTerm + "')]"
				+ " or Pacijent/Licni_podaci/Zanimanje_zaposlenog[contains(., '" + searchTerm + "')]"
				+ " or Pacijent/Licni_podaci/Socijalna_zastita/Naziv_sedista[contains(., '" + searchTerm + "')]"
				+ " or Pacijent/Licni_podaci/Socijalna_zastita/Opstina_sedista[contains(., '" + searchTerm + "')]"
				+ " or Pacijent/Saglasnost_pacijenta/Naziv_imunoloskog_lekara[contains(., '" + searchTerm + "')]"
				+ " or Evidencija_o_vakcinaciji/Zdravstvena_ustanova[contains(., '" + searchTerm + "')]"
				+ " or Evidencija_o_vakcinaciji/Vakcinacijski_punkt[contains(., '" + searchTerm + "')]"
				+ " or Evidencija_o_vakcinaciji/Lekar/Ime[contains(., '" + searchTerm + "')]"
				+ " or Evidencija_o_vakcinaciji/Lekar/Prezime[contains(., '" + searchTerm + "')]"
				+ " or Evidencija_o_vakcinaciji/Vakcine/Vakcina/Naziv[contains(., '" + searchTerm + "')]"
				+ " or Evidencija_o_vakcinaciji/Vakcine/Vakcina/Proizvodjac[contains(., '" + searchTerm + "')]"
				+ " or Evidencija_o_vakcinaciji/Vakcine/Vakcina/Nezeljena_reakcija[contains(., '" + searchTerm + "')]"
				+ " or Evidencija_o_vakcinaciji/Vakcine/Privremene_kontraindikacije/Dijagnoza[contains(., '" + searchTerm + "')]";*/

		/* DIGITALNI SERTIFIKAT
		String xPath = "/Digitalni_zeleni_sertifikat[Podaci_o_osobi/Ime[contains(., '" + searchTerm + "')]"
				+ " or Podaci_o_osobi/Prezime[contains(., '" + searchTerm + "')]"
				+ " or Podaci_o_vakcinaciji/Vakcinacija/Tip[contains(., '" + searchTerm + "')]"
				+ " or Podaci_o_vakcinaciji/Vakcinacija/Proizvodjac[contains(., '" + searchTerm + "')]"
				+ " or Podaci_o_vakcinaciji/Vakcinacija/Zdravstvena_ustanova[contains(., '" + searchTerm + "')]";*/

		try {//+ "/" + documentId
			return this.existManager.retrieve(collectionId , xPath, TARGET_NAMESPACE).getSize() != 0;
		} catch (Exception e) {
			return false;
		}
	}
}
