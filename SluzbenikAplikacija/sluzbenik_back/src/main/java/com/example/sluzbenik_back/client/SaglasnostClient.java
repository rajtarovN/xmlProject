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
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.io.IOUtils;

import com.example.sluzbenik_back.dto.IdentificationDTO;
import com.example.sluzbenik_back.dto.SaglasnostNaprednaDTO;
import com.example.sluzbenik_back.model.obrazac_saglasnosti_za_imunizaciju.ListaSaglasnosti;
//import com.sun.org.apache.bcel.internal.generic.IRETURN;

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
		/*
		 * JAXBContext context = JAXBContext.newInstance(Saglasnost.class); Unmarshaller
		 * unmarshaller = context.createUnmarshaller(); StringReader reader = new
		 * StringReader(txt); Saglasnost saglasnost = (Saglasnost)
		 * unmarshaller.unmarshal(reader);
		 */

		return txt;
	}

	public ListaSaglasnosti allXmlIdsByEmail(String email) throws Exception {
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

	public String getAllIds() throws Exception {
		System.out.println("Sent HTTP GET request to query saglasnost id's");
		URL url = new URL(BASE_URL + "/saglasnost/getAll");

		InputStream in = url.openStream();

		String txt = getStringFromInputStream(in);

		return txt;
	}

	public IdentificationDTO getByNaprednaPretraga(SaglasnostNaprednaDTO pretraga)
			throws HttpException, IOException, JAXBException {

		System.out.println("Sent HTTP POST request.");
		PostMethod post = new PostMethod(BASE_URL + "/saglasnost/naprednaPretraga");
		System.out.println(pretraga.toString());

		JAXBContext contextSaglasnost = JAXBContext.newInstance(SaglasnostNaprednaDTO.class);
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

	public IdentificationDTO getByObicnaPretraga(String searchTerm) throws Exception{
		URL url = new URL(BASE_URL + "/saglasnost/obicnaPretraga/" + searchTerm);
		InputStream in = url.openStream();

		String txt = getStringFromInputStream(in);
		JAXBContext context = JAXBContext.newInstance(IdentificationDTO.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		StringReader reader = new StringReader(txt);
		IdentificationDTO dto = (IdentificationDTO) unmarshaller.unmarshal(reader);

		return dto;
	}

	public String generateJson(String id) throws Exception{
		System.out.println("Sent HTTP GET request to query saglasnost id's");
		URL url = new URL(BASE_URL + "/saglasnost/generateJson/" + id);

		InputStream in = url.openStream();
		String s = getStringFromInputStream(in);

		return s;
	}

	public String generateRdf(String id) throws Exception{
		System.out.println("Sent HTTP GET request to query saglasnost id's");
		URL url = new URL(BASE_URL + "/saglasnost/generateRdf/" + id);

		InputStream in = url.openStream();
		String s = getStringFromInputStream(in);

		return s;
	}
}
