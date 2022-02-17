package com.example.demo.service;

import com.example.demo.dto.EvidentiraneVakcineDTO;
import com.example.demo.dto.ListaEvidentiranihVakcina;
import com.example.demo.dto.PotvrdaVakcinacijeDTO;
import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.ListaSaglasnosti;
import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost;
import com.example.demo.model.potvrda_o_vakcinaciji.ListaPotvrda;
import com.example.demo.model.potvrda_o_vakcinaciji.PotvrdaOVakcinaciji;
import com.example.demo.repository.PotvrdaVakcinacijeRepository;
import com.example.demo.util.DBManager;
import com.example.demo.util.FusekiManager;
import com.example.demo.util.MetadataExtractor;
import com.example.demo.util.XSLFORTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.*;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.demo.util.PathConstants.*;
import static com.example.demo.util.PathConstants.ZAHTEV_ZA_SERTIFIKAT_XSL;

@Service
public class PotvrdaVakcinacijeService  extends AbstractService {

    private static final String TARGET_NAMESPACE = "http://www.ftn.uns.ac.rs/xml_i_veb_servisi/potvrda_o_vakcinaciji";

    @Autowired
    private PotvrdaVakcinacijeRepository potvrdaVakcinacijeRepository;

    @Autowired
    private SaglasnostService saglasnostService;

    @Autowired
    private DBManager dbManager;

    @Autowired
    private FusekiManager fusekiManager;

    @Autowired
    public PotvrdaVakcinacijeService(PotvrdaVakcinacijeRepository repository) {
        super(repository,"/db/portal/lista_potvrda", "/lista_potvrda");
    }

    public List<String> pronadjiPoJmbgIDatumu(String jmbg, Date datumIzdavanja) {
        List<String> ids = new ArrayList<>();
        if(datumIzdavanja == null){
            datumIzdavanja = new Date();
        }
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");

        try {
            ids = this.potvrdaVakcinacijeRepository.pronadjiPoJmbgIDatumu(jmbg, ft.format(datumIzdavanja));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ids;
    }

    public List<String> pronadjiPoEbsIDatumu(String ebs, Date datumIzdavanja) {
        List<String> ids = new ArrayList<>();
        if(datumIzdavanja == null){
            datumIzdavanja = new Date();
        }
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");

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

    public PotvrdaVakcinacijeDTO kreirajPotvrdu(Saglasnost saglasnost){
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        String datumIzdavanja = ft.format(new Date());
        Date dateRodj = saglasnost.getPacijent().getLicniPodaci().getDatumRodjenja().toGregorianCalendar().getTime();
        String zUstanova = saglasnost.getEvidencijaOVakcinaciji().getZdravstvenaUstanova();


        PotvrdaVakcinacijeDTO dto = new PotvrdaVakcinacijeDTO(
                        saglasnost.getBrojSaglasnosti(), saglasnost.getPacijent().getLicniPodaci().getIme().getValue(),
                        saglasnost.getPacijent().getLicniPodaci().getPrezime().getValue(), ft.format(dateRodj),
                        saglasnost.getPacijent().getLicniPodaci().getPol(), zUstanova, datumIzdavanja);

        if(saglasnost.getPacijent().getStraniDrzavljanin() != null){
            dto.setDrz("str");
            dto.setEbs(saglasnost.getPacijent().getStraniDrzavljanin().getIdentifikacija().getValue());
        }else{
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
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
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
        if(content.getDrz().equals("srb")){
            PotvrdaOVakcinaciji.LicniPodaci.Jmbg jmbg = new PotvrdaOVakcinaciji.LicniPodaci.Jmbg();
            jmbg.setValue(content.getJmbg());
            jmbg.setProperty("pred:jmbg");
            lp.setJmbg(jmbg);
        }
        else{
            PotvrdaOVakcinaciji.LicniPodaci.Ebs ebs = new PotvrdaOVakcinaciji.LicniPodaci.Ebs();
            ebs.setValue(content.getEbs());
            ebs.setProperty("pred:ebs");
            lp.setEbs(ebs);
        }

        p.setLicniPodaci(lp);

        saveXMl(p, "potvrda_" + documentId);
    }

    public String saveDoze(String documentId, ListaEvidentiranihVakcina evidentiraneVakcineDTO) throws Exception {
        PotvrdaOVakcinaciji potvrdaOVakcinaciji = pronadjiPoId(documentId);
        PotvrdaOVakcinaciji.Vakcinacija vakcinacija = new PotvrdaOVakcinaciji.Vakcinacija();
        PotvrdaOVakcinaciji.Vakcinacija.Doze doze = new PotvrdaOVakcinaciji.Vakcinacija.Doze();
        List<PotvrdaOVakcinaciji.Vakcinacija.Doze.Doza> lista = new ArrayList<>();
        int i = 0;

        for (EvidentiraneVakcineDTO vakcinaDto: evidentiraneVakcineDTO.getEvidentiraneVakcineDTO()){
            i += 1;
            PotvrdaOVakcinaciji.Vakcinacija.Doze.Doza doza = new PotvrdaOVakcinaciji.Vakcinacija.Doze.Doza();
            XMLGregorianCalendar result1 = DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(vakcinaDto.getDatumDavanja());
            doza.setDatumDavanja(result1);
            doza.setBrojSerije(vakcinaDto.getSerijaVakcine());
            doza.setBroj(BigInteger.valueOf(i));
            doza.setNazivVakcine(vakcinaDto.getNazivVakcine());
            lista.add(doza);
        }
        doze.setDoze(lista);
        vakcinacija.setDoze(doze);
        potvrdaOVakcinaciji.setVakcinacija(vakcinacija);

        String finalString = saveXMl(potvrdaOVakcinaciji, "potvrda_" + documentId);
        repository.saveRDF(finalString, fusekiCollectionId);
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

        XMLResource xmlRes = this.readXML(id);
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

        XMLResource xmlRes = this.readXML(id);
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
        if(saglasnost != null){
            if(saglasnost.getPacijent().getStraniDrzavljanin() != null &&
                    saglasnost.getPacijent().getStraniDrzavljanin().getIdentifikacija() != null &&
                    saglasnost.getPacijent().getStraniDrzavljanin().getIdentifikacija().getValue() != null){
                ids = potvrdaVakcinacijeRepository.pronadjiPoEbs(email);
            }else{
                ids = potvrdaVakcinacijeRepository.pronadjiPoJmbg(email);
            }

            if(!ids.isEmpty()){
                for (String id: ids) {
                    PotvrdaOVakcinaciji potvrdaOVakcinaciji = pronadjiPoId(id);
                    if(potvrdaOVakcinaciji != null){
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

    public List<com.example.sluzbenik_back.dto.DokumentDTO> getPotvrdaAllByEmail(String email){
        try {
            System.out.println("OVDEEEEEE");
            String all = this.allXmlByEmail(email);

            JAXBContext context = JAXBContext.newInstance(ListaSaglasnosti.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader reader = new StringReader(all);
            ListaPotvrda potvrde = (ListaPotvrda) unmarshaller.unmarshal(reader);
            List<com.example.sluzbenik_back.dto.DokumentDTO> ret = new ArrayList<>();
            for (PotvrdaOVakcinaciji s: potvrde.getPotvrde()) {
                ret.add(new com.example.sluzbenik_back.dto.DokumentDTO(s));
            }System.out.println("OVDEEEEEE");
            return ret;

        }  catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
