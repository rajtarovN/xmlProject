//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.01.04 at 02:16:55 PM CET 
//


package rs.ac.uns.ftn.xml_i_veb_servisi.model.digitalni_zeleni_sertifikat;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *         &lt;element name="Podaci_o_sertifikatu"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Broj_sertifikata" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/&gt;
 *                   &lt;element name="Datum_i_vreme_izdavanja" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="Podaci_o_osobi"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Ime"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                         &lt;minLength value="2"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="Prezime"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                         &lt;minLength value="2"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="Pol"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                         &lt;enumeration value="Muski"/&gt;
 *                         &lt;enumeration value="Zenski"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="Datum_rodjenja" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *                   &lt;element name="Jmbg"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                         &lt;length value="13"/&gt;
 *                         &lt;pattern value="\d{13}"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="Broj_pasosa"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                         &lt;pattern value="\d{9}"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="Podaci_o_vakcinaciji"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence maxOccurs="unbounded"&gt;
 *                   &lt;element name="Vakcinacija"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Tip"&gt;
 *                               &lt;simpleType&gt;
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                                   &lt;enumeration value="Pfizer-BioNTech"/&gt;
 *                                   &lt;enumeration value="Sputnik V"/&gt;
 *                                   &lt;enumeration value="Sinopharm"/&gt;
 *                                   &lt;enumeration value="AstraZeneca"/&gt;
 *                                   &lt;enumeration value="Moderna"/&gt;
 *                                 &lt;/restriction&gt;
 *                               &lt;/simpleType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="Proizvodjac"&gt;
 *                               &lt;simpleType&gt;
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                                   &lt;minLength value="2"/&gt;
 *                                 &lt;/restriction&gt;
 *                               &lt;/simpleType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="Serija" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/&gt;
 *                             &lt;element name="Datum" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *                             &lt;element name="Zdravstvena_ustanova"&gt;
 *                               &lt;simpleType&gt;
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                                   &lt;minLength value="3"/&gt;
 *                                 &lt;/restriction&gt;
 *                               &lt;/simpleType&gt;
 *                             &lt;/element&gt;
 *                           &lt;/sequence&gt;
 *                           &lt;attribute name="br_doze"&gt;
 *                             &lt;simpleType&gt;
 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int"&gt;
 *                                 &lt;minInclusive value="1"/&gt;
 *                               &lt;/restriction&gt;
 *                             &lt;/simpleType&gt;
 *                           &lt;/attribute&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="Testovi"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Naziv_testa" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Vrsta_uzorka" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Datum_i_vreme_uzorkovanja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Datum_i_vreme_izdavanja_rezultata" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="Datum" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="qr_kod"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;minLength value="2"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="Id_sertifikata"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"&gt;
 *             &lt;minExclusive value="1"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "podaciOSertifikatu",
    "podaciOOsobi",
    "podaciOVakcinaciji",
    "testovi",
    "datum"
})
@XmlRootElement(name = "Digitalni_zeleni_sertifikat")
public class DigitalniZeleniSertifikat {

