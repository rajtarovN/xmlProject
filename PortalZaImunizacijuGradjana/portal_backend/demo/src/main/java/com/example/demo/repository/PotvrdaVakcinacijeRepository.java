package com.example.demo.repository;

import com.example.demo.util.DBManager;
import com.example.demo.util.FusekiManager;
import org.exist.xupdate.XUpdateProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PotvrdaVakcinacijeRepository extends RepositoryInterface {

    private String collectionId = "/db/portal/lista_potvrda";

    private static final String ID_STRING = "http://www.ftn.uns.ac.rs/xml_i_veb_servisi/potvrda_o_vakcinaciji/";

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
}
