package com.example.sluzbenik_back.util;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.TransformerException;

import org.exist.xmldb.EXistResource;
import org.xml.sax.SAXException;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;

import com.example.sluzbenik_back.exceptions.ForbiddenException;
import com.example.sluzbenik_back.model.dostupne_vakcine.Zalihe;

public class InitXmlAndRdfDb {

    public static void inicijalizujXMLBazu() throws JAXBException, XMLDBException, ClassNotFoundException, InstantiationException, IOException, IllegalAccessException {
        try {
            //zalihe

        	String zaliheId = "zalihe";
        	String collectionId = "/db/sluzbenik/zalihe";
            OutputStream os = parsiraj(zaliheId, "dostupne_vakcine"); 
            runXML(zaliheId, collectionId, os.toString());



        }catch (Exception e){
            e.printStackTrace();
            throw new ForbiddenException("Error pri inicijalizaciji baze.");
        }
    }

    public static OutputStream parsiraj(String documentId, String type) throws JAXBException {
        OutputStream os = new ByteArrayOutputStream();
        try {
            JAXBContext context = JAXBContext
                    .newInstance("com.example.sluzbenik_back.model." + type);

            Unmarshaller unmarshaller = context.createUnmarshaller();

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            if(type.equals("dostupne_vakcine")) {
                Zalihe zalihe = (Zalihe) unmarshaller
                        .unmarshal(new File("data/xml/" + documentId + ".xml"));
                marshaller.marshal(zalihe, os);
            }
          
            return os;
        }catch (Exception e){
            throw new ForbiddenException("Error pri parsiranju.");
        }
    }

    public static void runXML(String documentId, String collectionId, String os) throws ClassNotFoundException, XMLDBException, IllegalAccessException, InstantiationException {
        // initialize database driver
        System.out.println("[INFO] Loading driver class: " + "org.exist.xmldb.DatabaseImpl");
        Class<?> cl = Class.forName("org.exist.xmldb.DatabaseImpl");

        // encapsulation of the database driver functionality
        Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");

        // entry point for the API which enables you to get the Collection reference
        DatabaseManager.registerDatabase(database);

        // a collection of Resources stored within an XML database
        Collection col = null;
        XMLResource res = null;

        try {

            System.out.println("[INFO] Retrieving the collection: " + collectionId);
            col = getOrCreateCollection(collectionId);

            /*
             * create new XMLResource with a given id an id is assigned to the new resource
             * if left empty (null)
             */
            System.out.println("[INFO] Inserting the document: " + documentId);
            res = (XMLResource) col.createResource(documentId  + ".xml", XMLResource.RESOURCE_TYPE);

            res.setContent(os);
            System.out.println("[INFO] Storing the document: " + res.getId());

            col.storeResource(res);
            System.out.println("[INFO] Done. File is save to DB.");

        } finally {
            // don't forget to cleanup
            if (res != null) {
                try {
                    ((EXistResource) res).freeResources();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
            if (col != null) {
                try {
                    col.close();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
        }
    }

    public static Collection getOrCreateCollection(String collectionUri) throws XMLDBException {
        return getOrCreateCollection(collectionUri, 0);
    }

    public static Collection getOrCreateCollection(String collectionUri, int pathSegmentOffset) throws XMLDBException {
        Collection col = DatabaseManager.getCollection("xmldb:exist://localhost:8080/existSluzbenik/xmlrpc" + collectionUri, "admin", "");

        // create the collection if it does not exist
        if (col == null) {

            if (collectionUri.startsWith("/")) {
                collectionUri = collectionUri.substring(1);
            }

            String pathSegments[] = collectionUri.split("/");

            if (pathSegments.length > 0) {
                StringBuilder path = new StringBuilder();

                for (int i = 0; i <= pathSegmentOffset; i++) {
                    path.append("/" + pathSegments[i]);
                }

                Collection startCol = DatabaseManager.getCollection("xmldb:exist://localhost:8080/existSluzbenik/xmlrpc" + path, "admin", "");

                if (startCol == null) {

                    // child collection does not exist

                    String parentPath = path.substring(0, path.lastIndexOf("/"));
                    Collection parentCol = DatabaseManager.getCollection("xmldb:exist://localhost:8080/existSluzbenik/xmlrpc" + parentPath, "admin",
                            "");

                    CollectionManagementService mgt = (CollectionManagementService) parentCol
                            .getService("CollectionManagementService", "1.0");

                    System.out.println("[INFO] Creating the collection: " + pathSegments[pathSegmentOffset]);
                    col = mgt.createCollection(pathSegments[pathSegmentOffset]);

                    col.close();
                    parentCol.close();

                } else {
                    startCol.close();
                }
            }
            return getOrCreateCollection(collectionUri, ++pathSegmentOffset);
        } else {
            return col;
        }
    }

    public static void inicijalizujRDFBazu() throws IOException, SAXException, TransformerException {
        
    	// TODO unimplemented
        return;
    }
}
