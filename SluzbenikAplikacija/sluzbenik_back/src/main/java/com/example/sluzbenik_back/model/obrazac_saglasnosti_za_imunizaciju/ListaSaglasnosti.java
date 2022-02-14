package com.example.sluzbenik_back.model.obrazac_saglasnosti_za_imunizaciju;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "saglasnosti"
})
@XmlRootElement(name = "lista_saglasnosti")
public class ListaSaglasnosti {

    @XmlElement(required = true)
    protected List<Saglasnost> saglasnosti;

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
     * {@link Saglasnost }
     *
     *
     */
    public List<Saglasnost> getSaglasnosti() {
        if (saglasnosti == null) {
            saglasnosti = new ArrayList<Saglasnost>();
        }
        return this.saglasnosti;
    }

    public void setSaglasnosti(List<Saglasnost> saglasnosti){
        this.saglasnosti = saglasnosti;
    }

}
