package com.example.demo.service;

import com.example.demo.client.EmailClient;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.model.zahtev_za_sertifikatom.ListaZahteva;
import com.example.demo.model.zahtev_za_sertifikatom.ZahtevZaZeleniSertifikat;
import com.example.demo.repository.ZahtevRepository;
import com.example.demo.util.XSLFORTransformer;
import org.apache.commons.io.input.ReaderInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.demo.util.PathConstants.SAVE_PDF;
import static com.example.demo.util.PathConstants.ZAHTEV_XSL_FO;

@Service
public class ZahtevService extends AbstractService {

    @Autowired
    private ZahtevRepository zahtevRepository;

    @Autowired
    private EmailClient emailClient;

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    public ZahtevService(ZahtevRepository zahtevRepository) {

        super(zahtevRepository, "/db/portal/lista_zahteva", "/lista_zahteva");
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

    public ZahtevZaZeleniSertifikat pronadjiPoId(String id) throws Exception {
        XMLResource res = zahtevRepository.pronadjiPoId(id);
        try {
            if (res != null) {

                JAXBContext context = JAXBContext.newInstance("com.example.demo.model.zahtev_za_sertifikatom");
                Unmarshaller unmarshaller = context.createUnmarshaller();
                ZahtevZaZeleniSertifikat s = (ZahtevZaZeleniSertifikat) unmarshaller
                        .unmarshal((res).getContentAsDOM());

                return s;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public String getListuZahtevaPoStatusu(String status) throws Exception {
        List<String> ids = pronadjiPoStatusu(status);
        ListaZahteva listaZahteva = new ListaZahteva();
        List<ZahtevZaZeleniSertifikat> zahtevi = new ArrayList<>();
        for (String id : ids) {
            ZahtevZaZeleniSertifikat z = pronadjiPoId(id);
            zahtevi.add(z);
        }
        listaZahteva.setZahtevi(zahtevi);

        JAXBContext context = JAXBContext.newInstance(ListaZahteva.class);
        OutputStream os = new ByteArrayOutputStream();

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        marshaller.marshal(listaZahteva, os);
        return os.toString();
    }

    public List<String> pronadjiPoStatusu(String status) {
        List<String> ids = new ArrayList<>();
        try {
            ids = zahtevRepository.pronadjiPoStatusu(status);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ids;
    }

    public String odbijZahtev(String documentId, String razlog) throws Exception {
        try {
            ZahtevZaZeleniSertifikat zahtevZaZeleniSertifikat = pronadjiPoId(documentId);
            zahtevZaZeleniSertifikat.setStatus("odbijen");
            JAXBContext context = JAXBContext.newInstance(ZahtevZaZeleniSertifikat.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            marshaller.marshal(zahtevZaZeleniSertifikat, stream);

            String finalString = stream.toString();
            System.out.println(finalString);

            repository.saveXML("zahtev_"+documentId, collectionId, finalString);
            //TODO update rdf
            zahtevRepository.saveRDF(finalString, "/lista_zahteva");

            //TODO email
            String message = "Poštovani, \n Obaveštavamo vas da je vaš zahtev za digitalni sertifikat odbijen. Razlog odbijanja je: \n" + razlog;

            com.example.demo.model.email.Email emailModel = new com.example.demo.model.email.Email();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
            Date date = zahtevZaZeleniSertifikat.getPodnosilacZahteva().getDatumRodjenja().toGregorianCalendar().getTime();
            String email = korisnikService.pronadjiEmail(zahtevZaZeleniSertifikat.getPodnosilacZahteva().getIme(),
                    zahtevZaZeleniSertifikat.getPodnosilacZahteva().getPrezime(),
                    ft.format(date));
            emailModel.setTo("rajtea6@gmail.com"); // TODO
            emailModel.setContent(message);
            emailModel.setSubject("Odgovor na zahtev za digitalni sertifikat");
            emailClient.sendMail(emailModel);

            return "Uspesno odbijen zahtev za digitalni sertifikat!";
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Error pri odbijanju zahteva za digitalni sertifikat.");
        }
    }

}
