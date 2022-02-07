package com.example.sluzbenik_back.model.liste;

import com.example.sluzbenik_back.model.korisnik.Korisnik;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.ArrayList;
import java.util.List;

//When doing so you have to specify which classes could possibly be in the list using @XmlSeeAlso
@XmlRootElement
@XmlSeeAlso({ Korisnik.class})
public class JaxbLista<T> {
  private List<T> lista = new ArrayList<>();


  public JaxbLista() {
  }

  public JaxbLista(List<T> lista) {
    this.lista = lista;
  }

  @XmlAnyElement
  public List<T> getLista() {
    return this.lista;
  }
}