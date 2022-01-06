package rs.ac.uns.ftn.xml_i_veb_servisi.service;

import rs.ac.uns.ftn.xml_i_veb_servisi.repository.AbstractRepository;

public abstract class AbstractService {

    protected AbstractRepository repository;

    protected String collectionId;

    protected String fusekiCollectionId;

    public AbstractService(AbstractRepository repository, String collectionId, String fusekiCollectionId){
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
}
