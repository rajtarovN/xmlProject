package com.example.demo.service;

import com.example.demo.dto.SaglasnostDTO;
import com.example.demo.exceptions.ForbiddenException;
import com.example.demo.model.korisnik.ListaKorisnika;
import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost;
import com.example.demo.repository.SaglasnostRepository;
import com.example.demo.util.DBManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class SaglasnostService {

    @Autowired
    private SaglasnostRepository saglasnostRepository;

    @Autowired
    private DBManager dbManager;

    public ArrayList<SaglasnostDTO> pretragaTermina(String imePrezime, Date datumTermina) throws IllegalAccessException, InstantiationException, JAXBException, IOException, XMLDBException, ClassNotFoundException {
        List<String> ids = new ArrayList<>();
        if(datumTermina == null){
            datumTermina = new Date();
        }
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");

        try {
            ids = this.saglasnostRepository.pretragaTermina(imePrezime, ft.format(datumTermina));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        ids = (ArrayList<String>) ids;

        ArrayList<SaglasnostDTO> lista = new ArrayList<>();

        for(String i : ids) {
            Saglasnost z = this.pronadjiPoId(Long.parseLong(i));
            lista.add(new SaglasnostDTO(z));
        }

        return lista;

    }

    public Saglasnost pronadjiPoId(long id) throws IllegalAccessException, InstantiationException, JAXBException, ClassNotFoundException, XMLDBException, IOException {
        XMLResource res = this.saglasnostRepository.pronadjiPoId(id);
        try {
            if (res != null) {

                JAXBContext context = JAXBContext.newInstance("com.example.demo.model.obrazac_saglasnosti_za_imunizaciju");

                Unmarshaller unmarshaller = context.createUnmarshaller();

                Saglasnost s = (Saglasnost) unmarshaller
                        .unmarshal((res).getContentAsDOM());

                return s;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public OutputStream parsiraj(String documentId, String type) throws JAXBException {
        OutputStream os = new ByteArrayOutputStream();
        try {
            JAXBContext context = JAXBContext
                    .newInstance("com.example.demo.model." + type);

            Unmarshaller unmarshaller = context.createUnmarshaller();

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            Saglasnost saglasnost = (Saglasnost) unmarshaller
                    .unmarshal(new File("data/xml/" + documentId + ".xml"));
            marshaller.marshal(saglasnost, os);
            return os;
        }catch (Exception e){
            throw new ForbiddenException("Error pri parsiranju saglasnosti.");
        }
    }

    public void inicijalizujBazu() throws JAXBException, XMLDBException, ClassNotFoundException, InstantiationException, IOException, IllegalAccessException {
        try {
            List<String> docIds = new ArrayList<>();
            docIds = Arrays.asList("saglasnost_12345", "saglasnost_54321");
            for(String documentId : docIds){
                String collectionId = "/db/portal/lista_saglasnosti";
                OutputStream os = parsiraj(documentId, "obrazac_saglasnosti_za_imunizaciju");
                dbManager.saveFileToDB(documentId, collectionId, os.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ForbiddenException("Error pri inicijalizaciji baze.");
        }
    }
}
