import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class PotvrdeService {
  constructor(private http: HttpClient, private route: Router) {}

  getXmlByEmail(email: string): Observable<string> {
    const headers = new HttpHeaders({
      Accept: 'application/xml',
    });
    return this.http.get(
      `${environment.baseUrl}/${environment.getPotvrde}/${email}`,
      { headers: headers, responseType: 'text' }
    );
  }

  getPdf(id: string): Observable<any> {
    return this.http.get(
      `${environment.baseUrl}/${environment.potvrdaPdf}/${id}`,
      { responseType: 'arraybuffer' }
    );
  }

  obicnaPretraga(searchTerm: string): Observable<string> {
    const headers = new HttpHeaders({
      Accept: 'application/xml',
    });
    return this.http.get(
      `${environment.baseUrl}/${environment.obicnaPretragaPotvrda}/${searchTerm}`,
      { headers: headers, responseType: 'text' }
    );
  }

  getXHtml(id: string): Observable<any> {
    return this.http.get(
      `${environment.baseUrl}/${environment.potvrdaXhtml}/${id}`,
      { responseType: 'arraybuffer' }
    );
  }

  getAll(): Observable<any> {
    return this.http.get(`${environment.baseUrl}/potvrda/getAll`, {
      responseType: 'text',
    });
  }

  // api/potvrda/referenciraniDoc/id
  getAllRefs(id: String): Observable<any> {
    return this.http.get(
      `${environment.baseUrl}/potvrda/referenciraniDoc/` + id,
      {
        responseType: 'text',
      }
    );
  }

  getJSON(id: string): Observable<any> {
    return this.http.get(
      `${environment.baseUrl}/${environment.jsonPotvrda}/${id}`,
      { responseType: 'arraybuffer' as 'text' }
    );
  }

  getRDF(id: string): Observable<any> {
    return this.http.get(
      `${environment.baseUrl}/${environment.rdfPotvrda}/${id}`,
      { responseType: 'arraybuffer' as 'text' }
    );
  }

  naprednaPretraga(data: any): Observable<string> {
    const headers = new HttpHeaders({
      Accept: 'application/xml',
      'Content-Type': 'application/xml',
    });
    return this.http.post(
      `${environment.baseUrl}/potvrda/naprednaPretraga`,
      data,
      { headers: headers, responseType: 'text' }
    );
  }
}
