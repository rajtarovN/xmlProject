package com.example.demo.repository;

import java.util.ArrayList;
import org.springframework.stereotype.Repository;

@Repository
public class InteresovanjeRepository extends RepositoryInterface{
	
	public static final String SPARQL_FILE = "src/main/resources/static/sparql/interesovanje/";
	

	public String pronadjiPoEmailu(String email) throws Exception {

		ArrayList<String> params = new ArrayList<>();
		params.add("\"" + email + "\"");

		return this.fusekiManager.query("/lista_interesovanja", SPARQL_FILE + "interesovanje_email.rq", params).get(0);
	}
	
}
