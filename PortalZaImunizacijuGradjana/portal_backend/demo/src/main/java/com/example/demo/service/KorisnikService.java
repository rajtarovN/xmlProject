package com.example.demo.service;

import com.example.demo.dto.KorisnikPrijavaDTO;
import com.example.demo.exceptions.ForbiddenException;
import com.example.demo.model.korisnik.Korisnik;
import com.example.demo.model.korisnik.ListaKorisnika;
import com.example.demo.model.liste.JaxbLista;
import com.example.demo.repository.KorisnikRepository;
import com.example.demo.util.DBManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class KorisnikService  {

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private DBManager dbManager;


    public boolean registruj(String email, String korisnik) {
        boolean postoji = this.korisnikRepository.postojiPoMejlu(email);

        if (!postoji) {
            this.korisnikRepository.registracija(korisnik);
            return true;
        } else
            return false;
    }

    public boolean prijava(KorisnikPrijavaDTO korisnik) {
        return this.korisnikRepository.prijava(korisnik);
    }

    public Korisnik pronadjiPoEmailu(String email) {
        Resource res = this.korisnikRepository.pronadjiPoEmailu(email);

        if (res != null) {
            try {
                JAXBContext context = JAXBContext.newInstance("com.example.demo.model.korisnik");

                Unmarshaller unmarshaller = context.createUnmarshaller();
                Korisnik korisnik = (Korisnik) unmarshaller.unmarshal(((XMLResource) res).getContentAsDOM());
                return korisnik;

            } catch (Exception e) {
                return null;
            }
        } else
            return null;
    }

    public OutputStream parsiraj(String documentId, String type) throws JAXBException {
        OutputStream os = new ByteArrayOutputStream();
        try {
            JAXBContext context = JAXBContext
                    .newInstance("com.example.demo.model." + type);

            Unmarshaller unmarshaller = context.createUnmarshaller();

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            ListaKorisnika listaKorisnika = (ListaKorisnika) unmarshaller
                    .unmarshal(new File("data/xml/" + documentId + ".xml"));
            marshaller.marshal(listaKorisnika, os);
            return os;
        }catch (Exception e){
            throw new ForbiddenException("Error pri parsiranju korisnika.");
        }
    }

    public void inicijalizujBazu() throws JAXBException, XMLDBException, ClassNotFoundException, InstantiationException, IOException, IllegalAccessException {
        try {
            String documentId = "korisnik";
            String collectionId = "/db/portal";
            OutputStream os = parsiraj(documentId, "korisnik");
            dbManager.saveFileToDB(documentId, collectionId, os);
        }catch (Exception e){
            e.printStackTrace();
            throw new ForbiddenException("Error pri inicijalizaciji baze.");
        }
    }
}
