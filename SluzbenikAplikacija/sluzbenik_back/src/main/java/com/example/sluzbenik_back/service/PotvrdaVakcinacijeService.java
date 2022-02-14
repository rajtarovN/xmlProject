package com.example.sluzbenik_back.service;

import com.example.sluzbenik_back.client.PotvrdeClient;
import com.example.sluzbenik_back.dto.DokumentDTO;
import com.example.sluzbenik_back.model.obrazac_saglasnosti_za_imunizaciju.Saglasnost;
import com.example.sluzbenik_back.model.potvrda_o_vakcinaciji.ListaPotvrda;
import com.example.sluzbenik_back.model.potvrda_o_vakcinaciji.PotvrdaOVakcinaciji;
import com.example.sluzbenik_back.util.XSLFORTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.XMLDBException;

import java.util.ArrayList;
import java.util.List;

import static com.example.sluzbenik_back.util.PathConstants.SAGLASNOST_XSL_FO;
import static com.example.sluzbenik_back.util.PathConstants.SAVE_PDF;

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
            doc_str = potvrdeClient.getXml(id);
            System.out.println(doc_str);
        } catch (XMLDBException e1) {
            e1.printStackTrace();
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
}
