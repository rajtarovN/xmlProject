package com.example.demo.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import javax.xml.bind.JAXBException;

@Repository
public class InteresovanjeRepository extends RepositoryInterface {

	private String collectionId = "/db/portal/lista_interesovanja";

	public static final String SPARQL_FILE = "src/main/resources/static/sparql/interesovanje/";

	public String pronadjiPoEmailu(String email) throws Exception {

		ArrayList<String> params = new ArrayList<>();
		params.add("\"" + email + "\"");

		List<String> result = this.fusekiManager.query("/lista_interesovanja", SPARQL_FILE + "interesovanje_email.rq",
				params);
		if (result.size() > 0)
			return result.get(0);
		return null;
	}

	public List<String> getInteresovanjeOrderByAsc() throws Exception {
		ArrayList<String> params = new ArrayList<>();
		return this.fusekiManager.query("/lista_interesovanja", SPARQL_FILE + "interesovanje_sort.rq", params);
	}

	public XMLResource pronadjiPoId(String id) throws IllegalAccessException, JAXBException, InstantiationException,
			IOException, XMLDBException, ClassNotFoundException {
		return dbManager.readFileFromDB("interesovanje_" + id + ".xml", collectionId);
	}

	public void generateJson(String documentNameId, String graphUri, String about) throws Exception {
		fusekiManager.generisiJSON(documentNameId, graphUri, about);
	}
}
