package com.example.sluzbenik_back.service;

import com.example.sluzbenik_back.client.DigitalniSertifikatClient;
import com.example.sluzbenik_back.client.InteresovanjeClient;
import com.example.sluzbenik_back.client.PotvrdeClient;
import com.example.sluzbenik_back.client.ZahtevClient;
import com.example.sluzbenik_back.dto.DokumentDTO;
import com.example.sluzbenik_back.model.izvestaj_o_imunizaciji.*;
import com.example.sluzbenik_back.repository.IzvestajRepository;
import com.example.sluzbenik_back.repository.RepositoryInterface;
import com.example.sluzbenik_back.util.XSLFORTransformer;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.XMLDBException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.sluzbenik_back.util.PathConstants.*;
import static com.example.sluzbenik_back.util.PathConstants.DIGITALNISERTIFIKAT_XSL;

@Service
public class IzvestajService extends AbstractService{


    @Autowired
    private InteresovanjeClient interesovanjeClient;

    @Autowired
    private DigitalniSertifikatClient digitalniSertifikatClient;

    @Autowired
    private ZahtevClient zahtevClient;

    @Autowired
    private PotvrdeClient potvrdeClient;

    public IzvestajService(IzvestajRepository repository) {
        super(repository, "/db/sluzbenik/lista_izvestaja", "/lista_izvestaja");
    }

    public String createIzvestaj(String fromDate, String toDate) throws Exception {
        LocalDate todayDate = LocalDate.now();

        IzvestajOImunizaciji izvestaj = new IzvestajOImunizaciji();

        //period izvestaja
        PeriodIzvestaja period = new PeriodIzvestaja();
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        XMLGregorianCalendar pocetak = DatatypeFactory.newInstance().newXMLGregorianCalendar(ft.format(ft.parse(fromDate)));
        XMLGregorianCalendar kraj = DatatypeFactory.newInstance().newXMLGregorianCalendar(ft.format(ft.parse(toDate)));
        period.setPocetakPerioda(pocetak);
        period.setKrajPerioda(kraj);
        izvestaj.setPeriodIzvestaja(period);

        // podaci o zahtevima
        PodaciOZahtevima podaciOZahtevima = new PodaciOZahtevima();
        int brojInteresovanja = interesovanjeClient.getNumberOfInteresovanja(fromDate, toDate);
        podaciOZahtevima.setBrojDokumenataOInteresovanju(BigInteger.valueOf(brojInteresovanja));

        int brojSertifikataNaCekanju = zahtevClient.getZahteveNaCekanjuUPeriodu(fromDate, toDate);
        int brojIzdatihSertifikata = digitalniSertifikatClient.getNumberOfIzdatihSertifikata(fromDate, toDate);

        PodaciOZahtevima.BrojZahteva brojZahteva = new PodaciOZahtevima.BrojZahteva();
        brojZahteva.setPrimljeno(BigInteger.valueOf(brojIzdatihSertifikata+brojSertifikataNaCekanju));
        brojZahteva.setIzdato(BigInteger.valueOf(brojIzdatihSertifikata));

        podaciOZahtevima.setBrojZahteva(brojZahteva);
        izvestaj.setPodaciOZahtevima(podaciOZahtevima);

        //lista vakcina
        ListaVakcina listaVakcina = new ListaVakcina();
        List<Vakcina> lista = new ArrayList<Vakcina>();
        //prva doza
        Vakcina v1 = new Vakcina();
        v1.setRedniBrojDoze(BigInteger.valueOf(1));
        v1.setBrojDatihDoza(BigInteger.valueOf(potvrdeClient.getByPeriodAndDose(1, fromDate, toDate)));
        //druga doza
        Vakcina v2 = new Vakcina();
        v2.setRedniBrojDoze(BigInteger.valueOf(1));
        v2.setBrojDatihDoza(BigInteger.valueOf(potvrdeClient.getByPeriodAndDose(2, fromDate, toDate)));
        //treca doza
        Vakcina v3 = new Vakcina();
        v3.setRedniBrojDoze(BigInteger.valueOf(1));
        v3.setBrojDatihDoza(BigInteger.valueOf(potvrdeClient.getByPeriodAndDose(3, fromDate, toDate)));

        lista.add(v1);
        lista.add(v2);
        lista.add(v3);
        listaVakcina.setVakcina(lista);
        izvestaj.setListaVakcina(listaVakcina);

        //proizvodjaci
        RaspodelaPoProizvodjacima raspodelaPoProizvodjacima = new RaspodelaPoProizvodjacima();
        List<Proizvodjac> listaProizovdjaca = new ArrayList<Proizvodjac>();
        List<String> imenaProizvodjaca = new ArrayList<>();
        imenaProizvodjaca.add("Pfizer, BioNTech");
        imenaProizvodjaca.add("Sinopharm");
        imenaProizvodjaca.add("Sputnik V");
        imenaProizvodjaca.add("AstraZeneca, Oxford");

        for(String proizvodjac : imenaProizvodjaca){
            Proizvodjac p = new Proizvodjac();
            p.setImeProizvodjaca(proizvodjac);
            p.setBrojDoza(BigInteger.valueOf(potvrdeClient.getByPeriodAndManufactrer(proizvodjac, fromDate, toDate)));
            listaProizovdjaca.add(p);
        }
        raspodelaPoProizvodjacima.setProizvodjac(listaProizovdjaca);
        izvestaj.setRaspodelaPoProizvodjacima(raspodelaPoProizvodjacima);

        //datum kreiranja
        XMLGregorianCalendar datumIzdavanja = DatatypeFactory.newInstance().newXMLGregorianCalendar(ft.format(ft.parse(todayDate.toString())));
        izvestaj.setDatumIzdavanja(datumIzdavanja);

        //save
        JAXBContext contextSaglasnost = JAXBContext.newInstance(IzvestajOImunizaciji.class);
        OutputStream os = new ByteArrayOutputStream();

        Marshaller marshaller = contextSaglasnost.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        marshaller.marshal(izvestaj, os);
        repository.saveXML("izvestaj_o_imunizaciji_" + fromDate +"_"+toDate, collectionId, os.toString());

        //TODO
        return "";
    }

    public String generatePDF(String fromDate, String toDate) {
        XSLFORTransformer transformer = null;

        try {
            transformer = new XSLFORTransformer();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        String doc_str = "";
        try {
            doc_str = ((IzvestajRepository) this.repository).pronadjiPoPeriodu(fromDate, toDate).getContent().toString();
            System.out.println(doc_str);
        } catch (XMLDBException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean ok = false;
        String pdf_path = SAVE_PDF + "izvestaj_o_imunizaciji_" + fromDate + "_" + toDate + ".pdf";

        try {
            ok = transformer.generatePDF(doc_str, pdf_path, DIGITALNISERTIFIKAT_XSL_FO);
            if (ok)
                return pdf_path;
            else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String generateHTML(String fromDate, String toDate) throws XMLDBException {
        XSLFORTransformer transformer = null;

        try {
            transformer = new XSLFORTransformer();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        //XMLResource xmlRes = this.readXML(id);
        String doc_str = null;//xmlRes.getContent().toString();
        try {
            doc_str = ((IzvestajRepository) this.repository).pronadjiPoPeriodu(fromDate, toDate).getContent().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean ok = false;
        String html_path = SAVE_HTML + "izvestaj_o_imunizaciji_" + fromDate + "_" + toDate + ".html";
        System.out.println(doc_str);

        try {
            ok = transformer.generateHTML(doc_str, html_path, DIGITALNISERTIFIKAT_XSL);
            if (ok)
                return html_path;
            else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
