package com.example.demo.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonRootName;

@XmlRootElement(name = "IdentificationDTO")
@JsonRootName(value = "IdentificationDTO")
@XmlAccessorType(XmlAccessType.FIELD)
public class IdentificationDTO {


    @XmlElementWrapper(name="ids")
	List<String> ids;

	public IdentificationDTO() {
		super();
	}

	public IdentificationDTO(List<String> ids) {
		super();
		this.ids = ids;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> list) {
		this.ids = list;
	}

}
