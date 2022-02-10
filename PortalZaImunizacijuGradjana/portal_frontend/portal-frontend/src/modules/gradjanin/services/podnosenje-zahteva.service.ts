import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { environment } from '../../../../src/environments/environment'

declare const Xonomy: any;
declare const require: any;
// const xml2js = require("xml2js");

@Injectable({
  providedIn: 'root'
})
export class PodnosenjeZahtevaService {
  constructor(private http: HttpClient, private route: Router) {}

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/xml',
      Accept: 'application/xml',
    }),
  };


  addNew(zahtev: string): Observable<any> {
    console.log(`${environment.baseUrl}`)
    console.log(zahtev)
    return this.http.post<any>(`${environment.baseUrl}` +  '/zahtev/123/1992-02-02', zahtev, this.httpOptions);
  }

  public xmlString=  `
  <Zahtev_za_zeleni_sertifikat xmlns="http://www.ftn.uns.ac.rs/xml_i_veb_servisi/zahtev_za_sertifikatom"
      xmlns:xsi="http://www.ftn.uns.ac.rs/xml_i_veb_servisi/zahtev_za_sertifikatom"
      about="http://www.ftn.uns.ac.rs/xml_i_veb_servisi/zahtev_za_sertifikatom/"
      xmlns:pred="http://www.ftn.uns.ac.rs/xml_i_veb_servisi/rdf/zahtev_za_sertifikatom/predicate/"
      xsi:schemaLocation="http://www.ftn.uns.ac.rs/xml_i_veb_servisi/zahtev_za_sertifikatom ../schema/zahtev_sema.xsd">
      <Podnosilac_zahteva>
          <Ime property="pred:ime"></Ime>
          <Prezime property="pred:prezime"></Prezime>
          <Datum_rodjenja></Datum_rodjenja>
          <Pol></Pol>
          <Jmbg property="pred:jmbg"></Jmbg>
          <Broj_pasosa property="pred:broj_pasosa"></Broj_pasosa>
          <Razlog_podnosenja_zahteva></Razlog_podnosenja_zahteva>
      </Podnosilac_zahteva>
      <Zaglavlje>
          <Mesto_podnosenja_zahteva></Mesto_podnosenja_zahteva>
          <Dan_podnosenja_zahteva property="pred:dan_podnosenja_zahteva"></Dan_podnosenja_zahteva>
      </Zaglavlje>
  </Zahtev_za_zeleni_sertifikat>`;


  public obavestenjeSpecifikacija = {
    elements: {
      Zahtev_za_zeleni_sertifikat: {
      },
      Podnosilac_zahteva: {
      },
      Ime: {
        hasText: true,
        asker: Xonomy.askString,
        oneliner: true
      },
      Prezime: {
        hasText: true,
        asker: Xonomy.askString,
        oneliner: true
      },
      Datum_rodjenja: {

      },
      Pol: {
        hasText: true,
        asker: Xonomy.askString,
        oneliner: true
      },
      Jmbg: {
        hasText: true,
        asker: Xonomy.askString,
        oneliner: true
      },
      trazilac: {

      },
      Broj_pasosa: {
        hasText: true,
        asker: Xonomy.askString,
        oneliner: true
      },
      Razlog_podnosenja_zahteva: {
        hasText: true,
        asker: Xonomy.askString,
        oneliner: true
      },
      Zaglavlje: {
      },
      Mesto_podnosenja_zahteva: {
        hasText: true,
        asker: Xonomy.askString,
        oneliner: true
      },
      Dan_podnosenja_zahteva: {
        hasText: true,
        asker: Xonomy.askString,
        oneliner: true
      },
    }
  }
}
