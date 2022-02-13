package com.example.sluzbenik_back.client;

import com.example.sluzbenik_back.model.korisnik.Korisnik;
import com.example.sluzbenik_back.model.korisnik.ListaKorisnika;
import org.apache.commons.io.IOUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.List;

@org.springframework.stereotype.Service
public class KorisnikClient {

    public static String BASE_URL = "http://localhost:8081/api";

    public static String URL_ENCODING = "UTF-8";

    public KorisnikClient() {

    }

    public ListaKorisnika getGradjane() throws Exception {
        URL url = new URL(BASE_URL + "/korisnik");

        InputStream in = url.openStream();

        String txt = getStringFromInputStream(in);

        JAXBContext context = JAXBContext.newInstance(ListaKorisnika.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(txt);
        ListaKorisnika zalihe = (ListaKorisnika) unmarshaller.unmarshal(reader);

        return zalihe;

    }

    public static String getStringFromInputStream(InputStream in) throws Exception {
        return new String(IOUtils.toByteArray(in), URL_ENCODING);
    }
}
