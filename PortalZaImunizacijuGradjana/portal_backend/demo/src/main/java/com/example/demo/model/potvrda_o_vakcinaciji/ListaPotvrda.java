package com.example.demo.model.potvrda_o_vakcinaciji;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "potvrde"
})
@XmlRootElement(name = "lista_potvrda")
public class ListaPotvrda {

    @XmlElement(required = true)
    protected List<PotvrdaOVakcinaciji> potvrde;

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
     * {@link PotvrdaOVakcinaciji }
     *
     *
     */
    public List<PotvrdaOVakcinaciji> getPotvrde() {
        if (potvrde == null) {
            potvrde = new ArrayList<PotvrdaOVakcinaciji>();
        }
        return this.potvrde;
    }

    public void setPotvrde(List<PotvrdaOVakcinaciji> potvrde){
        this.potvrde = potvrde;
    }

}
