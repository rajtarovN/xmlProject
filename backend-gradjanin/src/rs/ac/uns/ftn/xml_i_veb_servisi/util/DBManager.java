package rs.ac.uns.ftn.xml_i_veb_servisi.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.OutputKeys;

import org.exist.xmldb.EXistResource;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;

import rs.ac.uns.ftn.xml_i_veb_servisi.model.digitalni_zeleni_sertifikat.DigitalniZeleniSertifikat;
import rs.ac.uns.ftn.xml_i_veb_servisi.model.interesovanje.Interesovanje;
import rs.ac.uns.ftn.xml_i_veb_servisi.model.izvestaj_o_imunizaciji.IzvestajOImunizaciji;
import rs.ac.uns.ftn.xml_i_veb_servisi.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost;
import rs.ac.uns.ftn.xml_i_veb_servisi.model.potvrda_o_vacinaciji.PotvrdaOVakcinaciji;
import rs.ac.uns.ftn.xml_i_veb_servisi.model.zahtev_za_sertifikatom.ZahtevZaZeleniSertifikat;
import rs.ac.uns.ftn.xml_i_veb_servisi.util.AuthenticationUtilities.ConnectionProperties;

import static rs.ac.uns.ftn.xml_i_veb_servisi.util.PathConstants.*;


