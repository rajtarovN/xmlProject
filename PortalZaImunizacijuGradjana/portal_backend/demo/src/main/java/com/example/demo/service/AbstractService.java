package com.example.demo.service;

import java.io.OutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.xmldb.api.modules.XMLResource;

import com.example.demo.repository.RepositoryInterface;


public abstract class AbstractService {
  
  protected RepositoryInterface repository;

  protected String collectionId;

  protected String fusekiCollectionId;

  public AbstractService(RepositoryInterface repository, String collectionId, String fusekiCollectionId){
    this.repository = repository;
    this.collectionId = collectionId;
    this.fusekiCollectionId = fusekiCollectionId;
  }
   
  public void saveRDF(String content, String uri) throws Exception {
      repository.saveRDF(content, fusekiCollectionId + uri);
  }

  public void saveXML(String documentId, String content) throws Exception {
      
      repository.saveXML(documentId, collectionId, content );

  }


  public XMLResource readXML(String documentId) {

      XMLResource document = null;
      
      try {
          document = repository.readXML(documentId, collectionId);
      } catch (Exception e) {
          e.printStackTrace();
      }
      
      return document;
  }

  public String readFileAsXML(String uri) throws Exception {
      uri = fusekiCollectionId + uri;
      return repository.readFileAsXML(uri);
  }

  public String readFileAsJSON(String uri) throws Exception {
      uri = fusekiCollectionId + uri;
      return repository.readFileAsJSON(uri);
  }


  /**
   * 
   * @param <T> ovo je tip objekta. Moze biti ZalbaNaCutanje, ZalbaNaOdluku, Resenje,...
   * @param classType ovo je class object. Moze biti ZalbaNaCutanje.class, ZalbaNaOdluku.class, Resenje.class,...
   * @return List<T> ovo je lista pronadjenih dokumenata. Npr vraca List<ZalbaNaCutanje> 
   * @throws Exception bilo koji exception
   * 
   * evo primera konkretne implementacije metode bez Generics-a:
   * public List<ZalbaNaCutanje> findAllFromCollection(){
       List<ZalbaNaCutanje> temp = new ArrayList<>();
        List<XMLResource> retval =  this.repository.findAllFromCollection(collectionId);

        JAXBContext context = JAXBContext.newInstance(ZalbaNaCutanje.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        for (XMLResource dokument : retval) {
            String s = dokument.getContent().toString();
            StringReader reader = new StringReader(s);
            ZalbaNaCutanje zalba = (ZalbaNaCutanje) unmarshaller.unmarshal(reader);
            temp.add(zalba);
        }
        return temp;
     }
   *
   */
  public <T> List<T> findAllFromCollection( Class<T> classType) throws Exception{
    System.out.println(classType);
    List<T> temp = new ArrayList<>();
    List<XMLResource> retval =  this.repository.findAllFromCollection(collectionId);

    JAXBContext context = JAXBContext.newInstance(classType);
    Unmarshaller unmarshaller = context.createUnmarshaller();

    for (XMLResource dokument : retval) {
        String s = dokument.getContent().toString();
        StringReader reader = new StringReader(s);
        T dokumentObject = (T) unmarshaller.unmarshal(reader);
        temp.add(dokumentObject);
    }
    return temp;
    
  }
}
