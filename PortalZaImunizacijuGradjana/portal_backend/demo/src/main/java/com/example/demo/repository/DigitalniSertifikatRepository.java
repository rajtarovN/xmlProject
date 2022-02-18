package com.example.demo.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.util.ExistManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

@Repository
public class DigitalniSertifikatRepository extends RepositoryInterface {

	@Autowired
	private ExistManager existManager;

	private static final String TARGET_NAMESPACE = "http://www.ftn.uns.ac.rs/xml_i_veb_servisi/digitalni_zeleni_sertifikat";

	public static final String SPARQL_FILE = "src/main/resources/static/sparql/sertifikat/";

	private String collectionId = "/db/portal/lista_sertifikata";

	public XMLResource pronadjiPoId(String id) throws IllegalAccessException, JAXBException, InstantiationException,
			IOException, XMLDBException, ClassNotFoundException {
		return dbManager.readFileFromDB("sertifikat_" + id + ".xml", collectionId);
	}

	public List<String> pronadjiPoJmbg(String jmbg) throws Exception {
		List<String> ids = new ArrayList<>();
		ArrayList<String> params = new ArrayList<>();
		params.add("\"" + jmbg + "\"");

		ids = this.fusekiManager.query("/lista_sertifikata", SPARQL_FILE + "sertifikat_jmbg.rq", params);
		return ids;
	}

	public List<String> naprednaPretraga(String brojPasosa, String brojSertifikata, String datum, String ime,
			String jmbg, String prezime, boolean and) throws Exception {
		List<String> ids = new ArrayList<>();
		ArrayList<String> params = new ArrayList<>();
		params.add(brojPasosa);
		params.add(brojSertifikata);
		params.add(datum);
		params.add(ime);
		params.add(jmbg);
		params.add(prezime);
		if (and) {

			ids = this.fusekiManager.queryAbout("/lista_sertifikata", SPARQL_FILE + "sertifikat_sve_and.rq", params);

		} else {

			ids = this.fusekiManager.queryAbout("/lista_sertifikata", SPARQL_FILE + "sertifikat_sve_or.rq", params);
		}
		System.out.println(ids);
		return ids;
	}

	public ResourceSet obicnaPretraga(String searchTerm){
		String xPath = "//Digitalni_zeleni_sertifikat[contains(lower-case(Podaci_o_osobi/Ime), lower-case('" + searchTerm + "')) "
				+ " or contains(lower-case(Podaci_o_osobi/Prezime), lower-case('" + searchTerm + "')) "
				+ " or contains(Podaci_o_vakcinaciji/Vakcinacija/Tip, '" + searchTerm + "')"
				+ " or contains(Podaci_o_vakcinaciji/Vakcinacija/Proizvodjac, '" + searchTerm + "')"
				+ " or contains(Podaci_o_vakcinaciji/Vakcinacija/Serija, '" + searchTerm + "')"
				+ " or contains(Podaci_o_vakcinaciji/Vakcinacija/Zdravstvena_ustanova, '" + searchTerm + "')" +
				" ]";

		try {
			return this.existManager.retrieve(collectionId , xPath, TARGET_NAMESPACE);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BadRequestException("Doslo je do errora pri obicnoj pretrazi sertifikata.");
		}
	}

	public void generateJson(String documentNameId, String graphUri, String about) throws Exception {
		fusekiManager.generisiJSON(documentNameId, graphUri, about);
	}
}
