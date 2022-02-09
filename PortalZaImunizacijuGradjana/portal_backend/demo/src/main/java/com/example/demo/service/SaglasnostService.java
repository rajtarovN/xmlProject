package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.SaglasnostRepository;


@Service
public class SaglasnostService extends AbstractService{

	protected String collectionId;

	protected String fusekiCollectionId;

	protected SaglasnostRepository saglasnostRepository;
	
	@Autowired
	public SaglasnostService(SaglasnostRepository saglasnostRepository) {

		super(saglasnostRepository, "/db/sample/library", "/saglasnost/" );
	}
	
	
	
}
