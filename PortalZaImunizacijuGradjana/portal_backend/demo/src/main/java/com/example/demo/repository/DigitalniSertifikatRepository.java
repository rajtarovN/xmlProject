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
public class DigitalniSertifikatRepository extends RepositoryInterface {

    private String collectionId = "/db/portal/lista_sertifikata";

    public static final String SPARQL_FILE = "src/main/resources/static/sparql/sertifikat/";

    @Autowired
    private FusekiManager fusekiManager;

    @Autowired
    private DBManager dbManager;

    public XMLResource pronadjiPoId(String id) throws IllegalAccessException, JAXBException, InstantiationException, IOException, XMLDBException, ClassNotFoundException {
        return dbManager.readFileFromDB("sertifikat_"+id+".xml", collectionId);
    }

    public List<String> pronadjiPoJmbg(String jmbg ) throws Exception {
        List<String> ids = new ArrayList<>();
        ArrayList<String> params = new ArrayList<>();
        params.add("\"" +jmbg+"\"");

        ids = this.fusekiManager.query("/lista_sertifikata", SPARQL_FILE + "sertifikat_jmbg.rq", params);
        return ids;
    }
}
