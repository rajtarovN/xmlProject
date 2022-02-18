package com.example.demo.repository;

import com.example.demo.util.FusekiManager;
import com.example.demo.util.MetadataExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ZahtevRepository extends RepositoryInterface{

    private String collectionId = "/db/portal/lista_zahteva";

    public static final String SPARQL_FILE = "src/main/resources/static/sparql/zahtev/";

    @Autowired
    private FusekiManager fusekiManager;

    public void saveRDF(String content, String uri)  throws Exception {
        InputStream in = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        MetadataExtractor extractor = new MetadataExtractor();
        extractor.extractMetadata(in, out);

        String rdfAsString = out.toString();
        InputStream rdfInputStream = new ByteArrayInputStream(rdfAsString.getBytes());
        fusekiManager.writeFuseki(rdfInputStream, uri);
    }

    public List<String> pronadjiPoStatusu(String status) throws Exception {
        List<String> ids = new ArrayList<>();
        ArrayList<String> params = new ArrayList<>();
        params.add("\"" +status+"\"");

        ids = this.fusekiManager.query("/lista_zahteva", SPARQL_FILE + "zahtev_status.rq", params);
        return ids;
    }

    public XMLResource pronadjiPoId(String id) throws IllegalAccessException, JAXBException, InstantiationException,
            IOException, XMLDBException, ClassNotFoundException {
        return dbManager.readFileFromDB("zahtev_" + id + ".xml", collectionId);
    }

    public List<String> pronadjiPoStatusuIJmbg(String status, String jmbg) throws Exception {
        List<String> ids = new ArrayList<>();
        ArrayList<String> params = new ArrayList<>();
        params.add("\"" +status+"\"");

        ids = this.fusekiManager.query("/lista_zahteva", SPARQL_FILE + "zahtev_status.rq", params);


        List<String> idsJmbg = new ArrayList<>();
        params = new ArrayList<>();
        params.add("\"" +jmbg+"\"");

        idsJmbg = this.fusekiManager.query("/lista_zahteva", SPARQL_FILE + "zahtev_jmbg.rq", params);

        List<String> idsFinal = new ArrayList<>();
        for(String i : ids){
            for(String j: idsJmbg){
                if(i.equals(j)){
                    idsFinal.add(i);
                    break;
                }
            }
        }
        return idsFinal;
    }

    public void generateJson(String documentNameId, String graphUri, String about) throws Exception {
        fusekiManager.generisiJSON(documentNameId, graphUri, about);
    }
    
    public List<String> pronadjiPoSertifikatRef(String sertifikat) throws Exception {
      ArrayList<String> params = new ArrayList<>();
      params.add("\"" + sertifikat + "\"");

      List<String> found = this.fusekiManager.queryAbout("/lista_zahteva", SPARQL_FILE + "zahtev_ref_sertifikat.rq", params);
      if(!found.isEmpty()) {
        return found;
      }
      return null;
    }
}
