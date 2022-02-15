package com.example.sluzbenik_back.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.input.ReaderInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sluzbenik_back.client.InteresovanjeClient;
import com.example.sluzbenik_back.model.dostupne_vakcine.Zalihe;
import com.example.sluzbenik_back.repository.DostupneVakcineRepository;

@Service
public class DostupneVakcineService extends AbstractService {

	@Autowired
    private InteresovanjeClient interesovanjeClient;
	
	@Autowired
	public DostupneVakcineService(DostupneVakcineRepository dostupneVakcineRepository) {

		super(dostupneVakcineRepository, "/db/sluzbenik/zalihe", "/zalihe/");
	}

	@Override
	public void saveXML(String documentId, String content) throws Exception {

		InputStream inputStream = new ReaderInputStream(new StringReader(content));

		JAXBContext context = JAXBContext.newInstance(Zalihe.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();

		Zalihe zalihe = (Zalihe) unmarshaller.unmarshal(inputStream);

		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		ByteArrayOutputStream stream = new ByteArrayOutputStream();

		marshaller.marshal(zalihe, stream);

		String finalString = new String(stream.toByteArray());
		System.out.println(finalString);

		content = finalString;

		repository.saveXML(documentId, collectionId, content);
		
	}
	

	public void saveWithResolvePending(String documentId, String content) throws Exception {

		this.saveXML(documentId, content);
		interesovanjeClient.sendOutPendingInteresovanja();
	}

}
