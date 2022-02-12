package com.example.demo.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.transform.OutputKeys;

import org.exist.xmldb.EXistResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;

@Component
public class DBManager {

	@Autowired
	private AuthenticationManagerExist authManager;

	public XMLResource readFileFromDB(String documentId, String collectionId) throws XMLDBException,
			ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, JAXBException {

		System.out.println("[INFO] " + "READ FILE FROM DB");

		System.out.println("[INFO] Loading driver class: " + authManager.getDriver());
		Class<?> cl = Class.forName(authManager.getDriver());

		Database database = (Database) cl.newInstance();
		database.setProperty("create-database", "true");

		DatabaseManager.registerDatabase(database);

		Collection col = null;
		XMLResource res = null;

		try {
			// get the collection
			System.out.println("[INFO] Retrieving the collection: " + collectionId);
			col = DatabaseManager.getCollection(authManager.getUri() + collectionId);
			col.setProperty(OutputKeys.INDENT, "yes");

			System.out.println("[INFO] Retrieving the document: " + documentId);
			res = (XMLResource) col.getResource(documentId);

			if (res == null) {
				System.out.println("[WARNING] Document '" + documentId + "' can not be found!");
			} else {
				System.out.println("[INFO] Showing the document as XML resource: ");
				System.out.println(res.getContent());

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

	public XMLResource saveFileToDB(String documentId, String collectionId, String os)
			throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {

		// initialize database driver
		System.out.println("[INFO] Loading driver class: " + authManager.getDriver());
		Class<?> cl = Class.forName(authManager.getDriver());

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
			res = (XMLResource) col.createResource(documentId + ".xml", XMLResource.RESOURCE_TYPE);

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
		return res;
	}

	public void deleteFileFromDB(String documentId, String collectionId)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		System.out.println("[INFO] Loading driver class: " + authManager.getDriver());
		Class<?> cl = Class.forName(authManager.getDriver());

		// encapsulation of the database driver functionality
		Database database = (Database) cl.newInstance();
		database.setProperty("create-database", "true");

		// entry point for the API which enables you to get the Collection reference
		DatabaseManager.registerDatabase(database);

		Collection col = null;
		try {
			System.out.println("db manager deleteDocument = " + (authManager.getUri() + collectionId));
			col = DatabaseManager.getCollection(authManager.getUri() + collectionId, authManager.getUser(),
					authManager.getPassword());
			Resource foundFile = col.getResource(documentId);
			col.removeResource(foundFile);
		} finally {
			if (col != null) {
				try {
					col.close();
				} catch (XMLDBException xe) {
					xe.printStackTrace();
				}
			}
		}
	}

	public List<XMLResource> findAllFromCollection(String collectionId) throws Exception {
		List<XMLResource> lista = new ArrayList<>();

		System.out.println("[INFO] Loading driver class: " + authManager.getDriver());
		Class<?> cl = Class.forName(authManager.getDriver());

		Database database = (Database) cl.newInstance();
		database.setProperty("create-database", "true");

		DatabaseManager.registerDatabase(database);

		Collection col = null;
		XMLResource res = null;

		try {
			// get the collection
			System.out.println("[INFO] Retrieving the collection: " + collectionId);
			col = DatabaseManager.getCollection(authManager.getUri() + collectionId);
			col.setProperty(OutputKeys.INDENT, "yes");

			// get all ids from a collection. Put them in array.
			String[] ids = col.listResources();
			for (String documentId : ids) {
				lista.add(readFileFromDB(documentId, collectionId));
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

		return lista;
	}

	public Collection getOrCreateCollection(String collectionUri) throws XMLDBException {
		return getOrCreateCollection(collectionUri, 0);
	}

	public Collection getOrCreateCollection(String collectionUri, int pathSegmentOffset) throws XMLDBException {

		Collection col = DatabaseManager.getCollection(authManager.getUri() + collectionUri, authManager.getUser(),
				authManager.getPassword());

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

				Collection startCol = DatabaseManager.getCollection(authManager.getUri() + path, authManager.getUser(),
						authManager.getPassword());

				if (startCol == null) {

					// child collection does not exist

					String parentPath = path.substring(0, path.lastIndexOf("/"));
					Collection parentCol = DatabaseManager.getCollection(authManager.getUri() + parentPath,
							authManager.getUser(), authManager.getPassword());

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

}
