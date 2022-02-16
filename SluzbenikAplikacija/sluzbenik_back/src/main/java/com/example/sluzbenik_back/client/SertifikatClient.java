package com.example.sluzbenik_back.client;

import com.example.sluzbenik_back.model.digitalni_zeleni_sertifikat.ListaSertifikata;
import com.example.sluzbenik_back.model.potvrda_o_vakcinaciji.ListaPotvrda;
import org.apache.commons.io.IOUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;

@org.springframework.stereotype.Service
public class SertifikatClient {

    public static String BASE_URL = "http://localhost:8081/api";

    public static String URL_ENCODING = "UTF-8";

    public SertifikatClient() {

    }

    public String getXml(String id) throws Exception {
        URL url = new URL(BASE_URL + "/sertifikat/xml/" + id);
        InputStream in = url.openStream();

        String txt = getStringFromInputStream(in);

        return txt;
    }

    public ListaSertifikata allXmlIdsByEmail(String email) throws Exception{
        URL url = new URL(BASE_URL + "/sertifikat/allXmlByEmail/" + email);
        InputStream in = url.openStream();

        String txt = getStringFromInputStream(in);
        JAXBContext context = JAXBContext.newInstance(ListaSertifikata.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(txt);
        ListaSertifikata lista = (ListaSertifikata) unmarshaller.unmarshal(reader);

        return lista;
    }

    public static String getStringFromInputStream(InputStream in) throws Exception {
        return new String(IOUtils.toByteArray(in), URL_ENCODING);
    }
}
