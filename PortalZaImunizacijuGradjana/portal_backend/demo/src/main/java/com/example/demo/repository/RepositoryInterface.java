package com.example.demo.repository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.xmldb.api.modules.XMLResource;

import com.example.demo.util.DBManager;
import com.example.demo.util.FusekiManager;
import com.example.demo.util.MetadataExtractor;

@Repository
public abstract class RepositoryInterface {

	@Autowired
	protected DBManager dbManager;

	@Autowired
	protected FusekiManager fusekiManager;

	public XMLResource readXML(String documentId, String collectionId) throws Exception {
		return dbManager.readFileFromDB(documentId, collectionId);
	}

	public void saveXML(String documentId, String collectionId, String content) throws Exception {
		dbManager.saveFileToDB(documentId, collectionId, content);
	}

	public void deleteXML(String documentId, String collectionId) throws Exception {
		dbManager.deleteFileFromDB(documentId, collectionId);
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
	public void saveRDF(String content, String uri) throws Exception {
		InputStream in = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		MetadataExtractor extractor = new MetadataExtractor();
		extractor.extractMetadata(in, out);

        String rdfAsString = out.toString();
        InputStream rdfInputStream = new ByteArrayInputStream(rdfAsString.getBytes());
        fusekiManager.writeFuseki(rdfInputStream, uri);
    }

	public List<XMLResource> findAllFromCollection(String collectionId) throws Exception {
		return dbManager.findAllFromCollection(collectionId);
	}
	
	public List<String> query(String graphUri, String sparqlFilePath, List<String> queryParams) throws Exception{
		return fusekiManager.query(graphUri, sparqlFilePath, queryParams);
	}
	
	public void deleteRDF(String documentId, String namedGraphUri, String predicate) throws IOException {
		fusekiManager.deleteRDF(documentId, namedGraphUri, predicate);
	}
	
	public List<String> readAllDocumentIds(String uri) throws IOException {
		return fusekiManager.readAllDocuments(uri);
	}
}
