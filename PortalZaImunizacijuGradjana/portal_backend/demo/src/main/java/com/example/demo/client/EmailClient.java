package com.example.demo.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import com.example.demo.model.email.Email;

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

}
