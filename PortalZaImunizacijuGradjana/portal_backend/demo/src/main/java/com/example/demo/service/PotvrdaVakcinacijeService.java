package com.example.demo.service;

import com.example.demo.dto.EvidentiraneVakcineDTO;
import com.example.demo.dto.ListaEvidentiranihVakcina;
import com.example.demo.dto.PotvrdaVakcinacijeDTO;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.ListaSaglasnosti;
import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost;
import com.example.demo.model.potvrda_o_vakcinaciji.ListaPotvrda;
import com.example.demo.model.potvrda_o_vakcinaciji.PotvrdaOVakcinaciji;
import com.example.demo.repository.PotvrdaVakcinacijeRepository;
import com.example.demo.util.*;
import static com.example.demo.util.PathConstants.POTVRDA_O_VAKCINACIJI_XSL;
import static com.example.demo.util.PathConstants.POTVRDA_O_VAKCINACIJI_XSL_FO;
import static com.example.demo.util.PathConstants.SAVE_HTML;
import static com.example.demo.util.PathConstants.SAVE_PDF;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.io.IOUtils;
import org.exist.xmldb.EXistResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.*;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.demo.util.PathConstants.*;
import com.example.demo.dto.DokumentDTO;
import com.example.demo.dto.EvidentiraneVakcineDTO;
import com.example.demo.dto.ListaEvidentiranihVakcina;
import com.example.demo.dto.PotvrdaVakcinacijeDTO;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.ListaSaglasnosti;
import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost;
import com.example.demo.model.potvrda_o_vakcinaciji.ListaPotvrda;
import com.example.demo.model.potvrda_o_vakcinaciji.PotvrdaOVakcinaciji;
import com.example.demo.repository.PotvrdaVakcinacijeRepository;
import com.example.demo.util.MetadataExtractor;
import com.example.demo.util.XSLFORTransformer;

@Service
public class PotvrdaVakcinacijeService extends AbstractService {

    private static final String TARGET_NAMESPACE = "http://www.ftn.uns.ac.rs/xml_i_veb_servisi/potvrda_o_vakcinaciji";

    @Autowired
    private PotvrdaVakcinacijeRepository potvrdaVakcinacijeRepository;

    @Autowired
    private SaglasnostService saglasnostService;

    @Autowired
    public PotvrdaVakcinacijeService(PotvrdaVakcinacijeRepository repository) {
        super(repository, "/db/portal/lista_potvrda", "/lista_potvrda");
    }

