package com.example.sluzbenik_back.service;

import static com.example.sluzbenik_back.util.PathConstants.POTVRDA_O_VAKCINACIJI_XSL;
import static com.example.sluzbenik_back.util.PathConstants.SAVE_HTML;
import static com.example.sluzbenik_back.util.PathConstants.SAVE_PDF;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.XMLDBException;

import com.example.sluzbenik_back.client.PotvrdeClient;
import com.example.sluzbenik_back.dto.DokumentDTO;
import com.example.sluzbenik_back.dto.IdentificationDTO;
import com.example.sluzbenik_back.dto.PotvrdaNaprednaDTO;
import com.example.sluzbenik_back.model.potvrda_o_vakcinaciji.ListaPotvrda;
import com.example.sluzbenik_back.model.potvrda_o_vakcinaciji.PotvrdaOVakcinaciji;
import com.example.sluzbenik_back.util.XSLFORTransformer;

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
        String pdf_path = SAVE_PDF + "potvrda_" + id.split(".xml")[0] + ".pdf";

        try {
            ok = transformer.generatePDF(doc_str, pdf_path, POTVRDA_O_VAKCINACIJI_XSL);
            if (ok)
                return pdf_path;
            else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public IdentificationDTO obicnaPretraga(String searchTerm) throws Exception {
        return this.potvrdeClient.getByObicnaPretraga(searchTerm);
    }

    public String generateHTML(String id) {
        XSLFORTransformer transformer = null;

        try {
            transformer = new XSLFORTransformer();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        //XMLResource xmlRes = potvrdeClient.getXml(id);
        String doc_str = null;//xmlRes.getContent().toString();
        try {
            doc_str = potvrdeClient.getXml(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public String getAllPotvrde() throws Exception {
        return this.potvrdeClient.getAllIds();
    }

    public byte[] generisiJSON(String documentId) throws Exception  {
        String res = potvrdeClient.generateJson(documentId);
        return res.getBytes();
    }

    public byte[] generisiRDF(String documentId) throws Exception  {
        String res = potvrdeClient.generateRdf(documentId);
        return res.getBytes();
    }
    
	public IdentificationDTO naprednaPretraga(PotvrdaNaprednaDTO dto) throws Exception {

		return this.potvrdeClient.getByNaprednaPretraga(dto);
	}
	
	public String getAllIdReferences(String id) throws Exception {
		return this.potvrdeClient.getAllIdReferences(id);
	}

}
