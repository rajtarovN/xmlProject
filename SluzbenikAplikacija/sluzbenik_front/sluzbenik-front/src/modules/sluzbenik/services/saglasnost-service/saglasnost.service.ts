import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class SaglasnostService {
  constructor(private http: HttpClient, private route: Router) {}

  getXmlIdsByEmail(email: string): Observable<string> {
    const headers = new HttpHeaders({
      Accept: 'application/xml',
    });
    return this.http.get(
      `${environment.baseUrl}/${environment.getSaglasnosti}/${email}`,
      { headers: headers, responseType: 'text' }
    );
  }

  getPdf(id: string): Observable<any> {
    return this.http.get(
      `${environment.baseUrl}/${environment.saglasnostPdf}/${id}`,
      { responseType: 'arraybuffer' }
    );
  }

  getXHtml(id: string): Observable<any> {
    return this.http.get(
      `${environment.baseUrl}/${environment.saglasnostXhtml}/${id}`,
      { responseType: 'arraybuffer' }
    );
  }

  // api/saglasnost/getAll
  getAll(): Observable<any> {
    return this.http.get(`${environment.baseUrl}/saglasnost/getAll`, {
      responseType: 'text',
    });
  }

  // api/saglasnost/referenciraniDoc/id
  getAllRefs(id: String): Observable<any> {
    return this.http.get(
      `${environment.baseUrl}/saglasnost/referenciraniDoc/` + id,
      {
        responseType: 'text',
      }
    );
  }

  naprednaPretraga(data: any): Observable<string> {
    const headers = new HttpHeaders({
      Accept: 'application/xml',
      'Content-Type': 'application/xml',
    });
    return this.http.post(
      `${environment.baseUrl}/saglasnost/naprednaPretraga`,
      data,
      { headers: headers, responseType: 'text' }
    );
  }

  obicnaPretraga(searchTerm: string): Observable<string> {
    const headers = new HttpHeaders({
      Accept: 'application/xml',
    });
    return this.http.get(
      `${environment.baseUrl}/${environment.obicnaPretragaSagl}/${searchTerm}`,
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
