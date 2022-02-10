package com.example.sluzbenik_back.repository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.xmldb.api.modules.XMLResource;

import com.example.sluzbenik_back.util.DBManager;
import com.example.sluzbenik_back.util.FusekiManager;
import com.example.sluzbenik_back.util.MetadataExtractor;


@Repository
public abstract class RepositoryInterface {

    @Autowired
    private DBManager dbManager;
  
    @Autowired
    private FusekiManager fusekiManager;
    
    public XMLResource readXML(String documentId, String collectionId) throws Exception {
        return dbManager.readFileFromDB(documentId, collectionId);
    }

    public void saveXML(String documentId, String collectionId, String content) throws Exception {
        dbManager.saveFileToDB(documentId, collectionId, content);
    }

    public String readFileAsXML(String uri) throws Exception {
       return fusekiManager.readFileAsXML(uri);
    }
    public String readFileAsJSON(String uri) throws Exception {
        return fusekiManager.readFileAsJSON(uri);
    }
    /**
     * 
     * @param content ovo je sam xml koji dobijemo sa fronta u xml formatu
     * @param rdfName
     * @param uri
     * @throws Exception
     */
    public void saveRDF(String content, String uri)  throws Exception {
        InputStream in = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		MetadataExtractor extractor = new MetadataExtractor();
        extractor.extractMetadata(in, out);

        String rdfAsString = new String(out.toByteArray());
        InputStream rdfInputStream = new ByteArrayInputStream(rdfAsString.getBytes());
        fusekiManager.writeFuseki(rdfInputStream, uri);
    }

    public List<XMLResource> findAllFromCollection(String collectionId) throws Exception{
        return dbManager.findAllFromCollection(collectionId);
    }
}
