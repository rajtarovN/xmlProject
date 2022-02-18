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

}
