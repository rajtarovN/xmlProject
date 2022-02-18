package com.example.sluzbenik_back.service;

import com.example.sluzbenik_back.client.KorisnikClient;
import com.example.sluzbenik_back.dto.KorisnikDTO;
import com.example.sluzbenik_back.dto.KorisnikPrijavaDTO;
import com.example.sluzbenik_back.exceptions.ForbiddenException;
import com.example.sluzbenik_back.model.korisnik.Korisnik;
import com.example.sluzbenik_back.model.korisnik.ListaKorisnika;
import com.example.sluzbenik_back.repository.KorisnikRepository;
import com.example.sluzbenik_back.util.DBManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.Resource;
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
import java.util.ArrayList;
import java.util.List;

@Service
public class KorisnikService  {

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private DBManager dbManager;

    @Autowired
    private KorisnikClient korisnikClient;


    public boolean prijava(KorisnikPrijavaDTO korisnik) {
        return this.korisnikRepository.prijava(korisnik);
    }

    public Korisnik pronadjiPoEmailu(String email) {
        Resource res = this.korisnikRepository.pronadjiPoEmailu(email);

        if (res != null) {
            try {
                JAXBContext context = JAXBContext.newInstance("com.example.sluzbenik_back.model.korisnik");

                Unmarshaller unmarshaller = context.createUnmarshaller();
                Korisnik korisnik = (Korisnik) unmarshaller.unmarshal(((XMLResource) res).getContentAsDOM());
                return korisnik;

            } catch (Exception e) {
                return null;
            }
        } else
            return null;
    }


    public List<KorisnikDTO> getKorisnike(String searchTerm) throws Exception {
        ListaKorisnika listaKorisnika = korisnikClient.getGradjane();
        List<KorisnikDTO> list = new ArrayList<>();
        for (Korisnik korisnik : listaKorisnika.getKorisnik() ) {
            if(korisnik.getUloga().equals("G")){
                if(searchTerm == null || searchTerm == "") {
                    list.add(new KorisnikDTO(korisnik.getEmail(), korisnik.getIme(), korisnik.getPrezime()));
                }
                else if(korisnik.getEmail().toLowerCase().contains(searchTerm.toLowerCase()) ||
                        korisnik.getIme().toLowerCase().contains(searchTerm.toLowerCase()) ||
                        korisnik.getPrezime().toLowerCase().contains(searchTerm.toLowerCase())){
                    list.add(new KorisnikDTO(korisnik.getEmail(), korisnik.getIme(), korisnik.getPrezime()));
                }
            }
        }
        return list;
    }

}
