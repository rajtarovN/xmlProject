package com.example.demo.util;

import com.example.demo.exceptions.ForbiddenException;
import com.example.demo.model.korisnik.ListaKorisnika;
import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost;
import com.example.demo.model.zahtev_za_sertifikatom.ZahtevZaZeleniSertifikat;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.exist.xmldb.EXistResource;
import org.xml.sax.SAXException;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.util.Arrays;
import java.util.List;

public class InitXmlAndRdfDb {

    public static void inicijalizujXMLBazu() throws JAXBException, XMLDBException, ClassNotFoundException, InstantiationException, IOException, IllegalAccessException {
        try {
            List<String> docIds = Arrays.asList("saglasnost_12345", "saglasnost_54321", "saglasnost_67890", "saglasnost_78901", "zahtev_0101000000110_2020-01-01-04-04-00");
            for(String documentId : docIds){
                String collectionId = "";
                OutputStream os = new ByteArrayOutputStream();
                if(documentId.contains("sag")){
                    collectionId = "/db/portal/lista_saglasnosti";
                    os = parsiraj(documentId, "obrazac_saglasnosti_za_imunizaciju");
                }
                else if(documentId.contains("zah")){
                    collectionId = "/db/portal/lista_zahteva";
                    os = parsiraj(documentId, "zahtev_za_sertifikatom");
                }
                runXML(documentId, collectionId, os.toString());
            }
            //korisnici
            String documentId = "korisnik";
            String collectionId = "/db/portal";
            OutputStream os = parsiraj("korisnik", "korisnik");
            runXML(documentId, collectionId, os.toString());

        }catch (Exception e){
            e.printStackTrace();
            throw new ForbiddenException("Error pri inicijalizaciji baze.");
        }
    }

    public static OutputStream parsiraj(String documentId, String type) throws JAXBException {
        OutputStream os = new ByteArrayOutputStream();
        try {
            JAXBContext context = JAXBContext
                    .newInstance("com.example.demo.model." + type);

            Unmarshaller unmarshaller = context.createUnmarshaller();

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            if(type.equals("obrazac_saglasnosti_za_imunizaciju")) {
                Saglasnost saglasnost = (Saglasnost) unmarshaller
                        .unmarshal(new File("data/xml/" + documentId + ".xml"));
                marshaller.marshal(saglasnost, os);
            }
            else if(type.equals("korisnik")){
                ListaKorisnika listaKorisnika = (ListaKorisnika) unmarshaller
                        .unmarshal(new File("data/xml/" + documentId + ".xml"));
                marshaller.marshal(listaKorisnika, os);
            }
            else if(type.equals("zahtev_za_sertifikatom")){
                ZahtevZaZeleniSertifikat zahtevZaZeleniSertifikat = (ZahtevZaZeleniSertifikat) unmarshaller
                        .unmarshal(new File("data/xml/" + documentId + ".xml"));
                marshaller.marshal(zahtevZaZeleniSertifikat, os);
            }
            return os;
        }catch (Exception e){
            throw new ForbiddenException("Error pri parsiranju saglasnosti.");
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
        Collection col = DatabaseManager.getCollection("xmldb:exist://localhost:8080/existPortal/xmlrpc" + collectionUri, "admin", "");

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

                Collection startCol = DatabaseManager.getCollection("xmldb:exist://localhost:8080/existPortal/xmlrpc" + path, "admin", "");

                if (startCol == null) {

                    // child collection does not exist

                    String parentPath = path.substring(0, path.lastIndexOf("/"));
                    Collection parentCol = DatabaseManager.getCollection("xmldb:exist://localhost:8080/existPortal/xmlrpc" + parentPath, "admin",
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
        AuthenticationManagerFuseki.ConnectionProperties fusekiConn = AuthenticationManagerFuseki.loadProperties();
        List<String> docIds = Arrays.asList("saglasnost_12345", "saglasnost_54321", "saglasnost_67890", "saglasnost_78901");
        for(String documentId : docIds) {
            String graphUri = "";
            if(documentId.contains("sag")){
                graphUri = "/lista_saglasnosti";
            }else{
                graphUri = "/lista_zahteva";
            }
            String xmlFilePath = "data/xml/" + documentId + ".xml";
            String rdfFilePath = "gen/" + documentId + ".rdf";

            MetadataExtractor metadataExtractor = new MetadataExtractor();

            System.out.println("[INFO] Extracting metadata from RDFa attributes...");
            metadataExtractor.extractMetadata(
                    new FileInputStream(new File(xmlFilePath)),
                    new FileOutputStream(new File(rdfFilePath)));

            Model model = ModelFactory.createDefaultModel();
            model.read(rdfFilePath);

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            model.write(out, SparqlUtil.NTRIPLES);

            System.out.println("[INFO] Extracted metadata as RDF/XML...");
            //model.write(System.out, SparqlUtil.RDF_XML);

            System.out.println("[INFO] Populating named graph \"" + graphUri + "\" with extracted metadata.");
            String sparqlUpdate = SparqlUtil.insertData(fusekiConn.dataEndpoint + graphUri, out.toString());
            System.out.println(sparqlUpdate);

            // UpdateRequest represents a unit of execution
            UpdateRequest update = UpdateFactory.create(sparqlUpdate);

            UpdateProcessor processor = UpdateExecutionFactory.createRemote(update, fusekiConn.updateEndpoint);
            processor.execute();
            model.close();
        }
    }
}
