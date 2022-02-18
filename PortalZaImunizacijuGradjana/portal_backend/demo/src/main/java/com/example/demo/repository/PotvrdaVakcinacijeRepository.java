package com.example.demo.repository;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.util.DBManager;
import com.example.demo.util.ExistManager;
import com.example.demo.util.FusekiManager;
import org.exist.xupdate.XUpdateProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PotvrdaVakcinacijeRepository extends RepositoryInterface {

    private String collectionId = "/db/portal/lista_potvrda";

    private static final String TARGET_NAMESPACE = "http://www.ftn.uns.ac.rs/xml_i_veb_servisi/potvrda_o_vakcinaciji";

    public static final String APPEND = "<xu:modifications version=\"1.0\" xmlns:xu=\"" + XUpdateProcessor.XUPDATE_NS
            + "\" xmlns=\"" + TARGET_NAMESPACE + "\">" + "<xu:append select=\"%1$s\" child=\"last()\">%2$s</xu:append>"
            + "</xu:modifications>";

    public static final String UPDATE = "<xu:modifications version=\"1.0\" xmlns:xu=\"" + XUpdateProcessor.XUPDATE_NS
            + "\" xmlns=\"" + TARGET_NAMESPACE + "\">" + "<xu:update select=\"%1$s\">%2$s</xu:update>"
            + "</xu:modifications>";

    public static final String SPARQL_FILE = "src/main/resources/static/sparql/potvrda/";

    @Autowired
    private FusekiManager fusekiManager;

    @Autowired
    private DBManager dbManager;

    @Autowired
    private ExistManager existManager;

    public List<String> pronadjiPoJmbgIDatumu(String jmbg, String datumIzdavanja ) throws Exception {
        List<String> ids = new ArrayList<>();
        ArrayList<String> params = new ArrayList<>();
        params.add("\"" +jmbg+"\"");
        params.add("\"" + datumIzdavanja + "\"" );

        ids = this.fusekiManager.query("/lista_potvrda", SPARQL_FILE + "potvrda_jmbg_datum.rq", params);
        return ids;
    }

    public List<String> pronadjiPoEbsIDatumu(String ebs, String datumIzdavanja ) throws Exception {
        List<String> ids = new ArrayList<>();
        ArrayList<String> params = new ArrayList<>();
        params.add("\"" +ebs+"\"");
        params.add("\"" + datumIzdavanja + "\"" );

        ids = this.fusekiManager.query("/lista_potvrda", SPARQL_FILE + "potvrda_ebs_datum.rq", params);
        return ids;
    }

    public XMLResource pronadjiPoId(String id) throws IllegalAccessException, JAXBException, InstantiationException, IOException, XMLDBException, ClassNotFoundException {
        return dbManager.readFileFromDB("potvrda_"+id+".xml", collectionId);
    }

    public List<String> pronadjiPoJmbg(String jmbg ) throws Exception {
        List<String> ids = new ArrayList<>();
        ArrayList<String> params = new ArrayList<>();
        params.add("\"" +jmbg+"\"");

        ids = this.fusekiManager.query("/lista_potvrda", SPARQL_FILE + "potvrda_jmbg.rq", params);
        return ids;
    }

    public List<String> pronadjiPoEbs(String ebs) throws Exception {
        List<String> ids = new ArrayList<>();
        ArrayList<String> params = new ArrayList<>();
        params.add("\"" +ebs+"\"");

        ids = this.fusekiManager.query("/lista_potvrda", SPARQL_FILE + "potvrda_ebs.rq", params);
        return ids;
    }

    public ResourceSet obicnaPretraga(String searchTerm){
        String xPath = "//potvrda_o_vakcinaciji[contains(lower-case(licni_podaci/ime), lower-case('" + searchTerm + "')) "
                + " or contains(lower-case(licni_podaci/prezime), lower-case('" + searchTerm + "')) "
                + " or contains(vakcinacija/doze/doza/naziv_vakcine, '" + searchTerm + "')"
                + " or contains(vakcinacija/doze/doza/broj_serije, '" + searchTerm + "')"
                + " or contains(lower-case(zdravstvena_ustanova), lower-case('" + searchTerm + "')) "
                + " ]";

        try {
            return this.existManager.retrieve(collectionId , xPath, TARGET_NAMESPACE);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Doslo je do errora pri obicnoj pretrazi potvrda.");
        }
    }
    
	public String pronadjiPoSertifikatRef(String sertifikat) throws Exception {
		ArrayList<String> params = new ArrayList<>();
		params.add("\"" + sertifikat + "\"");

		List<String> found = this.fusekiManager.queryAbout("/lista_potvrda", SPARQL_FILE + "potvrda_ref_sertifikat.rq", params);
		if(!found.isEmpty()) {
			return found.get(0);
		}
		return null;
	}


    public void generateJson(String documentNameId, String graphUri, String about) throws Exception {
        fusekiManager.generisiJSON(documentNameId, graphUri, about);
    }
    
	public List<String> naprednaPretraga(String ime, String prezime, String id, String datum, boolean and) throws Exception {
		List<String> ids = new ArrayList<>();
		ArrayList<String> params = new ArrayList<>();
		params.add(ime);
		params.add(prezime);
		params.add(id);
		params.add(datum);
		if (and) {

			ids = this.fusekiManager.queryAbout("/lista_potvrda", SPARQL_FILE + "potvrda_sve_and.rq", params);

		} else {

			ids = this.fusekiManager.queryAbout("/lista_potvrda", SPARQL_FILE + "potvrda_sve_or.rq", params);
		}
		System.out.println(ids);
		return ids;
	}
}
