//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.01.04 at 02:16:55 PM CET 
//


package com.example.sluzbenik_back.model.zahtev_za_sertifikatom;

import javax.xml.bind.annotation.*;


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
 *         &lt;element ref="{http://www.ftn.uns.ac.rs/xml_i_veb_servisi/zahtev_za_sertifikatom}Podnosilac_zahteva"/&gt;
 *         &lt;element ref="{http://www.ftn.uns.ac.rs/xml_i_veb_servisi/zahtev_za_sertifikatom}Zaglavlje"/&gt;
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
    "podnosilacZahteva",
    "zaglavlje"
})
@XmlRootElement(name = "Zahtev_za_zeleni_sertifikat")
public class ZahtevZaZeleniSertifikat {

    @XmlElement(name = "Podnosilac_zahteva", required = true)
    protected PodnosilacZahteva podnosilacZahteva;
    @XmlElement(name = "Zaglavlje", required = true)
    protected Zaglavlje zaglavlje;

    /**
     * Gets the value of the podnosilacZahteva property.
     * 
     * @return
     *     possible object is
     *     {@link PodnosilacZahteva }
     *     
     */
    public PodnosilacZahteva getPodnosilacZahteva() {
        return podnosilacZahteva;
    }

    /**
     * Sets the value of the podnosilacZahteva property.
     * 
     * @param value
     *     allowed object is
     *     {@link PodnosilacZahteva }
     *     
     */
    public void setPodnosilacZahteva(PodnosilacZahteva value) {
        this.podnosilacZahteva = value;
    }

    /**
     * Gets the value of the zaglavlje property.
     * 
     * @return
     *     possible object is
     *     {@link Zaglavlje }
     *     
     */
    public Zaglavlje getZaglavlje() {
        return zaglavlje;
    }

    /**
     * Sets the value of the zaglavlje property.
     * 
     * @param value
     *     allowed object is
     *     {@link Zaglavlje }
     *     
     */
    public void setZaglavlje(Zaglavlje value) {
        this.zaglavlje = value;
    }

}
