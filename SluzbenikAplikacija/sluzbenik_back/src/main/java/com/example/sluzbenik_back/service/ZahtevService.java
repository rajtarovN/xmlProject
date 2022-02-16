package com.example.sluzbenik_back.service;

import com.example.sluzbenik_back.client.ZahtevClient;
import com.example.sluzbenik_back.dto.KorisnikDTO;
import com.example.sluzbenik_back.dto.ZahtevDTO;
import com.example.sluzbenik_back.model.korisnik.Korisnik;
import com.example.sluzbenik_back.model.korisnik.ListaKorisnika;
import com.example.sluzbenik_back.model.zahtev_za_sertifikatom.ListaZahteva;
import com.example.sluzbenik_back.model.zahtev_za_sertifikatom.ZahtevZaZeleniSertifikat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ZahtevService {

    @Autowired
    private ZahtevClient zahtevClient;

    public List<ZahtevDTO> getZahteve(String searchTerm) throws Exception {
        ListaZahteva listaZahteva = zahtevClient.getZahteveNaCekanju();
        List<ZahtevDTO> list = new ArrayList<>();
        for (ZahtevZaZeleniSertifikat zahtev : listaZahteva.getZahtevi() ) {
            SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
            Date date = zahtev.getZaglavlje().getDanPodnosenjaZahteva().getValue().toGregorianCalendar().getTime();
            String idZahteva = zahtev.getAbout().substring(zahtev.getAbout().lastIndexOf('/') + 1);

            if(searchTerm == null || searchTerm == "") {
                list.add(new ZahtevDTO(idZahteva, zahtev.getPodnosilacZahteva().getIme().getValue(),
                        zahtev.getPodnosilacZahteva().getPrezime().getValue(),
                        zahtev.getPodnosilacZahteva().getJmbg().getValue(),
                        ft.format(date), zahtev.getStatus().getValue(),
                        zahtev.getPodnosilacZahteva().getRazlogPodnosenjaZahteva()));
            }
            else if(zahtev.getPodnosilacZahteva().getIme().getValue().toLowerCase().contains(searchTerm.toLowerCase()) ||
                    zahtev.getPodnosilacZahteva().getPrezime().getValue().toLowerCase().contains(searchTerm.toLowerCase())){
                list.add(new ZahtevDTO(idZahteva, zahtev.getPodnosilacZahteva().getIme().getValue(),
                        zahtev.getPodnosilacZahteva().getPrezime().getValue(),
                        zahtev.getPodnosilacZahteva().getJmbg().getValue(),
                        ft.format(date), zahtev.getStatus().getValue(),
                        zahtev.getPodnosilacZahteva().getRazlogPodnosenjaZahteva()));
            }

        }
        return list;
    }

    public String odbijZahtev(String idZahteva, String razlogOdbijanja) throws Exception{
        return zahtevClient.odbijZahtev(idZahteva, razlogOdbijanja);
    }

    public String odobriZahtev(String idZahteva) throws Exception{
        return zahtevClient.odobriZahtev(idZahteva);
    }

}
