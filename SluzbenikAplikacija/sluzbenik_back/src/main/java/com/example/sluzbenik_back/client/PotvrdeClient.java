package com.example.sluzbenik_back.client;

import com.example.sluzbenik_back.model.obrazac_saglasnosti_za_imunizaciju.ListaSaglasnosti;
import com.example.sluzbenik_back.model.potvrda_o_vakcinaciji.ListaPotvrda;
import org.apache.commons.io.IOUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;

@org.springframework.stereotype.Service
public class PotvrdeClient {

    public static String BASE_URL = "http://localhost:8081/api";

    public static String URL_ENCODING = "UTF-8";

    public PotvrdeClient() {

    }

    public ListaPotvrda allXmlIdsByEmail(String email) throws Exception{
        URL url = new URL(BASE_URL + "/potvrda/allXmlByEmail/" + email);
        InputStream in = url.openStream();

        String txt = getStringFromInputStream(in);
        JAXBContext context = JAXBContext.newInstance(ListaPotvrda.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(txt);
        ListaPotvrda listaPotvrda = (ListaPotvrda) unmarshaller.unmarshal(reader);

        return listaPotvrda;
    }

    public static String getStringFromInputStream(InputStream in) throws Exception {
        return new String(IOUtils.toByteArray(in), URL_ENCODING);
    }
}