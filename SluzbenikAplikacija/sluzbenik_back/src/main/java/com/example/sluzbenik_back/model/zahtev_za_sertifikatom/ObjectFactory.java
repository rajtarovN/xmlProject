//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.02.17 at 01:21:47 PM CET 
//


package com.example.sluzbenik_back.model.zahtev_za_sertifikatom;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the main.java.xml_i_veb_servisi.zahtev_za_sertifikatom package.
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: main.java.xml_i_veb_servisi.zahtev_za_sertifikatom
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ZahtevZaZeleniSertifikat }
     * 
     */
    public ZahtevZaZeleniSertifikat createZahtevZaZeleniSertifikat() {
        return new ZahtevZaZeleniSertifikat();
    }

    /**
     * Create an instance of {@link Zaglavlje }
     * 
     */
    public Zaglavlje createZaglavlje() {
        return new Zaglavlje();
    }

    /**
     * Create an instance of {@link PodnosilacZahteva }
     * 
     */
    public PodnosilacZahteva createPodnosilacZahteva() {
        return new PodnosilacZahteva();
    }

    /**
     * Create an instance of {@link ZahtevZaZeleniSertifikat.Status }
     * 
     */
    public ZahtevZaZeleniSertifikat.Status createZahtevZaZeleniSertifikatStatus() {
        return new ZahtevZaZeleniSertifikat.Status();
    }

    /**
     * Create an instance of {@link Zaglavlje.DanPodnosenjaZahteva }
     * 
     */
    public Zaglavlje.DanPodnosenjaZahteva createZaglavljeDanPodnosenjaZahteva() {
        return new Zaglavlje.DanPodnosenjaZahteva();
    }

    /**
     * Create an instance of {@link PodnosilacZahteva.Ime }
     * 
     */
    public PodnosilacZahteva.Ime createPodnosilacZahtevaIme() {
        return new PodnosilacZahteva.Ime();
    }

    /**
     * Create an instance of {@link PodnosilacZahteva.Prezime }
     * 
     */
    public PodnosilacZahteva.Prezime createPodnosilacZahtevaPrezime() {
        return new PodnosilacZahteva.Prezime();
    }

    /**
     * Create an instance of {@link PodnosilacZahteva.Jmbg }
     * 
     */
    public PodnosilacZahteva.Jmbg createPodnosilacZahtevaJmbg() {
        return new PodnosilacZahteva.Jmbg();
    }

    /**
     * Create an instance of {@link PodnosilacZahteva.BrojPasosa }
     * 
     */
    public PodnosilacZahteva.BrojPasosa createPodnosilacZahtevaBrojPasosa() {
        return new PodnosilacZahteva.BrojPasosa();
    }

}
