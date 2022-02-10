package com.example.demo.client;

import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;

import com.example.demo.model.dostupne_vakcine.Zalihe;

@org.springframework.stereotype.Service
public class DostupneVakcineClient {

	public static String BASE_URL = "http://localhost:8082/api";

	public static String URL_ENCODING = "UTF-8";

	public DostupneVakcineClient() {

	}

	public Zalihe getDostupneVakcine() throws Exception {
		System.out.println("Sent HTTP GET request to query customer info");
		URL url = new URL(BASE_URL + "/zalihe");

		InputStream in = url.openStream();

		String txt = getStringFromInputStream(in);

		JAXBContext context = JAXBContext.newInstance(Zalihe.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		StringReader reader = new StringReader(txt);
		Zalihe zalihe = (Zalihe) unmarshaller.unmarshal(reader);

		return zalihe;

	}

	public static String getStringFromInputStream(InputStream in) throws Exception {
		return new String(IOUtils.toByteArray(in), URL_ENCODING);
	}
}
