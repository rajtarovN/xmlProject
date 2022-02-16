package com.example.sluzbenik_back.model.digitalni_zeleni_sertifikat;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "sertifikati"
})
@XmlRootElement(name = "lista_sertifikata")
public class ListaSertifikata {

    @XmlElement(required = true)
    protected List<DigitalniZeleniSertifikat> sertifikati;

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
     * {@link DigitalniZeleniSertifikat }
     *
     *
     */
    public List<DigitalniZeleniSertifikat> getSertifikate() {
        if (sertifikati == null) {
            sertifikati = new ArrayList<DigitalniZeleniSertifikat>();
        }
        return this.sertifikati;
    }

    public void setSertifikate(List<DigitalniZeleniSertifikat> sertifikati){
        this.sertifikati = sertifikati;
    }

}
