//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.01.04 at 02:16:55 PM CET 
//


package com.example.sluzbenik_back.model.izvestaj_o_imunizaciji;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://www.ftn.uns.ac.rs/xml_i_veb_servisi/izvestaj_o_imunizaciji}Vakcina" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "vakcina"
})
@XmlRootElement(name = "Lista_vakcina")
public class ListaVakcina {

    @XmlElement(name = "Vakcina", required = true)
    protected List<Vakcina> vakcina;

    /**
     * Gets the value of the vakcina property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vakcina property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVakcina().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Vakcina }
     * 
     * 
     */
    public List<Vakcina> getVakcina() {
        if (vakcina == null) {
            vakcina = new ArrayList<Vakcina>();
        }
        return this.vakcina;
    }

    public void setVakcina(List<Vakcina> vakcina) {
        this.vakcina = vakcina;
    }
}
