package com.example.demo.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Repository;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

@Repository
public class DigitalniSertifikatRepository extends RepositoryInterface {

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

}
