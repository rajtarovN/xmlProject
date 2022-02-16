package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.util.DBManager;
import com.example.demo.util.ExistManager;
import com.example.demo.util.FusekiManager;

@Repository
public class DigitalniSertifikatRepository extends RepositoryInterface {
	
	public static final String SPARQL_FILE = "src/main/resources/static/sparql/sertifikat/";

	
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
