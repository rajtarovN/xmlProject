package com.example.demo.repository;

import com.example.demo.util.ExistManager;
import com.example.demo.util.FusekiManager;
import com.example.demo.util.MetadataExtractor;
import org.exist.xupdate.XUpdateProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.xmldb.api.base.ResourceSet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SaglasnostRepository {

    private String collectionId = "/db/portal/saglasnosti";
    private String documentId = "saglasnost.xml";

    private static final String ID_STRING = "http://www.ftn.uns.ac.rs/xml_i_veb_servisi/obrazac_saglasnosti_za_imunizaciju/";

    private static final String TARGET_NAMESPACE = "http://www.ftn.uns.ac.rs/xml_i_veb_servisi/obrazac_saglasnosti_za_imunizaciju";

    public static final String APPEND = "<xu:modifications version=\"1.0\" xmlns:xu=\"" + XUpdateProcessor.XUPDATE_NS
            + "\" xmlns=\"" + TARGET_NAMESPACE + "\">" + "<xu:append select=\"%1$s\" child=\"last()\">%2$s</xu:append>"
            + "</xu:modifications>";

    public static final String UPDATE = "<xu:modifications version=\"1.0\" xmlns:xu=\"" + XUpdateProcessor.XUPDATE_NS
            + "\" xmlns=\"" + TARGET_NAMESPACE + "\">" + "<xu:update select=\"%1$s\">%2$s</xu:update>"
            + "</xu:modifications>";

    public static final String SPARQL_FILE = "src/main/resources/static/sparql/saglasnost/";

    @Autowired
    private ExistManager existManager;

    @Autowired
    private FusekiManager fusekiManager;

    public List<String> pretragaTermina(String imePrezime, String datumTermina) throws Exception {
        List<String> ids = new ArrayList<>();


        if (imePrezime != null && datumTermina != null) {
            // po imePrezime i datumTermina
            ArrayList<String> params = new ArrayList<>();
            params.add(datumTermina);
            params.add(imePrezime);

            ids = this.fusekiManager.query("/lista_saglasnosti", SPARQL_FILE + "saglasnost_datum_imeprezime.rq", params);
            return ids;
        } else {
            if (datumTermina != null) {
                // samo po datumu
                ArrayList<String> params = new ArrayList<>();
                params.add(datumTermina);

                ids = this.fusekiManager.query("/lista_saglasnosti", SPARQL_FILE + "saglasnost_datum.rq", params);
                return ids;

            }
        }
        return ids;
    }

    public ResourceSet pronadjiPoId(long id) {
        //TODO jedan xml il posebni xml-ovi
        //String id_Str = ID_STRING + id;
        String xPath = "/lista_saglasnosti/zalba_cutanje[@Broj_saglasnosti='" + id + "']";
        ResourceSet set;
        try {
            set = this.existManager.retrieve(collectionId, xPath, TARGET_NAMESPACE);

            return set;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveRDF(String content, String uri)  throws Exception {
        InputStream in = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        MetadataExtractor extractor = new MetadataExtractor();
        extractor.extractMetadata(in, out);

        String rdfAsString = new String(out.toByteArray());
        InputStream rdfInputStream = new ByteArrayInputStream(rdfAsString.getBytes());
        fusekiManager.writeFuseki(rdfInputStream, uri);
    }
}
