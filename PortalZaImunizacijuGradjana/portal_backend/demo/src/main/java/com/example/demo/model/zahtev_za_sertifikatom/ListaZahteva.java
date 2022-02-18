package com.example.demo.model.zahtev_za_sertifikatom;

import com.example.demo.model.korisnik.Korisnik;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "zahtevi"
})
@XmlRootElement(name = "lista_zahteva")
public class ListaZahteva {

    @XmlElement(required = true)
    protected List<ZahtevZaZeleniSertifikat> zahtevi;

    /**
     * Gets the value of the korisnik property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the korisnik property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getZahtevi().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ZahtevZaZeleniSertifikat }
     *
     *
     */
    public List<ZahtevZaZeleniSertifikat> getZahtevi() {
        if (zahtevi == null) {
            zahtevi = new ArrayList<ZahtevZaZeleniSertifikat>();
        }
        return this.zahtevi;
    }

    public void setZahtevi(List<ZahtevZaZeleniSertifikat> zahtevi){
        this.zahtevi = zahtevi;
    }

}
