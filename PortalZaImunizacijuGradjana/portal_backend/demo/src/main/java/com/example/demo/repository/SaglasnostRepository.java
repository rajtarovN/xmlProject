package com.example.demo.repository;

import com.example.demo.exceptions.BadRequestException;
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

	public ResourceSet obicnaPretraga(String searchTerm){
		String xPath = "//Saglasnost[contains(lower-case(Pacijent/Licni_podaci/Ime), lower-case('" + searchTerm + "')) " +
				" or contains(lower-case(Pacijent/Licni_podaci/Prezime), lower-case('" + searchTerm + "')) " +
				" or contains(lower-case(Pacijent/Licni_podaci/Ime_roditelja), lower-case('" + searchTerm + "')) " +
				" or contains(lower-case(Pacijent/Licni_podaci/Mesto_rodjenja), lower-case('" + searchTerm + "')) " +
				" or contains(lower-case(Pacijent/Licni_podaci/Adresa/Mesto), lower-case('" + searchTerm + "')) " +
				" or contains(lower-case(Pacijent/Licni_podaci/Adresa/Grad), lower-case('" + searchTerm + "')) " +
				" or contains(lower-case(Pacijent/Licni_podaci/Kontakt_informacije/Email), lower-case('" + searchTerm + "')) " +
				" or contains(lower-case(Pacijent/Licni_podaci/Kontakt_informacije/Fiksni_telefon), lower-case('" + searchTerm + "')) " +
				" or contains(lower-case(Pacijent/Licni_podaci/Kontakt_informacije/Mobilni_telefon), lower-case('" + searchTerm + "')) " +
				" or contains(lower-case(Pacijent/Licni_podaci/Radni_status), lower-case('" + searchTerm + "')) " +
				" or contains(lower-case(Pacijent/Licni_podaci/Zanimanje_zaposlenog), lower-case('" + searchTerm + "')) " +
				" or contains(lower-case(Pacijent/Licni_podaci/Socijalna_zastita/Naziv_sedista), lower-case('" + searchTerm + "')) " +
				" or contains(lower-case(Pacijent/Licni_podaci/Socijalna_zastita/Opstina_sedista), lower-case('" + searchTerm + "')) " +
				" or contains(lower-case(Pacijent/Saglasnost_pacijenta/Naziv_imunoloskog_lekara), lower-case('" + searchTerm + "')) " +
				" or contains(lower-case(Evidencija_o_vakcinaciji/Zdravstvena_ustanova), lower-case('" + searchTerm + "')) " +
				" or contains(lower-case(Evidencija_o_vakcinaciji/Vakcinacijski_punkt), lower-case('" + searchTerm + "')) " +
				" or contains(lower-case(Evidencija_o_vakcinaciji/Lekar/Ime), lower-case('" + searchTerm + "')) " +
				" or contains(lower-case(Evidencija_o_vakcinaciji/Lekar/Prezime), lower-case('" + searchTerm + "')) " +
				" or contains(Evidencija_o_vakcinaciji/Vakcine/Vakcina/Naziv, '" + searchTerm + "') " +
				" or contains(Evidencija_o_vakcinaciji/Vakcine/Vakcina/Proizvodjac, '" + searchTerm + "') " +
				" or contains(Evidencija_o_vakcinaciji/Vakcine/Vakcina/Nezeljena_reakcija, '" + searchTerm + "') " +
				" or contains(lower-case(Evidencija_o_vakcinaciji/Vakcine/Privremene_kontraindikacije/Dijagnoza), lower-case('" + searchTerm + "')) " +
				" ]";

		try {
			return this.existManager.retrieve(collectionId , xPath, TARGET_NAMESPACE);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BadRequestException("Doslo je do errora pri obicnoj pretrazi saglasnosti.");
		}
	}

	public void generateJson(String documentNameId, String graphUri, String about) throws Exception {
		fusekiManager.generisiJSON(documentNameId, graphUri, about);
	}

}
