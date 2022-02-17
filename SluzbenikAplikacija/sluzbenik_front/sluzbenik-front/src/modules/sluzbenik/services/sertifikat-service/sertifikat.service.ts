import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class SertifikatService {
  constructor(private http: HttpClient) {}

  // api/sertifikat/getAll
  getAll(): Observable<any> {
    return this.http.get(`${environment.baseUrl}/sertifikat/getAll`, {
      responseType: 'text',
    });
  }

  naprednaPretraga(data: any): Observable<string> {
    const headers = new HttpHeaders({
      Accept: 'application/xml',
      'Content-Type': 'application/xml',
    });
    return this.http.post(
      `${environment.baseUrl}/sertifikat/naprednaPretraga`,
      data,
      { headers: headers, responseType: 'text' }
    );
  }

  getXmlByEmail(email: string): Observable<string> {
    const headers = new HttpHeaders({
      Accept: 'application/xml',
    });
    return this.http.get(
      `${environment.baseUrl}/${environment.getSertifikate}/${email}`,
      { headers: headers, responseType: 'text' }
    );
  }

  getPdf(id: string): Observable<any> {
    return this.http.get(
      `${environment.baseUrl}/${environment.sertifikatPdf}/${id}`,
      { responseType: 'arraybuffer' }
    );
  }

  obicnaPretraga(searchTerm: string): Observable<string> {
    const headers = new HttpHeaders({
      Accept: 'application/xml'
    });
    return this.http.get(
      `${environment.baseUrl}/${environment.obicnaPretragaSertif}/${searchTerm}`,
      { headers: headers, responseType: 'text' }
    );
  }
}
