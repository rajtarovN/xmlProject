package com.example.sluzbenik_back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sluzbenik_back.client.DigitalniSertifikatClient;
import com.example.sluzbenik_back.dto.IdentificationDTO;
import com.example.sluzbenik_back.dto.SertifikatNaprednaDTO;
import com.example.sluzbenik_back.repository.DigitalniSertifikatRepository;

@Service
public class DigitalniSertifikatService extends AbstractService {

	@Autowired
	private DigitalniSertifikatClient digitalniSertifikatClient;

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
}
