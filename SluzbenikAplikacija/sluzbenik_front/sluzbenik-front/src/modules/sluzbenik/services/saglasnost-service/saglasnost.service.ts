import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
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
}
