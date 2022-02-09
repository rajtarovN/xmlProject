package com.example.demo.repository;

import com.example.demo.dto.KorisnikPrijavaDTO;
import com.example.demo.util.ExistManager;
import org.exist.xupdate.XUpdateProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.Resource;

@Repository
public class KorisnikRepository{

    private String collectionId = "/db/portal";
    private String documentId = "korisnik.xml";

    private static final String TARGET_NAMESPACE = "http://www.ftn.uns.ac.rs/xml_i_veb_servisi/korisnik";

    public static final String APPEND = "<xu:modifications version=\"1.0\" xmlns:xu=\"" + XUpdateProcessor.XUPDATE_NS
            + "\" xmlns=\"" + TARGET_NAMESPACE + "\">" + "<xu:append select=\"%1$s\" child=\"last()\">%2$s</xu:append>"
            + "</xu:modifications>";

    public static final String UPDATE = "<xu:modifications version=\"1.0\" xmlns:xu=\"" + XUpdateProcessor.XUPDATE_NS
            + "\" xmlns=\"" + TARGET_NAMESPACE + "\">" + "<xu:update select=\"%1$s\">%2$s</xu:update>"
            + "</xu:modifications>";

    @Autowired
    private ExistManager existManager;

    public boolean prijava(KorisnikPrijavaDTO korisnik) {
        String xPath = "/lista_korisnika/korisnik[email='" + korisnik.getEmail() + "' and lozinka='"+korisnik.getLozinka()+"']";
        try {
            ResourceSet s = this.existManager.retrieve(collectionId, xPath, TARGET_NAMESPACE);
            return s.getSize()!=0;
        } catch (Exception e) {
            return false;
        }
    }

    public Resource pronadjiPoEmailu(String email) {
        String xPath = "/lista_korisnika/korisnik[email = '" + email + "']";
        try {
            ResourceSet set = this.existManager.retrieve(collectionId, xPath, TARGET_NAMESPACE);
            if(set.getSize() == 1)
                return set.getResource(0);
            else
                return null;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean postojiPoMejlu(String email) {
        String xPath = "/lista_korisnika/korisnik[email = '" + email + "']";
        try {
            return this.existManager.retrieve(collectionId, xPath, TARGET_NAMESPACE).getSize() != 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean registracija(String korisnik) {
        try {
            this.existManager.append(collectionId, documentId, "/lista_korisnika", korisnik, APPEND);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


}
