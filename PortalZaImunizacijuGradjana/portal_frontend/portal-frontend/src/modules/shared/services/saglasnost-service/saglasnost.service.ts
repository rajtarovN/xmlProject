import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { EvidencijaVakcinacije } from 'src/modules/zradnik/models/evidencija-vakcinacije';

@Injectable({
  providedIn: 'root',
})
export class SaglasnostService {
  constructor(private http: HttpClient, private route: Router) {}

  searchTermine(datumTermina: Date, imePrezime: string): Observable<string> {
    const headers = new HttpHeaders({
      Accept: 'application/xml',
    });
    return this.http.get(
      `${environment.baseUrl}/${environment.searchTermine}/${imePrezime}/${datumTermina}`,
      { headers: headers, responseType: 'text' }
    );
  }

  getEvidentiranevakcine(email: string): Observable<string> {
    const headers = new HttpHeaders({
      Accept: 'application/xml',
    });
    return this.http.get(
      `${environment.baseUrl}/${environment.getEvidentiraneVakcine}/${email}`,
      { headers: headers, responseType: 'text' }
    );
  }

  saveEvidenciju(
    evidencija: EvidencijaVakcinacije,
    brojSaglasnosti: string
  ): Observable<string> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/xml',
    });
    return this.http.post(
      `${environment.baseUrl}/${environment.saveEvidenciju}/${brojSaglasnosti}`,
      evidencija,
      { headers: headers, responseType: 'text' }
    );
  }

  saveEvidentiraneVakcine(
    vakcine: any,
    brojSaglasnosti: string
  ): Observable<string> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/xml',
    });
    return this.http.post(
      `${environment.baseUrl}/${environment.saveEvidentiraneVakcine}/${brojSaglasnosti}`,
      vakcine,
      { headers: headers, responseType: 'text' }
    );
  }

  getSaglasnostGradjanin(email: string) {
    const headers = new HttpHeaders({
      Accept: 'application/xml',
    });
    return this.http.get(
      `${environment.baseUrl}/saglasnost/zadnjaSaglasnost/` + email,
      {
        headers: headers,
        responseType: 'text',
      }
    );
  }

  saveSaglasnostGradjanin(
    document: String,
    documentId: string,
    vakcine: String
  ) {
    const headers = new HttpHeaders({
      'Content-Type': 'application/xml',
    });
    return this.http.post(
      `${environment.baseUrl}/saglasnost/saveSaglasnost/${documentId}/${vakcine}`,
      document,
      { headers: headers, responseType: 'text' }
    );
  }

  getAvailableVacs(): Observable<string> {
    const headers = new HttpHeaders({
      Accept: 'application/xml',
    });
    return this.http.get(
      `${environment.baseUrl}/${environment.getAvailableVacs}`,
      { headers: headers, responseType: 'text' }
    );
  }

  getPdf(id: string): Observable<any> {
    if(!id.startsWith("saglasnost")){
      id = "saglasnost_" + id + ".xml";
    }
    return this.http.get(
      `${environment.baseUrl}/${environment.saglasnostPdf}/${id}`,
      { responseType: 'arraybuffer' }
    );
  }
  getXHtml(id: string): Observable<any> {
    if(!id.startsWith("saglasnost")){
      id = "saglasnost_" + id + ".xml";
    }
    return this.http.get(
      `${environment.baseUrl}/${environment.saglasnostXhtml}/${id}`,
      { responseType: 'arraybuffer' });
  }

  getXmlIdsByEmail(email: string): Observable<string> {
    const headers = new HttpHeaders({
      Accept: 'application/xml',
    });
    return this.http.get(
      `${environment.baseUrl}` + '/saglasnost/getAllS/'+`${email}`,
      { headers: headers, responseType: 'text' }
    );
  }

  getJSON(id: string): Observable<any> {
    return this.http.get(
      `${environment.baseUrl}/${environment.jsonSaglasnost}/${id}`,
      { responseType: 'arraybuffer' as 'text' }
    );
  }

  getRDF(id: string): Observable<any> {
    return this.http.get(
      `${environment.baseUrl}/${environment.rdfSaglasnost}/${id}`,
      { responseType: 'arraybuffer' as 'text' }
    );
  }
}
