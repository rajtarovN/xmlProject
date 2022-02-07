package com.example.sluzbenik_back.model.korisnik;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;


/**
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://ftn.uns.ac.rs/korisnik}ime_i_prezime"/>
 *         &lt;element ref="{http://ftn.uns.ac.rs/korisnik}email"/>
 *         &lt;element ref="{http://ftn.uns.ac.rs/korisnik}lozinka"/>
 *         &lt;element ref="{http://ftn.uns.ac.rs/korisnik}uloga"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "imeIPrezime",
        "email",
        "lozinka",
        "uloga"
})
@XmlRootElement(name = "korisnik")
@JsonRootName(value = "korisnik")
public class Korisnik implements UserDetails{

    @XmlElement(name = "ime_i_prezime", required = true)
    @JsonProperty("ime_i_prezime")
    protected String imeIPrezime;
    @XmlElement(required = true)
    protected String email;
    @XmlElement(required = true)
    protected String lozinka;
    @XmlElement(required = true)
    protected String uloga;

    /**
     * Gets the value of the imeIPrezime property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getImeIPrezime() {
        return imeIPrezime;
    }

    /**
     * Sets the value of the imeIPrezime property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setImeIPrezime(String value) {
        this.imeIPrezime = value;
    }

    /**
     * Gets the value of the email property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the lozinka property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getLozinka() {
        return lozinka;
    }

    /**
     * Sets the value of the lozinka property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setLozinka(String value) {
        this.lozinka = value;
    }

    /**
     * Gets the value of the uloga property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getUloga() {
        return uloga;
    }

    /**
     * Sets the value of the uloga property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setUloga(String value) {
        this.uloga = value;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<Authority> authorities = new ArrayList<Authority>();

        authorities.add(new Authority("ROLE_" + this.uloga));

        return authorities;
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return this.lozinka;
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

}
