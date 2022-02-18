package com.example.demo.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "lista_evidentiranih_vakcina")
@XmlAccessorType(XmlAccessType.FIELD)
public class ListaEvidentiranihVakcina implements Serializable {
    private final static long serialVersionUID = 1L;

    @XmlElement(name = "evidentirana_vakcina")
    private List<EvidentiraneVakcineDTO> evidentiraneVakcineDTO;

    public List<EvidentiraneVakcineDTO> getEvidentiraneVakcineDTO() {
        return evidentiraneVakcineDTO;
    }
    public void setEvidentiraneVakcineDTO(List<EvidentiraneVakcineDTO> ipAddresses) {
        this.evidentiraneVakcineDTO = ipAddresses;
    }
}
