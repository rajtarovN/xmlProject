package rs.ac.uns.ftn.xml_i_veb_servisi.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;

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
import rs.ac.uns.ftn.xml_i_veb_servisi.util.AuthenticationUtilities.ConnectionProperties;

public class DBManager {
	private static ConnectionProperties conn;

	public static void main(String[] args) throws Exception {
		DBManager.saveToDb("2.xml", conn = AuthenticationUtilities.loadProperties(), "");
	}

	// Save document to db. DocumentId -> store under name.
	public static void saveToDb(String documentId, ConnectionProperties conn, String data) throws Exception {

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
			res = (XMLResource) col.createResource(documentId, XMLResource.RESOURCE_TYPE);

			// TODO - marshalling will be done in service class before saving, send string
			// param
			// of marshalled object

			System.out.println("[INFO] Unmarshalling XML document to an JAXB instance: ");
			JAXBContext context = JAXBContext
					.newInstance("rs.ac.uns.ftn.xml_i_veb_servisi.model.digitalni_zeleni_sertifikat");

			Unmarshaller unmarshaller = context.createUnmarshaller();

			DigitalniZeleniSertifikat digitalniZeleniSertifikat = (DigitalniZeleniSertifikat) unmarshaller
					.unmarshal(new File("data/xml/digitalni_sertifikat.xml"));
			System.out.println(digitalniZeleniSertifikat);

			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// marshal the contents to an output stream
			marshaller.marshal(digitalniZeleniSertifikat, os);
			// ------------------- TODO DELETE

			// link the stream to the XML resource
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

	public static XMLResource loadFromDb(String documentId, ConnectionProperties conn, String args[]) throws Exception {

		System.out.println("[INFO] " + DBManager.class.getSimpleName());

		// initialize collection and document identifiers
		String collectionId = null;

		System.out.println("[INFO] Read from db");

		collectionId = "/db/sample/library";
		documentId = "2.xml"; // TODO

		System.out.println("\t- document ID: " + documentId + "\n");

		// initialize database driver
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
			res = (XMLResource) col.getResource(documentId);

			if (res == null) {
				System.out.println("[WARNING] Document '" + documentId + "' can not be found!");
			} else {
				/*
				 * System.out.println("[INFO] Binding XML resouce to an JAXB instance: ");
				 * JAXBContext context =
				 * JAXBContext.newInstance("rs.ac.uns.ftn.examples.xmldb.bookstore");
				 * 
				 * Unmarshaller unmarshaller = context.createUnmarshaller();
				 * 
				 * //Bookstore bookstore = (Bookstore)
				 * unmarshaller.unmarshal(res.getContentAsDOM());
				 * 
				 * System.out.println("[INFO] Showing the document as JAXB instance: ");
				 * System.out.println(bookstore);
				 */

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
