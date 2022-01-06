package rs.ac.uns.ftn.xml_i_veb_servisi.service;

import org.apache.commons.io.input.ReaderInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.xml_i_veb_servisi.model.digitalni_zeleni_sertifikat.DigitalniZeleniSertifikat;
import rs.ac.uns.ftn.xml_i_veb_servisi.repository.DigitalniSertifikatRepository;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.math.BigInteger;

@Service
public class DigitalniSertifikatService extends AbstractService{

    @Autowired
    public DigitalniSertifikatService(DigitalniSertifikatRepository repository) {
        super(repository, "/db/gradjanin/digitalnisertifikat/", "/digitalnisertifikat/");
    }

    @Override
    public void saveRDF(String content, String documentId) throws Exception {
        InputStream inputStream = new ReaderInputStream(new StringReader(content));

        JAXBContext context =
                JAXBContext.newInstance(DigitalniZeleniSertifikat.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        DigitalniZeleniSertifikat digitalniZeleniSertifikat = (DigitalniZeleniSertifikat) unmarshaller.unmarshal(inputStream);

        digitalniZeleniSertifikat.setIdSertifikata(BigInteger.valueOf(Long.parseLong(documentId)));

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        marshaller.marshal(digitalniZeleniSertifikat, stream);

        String finalString = new String(stream.toByteArray());
        System.out.println(finalString);

        content = finalString;

        repository.saveRDF(content, documentId);
    }

}
