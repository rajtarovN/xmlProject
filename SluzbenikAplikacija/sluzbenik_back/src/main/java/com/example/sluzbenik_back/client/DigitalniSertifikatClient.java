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
import com.example.sluzbenik_back.dto.SertifikatNaprednaDTO;
import com.example.sluzbenik_back.model.digitalni_zeleni_sertifikat.ListaSertifikata;

@org.springframework.stereotype.Service
public class DigitalniSertifikatClient {

	public static String BASE_URL = "http://localhost:8081/api";

	public static String URL_ENCODING = "UTF-8";

	public DigitalniSertifikatClient() {

	}

	public String getAllIds() throws Exception {
		System.out.println("Sent HTTP GET request to query sertifikat id's");
		URL url = new URL(BASE_URL + "/sertifikat/getAll");

		InputStream in = url.openStream();

		String txt = getStringFromInputStream(in);

		return txt;
	}

	public int getNumberOfIzdatihSertifikata(String dateFrom, String dateTo) throws Exception {
		URL url = new URL(BASE_URL + "/sertifikat/getAllIssuedInDateRange/dateFrom/dateTo");

		InputStream in = url.openStream();

		String txt = getStringFromInputStream(in);
		System.out.println("Broj izdatih zelenih sertifikata:" + txt);
		return Integer.parseInt(txt);
	}

	public IdentificationDTO getByNaprednaPretraga(SertifikatNaprednaDTO pretraga)
			throws HttpException, IOException, JAXBException {

		System.out.println("Sent HTTP POST request.");
		PostMethod post = new PostMethod(BASE_URL + "/sertifikat/naprednaPretraga");
		System.out.println(pretraga.toString());

		JAXBContext contextSaglasnost = JAXBContext.newInstance(SertifikatNaprednaDTO.class);
		OutputStream os = new ByteArrayOutputStream();

		Marshaller marshallerPretraga = contextSaglasnost.createMarshaller();
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
			while ((end = is.read(buffer)) > 0) {
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

	public IdentificationDTO getByObicnaPretraga(String searchTerm) throws Exception{
		URL url = new URL(BASE_URL + "/sertifikat/obicnaPretraga/" + searchTerm);
		InputStream in = url.openStream();

		String txt = getStringFromInputStream(in);
		JAXBContext context = JAXBContext.newInstance(IdentificationDTO.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		StringReader reader = new StringReader(txt);
		IdentificationDTO dto = (IdentificationDTO) unmarshaller.unmarshal(reader);

		return dto;
	}
}
