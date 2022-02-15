package com.example.demo.service;

import com.example.demo.client.DigitalniSertifikatClient;
import com.example.demo.util.XSLFORTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import static com.example.demo.util.PathConstants.*;
import static com.example.demo.util.PathConstants.INTERESOVANJE_XSL;

@Service
public class DigitalniSertifikatService {
    //xml i rdf se kreiraju i cuvaju se u SluzbenikApp

    @Autowired
    private DigitalniSertifikatClient digitalniSertifikatClient;

    public String generatePDF(String id) {
        XSLFORTransformer transformer = null;

        try {
            transformer = new XSLFORTransformer();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        XMLResource xmlRes = this.readXML(id);
        String doc_str = "";
        try {
            doc_str = xmlRes.getContent().toString();
            System.out.println(doc_str);
        } catch (XMLDBException e1) {
            e1.printStackTrace();
        }

        boolean ok = false;
        String pdf_path = SAVE_PDF + "sertifikat_" + id.split(".xml")[0] + ".pdf";

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

    private XMLResource readXML(String id) {
        return null;
    }

    public String generateHTML(String id) throws XMLDBException {
        XSLFORTransformer transformer = null;

        try {
            transformer = new XSLFORTransformer();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        XMLResource xmlRes = this.readXML(id);
        String doc_str = xmlRes.getContent().toString();
        boolean ok = false;
        String html_path = SAVE_HTML + "sertifikat_" + id + ".html";
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