public class DBManager {
	private static ConnectionProperties conn;

	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		
		boolean work = true;
		while(work) {
			System.out.println("===============================");
			System.out.println("Odaberite jednu opciju:");
			System.out.println("1) Cuvanje digitalnog sertifikata");
			System.out.println("2) Cuvanje interesovanje");
			System.out.println("3) Cuvanje izvestaja");
			System.out.println("4) Cuvanje potvrde o vakcinaciji");
			System.out.println("5) Cuvanje saglasnosti");
			System.out.println("6) Cuvanje zahteva za sertifikat");
			System.out.println("7) Ucitavanje digitalnog sertifikata");
			System.out.println("8) Ucitavanje interesovanje");
			System.out.println("9) Ucitavanje izvestaja");
			System.out.println("10) Ucitavanje potvrde o vakcinaciji");
			System.out.println("11) Ucitavanje saglasnosti");
			System.out.println("12) Ucitavanje zahteva za sertifikat");
			System.out.println("x) Kraj");
			System.out.println(">>>");
			String input = scanner.nextLine();
			
			switch(input) {
			case("1"):
				DBManager.saveToDb("2", "digitalni_zeleni_sertifikat", conn = AuthenticationUtilities.loadProperties());
				break;
			case("2"):
				DBManager.saveToDb("2", "interesovanje", conn = AuthenticationUtilities.loadProperties());
				break;
			case("3"):
				DBManager.saveToDb("2", "izvestaj_o_imunizaciji", conn = AuthenticationUtilities.loadProperties());
				break;
			case("4"):
				DBManager.saveToDb("2", "potvrda_o_vacinaciji", conn = AuthenticationUtilities.loadProperties());
				break;
			case("5"):
				DBManager.saveToDb("2", "obrazac_saglasnosti_za_imunizaciju", conn = AuthenticationUtilities.loadProperties());
				break;
			case("6"):
				DBManager.saveToDb("2", "zahtev_za_sertifikatom", conn = AuthenticationUtilities.loadProperties());
				break;
			case("7"):
				DBManager.loadFromDb("digitalni_zeleni_sertifikat_2", conn = AuthenticationUtilities.loadProperties(), "digitalni_zeleni_sertifikat");
				break;
			case("8"):
				DBManager.loadFromDb("interesovanje_2", conn = AuthenticationUtilities.loadProperties(), "interesovanje");
				break;
			case("9"):
				DBManager.loadFromDb("izvestaj_o_imunizaciji_2", conn = AuthenticationUtilities.loadProperties(), "izvestaj_o_imunizaciji");
				break;
			case("10"):
				DBManager.loadFromDb("potvrda_o_vacinaciji_2", conn = AuthenticationUtilities.loadProperties(), "potvrda_o_vacinaciji");
				break;
			case("11"):
				DBManager.loadFromDb("obrazac_saglasnosti_za_imunizaciju_2", conn = AuthenticationUtilities.loadProperties(), "obrazac_saglasnosti_za_imunizaciju");
				break;
			case("12"):
				DBManager.loadFromDb("zahtev_za_sertifikatom_2", conn = AuthenticationUtilities.loadProperties(), "zahtev_za_sertifikatom");
				break;
			case("x"):
				work = false;
				break;
			}
		}
		scanner.close();
	}

	// Save document to db. DocumentId -> store under name.
	public static void saveToDb(String documentId, String type, ConnectionProperties conn) throws Exception {

		System.out.println("[INFO] " + DBManager.class.getSimpleName());

		System.out.println("[INFO] Save to db");

		String collectionId = "/db/sample/library";

		System.out.println("\t- document ID: " + documentId + "\n");

		// initialize database driver
		System.out.println("[INFO] Loading driver class: " + conn.driver);
		Class<?> cl = Class.forName(conn.driver);

		// encapsulation of the database driver functionality
		Database database = (Database) cl.newInstance();
		database.setProperty("create-database", "true");

		// entry point for the API which enables you to get the Collection reference
		DatabaseManager.registerDatabase(database);

		// a collection of Resources stored within an XML database
		Collection col = null;
		XMLResource res = null;
		OutputStream os = new ByteArrayOutputStream();

		try {

			System.out.println("[INFO] Retrieving the collection: " + collectionId);
			col = getOrCreateCollection(collectionId);

			System.out.println("[INFO] Inserting the document: " + documentId);
			res = (XMLResource) col.createResource(documentId +"_" + type + ".xml", XMLResource.RESOURCE_TYPE);

			System.out.println("[INFO] Unmarshalling XML document to an JAXB instance: ");
			JAXBContext context = JAXBContext
					.newInstance("rs.ac.uns.ftn.xml_i_veb_servisi.model." + type);

			Unmarshaller unmarshaller = context.createUnmarshaller();
			
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			
			if(type.startsWith("digitalni")) {
				
				DigitalniZeleniSertifikat digitalniZeleniSertifikat = (DigitalniZeleniSertifikat) unmarshaller
						.unmarshal(new File(XML_DOCUMENTS +  type +  "_" + documentId +".xml"));
				marshaller.marshal(digitalniZeleniSertifikat, os);
				
			}else if(type.startsWith("izvestaj")) {
				
				IzvestajOImunizaciji izvestaj = (IzvestajOImunizaciji) unmarshaller
						.unmarshal(new File(XML_DOCUMENTS +  type +  "_" + documentId +".xml")); 
				marshaller.marshal(izvestaj, os);
				
			}else if(type.startsWith("interesovanje")) {
				
				Interesovanje interesovanje = (Interesovanje) unmarshaller
						.unmarshal(new File(XML_DOCUMENTS +  type +  "_" + documentId +".xml"));
				marshaller.marshal(interesovanje, os);
				
			}else if(type.startsWith("potvrda")) {
				
				PotvrdaOVakcinaciji potvrda = (PotvrdaOVakcinaciji) unmarshaller
						.unmarshal(new File(XML_DOCUMENTS +  type +  "_" + documentId +".xml"));
				marshaller.marshal(potvrda, os);
			
			}else if(type.startsWith("obrazac")) {
				
				Saglasnost saglasnost = (Saglasnost) unmarshaller
						.unmarshal(new File(XML_DOCUMENTS +  type +  "_" + documentId +".xml"));
				marshaller.marshal(saglasnost, os);
			
			}else {
				
				ZahtevZaZeleniSertifikat zahtev = (ZahtevZaZeleniSertifikat) unmarshaller
						.unmarshal(new File(XML_DOCUMENTS +  type +  "_" + documentId +".xml"));
				marshaller.marshal(zahtev, os);
			}
			
			res.setContent(os);
			System.out.println("[INFO] Storing the document: " + res.getId());

			col.storeResource(res);
			System.out.println("[INFO] Done.");

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

	private static Collection getOrCreateCollection(String collectionUri) throws XMLDBException {
		return getOrCreateCollection(collectionUri, 0);
	}

	private static Collection getOrCreateCollection(String collectionUri, int pathSegmentOffset) throws XMLDBException {

		Collection col = DatabaseManager.getCollection(conn.uri + collectionUri, conn.user, conn.password);

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

				Collection startCol = DatabaseManager.getCollection(conn.uri + path, conn.user, conn.password);

				if (startCol == null) {

					// child collection does not exist

					String parentPath = path.substring(0, path.lastIndexOf("/"));
					Collection parentCol = DatabaseManager.getCollection(conn.uri + parentPath, conn.user,
							conn.password);

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

	public static XMLResource loadFromDb(String documentId, ConnectionProperties conn, String type) throws Exception {

		System.out.println("[INFO] " + DBManager.class.getSimpleName());

		String collectionId = null;

		System.out.println("[INFO] Read from db");

		collectionId = "/db/sample/library";
		
		System.out.println("\t- document ID: " + documentId + "\n");

		System.out.println("[INFO] Loading driver class: " + conn.driver);
		Class<?> cl = Class.forName(conn.driver);

		Database database = (Database) cl.newInstance();
		database.setProperty("create-database", "true");

		DatabaseManager.registerDatabase(database);

		Collection col = null;
		XMLResource res = null;

		try {
			// get the collection
			System.out.println("[INFO] Retrieving the collection: " + collectionId);
			col = DatabaseManager.getCollection(conn.uri + collectionId);
			col.setProperty(OutputKeys.INDENT, "yes");

			System.out.println("[INFO] Retrieving the document: " + documentId);
			res = (XMLResource) col.getResource(documentId + ".xml");

			if (res == null) {
				System.out.println("[WARNING] Document '" + documentId + "' can not be found!");
			} else {
				
				  System.out.println("[INFO] Binding XML resouce to an JAXB instance: ");
				  JAXBContext context =
				  JAXBContext.newInstance("rs.ac.uns.ftn.xml_i_veb_servisi.model."+type);
				  
				  Unmarshaller unmarshaller = context.createUnmarshaller();
				  
				  //Bookstore bookstore = (Bookstore)
				  unmarshaller.unmarshal(res.getContentAsDOM());
				  
				  System.out.println("[INFO] Showing the document as JAXB instance: ");
				  
				  System.out.println(res.toString());
				  
				 

			}
		} finally {
			// don't forget to clean up!

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
		return res;
	}

	
}
