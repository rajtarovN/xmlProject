package com.example.demo.service;

import com.example.demo.client.EmailClient;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.model.korisnik.Korisnik;
import com.example.demo.model.zahtev_za_sertifikatom.ListaZahteva;
import com.example.demo.model.zahtev_za_sertifikatom.ZahtevZaZeleniSertifikat;
import com.example.demo.repository.InteresovanjeRepository;
import com.example.demo.repository.PotvrdaVakcinacijeRepository;
import com.example.demo.repository.ZahtevRepository;
import com.example.demo.util.MetadataExtractor;
import com.example.demo.util.XSLFORTransformer;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.ReaderInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import static com.example.demo.util.PathConstants.SAVE_PDF;
import static com.example.demo.util.PathConstants.ZAHTEV_XSL_FO;

import static com.example.demo.util.PathConstants.*;


@Service
public class ZahtevService extends AbstractService {

    @Autowired
    private ZahtevRepository zahtevRepository;

    @Autowired
    private EmailClient emailClient;

    @Autowired
    private DigitalniSertifikatService digitalniSertifikatService;

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

        repository.saveXML("zahtev_"+documentId, collectionId, content);
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

    public String generateHTML(String id) throws XMLDBException {
        XSLFORTransformer transformer = null;

        try {
            transformer = new XSLFORTransformer();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        XMLResource xmlRes = this.readXML(id);
        String doc_str = xmlRes.getContent().toString();
        boolean ok = false;
        String html_path = SAVE_HTML + "zahtev_" + id + ".html";
        System.out.println(doc_str);

        try {
            ok = transformer.generateHTML(doc_str, html_path, ZAHTEV_ZA_SERTIFIKAT_XSL);
            if (ok)
                return html_path;
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

    public String odbijZahtev(String documentId, String razlog){
        try {
            ZahtevZaZeleniSertifikat zahtevZaZeleniSertifikat = setZahtevStatus(documentId, "odbijen");

            String finalString = marshal(zahtevZaZeleniSertifikat);
            System.out.println(finalString);

            repository.saveXML("zahtev_"+documentId, collectionId, finalString);
            repository.deleteRDF(documentId, "/lista_zahteva",
                    "http://www.ftn.uns.ac.rs/xml_i_veb_servisi/zahtev_za_sertifikatom/");
            zahtevRepository.saveRDF(finalString, "/lista_zahteva");

            String message = "Poštovani, \n Obaveštavamo vas da je vaš zahtev za digitalni sertifikat odbijen. Razlog odbijanja je: \n" + razlog;

            com.example.demo.model.email.Email emailModel = new com.example.demo.model.email.Email();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
            Date date = zahtevZaZeleniSertifikat.getPodnosilacZahteva().getDatumRodjenja().toGregorianCalendar().getTime();
            String email = zahtevZaZeleniSertifikat.getEmail();
            emailModel.setTo(email);
            emailModel.setContent(message);
            emailModel.setSubject("Odgovor na zahtev za digitalni sertifikat");
            emailClient.sendMail(emailModel);

            return "Uspesno odbijen zahtev za digitalni sertifikat!";
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Error pri odbijanju zahteva za digitalni sertifikat.");
        }
    }

    public String odobriZahtev(String documentId){
        try {
            ZahtevZaZeleniSertifikat zahtevZaZeleniSertifikat = setZahtevStatus(documentId, "odobren");

            String idSertifikata = digitalniSertifikatService.saveSertifikat(zahtevZaZeleniSertifikat);
            
            zahtevZaZeleniSertifikat.setSertifikat(new ZahtevZaZeleniSertifikat.Sertifikat());
            zahtevZaZeleniSertifikat.getSertifikat().setProperty("pred:seeAlso");
            zahtevZaZeleniSertifikat.getSertifikat().setValue(idSertifikata);
            
            String finalString = marshal(zahtevZaZeleniSertifikat);
            System.out.println(finalString);

            repository.saveXML("zahtev_"+documentId, collectionId, finalString);
            repository.deleteRDF(documentId, "/lista_zahteva",
                    "http://www.ftn.uns.ac.rs/xml_i_veb_servisi/zahtev_za_sertifikatom/");
            zahtevRepository.saveRDF(finalString, "/lista_zahteva");

            String message = "Poštovani, \n Obaveštavamo vas da je vaš zahtev za digitalni sertifikat odobren. \n";

            com.example.demo.model.email.Email emailModel = new com.example.demo.model.email.Email();
            String email = zahtevZaZeleniSertifikat.getEmail();
            emailModel.setTo(email);
            emailModel.setContent(message);
            emailModel.setSubject("Odgovor na zahtev za digitalni sertifikat");
            //TODO send pdf and xhtml
            //emailModel.setPdf();
            //emailModel.setXhtml();
            emailClient.sendMail(emailModel);

            return "Uspesno odobren zahtev za digitalni sertifikat!";
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Error pri odobravanju zahteva za digitalni sertifikat.");
        }
    }

    public String marshal(ZahtevZaZeleniSertifikat zahtevZaZeleniSertifikat) throws Exception{
        JAXBContext context = JAXBContext.newInstance(ZahtevZaZeleniSertifikat.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        marshaller.marshal(zahtevZaZeleniSertifikat, stream);

        return stream.toString();
    }

    public ZahtevZaZeleniSertifikat setZahtevStatus(String documentId, String statuss) throws Exception{
        ZahtevZaZeleniSertifikat zahtevZaZeleniSertifikat = pronadjiPoId(documentId);
        ZahtevZaZeleniSertifikat.Status status = new ZahtevZaZeleniSertifikat.Status();
        status.setValue(statuss);
        status.setProperty("pred:status");
        zahtevZaZeleniSertifikat.setStatus(status);
        return zahtevZaZeleniSertifikat;
    }

    public void saveRDF(String content, String uri) throws Exception {

        String jmbg = uri.split("_")[0];
        List<String> sviZahtevi = pronadjiPoStatusuIJmbg("na cekanju", jmbg);
        for(String s: sviZahtevi){
            String id = jmbg +"/"+s;
        repository.deleteRDF(id, "/lista_zahteva",
                    "http://www.ftn.uns.ac.rs/xml_i_veb_servisi/zahtev_za_sertifikatom/");
        }
            zahtevRepository.saveRDF(content, "/lista_zahteva");
    }

    private List<String> pronadjiPoStatusuIJmbg(String status, String jmbg) {
        List<String> ids = new ArrayList<>();
        try {
            ids = zahtevRepository.pronadjiPoStatusuIJmbg(status, jmbg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ids;
    }

    public String getListuZahtevaPoStatusuJMBG(String jmbg) throws Exception {

        List<String> ids = pronadjiPoStatusuIJmbg("na cekanju", jmbg);
        ListaZahteva listaZahteva = new ListaZahteva();
        List<ZahtevZaZeleniSertifikat> zahtevi = new ArrayList<>();
        for (String id : ids) {
            System.out.println(id+" ****");
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


    public byte[] generateJson(String documentId) throws Exception {
        String about = "http://www.ftn.uns.ac.rs/xml_i_veb_servisi/zahtev_za_sertifikatom/" + documentId;
        String graphUri = "/lista_zahteva";
        String documentNameId = "zahtev_" + documentId;
        String filePath = "src/main/resources/static/json/" + documentNameId + ".json";
        zahtevRepository.generateJson(documentNameId, graphUri, about);
        File file = new File(filePath);
        FileInputStream fileInputStream = new FileInputStream(file);

        return IOUtils.toByteArray(fileInputStream);
    }

    public byte[] generateRdf(String id) throws SAXException, IOException {
        String rdfFilePath = "src/main/resources/static/rdf/zahtev_" + id + ".rdf";
        String xmlFilePath = "src/main/resources/static/xml/zahtev_" + id + ".xml";
        MetadataExtractor metadataExtractor = new MetadataExtractor();
        String rs;
        FileWriter fw;
        try {
            XMLResource res = zahtevRepository.pronadjiPoId(id);
            rs = (String) res.getContent();
            fw = new FileWriter(xmlFilePath);
            fw.write(rs);
            fw.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        try {
            metadataExtractor.extractMetadata(
                    new FileInputStream(new File(xmlFilePath)),
                    new FileOutputStream(new File(rdfFilePath)));

            File file = new File(rdfFilePath);
            FileInputStream fileInputStream = new FileInputStream(file);

            return IOUtils.toByteArray(fileInputStream);

        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Error pri generisanju rdf zahteva.");
        }
    }
   
	public List<String> getZahtevRefFromSeeAlso(String seeAlso) throws Exception {
		return ((ZahtevRepository) this.repository).pronadjiPoSertifikatRef(seeAlso);
	}


}
