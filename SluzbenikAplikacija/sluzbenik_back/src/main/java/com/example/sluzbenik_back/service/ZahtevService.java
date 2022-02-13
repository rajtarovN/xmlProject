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
            Date date = zahtev.getZaglavlje().getDanPodnosenjaZahteva().toGregorianCalendar().getTime();

            if(searchTerm == null || searchTerm == "") {
                list.add(new ZahtevDTO(zahtev.getAbout(), zahtev.getPodnosilacZahteva().getIme(),
                        zahtev.getPodnosilacZahteva().getPrezime(), zahtev.getPodnosilacZahteva().getJmbg(),
                        ft.format(date), zahtev.getStatus(),
                        zahtev.getPodnosilacZahteva().getRazlogPodnosenjaZahteva()));
            }
            else if(zahtev.getPodnosilacZahteva().getIme().toLowerCase().contains(searchTerm.toLowerCase()) ||
                    zahtev.getPodnosilacZahteva().getPrezime().toLowerCase().contains(searchTerm.toLowerCase())){
                list.add(new ZahtevDTO(zahtev.getAbout(), zahtev.getPodnosilacZahteva().getIme(),
                        zahtev.getPodnosilacZahteva().getPrezime(), zahtev.getPodnosilacZahteva().getJmbg(),
                        ft.format(date), zahtev.getStatus(),
                        zahtev.getPodnosilacZahteva().getRazlogPodnosenjaZahteva()));
            }

        }
        return list;
    }

}
