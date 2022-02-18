package main.java.xml_i_veb_servisi.service;

import main.java.xml_i_veb_servisi.dto.KorisnikPrijavaDTO;
import main.java.xml_i_veb_servisi.model.korisnik.Korisnik;
import main.java.xml_i_veb_servisi.repository.KorisnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmldb.api.modules.XMLResource;

import org.xmldb.api.base.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

@Service
public class KorisnikService {

    @Autowired
    private KorisnikRepository korisnikRepository;

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
                JAXBContext context = JAXBContext.newInstance("com.ftn.xml.model.korisnik");

                Unmarshaller unmarshaller = context.createUnmarshaller();
                Korisnik korisnik = (Korisnik) unmarshaller.unmarshal(((XMLResource) res).getContentAsDOM());
                return korisnik;

            } catch (Exception e) {
                return null;
            }
        } else
            return null;
    }
}
