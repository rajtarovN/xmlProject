//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.01.06 at 03:04:04 PM CET 
//


package rs.ac.uns.ftn.xml_i_veb_servisi.model.interesovanje;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TDrzavljanstvo.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TDrzavljanstvo"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Drzavljanin_republike_srbije"/&gt;
 *     &lt;enumeration value="Strani_drzavljanin_sa_boravkom_u_rs"/&gt;
 *     &lt;enumeration value="Strani_drzavljanin_bez_boravka_u_rs"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "TDrzavljanstvo")
@XmlEnum
public enum TDrzavljanstvo {

    @XmlEnumValue("Drzavljanin_republike_srbije")
    DRZAVLJANIN_REPUBLIKE_SRBIJE("Drzavljanin_republike_srbije"),
    @XmlEnumValue("Strani_drzavljanin_sa_boravkom_u_rs")
    STRANI_DRZAVLJANIN_SA_BORAVKOM_U_RS("Strani_drzavljanin_sa_boravkom_u_rs"),
    @XmlEnumValue("Strani_drzavljanin_bez_boravka_u_rs")
    STRANI_DRZAVLJANIN_BEZ_BORAVKA_U_RS("Strani_drzavljanin_bez_boravka_u_rs");
    private final String value;

    TDrzavljanstvo(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TDrzavljanstvo fromValue(String v) {
        for (TDrzavljanstvo c: TDrzavljanstvo.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
