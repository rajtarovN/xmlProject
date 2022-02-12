package com.example.demo.service;

import com.example.demo.model.zahtev_za_sertifikatom.ZahtevZaZeleniSertifikat;
import com.example.demo.repository.ZahtevRepository;
import com.example.demo.util.XSLFORTransformer;
import org.apache.commons.io.input.ReaderInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;

import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import static com.example.demo.util.PathConstants.SAVE_PDF;
import static com.example.demo.util.PathConstants.ZAHTEV_XSL_FO;

@Service
public class ZahtevService extends AbstractService{

    @Autowired
    public ZahtevService(ZahtevRepository zahtevRepository) {

        super(zahtevRepository, "/db/portal/zahtev", "/zahtev/" );
    }

    @Override
    public void saveXML(String documentId, String content) throws Exception {
        InputStream inputStream = new ReaderInputStream(new StringReader(content));

        JAXBContext context = JAXBContext.newInstance(ZahtevZaZeleniSertifikat.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        ZahtevZaZeleniSertifikat zahtev = (ZahtevZaZeleniSertifikat) unmarshaller.unmarshal(inputStream);

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        marshaller.marshal(zahtev, stream);

        String finalString = new String(stream.toByteArray());
        System.out.println(finalString);

        content = finalString;

        repository.saveXML(documentId, collectionId, content);
    }
    public String generatePDF(String id) {
        XSLFORTransformer transformer = null;

        try {
            transformer = new XSLFORTransformer();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        XMLResource xmlRes = this.readXML(id);
        String doc_str = "";
        try {
            doc_str = xmlRes.getContent().toString();
            System.out.println(doc_str);
        } catch (XMLDBException e1) {
            e1.printStackTrace();
        }

        boolean ok = false;
        String pdf_path = SAVE_PDF + "zahtev_" + id.split(".xml")[0] + ".pdf";

        try {
            ok = transformer.generatePDF(doc_str, pdf_path, ZAHTEV_XSL_FO);
            if (ok)
                return pdf_path;
            else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
