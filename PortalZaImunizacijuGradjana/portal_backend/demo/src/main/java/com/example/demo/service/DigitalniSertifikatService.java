package com.example.demo.service;

import com.example.demo.client.DigitalniSertifikatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DigitalniSertifikatService {
    //xml i rdf se kreiraju i cuvaju se u SluzbenikApp

    @Autowired
    private DigitalniSertifikatClient digitalniSertifikatClient;
}
