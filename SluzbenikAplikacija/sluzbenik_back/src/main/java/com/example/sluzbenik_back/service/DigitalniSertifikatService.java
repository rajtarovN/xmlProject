package com.example.sluzbenik_back.service;

import com.example.sluzbenik_back.client.SertifikatClient;
import com.example.sluzbenik_back.dto.DokumentDTO;
import com.example.sluzbenik_back.model.digitalni_zeleni_sertifikat.DigitalniZeleniSertifikat;
import com.example.sluzbenik_back.model.digitalni_zeleni_sertifikat.ListaSertifikata;
import com.example.sluzbenik_back.util.XSLFORTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.XMLDBException;

import java.util.ArrayList;
import java.util.List;

import static com.example.sluzbenik_back.util.PathConstants.SAGLASNOST_XSL_FO;
import static com.example.sluzbenik_back.util.PathConstants.SAVE_PDF;

import com.example.sluzbenik_back.client.DigitalniSertifikatClient;
import com.example.sluzbenik_back.dto.IdentificationDTO;
import com.example.sluzbenik_back.dto.SertifikatNaprednaDTO;
import com.example.sluzbenik_back.repository.DigitalniSertifikatRepository;

@Service
public class DigitalniSertifikatService {

	@Autowired
	private DigitalniSertifikatClient digitalniSertifikatClient;

  @Autowired
    private SertifikatClient sertifikatClient;
    
	@Autowired
	public DigitalniSertifikatService(DigitalniSertifikatRepository repository) {

		super(repository, "/db/sluzbenik/lista_sertifikata", "/lista_sertifikata");
	}

	public String getAllSertifikati() throws Exception {
		return this.digitalniSertifikatClient.getAllIds();
	}

	public IdentificationDTO naprednaPretraga(SertifikatNaprednaDTO dto) throws Exception {

		return this.digitalniSertifikatClient.getByNaprednaPretraga(dto);
	}
    

    public List<DokumentDTO> getAllXmlIdsByEmail(String email) throws Exception {
        ListaSertifikata lista = sertifikatClient.allXmlIdsByEmail(email);
        List<DokumentDTO> ret = new ArrayList<>();
        for (DigitalniZeleniSertifikat s: lista.getSertifikate()) {
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
            doc_str = sertifikatClient.getXml(id);
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
