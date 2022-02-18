package com.example.demo.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.URL;

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

import com.example.demo.model.dostupne_vakcine.Zalihe;

@org.springframework.stereotype.Service
public class DostupneVakcineClient {

	public static String BASE_URL = "http://localhost:8082/api";

	public static String URL_ENCODING = "UTF-8";

	public DostupneVakcineClient() {

	}

	public Zalihe getDostupneVakcine() throws Exception {
		System.out.println("Sent HTTP GET request to query zalihe");
		URL url = new URL(BASE_URL + "/zalihe");

		InputStream in = url.openStream();

		String txt = getStringFromInputStream(in);

		JAXBContext context = JAXBContext.newInstance(Zalihe.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		StringReader reader = new StringReader(txt);
		Zalihe zalihe = (Zalihe) unmarshaller.unmarshal(reader);

		return zalihe;

	}

	public void updateVakcine(Zalihe zalihe) throws HttpException, IOException, JAXBException {

		System.out.println("Sent HTTP POST request.");
		PostMethod post = new PostMethod(BASE_URL + "/zalihe");
		System.out.println(zalihe.toString());

		JAXBContext contextSaglasnost = JAXBContext.newInstance(Zalihe.class);
		OutputStream os = new ByteArrayOutputStream();

		Marshaller marshallerZalihe= contextSaglasnost.createMarshaller();
		marshallerZalihe.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		marshallerZalihe.marshal(zalihe, os);
		System.out.println(os.toString());
		RequestEntity entity = new StringRequestEntity(os.toString(), "application/xml", URL_ENCODING);
		post.setRequestEntity(entity);
		HttpClient httpclient = new HttpClient();
		httpclient.executeMethod(post);

	}
	
	public static String getStringFromInputStream(InputStream in) throws Exception {
		return new String(IOUtils.toByteArray(in), URL_ENCODING);
	}
}
