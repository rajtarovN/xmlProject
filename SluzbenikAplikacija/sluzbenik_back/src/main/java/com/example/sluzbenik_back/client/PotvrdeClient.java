package com.example.sluzbenik_back.client;

import com.example.sluzbenik_back.dto.IdentificationDTO;
import com.example.sluzbenik_back.model.obrazac_saglasnosti_za_imunizaciju.ListaSaglasnosti;
import com.example.sluzbenik_back.model.potvrda_o_vakcinaciji.ListaPotvrda;
import com.example.sluzbenik_back.model.potvrda_o_vakcinaciji.PotvrdaOVakcinaciji;
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

    public String getXml(String id) throws Exception {
        URL url = new URL(BASE_URL + "/potvrda/xml/" + id);
        InputStream in = url.openStream();

        String txt = getStringFromInputStream(in);
        /*JAXBContext context = JAXBContext.newInstance(PotvrdaOVakcinaciji.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(txt);
        PotvrdaOVakcinaciji potvrdaOVakcinaciji = (PotvrdaOVakcinaciji) unmarshaller.unmarshal(reader);*/

        return txt;
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

    public IdentificationDTO getByObicnaPretraga(String searchTerm) throws Exception{
        URL url = new URL(BASE_URL + "/potvrda/obicnaPretraga/" + searchTerm);
        InputStream in = url.openStream();

        String txt = getStringFromInputStream(in);
        JAXBContext context = JAXBContext.newInstance(IdentificationDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(txt);
        IdentificationDTO dto = (IdentificationDTO) unmarshaller.unmarshal(reader);

        return dto;
    }

    public String getAllIds() throws Exception {
        System.out.println("Sent HTTP GET request to query saglasnost id's");
        URL url = new URL(BASE_URL + "/potvrda/getAll");

        InputStream in = url.openStream();

        String txt = getStringFromInputStream(in);

        return txt;
    }

    public String generateJson(String id) throws Exception{
        System.out.println("Sent HTTP GET request to query saglasnost id's");
        URL url = new URL(BASE_URL + "/potvrda/generateJson/" + id);

        InputStream in = url.openStream();
        String s = getStringFromInputStream(in);

        return s;
    }

    public String generateRdf(String id) throws Exception{
        System.out.println("Sent HTTP GET request to query saglasnost id's");
        URL url = new URL(BASE_URL + "/potvrda/generateRdf/" + id);

        InputStream in = url.openStream();
        String s = getStringFromInputStream(in);

        return s;
    }
}
