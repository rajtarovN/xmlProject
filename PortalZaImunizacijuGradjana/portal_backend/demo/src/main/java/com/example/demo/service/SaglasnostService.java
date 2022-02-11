package com.example.demo.service;

import com.example.demo.dto.EvidencijaVakcinacijeDTO;
import com.example.demo.dto.EvidentiraneVakcineDTO;
import com.example.demo.dto.ListaEvidentiranihVakcina;
import com.example.demo.dto.SaglasnostDTO;
import com.example.demo.exceptions.ForbiddenException;
import com.example.demo.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost;
import com.example.demo.repository.SaglasnostRepository;
import com.example.demo.util.DBManager;
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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SaglasnostService  extends AbstractService{

    protected String collectionId;

    protected String fusekiCollectionId;

    @Autowired
    private SaglasnostRepository saglasnostRepository;

    @Autowired
    private PotvrdaVakcinacijeService potvrdaVakcinacijeService;

    @Autowired
    private DBManager dbManager;

    @Autowired
    public SaglasnostService(SaglasnostRepository saglasnostRepository) {
        super(saglasnostRepository, "/db/portal/lista_saglasnosti", "/lista_saglasnosti" );
    }

    public ArrayList<SaglasnostDTO> pretragaTermina(String imePrezime, Date datumTermina) throws IllegalAccessException, InstantiationException, JAXBException, IOException, XMLDBException, ClassNotFoundException, ParseException, DatatypeConfigurationException {
        List<String> ids = new ArrayList<>();
        if(datumTermina == null){
            datumTermina = new Date();
        }
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");

        try {
            ids = this.saglasnostRepository.pretragaTermina(imePrezime, ft.format(datumTermina));
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<SaglasnostDTO> lista = new ArrayList<>();
        for(String i : ids) {
            Saglasnost z = this.pronadjiPoId(Long.parseLong(i));
            if(z.getEvidencijaOVakcinaciji().getVakcine().getVakcina().isEmpty()){
                SaglasnostDTO dto = new SaglasnostDTO(z);
                dto.setPrimioDozu(false);
                dto.setDobioPotvrdu(false);
                lista.add(dto);
            }
            else{
                boolean vaxxed = false;
                SaglasnostDTO dto = new SaglasnostDTO(z);
                dto.setPrimioDozu(false);
                dto.setDobioPotvrdu(false);
                for (Saglasnost.EvidencijaOVakcinaciji.Vakcine.Vakcina vakcina: z.getEvidencijaOVakcinaciji().getVakcine().getVakcina() ) {
                    if(vakcina.getDatumDavanja().toString().equals(z.getPacijent().getDatum().getValue().toString())){
                        vaxxed = true;
                        break;
                    }
                }
                if(vaxxed){
                    dto.setPrimioDozu(true);
                    List<String> potvrdeIds = new ArrayList<>();
                    if(z.getPacijent().getDrzavljaninSrbije() != null){
                        potvrdeIds = potvrdaVakcinacijeService.pronadjiPoJmbgIDatumu(z.getPacijent().getDrzavljaninSrbije().getJmbg().getValue(), datumTermina);
                    }
                    else if(z.getPacijent().getStraniDrzavljanin() != null){
                        potvrdeIds = potvrdaVakcinacijeService.pronadjiPoEbsIDatumu(z.getPacijent().getStraniDrzavljanin().getIdentifikacija().getValue(), datumTermina);
                    }
                    if(!potvrdeIds.isEmpty()){
                        dto.setDobioPotvrdu(true);
                    }
                    //TODO provera postojanja potvrde
                }

                lista.add(dto);
            }
        }
        return lista;
    }

    public ArrayList<EvidentiraneVakcineDTO> pronadjiPoEmailu(String email) throws IllegalAccessException, InstantiationException, JAXBException, IOException, XMLDBException, ClassNotFoundException {
        List<String> ids = new ArrayList<>();
        try {
            ids = this.saglasnostRepository.pronadjiPoEmailu(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<EvidentiraneVakcineDTO> lista = new ArrayList<>();
        for(String i : ids) {
            Saglasnost z = this.pronadjiPoId(Long.parseLong(i));
            if(!z.getEvidencijaOVakcinaciji().getVakcine().getVakcina().isEmpty()){
                for (Saglasnost.EvidencijaOVakcinaciji.Vakcine.Vakcina vakcina : z.getEvidencijaOVakcinaciji().getVakcine().getVakcina()) {
                    lista.add(new EvidentiraneVakcineDTO(vakcina));
                }
            }
        }
        return lista;

    }

    public Saglasnost pronadjiPoId(long id) throws IllegalAccessException, InstantiationException, JAXBException, ClassNotFoundException, XMLDBException, IOException {
        XMLResource res = this.saglasnostRepository.pronadjiPoId(id);
        try {
            if (res != null) {

                JAXBContext context = JAXBContext.newInstance("com.example.demo.model.obrazac_saglasnosti_za_imunizaciju");

                Unmarshaller unmarshaller = context.createUnmarshaller();

                Saglasnost s = (Saglasnost) unmarshaller
                        .unmarshal((res).getContentAsDOM());

                return s;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public String dodajEvidenciju(EvidencijaVakcinacijeDTO evidencijaDTO, String brojSaglasnosti) {
        try {
            Saglasnost saglasnost  = pronadjiPoId(Long.parseLong(brojSaglasnosti));

            Saglasnost.EvidencijaOVakcinaciji evidencija = new Saglasnost.EvidencijaOVakcinaciji();
            evidencija.setVakcinacijskiPunkt(evidencijaDTO.getVakcinacijskiPunkkt());
            Saglasnost.EvidencijaOVakcinaciji.ZdravstvenaUstanova zdravstvenaUstanova =
                    new Saglasnost.EvidencijaOVakcinaciji.ZdravstvenaUstanova();
            zdravstvenaUstanova.setValue(evidencijaDTO.getZdravstvenaUstanova());
            evidencija.setZdravstvenaUstanova(zdravstvenaUstanova);

            Saglasnost.EvidencijaOVakcinaciji.Lekar lekar = new Saglasnost.EvidencijaOVakcinaciji.Lekar();
            lekar.setIme(evidencijaDTO.getImeLekara());
            lekar.setPrezime(evidencijaDTO.getPrezimeLekara());
            lekar.setTelefon(evidencijaDTO.getTelefonLekara());
            evidencija.setLekar(lekar);

            Saglasnost.EvidencijaOVakcinaciji.Vakcine vakcine = new Saglasnost.EvidencijaOVakcinaciji.Vakcine();
            if(evidencijaDTO.getOdlukaKomisije() == null){
                vakcine.setOdlukaKomisijeZaTrajneKontraindikacije("");
            }else   vakcine.setOdlukaKomisijeZaTrajneKontraindikacije(evidencijaDTO.getOdlukaKomisije());
            Saglasnost.EvidencijaOVakcinaciji.Vakcine.PrivremeneKontraindikacije privremeneKontraindikacije =
                    new Saglasnost.EvidencijaOVakcinaciji.Vakcine.PrivremeneKontraindikacije();

            if(evidencijaDTO.getDatumUtvrdjivanja() != null ) {
                String FORMATER = "yyyy-MM-dd";
                DateFormat format = new SimpleDateFormat(FORMATER);
                Date date = format.parse(evidencijaDTO.getDatumUtvrdjivanja());
                XMLGregorianCalendar gDateFormatted =
                        DatatypeFactory.newInstance().newXMLGregorianCalendar(format.format(date));
                privremeneKontraindikacije.setDatumUtvrdjivanja(gDateFormatted);
                privremeneKontraindikacije.setDijagnoza(evidencijaDTO.getDijagnoza());
            }else{
                privremeneKontraindikacije.setDijagnoza("");
                privremeneKontraindikacije.setDatumUtvrdjivanja(null);
            }
            vakcine.setPrivremeneKontraindikacije(privremeneKontraindikacije);

            evidencija.setVakcine(vakcine);
            saglasnost.setEvidencijaOVakcinaciji(evidencija);

            String documentId = "saglasnost_"+brojSaglasnosti;
            String collectionId = "/db/portal/lista_saglasnosti";

            JAXBContext context = JAXBContext
                    .newInstance("com.example.demo.model.obrazac_saglasnosti_za_imunizaciju");
            OutputStream os = new ByteArrayOutputStream();

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            marshaller.marshal(saglasnost, os);
            dbManager.saveFileToDB(documentId, collectionId, os.toString());
            return "Uspesno sacuvana evidencija o vakcinaciji.";
        } catch (Exception e) {
            e.printStackTrace();
            throw new ForbiddenException("Error se desio pri cuvanju evidencije o vakcinaciji.");
        }
    }

    public String dodajEvidentiraneVakcine(ListaEvidentiranihVakcina evidentiraneVakcineDTO, String brojSaglasnosti){
        try {
            Saglasnost saglasnost = pronadjiPoId(Long.parseLong(brojSaglasnosti));
            Saglasnost.EvidencijaOVakcinaciji evidencija = saglasnost.getEvidencijaOVakcinaciji();
            Saglasnost.EvidencijaOVakcinaciji.Vakcine vakcine = saglasnost.getEvidencijaOVakcinaciji().getVakcine();
            List<Saglasnost.EvidencijaOVakcinaciji.Vakcine.Vakcina> listaVakcina = new ArrayList<>();
            int i = 0;

            for (EvidentiraneVakcineDTO vakcinaDto: evidentiraneVakcineDTO.getEvidentiraneVakcineDTO()) {
                i += 1;
                Saglasnost.EvidencijaOVakcinaciji.Vakcine.Vakcina vakcina =
                        new Saglasnost.EvidencijaOVakcinaciji.Vakcine.Vakcina();
                vakcina.setNaziv(vakcinaDto.getNazivVakcine());
                XMLGregorianCalendar result1 = DatatypeFactory.newInstance()
                        .newXMLGregorianCalendar(vakcinaDto.getDatumDavanja());
                vakcina.setDatumDavanja(result1);

                vakcina.setNacinDavanja("IM");
                if (vakcinaDto.getEkstremitet().equals("LR") ||
                        vakcinaDto.getEkstremitet().equals("DR"))
                    vakcina.setEkstremiter(vakcinaDto.getEkstremitet().equals("LR") ? "leva ruka" : "desna ruka");
                else
                    vakcina.setEkstremiter(vakcinaDto.getEkstremitet());
                vakcina.setSerija(vakcinaDto.getSerijaVakcine());
                vakcina.setProizvodjac(vakcinaDto.getProizvodjac());
                vakcina.setNezeljenaReakcija(vakcinaDto.getNezeljenaReakcija());
                vakcina.setDoza(BigInteger.valueOf(i));
                listaVakcina.add(vakcina);

            }
            vakcine.setVakcina(listaVakcina);
            evidencija.setVakcine(vakcine);
            saglasnost.setEvidencijaOVakcinaciji(evidencija);

            String documentId = "saglasnost_" + brojSaglasnosti;
            String collectionId = "/db/portal/lista_saglasnosti";

            JAXBContext context = JAXBContext
                    .newInstance("com.example.demo.model.obrazac_saglasnosti_za_imunizaciju");
            OutputStream os = new ByteArrayOutputStream();

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            marshaller.marshal(saglasnost, os);
            dbManager.saveFileToDB(documentId, collectionId, os.toString());

            return "Uspesno sacuvane evidentirane vakcine.";
        } catch (Exception e) {
            e.printStackTrace();
            throw new ForbiddenException("Error se desio pri cuvanju evidentirane vakcine.");
        }
    }

}
