package com.example.demo.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.input.ReaderInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exceptions.ForbiddenException;
import com.example.demo.model.interesovanje.Interesovanje;
import com.example.demo.model.korisnik.ListaKorisnika;
import com.example.demo.repository.InteresovanjeRepository;

@Service
public class InteresovanjeService extends AbstractService{

	protected String collectionId;

	protected String fusekiCollectionId;

	protected InteresovanjeRepository interesovanjeRepository;
	
	@Autowired
	public InteresovanjeService(InteresovanjeRepository interesovanjeRepository) {

		super(interesovanjeRepository, "/db/portal/interesovanje", "/interesovanje/" );
	}
	
	@Override
	public void saveXML(String documentId, String content) throws Exception {

		InputStream inputStream = new ReaderInputStream(new StringReader(content));

		JAXBContext context = JAXBContext.newInstance(Interesovanje.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();

		Interesovanje resenje = (Interesovanje) unmarshaller.unmarshal(inputStream);

		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		ByteArrayOutputStream stream = new ByteArrayOutputStream();

		marshaller.marshal(resenje, stream);

		String finalString = new String(stream.toByteArray());
		System.out.println(finalString);

		content = finalString;

		repository.saveXML(documentId, collectionId, content);
		repository.saveRDF(content, documentId);
	}

}
