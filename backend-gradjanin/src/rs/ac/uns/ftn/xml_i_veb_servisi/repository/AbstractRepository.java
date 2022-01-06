package rs.ac.uns.ftn.xml_i_veb_servisi.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.xml_i_veb_servisi.util.DBManager;
import rs.ac.uns.ftn.xml_i_veb_servisi.util.FusekiManager;
import rs.ac.uns.ftn.xml_i_veb_servisi.util.MetadataExtractor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Repository
public abstract class AbstractRepository {

    @Autowired
    private DBManager dbManager;

    @Autowired
    private FusekiManager fusekiManager;

    public void saveXML(String documentId, String collectionId, String content) throws Exception {
        //dbManager.saveToDb(documentId, collectionId, content);
        //TODO saveXML
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
