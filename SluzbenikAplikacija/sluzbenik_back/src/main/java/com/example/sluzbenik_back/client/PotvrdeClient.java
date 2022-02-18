package com.example.sluzbenik_back.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.io.IOUtils;

import com.example.sluzbenik_back.dto.IdentificationDTO;
import com.example.sluzbenik_back.dto.PotvrdaNaprednaDTO;
import com.example.sluzbenik_back.model.potvrda_o_vakcinaciji.ListaPotvrda;

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
        System.out.println("Sent HTTP GET request to query potvrda id's");
        URL url = new URL(BASE_URL + "/potvrda/getAll");

        InputStream in = url.openStream();

        String txt = getStringFromInputStream(in);

        return txt;
    }

    public int getByPeriodAndDose(int doza, String odDatum, String doDatum) throws Exception {
        System.out.println("Sent HTTP GET request to query potvrde by date and dose");
        URL url = new URL(BASE_URL + "/potvrda/getByPeriodAndDose/"+ doza +"/"+odDatum+"/"+doDatum+"");

        InputStream in = url.openStream();

        String txt = getStringFromInputStream(in);

        return Integer.parseInt(txt);
    }

    public int getByPeriodAndManufactrer(String manufacturer, String odDatum, String doDatum) throws Exception {
        System.out.println("Sent HTTP GET request to query potvrde by date and manufacturer");
        URL url = new URL(BASE_URL + "/potvrda/getByPeriodAndManufacturer/"+ manufacturer +"/"+odDatum+"/"+doDatum+"");

        InputStream in = url.openStream();

        String txt = getStringFromInputStream(in);

        return Integer.parseInt(txt);
    }
    
    public String generateJson(String id) throws Exception{
        System.out.println("Sent HTTP GET request to query potvrda id's");
        URL url = new URL(BASE_URL + "/potvrda/generateJson/" + id);

        InputStream in = url.openStream();
        String s = getStringFromInputStream(in);

        return s;
    }

    public String generateRdf(String id) throws Exception{
        System.out.println("Sent HTTP GET request to query potvrda id's");
        URL url = new URL(BASE_URL + "/potvrda/generateRdf/" + id);

        InputStream in = url.openStream();
        String s = getStringFromInputStream(in);

        return s;
    }
    
    public IdentificationDTO getByNaprednaPretraga(PotvrdaNaprednaDTO pretraga)
			throws HttpException, IOException, JAXBException {

		System.out.println("Sent HTTP POST request.");
		PostMethod post = new PostMethod(BASE_URL + "/potvrda/naprednaPretraga");
		System.out.println(pretraga.toString());

		JAXBContext contextPotvrda = JAXBContext.newInstance(PotvrdaNaprednaDTO.class);
		OutputStream os = new ByteArrayOutputStream();

		Marshaller marshallerPretraga = contextPotvrda.createMarshaller();
		marshallerPretraga.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		marshallerPretraga.marshal(pretraga, os);
		RequestEntity entity = new StringRequestEntity(os.toString(), "application/xml", URL_ENCODING);
		post.setRequestEntity(entity);
		HttpClient httpclient = new HttpClient();
		httpclient.executeMethod(post);
		try {

			InputStream is = post.getResponseBodyAsStream();
			byte[] buffer = new byte[1024];
			ByteArrayOutputStream builder = new ByteArrayOutputStream();
			int end;
			while((end = is.read(buffer)) > 0){
				builder.write(buffer, 0, end);
			}
			String text = new String(builder.toByteArray(), StandardCharsets.UTF_8);
			JAXBContext context = JAXBContext.newInstance(IdentificationDTO.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			StringReader reader = new StringReader(text);
			IdentificationDTO ids = (IdentificationDTO) unmarshaller.unmarshal(reader);
			return ids;

		} finally {

			post.releaseConnection(); 
		}

	}
    
    public String getAllIdReferences(String id) throws Exception {
		System.out.println("Sent HTTP GET request to query potvrda references id's");
		URL url = new URL(BASE_URL + "/potvrda/referenciraniDoc/" + id);

		InputStream in = url.openStream();

		String txt = getStringFromInputStream(in);

		return txt;
	}


}