    @XmlElement(name = "Podaci_o_sertifikatu", required = true)
    protected DigitalniZeleniSertifikat.PodaciOSertifikatu podaciOSertifikatu;
    @XmlElement(name = "Podaci_o_osobi", required = true)
    protected DigitalniZeleniSertifikat.PodaciOOsobi podaciOOsobi;
    @XmlElement(name = "Podaci_o_vakcinaciji", required = true)
    protected DigitalniZeleniSertifikat.PodaciOVakcinaciji podaciOVakcinaciji;
    @XmlElement(name = "Testovi", required = true)
    protected DigitalniZeleniSertifikat.Testovi testovi;
    @XmlElement(name = "Datum", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar datum;
    @XmlAttribute(name = "qr_kod")
    protected String qrKod;
    @XmlAttribute(name = "Id_sertifikata")
    protected BigInteger idSertifikata;

    /**
     * Gets the value of the podaciOSertifikatu property.
     * 
     * @return
     *     possible object is
     *     {@link DigitalniZeleniSertifikat.PodaciOSertifikatu }
     *     
     */
    public DigitalniZeleniSertifikat.PodaciOSertifikatu getPodaciOSertifikatu() {
        return podaciOSertifikatu;
    }

    /**
     * Sets the value of the podaciOSertifikatu property.
     * 
     * @param value
     *     allowed object is
     *     {@link DigitalniZeleniSertifikat.PodaciOSertifikatu }
     *     
     */
    public void setPodaciOSertifikatu(DigitalniZeleniSertifikat.PodaciOSertifikatu value) {
        this.podaciOSertifikatu = value;
    }

    /**
     * Gets the value of the podaciOOsobi property.
     * 
     * @return
     *     possible object is
     *     {@link DigitalniZeleniSertifikat.PodaciOOsobi }
     *     
     */
    public DigitalniZeleniSertifikat.PodaciOOsobi getPodaciOOsobi() {
        return podaciOOsobi;
    }

    /**
     * Sets the value of the podaciOOsobi property.
     * 
     * @param value
     *     allowed object is
     *     {@link DigitalniZeleniSertifikat.PodaciOOsobi }
     *     
     */
    public void setPodaciOOsobi(DigitalniZeleniSertifikat.PodaciOOsobi value) {
        this.podaciOOsobi = value;
    }

    /**
     * Gets the value of the podaciOVakcinaciji property.
     * 
     * @return
     *     possible object is
     *     {@link DigitalniZeleniSertifikat.PodaciOVakcinaciji }
     *     
     */
    public DigitalniZeleniSertifikat.PodaciOVakcinaciji getPodaciOVakcinaciji() {
        return podaciOVakcinaciji;
    }

    /**
     * Sets the value of the podaciOVakcinaciji property.
     * 
     * @param value
     *     allowed object is
     *     {@link DigitalniZeleniSertifikat.PodaciOVakcinaciji }
     *     
     */
    public void setPodaciOVakcinaciji(DigitalniZeleniSertifikat.PodaciOVakcinaciji value) {
        this.podaciOVakcinaciji = value;
    }

    /**
     * Gets the value of the testovi property.
     * 
     * @return
     *     possible object is
     *     {@link DigitalniZeleniSertifikat.Testovi }
     *     
     */
    public DigitalniZeleniSertifikat.Testovi getTestovi() {
        return testovi;
    }

    /**
     * Sets the value of the testovi property.
     * 
     * @param value
     *     allowed object is
     *     {@link DigitalniZeleniSertifikat.Testovi }
     *     
     */
    public void setTestovi(DigitalniZeleniSertifikat.Testovi value) {
        this.testovi = value;
    }

    /**
     * Gets the value of the datum property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDatum() {
        return datum;
    }

    /**
     * Sets the value of the datum property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDatum(XMLGregorianCalendar value) {
        this.datum = value;
    }

    /**
     * Gets the value of the qrKod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQrKod() {
        return qrKod;
    }

    /**
     * Sets the value of the qrKod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQrKod(String value) {
        this.qrKod = value;
    }

    /**
     * Gets the value of the idSertifikata property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdSertifikata() {
        return idSertifikata;
    }

    /**
     * Sets the value of the idSertifikata property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdSertifikata(BigInteger value) {
        this.idSertifikata = value;
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
     *         &lt;element name="Ime"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
     *               &lt;minLength value="2"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="Prezime"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
     *               &lt;minLength value="2"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="Pol"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
     *               &lt;enumeration value="Muski"/&gt;
     *               &lt;enumeration value="Zenski"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="Datum_rodjenja" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
     *         &lt;element name="Jmbg"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
     *               &lt;length value="13"/&gt;
     *               &lt;pattern value="\d{13}"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="Broj_pasosa"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
     *               &lt;pattern value="\d{9}"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
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
        "ime",
        "prezime",
        "pol",
        "datumRodjenja",
        "jmbg",
        "brojPasosa"
    })
    public static class PodaciOOsobi {

        @XmlElement(name = "Ime", required = true)
        protected String ime;
        @XmlElement(name = "Prezime", required = true)
        protected String prezime;
        @XmlElement(name = "Pol", required = true)
        protected String pol;
        @XmlElement(name = "Datum_rodjenja", required = true)
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar datumRodjenja;
        @XmlElement(name = "Jmbg", required = true)
        protected String jmbg;
        @XmlElement(name = "Broj_pasosa", required = true)
        protected String brojPasosa;

        /**
         * Gets the value of the ime property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIme() {
            return ime;
        }

        /**
         * Sets the value of the ime property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIme(String value) {
            this.ime = value;
        }

        /**
         * Gets the value of the prezime property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPrezime() {
            return prezime;
        }

        /**
         * Sets the value of the prezime property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPrezime(String value) {
            this.prezime = value;
        }

        /**
         * Gets the value of the pol property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPol() {
            return pol;
        }

        /**
         * Sets the value of the pol property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPol(String value) {
            this.pol = value;
        }

        /**
         * Gets the value of the datumRodjenja property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getDatumRodjenja() {
            return datumRodjenja;
        }

        /**
         * Sets the value of the datumRodjenja property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setDatumRodjenja(XMLGregorianCalendar value) {
            this.datumRodjenja = value;
        }

        /**
         * Gets the value of the jmbg property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getJmbg() {
            return jmbg;
        }

        /**
         * Sets the value of the jmbg property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setJmbg(String value) {
            this.jmbg = value;
        }

        /**
         * Gets the value of the brojPasosa property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBrojPasosa() {
            return brojPasosa;
        }

        /**
         * Sets the value of the brojPasosa property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBrojPasosa(String value) {
            this.brojPasosa = value;
        }

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
     *         &lt;element name="Broj_sertifikata" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/&gt;
     *         &lt;element name="Datum_i_vreme_izdavanja" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
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
        "brojSertifikata",
        "datumIVremeIzdavanja"
    })
    public static class PodaciOSertifikatu {

        @XmlElement(name = "Broj_sertifikata", required = true)
        @XmlSchemaType(name = "positiveInteger")
        protected BigInteger brojSertifikata;
        @XmlElement(name = "Datum_i_vreme_izdavanja", required = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar datumIVremeIzdavanja;

        /**
         * Gets the value of the brojSertifikata property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getBrojSertifikata() {
            return brojSertifikata;
        }

        /**
         * Sets the value of the brojSertifikata property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setBrojSertifikata(BigInteger value) {
            this.brojSertifikata = value;
        }

        /**
         * Gets the value of the datumIVremeIzdavanja property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getDatumIVremeIzdavanja() {
            return datumIVremeIzdavanja;
        }

        /**
         * Sets the value of the datumIVremeIzdavanja property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setDatumIVremeIzdavanja(XMLGregorianCalendar value) {
            this.datumIVremeIzdavanja = value;
        }

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
     *       &lt;sequence maxOccurs="unbounded"&gt;
     *         &lt;element name="Vakcinacija"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Tip"&gt;
     *                     &lt;simpleType&gt;
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
     *                         &lt;enumeration value="Pfizer-BioNTech"/&gt;
     *                         &lt;enumeration value="Sputnik V"/&gt;
     *                         &lt;enumeration value="Sinopharm"/&gt;
     *                         &lt;enumeration value="AstraZeneca"/&gt;
     *                         &lt;enumeration value="Moderna"/&gt;
     *                       &lt;/restriction&gt;
     *                     &lt;/simpleType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="Proizvodjac"&gt;
     *                     &lt;simpleType&gt;
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
     *                         &lt;minLength value="2"/&gt;
     *                       &lt;/restriction&gt;
     *                     &lt;/simpleType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="Serija" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/&gt;
     *                   &lt;element name="Datum" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
     *                   &lt;element name="Zdravstvena_ustanova"&gt;
     *                     &lt;simpleType&gt;
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
     *                         &lt;minLength value="3"/&gt;
     *                       &lt;/restriction&gt;
     *                     &lt;/simpleType&gt;
     *                   &lt;/element&gt;
     *                 &lt;/sequence&gt;
     *                 &lt;attribute name="br_doze"&gt;
     *                   &lt;simpleType&gt;
     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int"&gt;
     *                       &lt;minInclusive value="1"/&gt;
     *                     &lt;/restriction&gt;
     *                   &lt;/simpleType&gt;
     *                 &lt;/attribute&gt;
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
        "vakcinacija"
    })
    public static class PodaciOVakcinaciji {

        @XmlElement(name = "Vakcinacija", required = true)
        protected List<DigitalniZeleniSertifikat.PodaciOVakcinaciji.Vakcinacija> vakcinacija;

        /**
         * Gets the value of the vakcinacija property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the vakcinacija property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getVakcinacija().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DigitalniZeleniSertifikat.PodaciOVakcinaciji.Vakcinacija }
         * 
         * 
         */
        public List<DigitalniZeleniSertifikat.PodaciOVakcinaciji.Vakcinacija> getVakcinacija() {
            if (vakcinacija == null) {
                vakcinacija = new ArrayList<DigitalniZeleniSertifikat.PodaciOVakcinaciji.Vakcinacija>();
            }
            return this.vakcinacija;
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
         *         &lt;element name="Tip"&gt;
         *           &lt;simpleType&gt;
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
         *               &lt;enumeration value="Pfizer-BioNTech"/&gt;
         *               &lt;enumeration value="Sputnik V"/&gt;
         *               &lt;enumeration value="Sinopharm"/&gt;
         *               &lt;enumeration value="AstraZeneca"/&gt;
         *               &lt;enumeration value="Moderna"/&gt;
         *             &lt;/restriction&gt;
         *           &lt;/simpleType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="Proizvodjac"&gt;
         *           &lt;simpleType&gt;
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
         *               &lt;minLength value="2"/&gt;
         *             &lt;/restriction&gt;
         *           &lt;/simpleType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="Serija" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/&gt;
         *         &lt;element name="Datum" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
         *         &lt;element name="Zdravstvena_ustanova"&gt;
         *           &lt;simpleType&gt;
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
         *               &lt;minLength value="3"/&gt;
         *             &lt;/restriction&gt;
         *           &lt;/simpleType&gt;
         *         &lt;/element&gt;
         *       &lt;/sequence&gt;
         *       &lt;attribute name="br_doze"&gt;
         *         &lt;simpleType&gt;
         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int"&gt;
         *             &lt;minInclusive value="1"/&gt;
         *           &lt;/restriction&gt;
         *         &lt;/simpleType&gt;
         *       &lt;/attribute&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "tip",
            "proizvodjac",
            "serija",
            "datum",
            "zdravstvenaUstanova"
        })
        public static class Vakcinacija {

