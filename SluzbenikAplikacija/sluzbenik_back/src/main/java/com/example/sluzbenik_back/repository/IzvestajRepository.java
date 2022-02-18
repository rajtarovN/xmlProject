package com.example.sluzbenik_back.repository;

import com.example.sluzbenik_back.util.DBManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Repository
public class IzvestajRepository extends RepositoryInterface{

    @Autowired
    private DBManager dbManager;

    private String collectionId = "/db/sluzbenik/lista_izvestaja";

    public XMLResource pronadjiPoPeriodu(String fromDate, String toDate) throws IllegalAccessException, JAXBException, InstantiationException,
            IOException, XMLDBException, ClassNotFoundException {
        return dbManager.readFileFromDB("izvestaj_o_imunizaciji_" + fromDate + "_" + toDate + ".xml", collectionId);
    }
}
