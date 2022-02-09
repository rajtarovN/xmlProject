package com.example.sluzbenik_back.util;

import org.exist.xmldb.EXistResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XPathQueryService;
import org.xmldb.api.modules.XUpdateQueryService;

@Service
public class ExistManager {

    @Autowired
    private AuthenticationManagerExist authManager;

    public void createConnection() throws Exception {
        Class<?> cl = Class.forName(this.authManager.getDriver());

        Database db = (Database) cl.newInstance();

        db.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(db);
    }

    public void closeConnection(Collection col, XMLResource res) throws XMLDBException {
        if (col != null) {
            col.close();
        }

        if (res != null) {
            ((EXistResource) res).freeResources();
        }
    }

    // executing xquery on collection
    public ResourceSet retrieve(String collectionId, String xpathExp, String TARGET_NAMESPACE) throws Exception {
        createConnection();

        Collection col = null;
        ResourceSet res = null;

        try {
            col = DatabaseManager.getCollection(this.authManager.getUri() + collectionId, this.authManager.getUser(),
                    this.authManager.getPassword());
            XPathQueryService xPathService = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            xPathService.setProperty("indent", "yes");
            xPathService.setNamespace("", TARGET_NAMESPACE);

            res = xPathService.query(xpathExp);
        } finally {
            if (col != null)
                col.close();
        }

        return res;
    }

    public void append(String collectionId, String documentId, String contextXPath, String patch, String APPEND) throws Exception {
        createConnection();
        Collection col = null;
        XMLResource res = null;
        String chosenTemplate = APPEND;

        try {
            col = DatabaseManager.getCollection(this.authManager.getUri() + collectionId, this.authManager.getUser(),
                    this.authManager.getPassword());
            XUpdateQueryService service = (XUpdateQueryService) col.getService("XUpdateQueryService", "1.0");
            service.setProperty("indent", "yes");

            service.updateResource(documentId, String.format(chosenTemplate, contextXPath, patch));

        } finally {
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
}
