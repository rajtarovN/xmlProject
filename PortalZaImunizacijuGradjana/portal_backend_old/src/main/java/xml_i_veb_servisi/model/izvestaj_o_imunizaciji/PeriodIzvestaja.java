//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.01.04 at 02:16:55 PM CET 
//


package main.java.xml_i_veb_servisi.model.izvestaj_o_imunizaciji;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="Pocetak_perioda" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="Kraj_perioda" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
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
    "pocetakPerioda",
    "krajPerioda"
})
@XmlRootElement(name = "Period_izvestaja")
public class PeriodIzvestaja {

    @XmlElement(name = "Pocetak_perioda", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar pocetakPerioda;
    @XmlElement(name = "Kraj_perioda", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar krajPerioda;

    /**
     * Gets the value of the pocetakPerioda property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPocetakPerioda() {
        return pocetakPerioda;
    }

    /**
     * Sets the value of the pocetakPerioda property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPocetakPerioda(XMLGregorianCalendar value) {
        this.pocetakPerioda = value;
    }

    /**
     * Gets the value of the krajPerioda property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getKrajPerioda() {
        return krajPerioda;
    }

    /**
     * Sets the value of the krajPerioda property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setKrajPerioda(XMLGregorianCalendar value) {
        this.krajPerioda = value;
    }

}