            @XmlElement(name = "Tip", required = true)
            protected String tip;
            @XmlElement(name = "Proizvodjac", required = true)
            protected String proizvodjac;
            @XmlElement(name = "Serija", required = true)
            @XmlSchemaType(name = "positiveInteger")
            protected BigInteger serija;
            @XmlElement(name = "Datum", required = true)
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar datum;
            @XmlElement(name = "Zdravstvena_ustanova", required = true)
            protected String zdravstvenaUstanova;
            @XmlAttribute(name = "br_doze")
            protected Integer brDoze;

            /**
             * Gets the value of the tip property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTip() {
                return tip;
            }

            /**
             * Sets the value of the tip property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTip(String value) {
                this.tip = value;
            }

            /**
             * Gets the value of the proizvodjac property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getProizvodjac() {
                return proizvodjac;
            }

            /**
             * Sets the value of the proizvodjac property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setProizvodjac(String value) {
                this.proizvodjac = value;
            }

            /**
             * Gets the value of the serija property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getSerija() {
                return serija;
            }

            /**
             * Sets the value of the serija property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setSerija(BigInteger value) {
                this.serija = value;
            }

            /**
             * Gets the value of the datum property.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getDatum() {
                return datum;
            }

            /**
             * Sets the value of the datum property.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setDatum(XMLGregorianCalendar value) {
                this.datum = value;
            }

            /**
             * Gets the value of the zdravstvenaUstanova property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getZdravstvenaUstanova() {
                return zdravstvenaUstanova;
            }

            /**
             * Sets the value of the zdravstvenaUstanova property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setZdravstvenaUstanova(String value) {
                this.zdravstvenaUstanova = value;
            }

            /**
             * Gets the value of the brDoze property.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getBrDoze() {
                return brDoze;
            }

            /**
             * Sets the value of the brDoze property.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setBrDoze(Integer value) {
                this.brDoze = value;
            }

        }

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
     *         &lt;element name="Naziv_testa" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="Vrsta_uzorka" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="Datum_i_vreme_uzorkovanja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="Datum_i_vreme_izdavanja_rezultata" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "nazivTesta",
        "vrstaUzorka",
        "datumIVremeUzorkovanja",
        "datumIVremeIzdavanjaRezultata"
    })
    public static class Testovi {

        @XmlElement(name = "Naziv_testa", required = true)
        protected String nazivTesta;
        @XmlElement(name = "Vrsta_uzorka", required = true)
        protected String vrstaUzorka;
        @XmlElement(name = "Datum_i_vreme_uzorkovanja", required = true)
        protected String datumIVremeUzorkovanja;
        @XmlElement(name = "Datum_i_vreme_izdavanja_rezultata", required = true)
        protected String datumIVremeIzdavanjaRezultata;

        /**
         * Gets the value of the nazivTesta property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNazivTesta() {
            return nazivTesta;
        }

        /**
         * Sets the value of the nazivTesta property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNazivTesta(String value) {
            this.nazivTesta = value;
        }

        /**
         * Gets the value of the vrstaUzorka property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getVrstaUzorka() {
            return vrstaUzorka;
        }

        /**
         * Sets the value of the vrstaUzorka property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setVrstaUzorka(String value) {
            this.vrstaUzorka = value;
        }

        /**
         * Gets the value of the datumIVremeUzorkovanja property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDatumIVremeUzorkovanja() {
            return datumIVremeUzorkovanja;
        }

        /**
         * Sets the value of the datumIVremeUzorkovanja property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDatumIVremeUzorkovanja(String value) {
            this.datumIVremeUzorkovanja = value;
        }

        /**
         * Gets the value of the datumIVremeIzdavanjaRezultata property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDatumIVremeIzdavanjaRezultata() {
            return datumIVremeIzdavanjaRezultata;
        }

        /**
         * Sets the value of the datumIVremeIzdavanjaRezultata property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDatumIVremeIzdavanjaRezultata(String value) {
            this.datumIVremeIzdavanjaRezultata = value;
        }

    }

}