import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ZahtevService {

  constructor(private http: HttpClient, private route: Router) {}

  getZahteve(searchTerm: string): Observable<string>{
    const headers = new HttpHeaders({
      Accept: 'application/xml',
    });
    return this.http.get(
      `${environment.baseUrl}/${environment.getZahteve}/${searchTerm}`,
      { headers: headers,
        responseType: "text" }
    );
  }

  odbijZahtev(idZahteva:string, razlog: string): Observable<string>{
    const headers = new HttpHeaders({
      Accept: 'application/xml',
    });
    return this.http.get(
      `${environment.baseUrl}/${environment.odbijZahtev}/${idZahteva}/${razlog}`,
      { headers: headers,
        responseType: "text" }
    );
  }

  odobriZahtev(idZahteva:string): Observable<string>{
    const headers = new HttpHeaders({
      Accept: 'application/xml',
    });
    return this.http.get(
      `${environment.baseUrl}/${environment.odobriZahtev}/${idZahteva}`,
      { headers: headers,
        responseType: "text" }
    );
  }
}
