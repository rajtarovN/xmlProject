package com.example.sluzbenik_back.service;

import com.example.sluzbenik_back.client.PotvrdeClient;
import com.example.sluzbenik_back.dto.DokumentDTO;
import com.example.sluzbenik_back.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost;
import com.example.sluzbenik_back.model.potvrda_o_vakcinaciji.ListaPotvrda;
import com.example.sluzbenik_back.model.potvrda_o_vakcinaciji.PotvrdaOVakcinaciji;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PotvrdaVakcinacijeService {

    @Autowired
    private PotvrdeClient potvrdeClient;

    public List<DokumentDTO> getAllXmlIdsByEmail(String email) throws Exception {
        ListaPotvrda listaPotvrda = potvrdeClient.allXmlIdsByEmail(email);
        List<DokumentDTO> ret = new ArrayList<>();
        for (PotvrdaOVakcinaciji s: listaPotvrda.getPotvrde()) {
            ret.add(new DokumentDTO(s));
        }
        return ret;
    }
}
