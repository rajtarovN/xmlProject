package com.example.demo.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.input.ReaderInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.client.DostupneVakcineClient;
import com.example.demo.model.dostupne_vakcine.Zalihe;
import com.example.demo.model.dostupne_vakcine.Zalihe.Vakcina;
import com.example.demo.model.interesovanje.Interesovanje;
import com.example.demo.repository.InteresovanjeRepository;

@Service
public class InteresovanjeService extends AbstractService{
	
	private DostupneVakcineClient dostupneVakcineClient;
	
	@Autowired
	public InteresovanjeService(InteresovanjeRepository interesovanjeRepository, DostupneVakcineClient dostupneVakcineClient) {

		super(interesovanjeRepository, "/db/portal/interesovanje", "/interesovanje/" );
		
		this.dostupneVakcineClient = dostupneVakcineClient;
	}
	
	@Override
	public void saveXML(String documentId, String content) throws Exception {

		InputStream inputStream = new ReaderInputStream(new StringReader(content));

		JAXBContext context = JAXBContext.newInstance(Interesovanje.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();

		Interesovanje interesovanje = (Interesovanje) unmarshaller.unmarshal(inputStream);

		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		ByteArrayOutputStream stream = new ByteArrayOutputStream();

		marshaller.marshal(interesovanje, stream);

		String finalString = new String(stream.toByteArray());
		System.out.println(finalString);

		content = finalString;

		Zalihe zalihe = this.dostupneVakcineClient.getDostupneVakcine();
		
		List<String> proizvodjaci = interesovanje.getProizvodjaci().getProizvodjac();

		String message = "";
		Boolean dostupno = false;
		for (Vakcina zaliha : zalihe.getVakcina()) {

			if((proizvodjaci.contains(zaliha.getNaziv()) || proizvodjaci.contains("Bilo koja")) && zaliha.getDostupno() - zaliha.getRezervisano() > 0) {
				dostupno = true;
				
				message += zaliha.getNaziv() + '\n';
			}
		}
		
		if(!dostupno) {
			
		}else {
			message = "Poštovani, žn Obaveštavamo vas da je izvršena prijava za vakcinaciju. Vaš termin je ... Dostupne vakcine su " + message;
		}
		repository.saveXML(documentId, collectionId, content);
	}

}
