package com.example.sluzbenik_back.service;

import com.example.sluzbenik_back.repository.DigitalniSertifikatRepository;
import com.example.sluzbenik_back.repository.DostupneVakcineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DigitalniSertifikatService extends AbstractService {

    @Autowired
    public DigitalniSertifikatService(DigitalniSertifikatRepository repository) {

        super(repository, "/db/sluzbenik/lista_sertifikata", "/lista_sertifikata");
    }
}
