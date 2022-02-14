package com.example.sluzbenik_back.client;

import com.example.sluzbenik_back.model.obrazac_saglasnosti_za_imunizaciju.ListaSaglasnosti;
import com.example.sluzbenik_back.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost;
import com.example.sluzbenik_back.model.zahtev_za_sertifikatom.ListaZahteva;
import org.apache.commons.io.IOUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;

@org.springframework.stereotype.Service
public class SaglasnostClient {

    public static String BASE_URL = "http://localhost:8081/api";

    public static String URL_ENCODING = "UTF-8";

    public SaglasnostClient() {

    }

    public String getXml(String id) throws Exception {
        URL url = new URL(BASE_URL + "/saglasnost/xml/" + id);
        InputStream in = url.openStream();

        String txt = getStringFromInputStream(in);
        /*JAXBContext context = JAXBContext.newInstance(Saglasnost.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(txt);
        Saglasnost saglasnost = (Saglasnost) unmarshaller.unmarshal(reader);*/

        return txt;
    }

    public ListaSaglasnosti allXmlIdsByEmail(String email) throws Exception{
        URL url = new URL(BASE_URL + "/saglasnost/allXmlByEmail/" + email);
        InputStream in = url.openStream();

        String txt = getStringFromInputStream(in);
        JAXBContext context = JAXBContext.newInstance(ListaSaglasnosti.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(txt);
        ListaSaglasnosti saglasnosti = (ListaSaglasnosti) unmarshaller.unmarshal(reader);

        return saglasnosti;
    }

    public static String getStringFromInputStream(InputStream in) throws Exception {
        return new String(IOUtils.toByteArray(in), URL_ENCODING);
    }
}
