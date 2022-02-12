package com.example.demo.repository;

import com.example.demo.util.FusekiManager;
import com.example.demo.util.MetadataExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Repository
public class ZahtevRepository extends RepositoryInterface{

    @Autowired
    private FusekiManager fusekiManager;

    public void saveRDF(String content, String uri)  throws Exception {
        InputStream in = new ByteArrayInputStream(content.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        MetadataExtractor extractor = new MetadataExtractor();
        extractor.extractMetadata(in, out);

        String rdfAsString = new String(out.toByteArray());
        InputStream rdfInputStream = new ByteArrayInputStream(rdfAsString.getBytes());
        fusekiManager.writeFuseki(rdfInputStream, uri);
    }
}
