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


  addNew(zahtev: string, jmbg: string, datum: string): Observable<any> {
    console.log(`${environment.baseUrl}`)
    console.log(zahtev)
    datum = jmbg+"/"+datum;
    const opt = {
      headers: new HttpHeaders({
        'Content-Type': 'application/xml',
        Accept: 'application/xml',
        responseType: 'text',
      }),
    };
    return this.http.post<any>(`${environment.baseUrl}` +  '/zahtev/'+datum, zahtev, opt);
  }

  getXmlString(){//ime: string, prezime: string, rodjendan: string, pol: string){
    return ` <Razlog_podnosenja_zahteva></Razlog_podnosenja_zahteva>`;
  }

  getZahtev(email: string){
    return this.http.get<any>(`${environment.baseUrl}` +  '/zahtev/getZahtev/'+email, this.httpOptions);
  }

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
        //asker: Xonomy.askString,
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

  getPdf(id: string): Observable<any> {
    return this.http.get(`${environment.baseUrl}` + '/zahtev/generatePDF/' + id, {responseType: 'arraybuffer'});
  }
  getHtml(id: string): Observable<any> {
    return this.http.get(`${environment.baseUrl}` + '/zahtev/generateHTML/' + id, {responseType: 'arraybuffer'});
  }

  getJSON(id: string): Observable<any> {
    return this.http.get(
      `${environment.baseUrl}/${environment.jsonZahtev}/${id}`,
      { responseType: 'arraybuffer' as 'text' }
    );
  }

  getRDF(id: string): Observable<any> {
    return this.http.get(
      `${environment.baseUrl}/${environment.rdfZahtev}/${id}`,
      { responseType: 'arraybuffer' as 'text' }
    );
  }
}
