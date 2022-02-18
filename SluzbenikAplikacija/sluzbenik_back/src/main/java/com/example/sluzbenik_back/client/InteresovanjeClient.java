package com.example.sluzbenik_back.client;

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

@org.springframework.stereotype.Service
public class InteresovanjeClient {

	public static String BASE_URL = "http://localhost:8081/api/interesovanje";

	public static String URL_ENCODING = "UTF-8";

	public InteresovanjeClient() {

	}



	public void sendOutPendingInteresovanja() throws Exception {
		System.out.println("Sent HTTP GET request to resolve pending interesovanja");
		URL url = new URL(BASE_URL + "/updatePending");

		InputStream in = url.openStream();

		String txt = getStringFromInputStream(in);
		System.out.println(txt);

	}

	public int getNumberOfInteresovanja(String dateFrom, String dateTo) throws Exception {
		URL url = new URL(BASE_URL + "/getAllInDateRange/"+dateFrom+"/"+dateTo);

		InputStream in = url.openStream();

		String txt = getStringFromInputStream(in);
		System.out.println("Broj interesovanja:" + txt);
		return Integer.parseInt(txt);
	}
	
	public static String getStringFromInputStream(InputStream in) throws Exception {
		return new String(IOUtils.toByteArray(in), URL_ENCODING);
	}
}
