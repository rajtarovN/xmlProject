import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SertifikatService {

  constructor(private http: HttpClient, private route: Router) {}

  getPdf(id: string): Observable<any> {
    return this.http.get(
      `${environment.baseUrl}/${environment.sertifikatPdf}/${id}`,
      { responseType: 'arraybuffer' }
    );
  }
  getXHtml(id: string): Observable<any> {
    return this.http.get(
      `${environment.baseUrl}/${environment.sertifikatXhtml}/${id}`,
      { responseType: 'arraybuffer' });}

  getXmlByEmail(email: string): Observable<string> {
    const headers = new HttpHeaders({
      Accept: 'application/xml',
    });
    return this.http.get(
      `${environment.baseUrl}/sertifikat/getAllS/${email}`,
      { headers: headers, responseType: 'text' }
    );
  }

  getJSON(id: string): Observable<any> {
    return this.http.get(
      `${environment.baseUrl}/${environment.jsonSertifikat}/${id}`,
      { responseType: 'arraybuffer' as 'text' }
    );
  }

  getRDF(id: string): Observable<any> {
    return this.http.get(
      `${environment.baseUrl}/${environment.rdfSertifikat}/${id}`,
      { responseType: 'arraybuffer' as 'text' }
    );
  }
}
