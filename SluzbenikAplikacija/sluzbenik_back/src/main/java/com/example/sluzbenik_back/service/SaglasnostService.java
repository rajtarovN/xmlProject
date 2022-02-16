package com.example.sluzbenik_back.service;

import com.example.sluzbenik_back.client.SaglasnostClient;
import com.example.sluzbenik_back.dto.DokumentDTO;
import com.example.sluzbenik_back.dto.IdentificationDTO;
import com.example.sluzbenik_back.dto.SaglasnostNaprednaDTO;
import com.example.sluzbenik_back.model.obrazac_saglasnosti_za_imunizaciju.ListaSaglasnosti;
import com.example.sluzbenik_back.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost;
import com.example.sluzbenik_back.util.XSLFORTransformer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.sluzbenik_back.util.PathConstants.*;

@Service
public class SaglasnostService {
	
    @Autowired
    private SaglasnostClient saglasnostClient;

    public List<DokumentDTO> getAllXmlIdsByEmail(String email) throws Exception {
        ListaSaglasnosti saglasnostJaxbLista = saglasnostClient.allXmlIdsByEmail(email);
        List<DokumentDTO> ret = new ArrayList<>();
        for (Saglasnost s: saglasnostJaxbLista.getSaglasnosti()) {
            ret.add(new DokumentDTO(s));
        }
        return ret;
    }

    public String generatePDF(String id) {
        //TODO natasa
        XSLFORTransformer transformer = null;

        try {
            transformer = new XSLFORTransformer();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        String doc_str = "";
        try {
            doc_str = saglasnostClient.getXml(id);
            System.out.println(doc_str);
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean ok = false;
        String pdf_path = SAVE_PDF + "saglasnost_" + id.split(".xml")[0] + ".pdf";

        try {
            ok = transformer.generatePDF(doc_str, pdf_path, SAGLASNOST_XSL_FO);
            if (ok)
                return pdf_path;
            else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public String getAllSaglasnosti() throws Exception {
		return this.saglasnostClient.getAllIds();
	}
    
	public IdentificationDTO naprednaPretraga(SaglasnostNaprednaDTO dto) throws Exception {

		return this.saglasnostClient.getByNaprednaPretraga(dto);
	}

}
