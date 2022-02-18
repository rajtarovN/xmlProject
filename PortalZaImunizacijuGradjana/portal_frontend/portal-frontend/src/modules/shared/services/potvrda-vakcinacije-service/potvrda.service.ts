import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { PotvrdaVakcinacije } from 'src/modules/zradnik/models/potvrda-vakcinacije';

@Injectable({
  providedIn: 'root'
})
export class PotvrdaService {
  constructor(private http: HttpClient, private route: Router) {}

  createPotvrdu(brojSaglasnosti: string): Observable<string> {
    const headers = new HttpHeaders({
      Accept: 'application/xml',
    });
    return this.http.get(
      `${environment.baseUrl}/${environment.createPotvrdu}/${brojSaglasnosti}`,
      { headers: headers,
        responseType: "text" }
    );
  }

  savePotvrdu(potvrda: PotvrdaVakcinacije): Observable<string> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/xml',
    });
    return this.http.post(
      `${environment.baseUrl}/${environment.savePotvrdu}`,
      potvrda,
      { headers: headers,
        responseType: "text" }
    );
  }

  saveDoze(vakcine: any, potvrdaId: string, email: string): Observable<string> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/xml',
    });
    return this.http.post(
      `${environment.baseUrl}/${environment.saveDoze}/${potvrdaId}/${email}`,
      vakcine,
      { headers: headers,
        responseType: "text", }
    );
  }
  getPdf(id: string): Observable<any> {
    return this.http.get(
      `${environment.baseUrl}/${environment.potvrdaPdf}/${id}`,
      { responseType: 'arraybuffer' }
    );
  }
  getXHtml(id: string): Observable<any> {
    return this.http.get(
      `${environment.baseUrl}/${environment.potvrdaXhtml}/${id}`,
      { responseType: 'arraybuffer' }
    );
  }

  getXmlByEmail(email: string): Observable<string> {
    const headers = new HttpHeaders({
      Accept: 'application/xml',
    });
    return this.http.get(
      `${environment.baseUrl}/potvrda/getAllS/${email}`,
      { headers: headers, responseType: 'text' }
    );
  }
}
