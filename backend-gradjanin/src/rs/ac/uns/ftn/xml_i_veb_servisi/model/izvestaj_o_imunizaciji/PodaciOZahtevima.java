//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.01.04 at 02:16:55 PM CET 
//


package rs.ac.uns.ftn.xml_i_veb_servisi.model.izvestaj_o_imunizaciji;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="Broj_dokumenata_o_interesovanju" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/&gt;
 *         &lt;element name="Broj_zahteva"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Primljeno" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/&gt;
 *                   &lt;element name="Izdato" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
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
    "brojDokumenataOInteresovanju",
    "brojZahteva"
})
@XmlRootElement(name = "Podaci_o_zahtevima")
public class PodaciOZahtevima {

    @XmlElement(name = "Broj_dokumenata_o_interesovanju", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger brojDokumenataOInteresovanju;
    @XmlElement(name = "Broj_zahteva", required = true)
    protected PodaciOZahtevima.BrojZahteva brojZahteva;

    /**
     * Gets the value of the brojDokumenataOInteresovanju property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getBrojDokumenataOInteresovanju() {
        return brojDokumenataOInteresovanju;
    }

    /**
     * Sets the value of the brojDokumenataOInteresovanju property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setBrojDokumenataOInteresovanju(BigInteger value) {
        this.brojDokumenataOInteresovanju = value;
    }

    /**
     * Gets the value of the brojZahteva property.
     * 
     * @return
     *     possible object is
     *     {@link PodaciOZahtevima.BrojZahteva }
     *     
     */
    public PodaciOZahtevima.BrojZahteva getBrojZahteva() {
        return brojZahteva;
    }

    /**
     * Sets the value of the brojZahteva property.
     * 
     * @param value
     *     allowed object is
     *     {@link PodaciOZahtevima.BrojZahteva }
     *     
     */
    public void setBrojZahteva(PodaciOZahtevima.BrojZahteva value) {
        this.brojZahteva = value;
    }


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
     *         &lt;element name="Primljeno" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/&gt;
     *         &lt;element name="Izdato" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/&gt;
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
        "primljeno",
        "izdato"
    })
    public static class BrojZahteva {

        @XmlElement(name = "Primljeno", required = true)
        @XmlSchemaType(name = "nonNegativeInteger")
        protected BigInteger primljeno;
        @XmlElement(name = "Izdato", required = true)
        @XmlSchemaType(name = "nonNegativeInteger")
        protected BigInteger izdato;

        /**
         * Gets the value of the primljeno property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getPrimljeno() {
            return primljeno;
        }

        /**
         * Sets the value of the primljeno property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setPrimljeno(BigInteger value) {
            this.primljeno = value;
        }

        /**
         * Gets the value of the izdato property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getIzdato() {
            return izdato;
        }

        /**
         * Sets the value of the izdato property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setIzdato(BigInteger value) {
            this.izdato = value;
        }

    }

}
