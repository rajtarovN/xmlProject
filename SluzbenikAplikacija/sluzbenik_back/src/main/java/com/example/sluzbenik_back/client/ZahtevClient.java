package com.example.sluzbenik_back.client;

import com.example.sluzbenik_back.model.zahtev_za_sertifikatom.ListaZahteva;
import org.apache.commons.io.IOUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;

@org.springframework.stereotype.Service
public class ZahtevClient {

    public static String BASE_URL = "http://localhost:8081/api";

    public static String URL_ENCODING = "UTF-8";

    public ZahtevClient() {

    }

    public ListaZahteva getZahteveNaCekanju() throws Exception {
        URL url = new URL(BASE_URL + "/zahtev/findByStatus");
        InputStream in = url.openStream();

        String txt = getStringFromInputStream(in);
        JAXBContext context = JAXBContext.newInstance(ListaZahteva.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(txt);
        ListaZahteva zahtevi = (ListaZahteva) unmarshaller.unmarshal(reader);

        return zahtevi;
    }

    public String odbijZahtev(String idZahteva, String razlog) throws Exception{
        URL url = new URL(BASE_URL + "/zahtev/odbijZahtev/" + idZahteva + "/" + razlog);
        InputStream in = url.openStream();

        String txt = getStringFromInputStream(in);
        return txt;
    }

    public String odobriZahtev(String idZahteva) throws Exception{
        URL url = new URL(BASE_URL + "/zahtev/odobriZahtev/" + idZahteva );
        try{
            InputStream in = url.openStream();
            return "Uspesno odobren zahtev";
        }
        catch (Exception e){
            return "Uspesno odobren zahtev";
        }

    }

    public static String getStringFromInputStream(InputStream in) throws Exception {
        return new String(IOUtils.toByteArray(in), URL_ENCODING);
    }
}
