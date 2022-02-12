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
import org.apache.commons.httpclient.NTCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;

import com.example.demo.model.dostupne_vakcine.Zalihe;
import com.example.demo.model.email.Email;
import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost;

@org.springframework.stereotype.Service
public class EmailClient {

	public static String BASE_URL = "http://localhost:8094/api";

	public static String URL_ENCODING = "UTF-8";

	public EmailClient() {

	}

	public void sendMail(Email email) throws HttpException, IOException, JAXBException {

        System.out.println("Sent HTTP POST request.");
        PostMethod post = new PostMethod(BASE_URL + "/email");
        System.out.println(email.toString());
        
        JAXBContext contextSaglasnost = JAXBContext.newInstance(Email.class);
		OutputStream os = new ByteArrayOutputStream();

		Marshaller marshallerSaglasnost = contextSaglasnost.createMarshaller();
		marshallerSaglasnost.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		marshallerSaglasnost.marshal(email, os);
	     System.out.println(os.toString());
        RequestEntity entity = new StringRequestEntity(os.toString(), "application/xml", URL_ENCODING);
        post.setRequestEntity(entity);
        HttpClient httpclient = new HttpClient();
        httpclient.executeMethod(post);

	}
	
	public void nesto() throws Exception {
		System.out.println("Sent HTTP GET request to query customer info");
		URL url = new URL(BASE_URL + "/email");

		InputStream in = url.openStream();

		String txt = getStringFromInputStream(in);
		System.out.println("CAOOOOOOOOOOOOOOOOOOO1");
		System.out.println(txt);
		System.out.println("CAOOOOOOOOOOOOOOOOOOO2");

	}

	public static String getStringFromInputStream(InputStream in) throws Exception {
		return new String(IOUtils.toByteArray(in), URL_ENCODING);
	}


}
