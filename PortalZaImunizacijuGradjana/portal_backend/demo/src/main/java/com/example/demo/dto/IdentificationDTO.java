package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "saglasnost" })

@XmlRootElement(name = "saglasnost")
public class IdentificationDTO {

	@XmlElement(required = true)
	List<String> saglasnost = new ArrayList<String>();

	public IdentificationDTO() {
		super();
	}

	public IdentificationDTO(List<String> saglasnost) {
		super();
		this.saglasnost = saglasnost;
	}

	public List<String> getSaglasnost() {
		return saglasnost;
	}

	public void setSaglasnost(List<String> list) {
		this.saglasnost = list;
	}

}
