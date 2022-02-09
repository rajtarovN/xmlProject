import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { EvidencijaVakcinacije } from 'src/modules/zradnik/models/evidencija-vakcinacije';
import { SaglasnostGradjanaElement } from 'src/modules/zradnik/models/saglasnost-gradjana-element';

@Injectable({
  providedIn: 'root'
})
export class SaglasnostService {
  constructor(private http: HttpClient, private route: Router) {}

  searchTermine(datumTermina: Date, imePrezime: string): Observable<string> {
    const headers = new HttpHeaders({
      Accept: 'application/xml',
    });
    return this.http.get(
      `${environment.baseUrl}/${environment.searchTermine}/${imePrezime}/${datumTermina}`,
      { headers: headers,
        responseType: "text" }
    );
  }

  getEvidentiranevakcine(email: string): Observable<string>{
    const headers = new HttpHeaders({
      Accept: 'application/xml',
    });
    return this.http.get(
      `${environment.baseUrl}/${environment.getEvidentiraneVakcine}/${email}`,
      { headers: headers,
        responseType: "text" }
    );
  }

  saveEvidenciju(evidencija: EvidencijaVakcinacije, brojSaglasnosti: string): Observable<string> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/xml',
    });
    return this.http.post(
      `${environment.baseUrl}/${environment.saveEvidenciju}/${brojSaglasnosti}`,
      evidencija,
      { headers: headers,
        responseType: "text", }
    );
  }
}
