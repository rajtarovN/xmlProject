//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.01.04 at 02:16:55 PM CET 
//


package com.example.demo.model.zahtev_za_sertifikatom;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
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
 *         &lt;element name="Mesto_podnosenja_zahteva"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="1"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="Dan_podnosenja_zahteva"&gt;
 *           &lt;complexType&gt;
 *             &lt;simpleContent&gt;
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;dateTime"&gt;
 *                 &lt;attribute name="property" type="{http://www.w3.org/2001/XMLSchema}string" fixed="pred:dan_podnosenja_zahteva" /&gt;
 *               &lt;/extension&gt;
 *             &lt;/simpleContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
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
    "mestoPodnosenjaZahteva",
    "danPodnosenjaZahteva"
})
@XmlRootElement(name = "Zaglavlje")
public class Zaglavlje {

    @XmlElement(name = "Mesto_podnosenja_zahteva", required = true)
    protected String mestoPodnosenjaZahteva;
    @XmlElement(name = "Dan_podnosenja_zahteva", required = true)
    protected Zaglavlje.DanPodnosenjaZahteva danPodnosenjaZahteva;

    /**
     * Gets the value of the mestoPodnosenjaZahteva property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMestoPodnosenjaZahteva() {
        return mestoPodnosenjaZahteva;
    }

    /**
     * Sets the value of the mestoPodnosenjaZahteva property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMestoPodnosenjaZahteva(String value) {
        this.mestoPodnosenjaZahteva = value;
    }

    /**
     * Gets the value of the danPodnosenjaZahteva property.
     * 
     * @return
     *     possible object is
     *     {@link Zaglavlje.DanPodnosenjaZahteva }
     *     
     */
    public Zaglavlje.DanPodnosenjaZahteva getDanPodnosenjaZahteva() {
        return danPodnosenjaZahteva;
    }

    /**
     * Sets the value of the danPodnosenjaZahteva property.
     * 
     * @param value
     *     allowed object is
     *     {@link Zaglavlje.DanPodnosenjaZahteva }
     *     
     */
    public void setDanPodnosenjaZahteva(Zaglavlje.DanPodnosenjaZahteva value) {
        this.danPodnosenjaZahteva = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;simpleContent&gt;
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;dateTime"&gt;
     *       &lt;attribute name="property" type="{http://www.w3.org/2001/XMLSchema}string" fixed="pred:dan_podnosenja_zahteva" /&gt;
     *     &lt;/extension&gt;
     *   &lt;/simpleContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class DanPodnosenjaZahteva {

        @XmlValue
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar value;
        @XmlAttribute(name = "property")
        protected String property;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setValue(XMLGregorianCalendar value) {
            this.value = value;
        }

        /**
         * Gets the value of the property property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getProperty() {
            if (property == null) {
                return "pred:dan_podnosenja_zahteva";
            } else {
                return property;
            }
        }

        /**
         * Sets the value of the property property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setProperty(String value) {
            this.property = value;
        }

    }

}
