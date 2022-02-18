# xmlProject

SW02-2018	Rajtarov Nataša

SW62-2018	Mirilović Olivera

SW67-2018	Rajnović Teodora

SW72-2018 Savić Isidora


UPUTSTVA ZA POKRETANJE:

- Nameštanje baze:

  - XML baza
  1. Preuzeti Apache TomEE plus aplikativni server: http://tomee.apache.org/download-ng.html
  2. Preuzeti distribuciju eXist XML baze podataka: https://github.com/eXist-db/exist/releases/tag/eXist-4.6.1
  3. Preimenovati exist-x.x.x.war u existPortal.war i u existSluzbenik.war
  4. Deployovati tj. kopirati existPortal.war i existSluzbenik.war u /webapps direktorijum TomEE-a
  5. Pokrenuti aplikativni server i pristupiti dashboardu portal exist-a: http://localhost:8080/existPortal i službenik na http://localhost:8080/existSluzbenik
  
  - RDF baza
  1. Preuzeti distribuciju Apache Jena Fuseki SPARQL servera verzije 3.17.0: https://archive.apache.org/dist/jena/binaries/apache-jena-fuseki-3.17.0.zip
  2. Raspakovati apache-jena-fuseki-x.x.x.zip fajl
  3. Deployovati tj. kopirati ekstrahovani fusekiPortal.war u /webapps direktorijum TomEE-a
  4. Pokrenuti aplikativni server i pristupiti admin interfejsu Fuseki servera: http://localhost:8080/fusekiPortal
  5. U fuseki portalu kreirati novu bazu pod nazivom portalDB
  
  
- Službenik:
  - Backend:
    -Projekat se nalazi u SluzbenikApplikacija/sluzbenik_back folderu. Port - 8082. Pre pokretanja potrebno je ubaciti sve iz lib foldera u build projekta. Potrebno je pokrenuti SluzbenikBackApplication koja sadrži main metodu. 
  - Frontend
    -Projekat se nalazi u SluzbenikApplikacija/sluzbenik_front folderu. Port - 4021. Potrebno je u terminalu u projektnom direktorijumu pokerenuti npm intall, a zatim npm start. 
   -Korisnici  
    -Korisnik email - sluzbenik@maildrop.cc, lozinka - test


- Portal:
  - Backend:
    -Projekat se nalazi u PortalZaImunizacijuGradjana/portal_backend/demo folderu. Port - 8081.  Pre pokretanja potrebno je ubaciti sve iz lib foldera u build projekta. Potrebno je pokrenuti DemoApplication koja sadrži main metodu. 
  - Frontend
    -Projekat se nalazi u SluzbenikApplikacija/sluzbenik_front folderu. Port - 4020. Potrebno je u terminalu u projektnom direktorijumu pokerenuti npm intall, a zatim npm start.
  - Korisnici
    -Korisnik email - gradjanin@maildrop.cc, lozinka - test, uloga - Građanin
    -Korisnik email - zradnik@maildrop.cc, lozinka - test, uloga - Zdravstveni radnik
    
    
- Email server:
  - Backend:
    -Projekat se nalazi u emailService folderu. Port - 8094. Pre pokretanja potrebno je ubaciti sve iz lib foldera u build projekta. Potrebno je pokrenuti EmailServiceApplication koja sadrži main metodu. 