    public List<String> pronadjiPoJmbgIDatumu(String jmbg, Date datumIzdavanja) {
        List<String> ids = new ArrayList<>();
        if (datumIzdavanja == null) {
            datumIzdavanja = new Date();
        }
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");

        try {
            ids = this.potvrdaVakcinacijeRepository.pronadjiPoJmbgIDatumu(jmbg, ft.format(datumIzdavanja));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ids;
    }

    public List<String> pronadjiPoEbsIDatumu(String ebs, Date datumIzdavanja) {
        List<String> ids = new ArrayList<>();
        if (datumIzdavanja == null) {
            datumIzdavanja = new Date();
        }
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");

        try {
            ids = this.potvrdaVakcinacijeRepository.pronadjiPoEbsIDatumu(ebs, ft.format(datumIzdavanja));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ids;
    }

    public PotvrdaOVakcinaciji pronadjiPoId(String id) throws IllegalAccessException, InstantiationException, JAXBException, ClassNotFoundException, XMLDBException, IOException {
        XMLResource res = this.potvrdaVakcinacijeRepository.pronadjiPoId(id);
        try {
            if (res != null) {

                JAXBContext context = JAXBContext.newInstance("com.example.demo.model.potvrda_o_vakcinaciji");

                Unmarshaller unmarshaller = context.createUnmarshaller();

                PotvrdaOVakcinaciji s = (PotvrdaOVakcinaciji) unmarshaller
                        .unmarshal((res).getContentAsDOM());

                return s;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public PotvrdaVakcinacijeDTO kreirajPotvrdu(Saglasnost saglasnost) {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        String datumIzdavanja = ft.format(new Date());
        Date dateRodj = saglasnost.getPacijent().getLicniPodaci().getDatumRodjenja().toGregorianCalendar().getTime();
        String zUstanova = saglasnost.getEvidencijaOVakcinaciji().getZdravstvenaUstanova();

        PotvrdaVakcinacijeDTO dto = new PotvrdaVakcinacijeDTO(
                saglasnost.getBrojSaglasnosti(), saglasnost.getPacijent().getLicniPodaci().getIme().getValue(),
                saglasnost.getPacijent().getLicniPodaci().getPrezime().getValue(), ft.format(dateRodj),
                saglasnost.getPacijent().getLicniPodaci().getPol(), zUstanova, datumIzdavanja);

        if (saglasnost.getPacijent().getStraniDrzavljanin() != null) {
            dto.setDrz("str");
            dto.setEbs(saglasnost.getPacijent().getStraniDrzavljanin().getIdentifikacija().getValue());
        } else {
            dto.setDrz("srb");
            dto.setJmbg(saglasnost.getPacijent().getDrzavljaninSrbije().getJmbg().getValue());
        }
        return dto;
    }

    public void savePotvrdu(String documentId, PotvrdaVakcinacijeDTO content) throws Exception {
        PotvrdaOVakcinaciji p = new PotvrdaOVakcinaciji();
        p.setSifraPotvrdeVakcine(documentId);
        p.setAbout(TARGET_NAMESPACE + "/" + documentId);
        p.setZdravstvenaUstanova(content.getzUstanova());

        PotvrdaOVakcinaciji.DatumIzdavanja datumIzdavanja = new PotvrdaOVakcinaciji.DatumIzdavanja();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        XMLGregorianCalendar datumXML = DatatypeFactory.newInstance().newXMLGregorianCalendar(ft.format(new Date()));
        datumIzdavanja.setValue(datumXML);
        datumIzdavanja.setProperty("pred:datum_izdavanja");
        p.setDatumIzdavanja(datumIzdavanja);

        PotvrdaOVakcinaciji.LicniPodaci lp = new PotvrdaOVakcinaciji.LicniPodaci();
        PotvrdaOVakcinaciji.LicniPodaci.Ime ime = new PotvrdaOVakcinaciji.LicniPodaci.Ime();
        ime.setValue(content.getIme());
        ime.setProperty("pred:ime");
        lp.setIme(ime);

        PotvrdaOVakcinaciji.LicniPodaci.Prezime prezime = new PotvrdaOVakcinaciji.LicniPodaci.Prezime();
        prezime.setValue(content.getPrezime());
        prezime.setProperty("pred:prezime");
        lp.setPrezime(prezime);

        Date date = format.parse(content.getDatumRodjenja());
        datumXML = DatatypeFactory.newInstance().newXMLGregorianCalendar(format.format(date));
        lp.setDatumRodjenja(datumXML);

        lp.setPol(content.getPol());
        if (content.getDrz().equals("srb")) {
            PotvrdaOVakcinaciji.LicniPodaci.Jmbg jmbg = new PotvrdaOVakcinaciji.LicniPodaci.Jmbg();
            jmbg.setValue(content.getJmbg());
            jmbg.setProperty("pred:jmbg");
            lp.setJmbg(jmbg);
            p.setQrKod(QRCodeService.getQRCode("http://localhost:4200/potvrda_o_vakcinaciji/"+content.getJmbg()));
        }
        else{
            PotvrdaOVakcinaciji.LicniPodaci.Ebs ebs = new PotvrdaOVakcinaciji.LicniPodaci.Ebs();
            ebs.setValue(content.getEbs());
            ebs.setProperty("pred:ebs");
            lp.setEbs(ebs);
            p.setQrKod(QRCodeService.getQRCode("http://localhost:4200/potvrda_o_vakcinaciji/"+content.getEbs()));
        }

        p.setLicniPodaci(lp);

        saveXMl(p, "potvrda_" + documentId);
    }

    public String saveDoze(String documentId, ListaEvidentiranihVakcina evidentiraneVakcineDTO, String email) throws Exception {
        PotvrdaOVakcinaciji potvrdaOVakcinaciji = pronadjiPoId(documentId);
        PotvrdaOVakcinaciji.Vakcinacija vakcinacija = new PotvrdaOVakcinaciji.Vakcinacija();
        PotvrdaOVakcinaciji.Vakcinacija.Doze doze = new PotvrdaOVakcinaciji.Vakcinacija.Doze();
        List<PotvrdaOVakcinaciji.Vakcinacija.Doze.Doza> lista = new ArrayList<>();
        int i = 0;
        String lastVaxName = "";
        for (EvidentiraneVakcineDTO vakcinaDto : evidentiraneVakcineDTO.getEvidentiraneVakcineDTO()) {
            i += 1;
            PotvrdaOVakcinaciji.Vakcinacija.Doze.Doza doza = new PotvrdaOVakcinaciji.Vakcinacija.Doze.Doza();
            XMLGregorianCalendar result1 = DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(vakcinaDto.getDatumDavanja());
            doza.setDatumDavanja(result1);
            doza.setBrojSerije(vakcinaDto.getSerijaVakcine());
            doza.setBroj(BigInteger.valueOf(i));
            doza.setNazivVakcine(vakcinaDto.getNazivVakcine());
            lastVaxName = vakcinaDto.getNazivVakcine();
            lista.add(doza);
        }
        doze.setDoze(lista);
        vakcinacija.setDoze(doze);
        potvrdaOVakcinaciji.setVakcinacija(vakcinacija);

        String finalString = saveXMl(potvrdaOVakcinaciji, "potvrda_" + documentId);
        repository.saveRDF(finalString, fusekiCollectionId);

        String ime = potvrdaOVakcinaciji.getLicniPodaci().getIme().getValue();
        String prezime = potvrdaOVakcinaciji.getLicniPodaci().getPrezime().getValue();
        saglasnostService.createNextAppointment(i, lastVaxName, email, ime, prezime);

        return "Uspesno izdata potvrda o vakcinaciji.";
    }

    public String saveXMl(PotvrdaOVakcinaciji potvrdaOVakcinaciji, String documentId) throws Exception {
        JAXBContext context = JAXBContext.newInstance("com.example.demo.model.potvrda_o_vakcinaciji");

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        marshaller.marshal(potvrdaOVakcinaciji, stream);

        String finalString = stream.toString();
        System.out.println(finalString);

        repository.saveXML(documentId, collectionId, finalString);
        return finalString;
    }

    public String generatePDF(String id) {
        XSLFORTransformer transformer = null;

        try {
            transformer = new XSLFORTransformer();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        XMLResource xmlRes = this.readXML("potvrda_"+id+".xml");
        String doc_str = "";
        try {
            doc_str = xmlRes.getContent().toString();
            System.out.println(doc_str);
        } catch (XMLDBException e1) {
            e1.printStackTrace();
        }

        boolean ok = false;
        String pdf_path = SAVE_PDF + "potvrda_" + id.split(".xml")[0] + ".pdf";

        try {
            ok = transformer.generatePDF(doc_str, pdf_path, POTVRDA_O_VAKCINACIJI_XSL_FO);
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

        XMLResource xmlRes = this.readXML("potvrda_"+id+".xml");
        String doc_str = xmlRes.getContent().toString();
        boolean ok = false;
        String html_path = SAVE_HTML + "potvrda_" + id + ".html";
        System.out.println(doc_str);

        try {
            ok = transformer.generateHTML(doc_str, html_path, POTVRDA_O_VAKCINACIJI_XSL);
            if (ok)
                return html_path;
            else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String allXmlByEmail(String email) throws Exception {
        Saglasnost saglasnost = saglasnostService.pronadjiSaglasnostPoEmailu(email);
        List<String> ids = new ArrayList<>();
        List<PotvrdaOVakcinaciji> ret = new ArrayList<>();
        if (saglasnost != null) {
            if (saglasnost.getPacijent().getStraniDrzavljanin() != null &&
                    saglasnost.getPacijent().getStraniDrzavljanin().getIdentifikacija() != null &&
                    saglasnost.getPacijent().getStraniDrzavljanin().getIdentifikacija().getValue() != null) {
                ids = potvrdaVakcinacijeRepository.pronadjiPoEbs(saglasnost.getPacijent().getStraniDrzavljanin().getIdentifikacija().getValue());
            } else {
                ids = potvrdaVakcinacijeRepository.pronadjiPoJmbg(saglasnost.getPacijent().getDrzavljaninSrbije().getJmbg().getValue());
            }

            if (!ids.isEmpty()) {
                for (String id : ids) {
                    PotvrdaOVakcinaciji potvrdaOVakcinaciji = pronadjiPoId(id);
                    if (potvrdaOVakcinaciji != null) {
                        ret.add(potvrdaOVakcinaciji);
                    }
                }
            }
        }
        ListaPotvrda listaPotvrda = new ListaPotvrda();
        listaPotvrda.setPotvrde(ret);
        JAXBContext context = JAXBContext.newInstance(ListaPotvrda.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        marshaller.marshal(listaPotvrda, stream);

        return stream.toString();
    }

    public XMLResource getXML(String documentId) throws IllegalAccessException, InstantiationException, JAXBException, ClassNotFoundException, XMLDBException, IOException {
        return this.potvrdaVakcinacijeRepository.pronadjiPoId(documentId);
    }

    public List<DokumentDTO> getPotvrdaAllByEmail(String email) {
        try {
            System.out.println("OVDEEEEEE");
            String all = this.allXmlByEmail(email);

            JAXBContext context = JAXBContext.newInstance(ListaPotvrda.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader reader = new StringReader(all);
            ListaPotvrda potvrde = (ListaPotvrda) unmarshaller.unmarshal(reader);
            List<DokumentDTO> ret = new ArrayList<>();
            for (PotvrdaOVakcinaciji s : potvrde.getPotvrde()) {
                ret.add(new DokumentDTO(s));
            }
            System.out.println("OVDEEEEEE");
            return ret;

        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Error pri dobavljanju potvrda.");
        }
    }

    public List<String> getAllPotvrde() throws IOException {
        return this.potvrdaVakcinacijeRepository.readAllDocumentIds(fusekiCollectionId);
    }

    public List<String> obicnaPretraga(String searchTerm) throws Exception {
        List<String> filteredIds = new ArrayList<>();
        ResourceSet result = this.potvrdaVakcinacijeRepository.obicnaPretraga(searchTerm);
        ResourceIterator i = result.getIterator();
        Resource res = null;
        JAXBContext context = JAXBContext.newInstance(PotvrdaOVakcinaciji.class);

        while (i.hasMoreResources()) {
            try {
                Unmarshaller unmarshaller = context.createUnmarshaller();
                res = i.nextResource();
                PotvrdaOVakcinaciji r = (PotvrdaOVakcinaciji) unmarshaller.unmarshal(((XMLResource) res).getContentAsDOM());

                String about = r.getAbout();

                filteredIds.add(about);
            } finally {
                try {
                    ((EXistResource) res).freeResources();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
        }
        return filteredIds;
    }

    public String getByPeriodAndDose(int doza, String odDatum, String doDatum) throws IOException, JAXBException, XMLDBException, ClassNotFoundException, IllegalAccessException, InstantiationException, DatatypeConfigurationException, ParseException {
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        XMLGregorianCalendar pocetak = DatatypeFactory.newInstance().newXMLGregorianCalendar(ft.format(ft.parse(odDatum)));
        XMLGregorianCalendar kraj = DatatypeFactory.newInstance().newXMLGregorianCalendar(ft.format(ft.parse(doDatum)));

        try{
            List<String> sviId = this.getAllPotvrde();
            int brVakcina = 0;
            for(String id : sviId){
                PotvrdaOVakcinaciji potvrda = this.pronadjiPoId(id);
                if( potvrda.getDatumIzdavanja().getValue().compare(kraj) ==  DatatypeConstants.LESSER &&
                        potvrda.getDatumIzdavanja().getValue().compare(pocetak) == DatatypeConstants.GREATER &&
                        potvrda.getVakcinacija().getDoze().getDoza().size()+1 >= doza){
                    brVakcina+=1;
                }
            }

            return String.valueOf(brVakcina);
        }
        catch (Exception e){
            return "0";
        }
    }

    public String getByPeriodAndManufacturer(String proizvodjac, String odDatum, String doDatum) throws IOException, JAXBException, XMLDBException, ClassNotFoundException, IllegalAccessException, InstantiationException, DatatypeConfigurationException, ParseException {
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        XMLGregorianCalendar pocetak = DatatypeFactory.newInstance().newXMLGregorianCalendar(ft.format(ft.parse(odDatum)));
        XMLGregorianCalendar kraj = DatatypeFactory.newInstance().newXMLGregorianCalendar(ft.format(ft.parse(doDatum)));

        try{
            List<String> sviId = this.getAllPotvrde();
            int brVakcina = 0;
            for(String id : sviId){
                PotvrdaOVakcinaciji potvrda = this.pronadjiPoId(id);
                if( potvrda.getDatumIzdavanja().getValue().compare(kraj) ==  DatatypeConstants.LESSER &&
                        potvrda.getDatumIzdavanja().getValue().compare(pocetak) == DatatypeConstants.GREATER){
                    for(PotvrdaOVakcinaciji.Vakcinacija.Doze.Doza doza : potvrda.getVakcinacija().getDoze().getDoza()){
                        if(doza.getNazivVakcine().contains(proizvodjac)){
                            brVakcina+=1;
                        }
                    }

                }
            }

            return String.valueOf(brVakcina);
        }
        catch (Exception e){
            return "0";
        }

    }
    
    public byte[] generateJson(String documentId) throws Exception {
        String about = "http://www.ftn.uns.ac.rs/xml_i_veb_servisi/potvrda_o_vakcinaciji/" + documentId;
        String graphUri = "/lista_potvrda";
        String documentNameId = "potvrda_" + documentId;
        String filePath = "src/main/resources/static/json/" + documentNameId + ".json";
        this.potvrdaVakcinacijeRepository.generateJson(documentNameId, graphUri, about);
        File file = new File(filePath);
        FileInputStream fileInputStream = new FileInputStream(file);

        return IOUtils.toByteArray(fileInputStream);
    }

    public byte[] generateRdf(String id) throws SAXException, IOException {
        String rdfFilePath = "src/main/resources/static/rdf/potvrda_" + id + ".rdf";
        String xmlFilePath = "src/main/resources/static/xml/potvrda_" + id + ".xml";
        MetadataExtractor metadataExtractor = new MetadataExtractor();
        String rs;
        FileWriter fw;
        try {
            XMLResource res = this.potvrdaVakcinacijeRepository.pronadjiPoId(id);
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
            throw new BadRequestException("Error pri generisanju rdf potvrde.");
        }
    }

	public List<String> getDocumentIdReferences(String id) throws Exception {
		List<String> refs = new ArrayList<>();
		String id1 = saglasnostService.getSaglasnostRefFromSeeAlso(id);
		refs.add(id1);
		return refs;
	}
        
    public List<String> naprednaPretraga(String ime, String prezime, String id, String datum, boolean and) throws Exception {
    	return this.potvrdaVakcinacijeRepository.naprednaPretraga(ime, prezime, id, datum, and);
	}
}
