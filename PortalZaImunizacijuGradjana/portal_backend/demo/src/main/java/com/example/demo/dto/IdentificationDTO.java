package com.example.demo.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonRootName;

@XmlRootElement(name = "IdentificationDTO")
@JsonRootName(value = "IdentificationDTO")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class IdentificationDTO {

	@XmlElementWrapper(name="saglasnost")
	List<String> saglasnost;

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
